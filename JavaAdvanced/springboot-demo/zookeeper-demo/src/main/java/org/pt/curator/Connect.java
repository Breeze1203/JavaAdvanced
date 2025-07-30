package org.pt.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @ClassName Connect
 * @Author pt
 * @Description
 * @Date 2025/7/30 21:39
 **/
public class Connect {
    public static final String ZK_URL = "localhost:2181";

    public static void main(String[] args) throws Exception {
        CuratorFramework client = getClient();
        client.start();
        System.out.println("Curator客户端已启动。");
        String crudPath = "/curator-node";
        try {
            // Create: creatingParentsIfNeeded()能自动创建不存在的父节点
            client.create().creatingParentsIfNeeded().forPath(crudPath, "initial data".getBytes());
            System.out.println("创建节点成功: " + crudPath);

            // Read:
            byte[] data = client.getData().forPath(crudPath);
            System.out.println("读取数据: " + new String(data));
            // Update:
            client.setData().forPath(crudPath, "updated data".getBytes());
            System.out.println("更新数据成功。");
            // Read again:
            byte[] updatedData = client.getData().forPath(crudPath);
            System.out.println("再次读取数据: " + new String(updatedData));
            // deletingChildrenIfNeeded能删除所有子节点
            client.delete().deletingChildrenIfNeeded().forPath(crudPath);
            System.out.println("删除节点成功: " + crudPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CuratorFramework getClient(){
        return CuratorFrameworkFactory.builder()
                .connectString(ZK_URL)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
    }
}
