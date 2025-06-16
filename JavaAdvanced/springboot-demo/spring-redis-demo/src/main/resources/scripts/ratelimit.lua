-- KEYS[1]: 限流的 key (例如: ratelimit:user:123)
-- ARGV[1]: 时间窗口（秒）
-- ARGV[2]: 窗口内的最大请求数

local current_requests = tonumber(redis.call('get', KEYS[1]) or "0")

if current_requests < tonumber(ARGV[2]) then
    -- 未达到阈值，计数器加1
    local new_val = redis.call('incr', KEYS[1])
    -- 如果是第一次设置，需要设置过期时间
    if new_val == 1 then
        redis.call('expire', KEYS[1], ARGV[1])
    end
    return 1 -- 允许访问
else
    -- 已达到阈值
    return 0 -- 拒绝访问
end