-- KEYS[1]: 锁的 key
-- ARGV[1]: 锁的 value (用于验证是否是自己的锁)

-- 先获取锁的 value
local lockValue = redis.call('get', KEYS[1])
-- 检查锁是否存在，并且 value 与期望的 value 是否一致
if lockValue == ARGV[1] then
    -- 如果是自己的锁，则删除，释放锁
    return redis.call('del', KEYS[1])
else
    -- 不是自己的锁，或者锁已不存在，不做任何操作
    return 0
end