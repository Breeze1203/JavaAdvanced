-- KEYS[1]: 锁的 key
-- ARGV[1]: 锁的 value (通常是唯一的请求ID或线程ID)
-- ARGV[2]: 锁的过期时间（毫秒）

-- 尝试获取锁，使用 set 命令的 NX 和 PX 选项
-- 如果 key 不存在(NX)，则设置 key 和 value，并设置过期时间(PX)
local result = redis.call('set', KEYS[1], ARGV[1], 'NX', 'PX', ARGV[2])

if result then
    return 1
else
    return 0
end