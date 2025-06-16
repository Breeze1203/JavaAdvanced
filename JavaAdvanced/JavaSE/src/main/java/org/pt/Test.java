package org.pt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @ClassName Test
 * @Author pt
 * @Description
 * @Date 2024/10/28 14:41
 **/
public class Test {
    private static final String username = "runxuntong"; // 渠道商账号
    private static final String action = "get_sim_list";  // 方法
    private static final String data = "{\"Return\":\"123\"}";
     // 业务数据JSON
    private static final String page = "{\"pageIndex\":\"1\",\"pageSize\":\"10\"}";  // 分页参数
     private static final String tp = String.valueOf(System.currentTimeMillis() / 1000); // 当前时间戳(10位)
    //private static final String tp="1732669236";
    private static final String key = "14e1b600b1fd579f47433b88e8d85291"; // 密钥
    // 签名 MD5(username + action + data + tp + key)
    
    // 生成签名
    public static String generateSign(String signStr) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(signStr.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            return null;
        }
    }
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 获取当前时间的时间戳
        long currentTimeStamp = System.currentTimeMillis();
        System.out.println("当前时间的时间戳: " + currentTimeStamp);

        // 将时间戳转换为日期和时间
        Date date = new Date(currentTimeStamp);
        String dateString = sdf.format(date);
        System.out.println("转换回日期和时间: " + dateString);

        // 将特定的日期字符串转换为时间戳
        String inputDate = "2024-12-03 08:12:13";
        try {
            Date parsedDate = sdf.parse(inputDate);
            long timeStamp = parsedDate.getTime();
            System.out.println("特定日期的时间戳: " + timeStamp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 将时间戳转换回特定的日期字符串
        long specificTimeStamp = 1701529533000L; // 假设这是2024-12-03 08:12:13 UTC的时间戳
        Date specificDate = new Date(specificTimeStamp);
        String formattedDate = sdf.format(specificDate);
        System.out.println("时间戳转换回日期: " + formattedDate);
        String signStr = username + action + page + tp + key;
        String a = generateSign(signStr);
        System.out.println(a);
        try {
            URL url = new URL("https://llc.gdzlw.cn/Interface/agentInterface.ashx");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // 构建请求JSON
            String jsonInputString = String.format("{\"Username\":\"%s\",\"Action\":\"%s\",\"Data\":\"%s\",\"Tp\":\"%s\",\"Sign\":\"%s\"}",
                username, action, "{\"pageIndex\":\"1\",\"pageSize\":\"10\"}", tp, a);
            System.out.println(jsonInputString);

            // 发送请求
            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            // 读取响应
            try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Response: " + response.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Object> objects = new ArrayList<>();
    }

}
