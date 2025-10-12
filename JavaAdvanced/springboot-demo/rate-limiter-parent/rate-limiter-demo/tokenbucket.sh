#!/bin/bash
echo "--- 令牌桶测试开始 ---"
echo "等待5秒，让令牌桶攒满..."
sleep 5

echo "开始发送12个并发请求 (Burst!)..."
for i in {1..12}
do
   # 使用 & 让所有curl命令在后台并发执行，模拟瞬时突发
   curl --noproxy localhost,127.0.0.1 http://localhost:8080/token
   printf "\n"
done

# 等待所有后台任务完成
wait
echo "--- 令牌桶测试结束 ---"