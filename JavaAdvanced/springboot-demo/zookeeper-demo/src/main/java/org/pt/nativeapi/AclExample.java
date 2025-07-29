package org.pt.nativeapi;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AclExample
 * @Author pt
 * @Description
 * @Date 2025/7/29 21:26
 **/
import java.util.concurrent.CountDownLatch;

public class AclExample {

    private static final String ZK_URL = "localhost:2181";
    private static final int SESSION_TIMEOUT = 5000;
    private static final String PROTECTED_PATH = "/protected-node-a";

    public static void main(String[] args) throws Exception {
        // --- Use an "admin" client to create the node and set ACLs ---
        ZooKeeper adminClient = connectZooKeeper();
        // Authenticate immediately after connecting, before any other operation.
        adminClient.addAuthInfo("digest", "admin:admin_pass".getBytes());
        System.out.println("【Admin客户端】: 已添加 'admin:admin_pass' 认证信息。");

        // Now that the client is authenticated, it can check for and clean up the old node.
        if (adminClient.exists(PROTECTED_PATH, false) != null) {
            System.out.println("【Admin客户端】: 发现旧节点，正在删除...");
            adminClient.delete(PROTECTED_PATH, -1);
            System.out.println("【Admin客户端】: 旧节点已删除。");
        }

        System.out.println("\n【Admin客户端】: 创建一个新节点 " + PROTECTED_PATH);
        adminClient.create(PROTECTED_PATH, "secret-data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println("\n--- 步骤 1: 设置包含 'admin' 和 'user' 的新ACL ---");
        ACL adminAcl = new ACL(ZooDefs.Perms.ALL, new Id("digest", DigestAuthenticationProvider.generateDigest("admin:admin_pass")));
        ACL userAcl = new ACL(ZooDefs.Perms.ALL, new Id("digest", DigestAuthenticationProvider.generateDigest("user:user_pass")));

        List<ACL> newAclList = new ArrayList<>();
        newAclList.add(adminAcl);
        newAclList.add(userAcl);

        adminClient.setACL(PROTECTED_PATH, newAclList, -1);
        System.out.println("新ACL已设置: " + newAclList);

        System.out.println("\n--- 步骤 2: 验证新ACL ---");
        List<ACL> confirmedAcls = adminClient.getACL(PROTECTED_PATH, new Stat());
        System.out.println("ACL验证成功: " + confirmedAcls);
        adminClient.close();
        // --- 2. Verify an unauthorized "guest" client ---
        System.out.println("\n\n【Guest客户端】: 尝试访问受保护的节点...");
        try (ZooKeeper guestClient = connectZooKeeper()) {
            guestClient.getData(PROTECTED_PATH, false, null);
        } catch (KeeperException.NoAuthException e) {
            System.out.println("操作成功失败！Guest客户端访问被拒绝，抛出 NoAuthException，符合预期！");
        }

        // --- 3. Verify an authorized "user" client ---
        System.out.println("\n\n【User客户端】: 添加认证信息后尝试访问...");
        try (ZooKeeper userClient = connectZooKeeper()) {
            userClient.addAuthInfo("digest", "user:user_pass".getBytes());
            byte[] data = userClient.getData(PROTECTED_PATH, false, null);
            System.out.println("操作成功！User客户端成功读取数据: " + new String(data));
        }
    }

    private static ZooKeeper connectZooKeeper() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        ZooKeeper zk = new ZooKeeper(ZK_URL, SESSION_TIMEOUT, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                latch.countDown();
            }
        });
        latch.await();
        return zk;
    }
}