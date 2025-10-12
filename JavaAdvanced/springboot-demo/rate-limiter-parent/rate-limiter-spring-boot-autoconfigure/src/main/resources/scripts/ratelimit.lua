-- @description  令牌桶限流算法
-- 限流的key
local key=KEYS[1]
-- ARGV参数
-- 桶的容量 (rate limit)
local capacity = tonumber(ARGV[1])

-- 每毫秒生成的令牌数 (refill rate)
local tokens_per_ms = tonumber(ARGV[2])
-- 当前时间戳 (毫秒)
local current_ts = tonumber(ARGV[3])
-- 本次请求需要消费的令牌数 (通常是1)
local requested_tokens = tonumber(ARGV[4])
-- key 的过期时间 (秒)，防止冷数据占满内存
local expire_seconds = tonumber(ARGV[5])

-- 获取桶的当前状态 (剩余令牌数 和 上次刷新时间戳)
local bucket_state = redis.call('HMGET', key, 'tokens', 'last_ts')
local last_tokens = tonumber(bucket_state[1])
local last_ts = tonumber(bucket_state[2])

-- 如果桶不存在，是第一次请求，进行初始化
if last_tokens == nil then
    last_tokens = capacity
    last_ts = current_ts
end

-- 计算自上次请求以来经过的时间
local elapsed_ms = math.max(0, current_ts - last_ts)
-- 计算需要补充的令牌数
local generated_tokens = elapsed_ms * tokens_per_ms
-- 补充令牌，但不能超过桶的容量
local filled_tokens = math.min(capacity, last_tokens + generated_tokens)

local allowed = 0
-- 如果当前令牌足够本次消费
if filled_tokens >= requested_tokens then
    -- 消费令牌
    local new_tokens = filled_tokens - requested_tokens
    -- 更新桶的状态
    redis.call('HMSET', key, 'tokens', new_tokens, 'last_ts', current_ts)
    -- 设置/刷新过期时间
    redis.call('EXPIRE', key, expire_seconds)
    allowed = 1
else
    -- 如果令牌不足，不消费，仅更新状态（补充令牌但不扣减）
    redis.call('HMSET', key, 'tokens', filled_tokens, 'last_ts', current_ts)
    redis.call('EXPIRE', key, expire_seconds)
    allowed = 0
end

return allowed
