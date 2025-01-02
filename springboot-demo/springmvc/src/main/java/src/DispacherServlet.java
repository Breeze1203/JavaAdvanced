package src;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
/**
 * @ClassName DispacherServlet
 * @Author pt
 * @Description
 * @Date 2025/1/2 15:18
 **/
public class DispacherServlet extends HttpServlet {

    // 定义一个 Map 容器，存储映射关系
    private static Map<String, InvocationHandler> HandlerMap;

    @Override
    public void init(){
        System.out.println("项目启动了.....");
        // 指定要扫描的包路径（原本是从xml文件中读取的）
        String packagePath = "src";
        // 在指定的包路径下扫描带有@Controller注解的类
        Set<Class<?>> classSet = ClassUtil.
                scanPackageByAnnotation(packagePath, Controller.class);
        System.out.println("扫描到类的数量为:" + classSet.size());
        // 创建一个HandlerMapping并调用urlMapping()方法
        HandlerMapping handlerMapping = new HandlerMapping();
        HandlerMap = handlerMapping.urlMapping(classSet);
        // 最终获取到一个带有所有映射关系的 Map 集合
        System.out.println("HandlerMap的长度：" + HandlerMap.size());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 获取客户端的请求路径
        StringBuffer requestURL = req.getRequestURL();
        System.out.println("客户端请求路径：" + requestURL);
        // 判断请求路径中是否包含项目名，包含的话使用空字符替换掉
        String path = new String(requestURL).replace("http://" +
                req.getServerName() + ":" + req.getServerPort(), "");
        System.out.println("处理后的客户端请求路径：" + path);
        // 根据处理好的 path 作为条件去map中查找对应的方法
        InvocationHandler handler = HandlerMap.get(path);
        // 获取到对应的类实例对象和Java方法
        Object object = handler.getObject();
        Method method = handler.getMethod();
        // 判断该方法上是否添加了@ResponseBody注解：
        //      true：直接返回数据  false：跳转页面
        boolean f = method.isAnnotationPresent(ResponseBody.class);
        System.out.println("是否添加了@ResponseBody注解：" + f);
        // 如果方法上存在@ResponseBody注解
        if (f){
            try {
                // 通过反射的方式调用方法并执行
                Object invoke = method.invoke(object);
                // 将结果通过Response直接写回给客户端
                resp.getWriter().print(invoke.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else{
            // 获取客户端的请求路径作为返回时的前路径
            String URL = "http://" + req.getServerName() + ":" +
                    req.getServerPort() + "/" + req.getContextPath();
            System.out.println("URL:" + URL);
            // 自定义的前后缀（原本也是在xml中读取）
            String prefix = "";
            String suffix = ".jsp";
            try {
                // 通过反射机制，执行对应的Java方法
                Object invoke = method.invoke(object);
                if(invoke instanceof ModelAndView){
                    // 如果是返回的ModelAndView对象，这里做额外处理....
                } else{
                    // 获取Java方法执行之后的返回结果
                    String str = (String)invoke;
                    // 如果指定了跳转方法为 forward: 转发
                    if(str.contains("forward:")){
                        System.out.println("以转发的方式跳转页面...");
                        req.getRequestDispatcher("index.jsp").forward(req,resp);
                    }
                    // 如果指定了跳转方法为 redirect: 重定向
                    if(str.contains("redirect:")){
                        System.out.println("以重定向的方式跳转页面...");
                        resp.sendRedirect(URL + prefix +
                                str.replace("redirect:","") + suffix);
                    }
                    // 如果没有指定，则默认使用转发的方式跳转页面
                    if(!str.contains("forward:") && !str.contains("redirect:")){
                        resp.sendRedirect(URL + prefix + str + suffix);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

