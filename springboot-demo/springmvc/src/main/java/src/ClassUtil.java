package src;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.Set;

/**
 * @ClassName ClassUtil
 * @Author pt
 * @Description
 * @Date 2025/1/2 15:24
 **/
public class ClassUtil {
    public static Set<Class<?>> scanPackageByAnnotation(String packagePath, Class annotation) {
        try {
            Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forPackage(packagePath))
                    .setScanners(new SubTypesScanner(false), new TypeAnnotationsScanner())
                    .filterInputsBy(new FilterBuilder().includePackage(packagePath)));
            return reflections.getTypesAnnotatedWith(annotation);
        } catch (Exception e) {
            // 处理异常，可能是反射异常或配置问题
            e.printStackTrace();
            return null;
        }
    }
}
