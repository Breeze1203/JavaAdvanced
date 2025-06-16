--[[
  基于 Redis 有序集合实现的滑动窗口限流器。

  KEYS[1]: 需要被限流的资源的唯一键 (例如: "rate_limit:user:123")。

  ARGV[1]: 时间窗口的大小，单位为秒 (例如: 60)。
  ARGV[2]: 在时间窗口内允许的最大请求数 (例如: 100)。
--]]
-- 从 Redis 服务器获取当前时间。TIME 命令返回一个表: {秒, 微秒}。
local now = redis.call('TIME')
local current_seconds = tonumber(now[1])
local current_microseconds = tonumber(now[2])

-- 将秒和微秒组合成一个高精度的时间戳分数。
-- 这个值也将作为本次请求的唯一成员。
local current_timestamp_score = current_seconds * 1000000 + current_microseconds
-- 从参数中获取窗口大小和最大请求数。
local window_size_seconds = tonumber(ARGV[1])
local max_requests = tonumber(ARGV[2])
-- 计算出有效窗口的最小分数（即窗口的起始时间）。
-- 任何分数小于此值的请求都被认为是“过期的”，将会被移除。
local window_start_score = current_timestamp_score - (window_size_seconds * 1000000)
-- 1. 清理旧记录: 原子性地从有序集合中移除所有过期的成员。
--    这些都是在当前滑动窗口开始之前发生的请求。
redis.call('ZREMRANGEBYSCORE', KEYS[1], '-inf', window_start_score)
-- 2. 统计数量: 获取当前窗口内的请求总数。
local request_count = redis.call('ZCARD', KEYS[1])

-- 3. 检查阈值: 将当前计数与允许的最大请求数进行比较。
if request_count < max_requests then
  -- 未达到限流阈值。
  -- 4. 添加新请求: 将当前请求的时间戳添加到有序集合中。
  --    我们使用高精度的时间戳同时作为分数(score)和成员(member)。
  redis.call('ZADD', KEYS[1], current_timestamp_score, current_timestamp_score)

  -- 5. 设置过期时间: 给这个 Key 本身设置一个过期时间。这是一个良好实践，
  --    用于自动清理非活跃用户的键，以节省内存。
  --    过期时间设置为窗口大小。
  redis.call('EXPIRE', KEYS[1], window_size_seconds)

  -- 返回 1 表示请求被允许。
  return 1
else
  -- 已经达到限流阈值。
  -- 返回 0 表示请求应该被拒绝。
  return 0
end