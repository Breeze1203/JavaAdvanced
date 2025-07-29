package org.pt.nativeapi;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @ClassName WatcherExample
 * @Author pt
 * @Description
 * @Date 2025/7/29 21:17
 **/
public class WatcherExample {
    public static void run(ZooKeeper zooKeeper) throws Exception {
        String path = "/watched-node";
        zooKeeper.create(path, "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        // 定义一个可移除的Watcher
        Watcher removableWatcher = event -> {
            System.out.println("RemovableWatcher 触发了! Event: " + event);
        };
        // 设置Watcher
        System.out.println("1. 在节点 " + path + " 上设置Watcher...");
        zooKeeper.exists(path, removableWatcher);
        // 在事件触发前，主动移除它
        System.out.println("2. 主动移除刚刚设置的Watcher...");
        // WatcherType可以是 ANY, CHILDREN, DATA
        zooKeeper.removeWatches(path, removableWatcher, Watcher.WatcherType.Any, false);
        System.out.println("Watcher已被移除。");
        // 修改数据，看Watcher是否还会触发
        System.out.println("3. 修改节点数据...");
        zooKeeper.setData(path, "new data".getBytes(), 0);
        System.out.println("数据修改完毕，由于Watcher已被移除，控制台不应有 'RemovableWatcher 触发了' 的输出。");
        Thread.sleep(1000); // 等待片刻以确认
        zooKeeper.delete(path, -1);
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
