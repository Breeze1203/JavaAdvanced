#!/bin/bash
echo "--- 漏桶测试开始 ---"
echo "等待5秒，确保漏桶是空的..."
sleep 5

echo "开始发送12个并发请求 (Burst!)..."
for i in {1..12}
do
   # 同样使用并发请求
   curl --noproxy localhost,127.0.0.1 http://localhost:8080/leaky
   printf "\n"
done

wait
echo "--- 漏桶测试结束 ---"