-- scripts/stock_deduct.lua
-- KEYS[1]: 商品库存的 key (例如: stock:product:1001)
-- ARGV[1]: 本次要扣减的数量 (通常是 1)

local stock = tonumber(redis.call('get', KEYS[1]))
-- 检查库存是否存在且大于0
if stock and stock > 0 then
    -- 检查库存是否足够本次扣减
    local requested_amount = tonumber(ARGV[1])
    if stock >= requested_amount then
        -- 库存充足，执行扣减操作
        return redis.call('decrby', KEYS[1], requested_amount)
    else
        -- 库存不足
        return -1
    end
else
    -- 库存不存在或已售罄
    return -2
end