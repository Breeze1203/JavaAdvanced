package src;

import java.lang.reflect.Method;

/**
 * @ClassName InvocationHandler
 * @Author pt
 * @Description
 * @Date 2025/1/2 14:57
 **/
public class InvocationHandler {
    // 这里会存放方法对应的对象实例
    private Object object;
    // 这里会存放对应的Java方法
    private Method method;

    // 构造方法：无参和全参构造
    public InvocationHandler(){}
    public InvocationHandler(Object object, Method method) {
        this.object = object;
        this.method = method;
    }

    // Get and Set方法
    public Object getObject() {
        return object;
    }
    public void setObject(Object object) {
        this.object = object;
    }
    public Method getMethod() {
        return method;
    }
    public void setMethod(Method method) {
        this.method = method;
    }

    // 这里重写了toString()方法
    @Override
    public String toString() {
        return "InvocationHandler{" +
                "object=" + object +
                ", method=" + method +
                '}';
    }
}

