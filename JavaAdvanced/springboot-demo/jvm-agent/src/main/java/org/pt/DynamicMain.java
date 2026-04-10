package org.pt;

import com.sun.tools.attach.VirtualMachine;

/**
 * @ClassName DynamicMain
 * @Author pt
 * @Description
 * @Date 2026/4/10 18:36
 **/
public class DynamicMain {
    public static void main(String[] args) throws InterruptedException {
        long pid = ProcessHandle.current().pid();
        System.out.println("=====================================");
        System.out.println("💉 业务系统 (目标进程) 启动成功！");
        System.out.println("🎯 请记下当前的 PID: " + pid);
        System.out.println("=====================================");
        System.out.println("请保持此窗口不要关闭，去另一个终端窗口执行 Attacher...\n");
        int count = 1;
        while (true) {
            System.out.println("[业务服务] 正在处理第 " + count + " 个请求...");
            count++;
            // 暂停 3 秒，防止日志刷得太快看不清
            Thread.sleep(3000);
        }
    }
}
