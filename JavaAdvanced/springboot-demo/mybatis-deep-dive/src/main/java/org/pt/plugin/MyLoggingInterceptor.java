package org.pt.plugin;

/**
 * @ClassName MyLoggingInterceptor
 * @Author pt
 * @Description
 * @Date 2025/6/28 21:30
 **/

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

// @Intercepts 注解表明这是一个拦截器
// @Signature 注解定义了要拦截哪个对象的哪个方法
@Intercepts({
        @Signature(
                type = Executor.class, // 拦截 Executor 组件
                method = "update",      // 拦截 update 方法（增、删、改都由它执行）
                args = {MappedStatement.class, Object.class} // 方法的参数列表
        ),
        @Signature(
                type = Executor.class, // 同样拦截 Executor 组件
                method = "query",       // 拦截 query 方法（查询由它执行）
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
        )
})
public class MyLoggingInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // invocation 对象包含了被拦截方法的所有信息
        Object target = invocation.getTarget(); // 被拦截的对象，这里是 Executor
        Object[] args = invocation.getArgs();   // 被拦截方法的参数
        MappedStatement ms = (MappedStatement) args[0];
        System.out.println("=================================================");
        System.out.println("【MY-PLUGIN】插件触发：准备执行SQL");
        System.out.println("【MY-PLUGIN】SQL ID: " + ms.getId());
        System.out.println("【MY-PLUGIN】SQL CommandType: " + ms.getSqlCommandType());

        long start = System.currentTimeMillis();
        // 调用 invocation.proceed() 来执行原始方法，这是责任链模式的体现
        Object result = invocation.proceed();
        long end = System.currentTimeMillis();

        System.out.println("【MY-PLUGIN】SQL执行完毕，耗时: " + (end - start) + "ms");
        System.out.println("=================================================");

        return result;
    }

    @Override
    public Object plugin(Object target) {
        // MyBatis 在创建被拦截对象时，会调用这个方法来生成代理对象
        System.out.println("---------------------");
        System.out.println("开始生成代理对象");
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 可以在这里接收插件的配置属性
    }
}
