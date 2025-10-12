-- 限流的 Redis Key (ZSET的名称)
local key = KEYS[1]

-- ARGV 参数
-- 阈值 (limit)
local limit = tonumber(ARGV[1])
-- 窗口大小 (毫秒)
local window_size_ms = tonumber(ARGV[2])
-- 当前时间戳 (毫秒)
local current_ts = tonumber(ARGV[3])
-- 本次请求的唯一标识 (member, e.g., "timestamp-uuid")
local member = ARGV[4]

-- 计算窗口的起始时间点
local window_start_ts = current_ts - window_size_ms

--  清理窗口之外的过期成员 (核心步骤)
-- 移除 ZSET 中所有分数(时间戳)在 0 到 window_start_ts 之间的成员
redis.call('ZREMRANGEBYSCORE', key, 0, window_start_ts)

-- 获取当前窗口内的请求总数
local current_count = redis.call('ZCARD', key)

local allowed = 0
-- 判断是否超过阈值
if current_count < limit then
    -- 未超过阈值，允许请求
    -- 将当前请求添加到 ZSET 中，score 和 member 都与时间相关
    redis.call('ZADD', key, current_ts, member)
    allowed = 1
else
    -- 已达到阈值，拒绝请求
    allowed = 0
end

-- 设置一个过期时间，防止冷数据(整个ZSET)永久占用内存
-- 过期时间应该略大于窗口时间
redis.call('EXPIRE', key, math.ceil(window_size_ms / 1000) + 1)

return allowed