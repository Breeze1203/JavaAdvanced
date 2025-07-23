package org.pt;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName NativeZkApiExample
 * @Author pt
 * @Description
 * @Date 2025/7/16 21:33
 **/
public class NativeZkApiExample {

    private static final String ZK_ADDRESS = "localhost:2181";
    private static final int SESSION_TIMEOUT = 5000;
    private static final String ZK_PATH = "/native_zk_test";

    private ZooKeeper zooKeeper;

    private final CountDownLatch connectionLatch = new CountDownLatch(1);

    /**
     * 连接到 ZooKeeper
     */
    public void connect() throws IOException, InterruptedException {

        zooKeeper = new ZooKeeper(ZK_ADDRESS, SESSION_TIMEOUT, event -> {
            // 判断是否是连接成功事件
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                System.out.println("成功连接到 ZooKeeper 服务器！");
                // 连接成功，计数器减一，唤醒等待的主线程
                connectionLatch.countDown();
            }
        });

        System.out.println("正在尝试连接 ZooKeeper...");
        // 主线程在此等待，直到连接成功（latch 变为0）
        connectionLatch.await();
    }

    /**
     * 关闭连接
     */
    public void close() throws InterruptedException {
        if (zooKeeper != null) {
            zooKeeper.close();
            System.out.println("已关闭与 ZooKeeper 的连接。");
        }
    }

    /**
     * 节点数据变化和删除事件的监听器
     * 这是理解原生API核心逻辑的关键部分！
     */
    private final Watcher dataWatcher = event -> {
        // 判断事件类型
        if (event.getType() == Watcher.Event.EventType.NodeDataChanged) {
            System.out.println(">>> 监听到节点 [" + event.getPath() + "] 数据发生变化！");
            // 关键：Watcher 是一次性的，触发后必须重新注册才能继续监听
            try {
                // 重新获取数据并再次注册这个 watcher
                byte[] updatedData = zooKeeper.getData(event.getPath(), this.dataWatcher, null);
                System.out.println(">>> 重新获取到的新数据是: " + new String(updatedData));
            } catch (Exception e) {
                // 如果在重新注册时节点已被删除，这里会抛出异常
            }
        } else if (event.getType() == Watcher.Event.EventType.NodeDeleted) {
            System.out.println(">>> 监听到节点 [" + event.getPath() + "] 已被删除！");
        }
    };

    /**
     * 演示完整的业务逻辑
     */
    public void runDemo() throws KeeperException, InterruptedException {
        // 1. 创建节点
        String initialData = "initial data";
        // CreateMode.PERSISTENT 表示创建一个持久节点
        // ZooDefs.Ids.OPEN_ACL_UNSAFE 表示不进行任何权限控制
        String createdPath = zooKeeper.create(ZK_PATH, initialData.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("1. 创建节点成功: " + createdPath + " | 数据: " + initialData);

        // 2. 获取数据并注册监听器
        // 传入我们定义的 dataWatcher，这样当 ZK_PATH 的数据变化或被删除时，它就会被触发
        Stat stat = new Stat(); // 用于接收节点元数据
        byte[] data = zooKeeper.getData(ZK_PATH, dataWatcher, stat);
        System.out.println("2. 获取数据成功: " + new String(data) + " | 版本号: " + stat.getVersion());

        // 3. 更新数据 (这将触发 dataWatcher)
        String updatedData = "updated data";
        // -1 表示我们期望更新最新版本的节点
        zooKeeper.setData(ZK_PATH, updatedData.getBytes(), -1);
        System.out.println("3. 更新数据成功为: " + updatedData);
        Thread.sleep(1000); // 等待 Watcher 异步回调

        // 4. 再次更新数据 (这将再次触发 dataWatcher，因为我们在回调里重新注册了)
        String finalData = "final data";
        zooKeeper.setData(ZK_PATH, finalData.getBytes(), -1);
        System.out.println("4. 再次更新数据为: " + finalData);
        Thread.sleep(1000); // 等待 Watcher 异步回调

        // 5. 删除节点 (这也将触发 dataWatcher)
        zooKeeper.delete(ZK_PATH, -1);
        System.out.println("5. 删除节点成功: " + ZK_PATH);
        Thread.sleep(1000); // 等待 Watcher 异步回调
    }


    public static void main(String[] args) {
        NativeZkApiExample example = new NativeZkApiExample();
        try {
            example.connect();
            example.runDemo();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                example.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
