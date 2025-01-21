package org.pt.json;

/**
 * @ClassName JsonSerializer
 * @Author pt
 * @Description
 * @Date 2025/1/6 17:16
 **/
import java.lang.reflect.Field;
import java.util.Arrays;

public class JsonSerializer {

    // 将任意对象转换为JSON格式的字符串
    public static String toJSON(Object obj) throws IllegalAccessException {
        StringBuilder buffer = new StringBuilder();
        serializeValue(obj, buffer);
        return buffer.toString();
    }

    // 递归序列化值
    private static void serializeValue(Object value, StringBuilder buffer) throws IllegalAccessException {
        if (value instanceof String) {
            // 序列化字符串
            buffer.append("\"").append(escapeString((String) value)).append("\"");
        } else if (value instanceof Integer || value instanceof Long) {
            // 序列化整数
            buffer.append(value.toString());
        } else if (value instanceof Double || value instanceof Float) {
            // 序列化浮点数
            buffer.append(value.toString());
        } else if (value instanceof Boolean) {
            // 序列化布尔值
            buffer.append(value.toString());
        } else if (value instanceof Object[]) {
            // 序列化数组
            Object[] array = (Object[]) value;
            buffer.append("[");
            for (int i = 0; i < array.length; i++) {
                if (i > 0) {
                    buffer.append(", ");
                }
                serializeValue(array[i], buffer);
            }
            buffer.append("]");
        } else if (value instanceof Iterable) {
            // 序列化Iterable对象
            Iterable<?> iterable = (Iterable<?>) value;
            buffer.append("[");
            boolean first = true;
            for (Object item : iterable) {
                if (!first) {
                    buffer.append(", ");
                }
                serializeValue(item, buffer);
                first = false;
            }
            buffer.append("]");
        } else if (value != null) {
            // 序列化对象
            buffer.append("{");
            boolean first = true;
            for (Field field : value.getClass().getDeclaredFields()) {
                if (!first) {
                    buffer.append(", ");
                }
                field.setAccessible(true);
                buffer.append("\"").append(field.getName()).append("\": ");
                serializeValue(field.get(value), buffer);
                first = false;
            }
            buffer.append("}");
        }
    }

    // 对字符串进行转义，以确保生成有效的JSON字符串
    private static String escapeString(String s) {
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    public static void main(String[] args) throws IllegalAccessException {
        // 创建一个Person实例
        Person person = new Person("Alice", 30);

        // 将Person实例转换为JSON字符串
        String jsonStr = toJSON(person);

        // 打印JSON字符串
        System.out.println(jsonStr);
    }
}

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}