package src;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName HandlerMapping
 * @Author pt
 * @Description
 * @Date 2025/1/2 14:59
 **/
public class HandlerMapping {
    public Map<String,InvocationHandler> urlMapping(Set<Class<?>> classSet) {
        // 初始化一个map集合，用于存放映射关系
        HashMap<String, InvocationHandler> handelMap = new HashMap<>();
        // 变了Controller集合
        for (Class<?> aClass : classSet) {
            // 获取类上@RequestMapping注解的值
            RequestMapping annotation = aClass.getAnnotation(RequestMapping.class);
            System.out.println("类的请求路径:" + annotation.value());
            String classReqPath=annotation.value();
            // 获取类的所有方法
            Method[] methods = aClass.getMethods();
            System.out.println("类中方法数量为：" + methods.length);
            // 开始遍历这个类中的所有方法
            if (methods.length != 0) {
                for (Method method : methods) {
                    // 判断每个方法上是否带有@RequestMapping注解
                    boolean flag = method.isAnnotationPresent(RequestMapping.class);
                    if (flag) {
                        // 获取方法上@RequestMapping注解的值
                        String methodReqPath = method.getAnnotation(RequestMapping.class).value();
                        // 判断得到的值是否为空，不为空则获取对应的值
                        String reqPath = methodReqPath == null ||
                                methodReqPath.equals("") ? "" : methodReqPath;
                        System.out.println("方法上的请求路径:" + reqPath);
                        // 将得到的值封装成 InvocationHandler 对象
                        try {
                            // 放入一个当前类的实例对象，用于执行后面的类方法
                            InvocationHandler invocationHandler = new
                                    InvocationHandler(aClass.newInstance(), method);
                            // 使用 类的请求路径 + 方法的请求路径 作为Key
                            handelMap.put(classReqPath + reqPath,
                                    invocationHandler);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return handelMap;
    }

}
