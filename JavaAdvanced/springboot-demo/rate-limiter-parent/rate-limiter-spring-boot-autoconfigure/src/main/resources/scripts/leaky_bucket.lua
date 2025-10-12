-- 限流的 Redis Key
local key = KEYS[1]

-- ARGV 参数
-- 漏水速率 (每秒漏出多少个请求)
local rate = tonumber(ARGV[1])
-- 桶的容量 (capacity)
local capacity = tonumber(ARGV[2])
-- 当前时间戳 (秒，浮点数)
local now = tonumber(ARGV[3])

-- 获取漏桶状态：上次漏水时间(last_ts), 桶中剩余水量(water)
local state = redis.call('HMGET', key, 'last_ts', 'water')
local last_ts = tonumber(state[1]) or now
local water = tonumber(state[2]) or 0

-- 根据时间差，计算漏掉的水
local leaked_water = (now - last_ts) * rate
water = math.max(0, water - leaked_water)

-- 更新上次漏水时间为当前时间
last_ts = now

local allowed = 0
--  决策：检查加水后是否会溢出
if (water + 1) <= capacity then
    -- 未溢出，允许请求，并增加水量
    water = water + 1
    allowed = 1
end

--  无论如何，都更新状态
redis.call('HMSET', key, 'last_ts', last_ts, 'water', water)
redis.call('EXPIRE', key, math.ceil((capacity / rate) * 2))

return allowed