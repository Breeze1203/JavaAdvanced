package org.pt;

import java.lang.instrument.Instrumentation;

/**
 * @ClassName MyDynamicAgent
 * @Author pt
 * @Description
 * @Date 2026/4/10 18:13
 **/
public class MyDynamicAgent {
    // 动态加载的入口
    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("[Dynamic Agent] 成功 Attach 到运行中的 JVM！");
        System.out.println("[Dynamic Agent] 接收到的动态参数: " + agentArgs);
        System.out.println("[Dynamic Agent] 当前 JVM 中已加载的类数量: " + inst.getAllLoadedClasses().length);

        // 在实际生产中，这里会结合 retransformClasses 重新触发类的加载，
        // 并使用 Javassist 动态替换方法的字节码。
    }
}
