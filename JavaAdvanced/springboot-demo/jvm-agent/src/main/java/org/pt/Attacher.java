package org.pt;

import com.sun.tools.attach.VirtualMachine;

/**
 * @ClassName Attacher
 * @Author pt
 * @Description
 * @Date 2026/4/10 18:47
 **/
public class Attacher {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("用法: java Attacher <PID> <Agent_JAR_Path>");
            return;
        }

        String pid = args[0];
        String agentPath = args[1];

        try {
            System.out.println("正在连接到 PID: " + pid);
            // 附着到目标 JVM
            VirtualMachine vm = VirtualMachine.attach(pid);

            // 加载 Agent
            vm.loadAgent(agentPath, "HelloFromAttacher");

            // 断开连接
            vm.detach();
            System.out.println("Agent 注入成功！请查看目标 JVM 的控制台输出。");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
