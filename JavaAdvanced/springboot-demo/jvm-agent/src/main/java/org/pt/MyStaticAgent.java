package org.pt;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * @ClassName MyStaticAgent
 * @Author pt
 * @Description
 * @Date 2026/4/10 18:11
 **/
public class MyStaticAgent {
    // 静态加载的入口
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("[Static Agent] 成功拦截 JVM 启动！传入的参数是: " + agentArgs);

        // 注册一个类转换器
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                // 过滤：只打印我们关心的类（例如以 Target 开头的类）
                if (className != null && className.startsWith("Target")) {
                    System.out.println("[Static Agent] 正在加载类: " + className);
                }
                return classfileBuffer; // 返回原字节码，不做修改
            }
        });
    }
}
