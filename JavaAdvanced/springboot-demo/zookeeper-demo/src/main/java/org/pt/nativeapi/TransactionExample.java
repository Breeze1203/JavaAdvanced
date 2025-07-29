package org.pt.nativeapi;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName TransactionExample
 * @Author pt
 * @Description
 * @Date 2025/7/29 21:19
 **/
public class TransactionExample {
    public static void run(ZooKeeper zooKeeper) throws Exception {
        System.out.println("开始执行事务...");

        Transaction tx = zooKeeper.transaction();
        tx.create("/tx-node", "tx-data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        tx.setData("/tx-node", "updated-tx-data".getBytes(), 0); // 基于上一步创建的版本0
        tx.delete("/tx-node", 1); // 基于上一步更新的版本1
        // 提交事务
        List<OpResult> results = tx.commit();
        System.out.println("事务提交完毕，结果数量: " + results.size());
        for (OpResult result : results) {
            // 检查每个操作的结果
            System.out.println("操作结果: " + result.getClass().getSimpleName());
        }
    }

    public static void main(String[] args) throws IOException {
        ZooKeeper zooKeeper = ConnectionExample.getExample();
        try {
            run(zooKeeper);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
