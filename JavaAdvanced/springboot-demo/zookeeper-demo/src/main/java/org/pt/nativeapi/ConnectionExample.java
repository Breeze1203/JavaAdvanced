package org.pt.nativeapi;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName ConnectionExample
 * @Author pt
 * @Description
 * @Date 2025/7/29 21:06
 **/
public class ConnectionExample {
    public static void main(String[] args) throws InterruptedException, IOException {
        final CountDownLatch latch = new CountDownLatch(1);
        // 创建ZooKeeper客户端，连接是异步的
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                // 通过默认Watcher处理会话事件
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("会话建立成功！");
                    latch.countDown();
                } else if (event.getState() == Event.KeeperState.Expired) {
                    System.out.println("会话过期！");
                }
            }
        });
        System.out.println("客户端状态 (初始): " + zooKeeper.getState()); // 通常是 CONNECTING
        System.out.println("正在连接ZooKeeper服务器...");
        // 使用CountDownLatch阻塞主线程，直到连接成功
        latch.await();
        System.out.println("客户端状态 (连接后): " + zooKeeper.getState()); // CONNECTED
        System.out.println("会话ID: " + zooKeeper.getSessionId());
        zooKeeper.close();
        System.out.println("客户端已关闭。");
    }

    public static ZooKeeper getExample() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                // 通过默认Watcher处理会话事件
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("会话建立成功！");
                } else if (event.getState() == Event.KeeperState.Expired) {
                    System.out.println("会话过期！");
                }
            }
        });
        return zooKeeper;
    }
}
