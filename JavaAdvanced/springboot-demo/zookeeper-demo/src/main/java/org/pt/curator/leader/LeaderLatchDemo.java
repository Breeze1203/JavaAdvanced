package org.pt.curator.leader;

/**
 * @ClassName LeaderLatchDemo
 * @Author pt
 * @Description
 * @Date 2025/8/4 22:22
 **/
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LeaderLatchDemo {

    private static final String ZK_URL = "localhost:2181";
    private static final String ELECTION_PATH = "/demo/leader-election";
    private static final int PARTICIPANT_QTY = 5; // 5个候选人

    public static void main(String[] args) throws Exception {
        System.out.println("创建 " + PARTICIPANT_QTY + " 个候选人参与选举...");
        List<CuratorFramework> clients = new ArrayList<>();
        List<LeaderLatch> latches = new ArrayList<>();

        // 清理旧的选举节点，确保每次演示都是干净的环境
        try (CuratorFramework tempClient = CuratorFrameworkFactory.newClient(ZK_URL, new ExponentialBackoffRetry(1000, 3))) {
            tempClient.start();
            if (tempClient.checkExists().forPath(ELECTION_PATH) != null) {
                tempClient.delete().deletingChildrenIfNeeded().forPath(ELECTION_PATH);
                System.out.println("已清理旧的选举路径: " + ELECTION_PATH);
            }
        }

        for (int i = 0; i < PARTICIPANT_QTY; i++) {
            CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_URL, new ExponentialBackoffRetry(1000, 3));
            clients.add(client);
            client.start();

            final String participantId = "候选人-" + (i + 1);
            LeaderLatch latch = new LeaderLatch(client, ELECTION_PATH, participantId);

            latch.addListener(new LeaderLatchListener() {
                @Override
                public void isLeader() {
                    System.out.println(">>> [" + java.time.LocalTime.now() + "] " + participantId + " 通过监听器得知自己成为了领导者！");
                }

                @Override
                public void notLeader() {

                }
            });

            latches.add(latch);
            latch.start();
            System.out.println(participantId + " 已加入选举。");
        }

        try {
            System.out.println("\n--- 选举开始，等待第一个领导者产生 ---");
            while (latches.stream().noneMatch(LeaderLatch::hasLeadership)) {
                Thread.sleep(100);
            }

            LeaderLatch firstLeader = findLeader(latches);
            if (firstLeader != null) {
                System.out.println("选举完成，当前领导者是: " + firstLeader.getId() +
                        "，\n  对应的ZNode是: " + firstLeader.getOurPath());
            }

            System.out.println("\n当前领导者将工作5秒后，主动放弃领导权...");
            Thread.sleep(5000);

            if (firstLeader != null) {
                System.out.println(firstLeader.getId() + " 正在放弃领导权...");
                firstLeader.close();
                latches.remove(firstLeader);
            }

            System.out.println("\n--- 等待新的领导者产生 ---");
            while (latches.stream().noneMatch(LeaderLatch::hasLeadership)) {
                Thread.sleep(100);
            }

            LeaderLatch secondLeader = findLeader(latches);
            if (secondLeader != null) {
                System.out.println("新一轮选举完成，当前领导者是: " + secondLeader.getId() +
                        "，\n  对应的ZNode是: " + secondLeader.getOurPath());
            }

            System.out.println("\nDemo结束。");

        } finally {
            System.out.println("\n正在关闭所有候选人...");
            for (LeaderLatch latch : latches) {
                try {
                    latch.close();
                } catch (IOException e) {
                    // ignore
                }
            }
            for (CuratorFramework client : clients) {
                client.close();
            }
        }
    }

    private static LeaderLatch findLeader(List<LeaderLatch> latches) {
        return latches.stream()
                .filter(LeaderLatch::hasLeadership)
                .findFirst()
                .orElse(null);
    }
}