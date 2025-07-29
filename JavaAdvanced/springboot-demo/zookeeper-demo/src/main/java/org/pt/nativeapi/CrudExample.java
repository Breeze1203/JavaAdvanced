package org.pt.nativeapi;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * @ClassName CrudExample
 * @Author pt
 * @Description
 * @Date 2025/7/29 21:12
 **/
public class CrudExample {
    public static void run(ZooKeeper zooKeeper) throws Exception {
        String path = "/my-node";
        // 创建节点 (Create)
        System.out.println("1. 创建节点...");
        // 使用 Ids.OPEN_ACL_UNSAFE 表示无任何权限限制
        String createdPath = zooKeeper.create(path, "initial data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("成功创建节点: " + createdPath);

        // 检查节点是否存在 (Exists)
        System.out.println("\n2. 检查节点是否存在...");
        Stat stat = zooKeeper.exists(path, false);
        if (stat != null) {
            System.out.println("节点存在，版本号: " + stat.getVersion());
        }

        // 获取节点数据 (Read)
        System.out.println("\n3. 获取节点数据...");
        byte[] data = zooKeeper.getData(path, false, stat); // 传入stat对象，会被填充为最新状态
        System.out.println("节点数据: " + new String(data));
        System.out.println("当前数据版本号: " + stat.getVersion());

        // 更新节点数据 (Update)
        System.out.println("\n4. 更新节点数据...");
        int currentVersion = stat.getVersion();
        Stat updatedStat = zooKeeper.setData(path, "updated data".getBytes(), currentVersion);
        System.out.println("数据更新成功，最新版本号: " + updatedStat.getVersion());
        // 尝试用旧版本号更新，会失败
        try {
            zooKeeper.setData(path, "another update".getBytes(), currentVersion);
        } catch (KeeperException.BadVersionException e) {
            System.out.println("乐观锁生效：使用旧版本号更新失败！");
        }
        // 删除节点 (Delete)
        System.out.println("\n5. 删除节点...");
        int deleteVersion = updatedStat.getVersion();
        zooKeeper.delete(path, deleteVersion);
        System.out.println("节点 " + path + " 已删除。");
        // 再次检查
        Stat finalStat = zooKeeper.exists(path, false);
        System.out.println("删除后再次检查，节点是否存在: " + (finalStat != null));
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
