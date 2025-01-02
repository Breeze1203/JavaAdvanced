package org.pt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class test_a {
public static void main(String[] args) {
    // 生成时间戳
        String tp = String.valueOf(System.currentTimeMillis() / 1000);
        String username = "zhongshan";
        String action = "test";
        String data = "{\"Return\":\"123\"}";
        String key = "14e1b600b1fd579f47433b88e8d85291"; // 密钥
        
        // 生成sign
        String signStr = username + action + data + tp + key;
        String sign =Test.generateSign(signStr);

        try {
            URL url = new URL("https://llc.gdzlw.cn/Interface/agentInterface.ashx");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // 构建请求JSON
            String jsonInputString = String.format("{\"Username\":\"%s\",\"Action\":\"%s\",\"Data\":%s,\"Tp\":\"%s\",\"Sign\":\"%s\"}",
                username, action, data, tp, sign);
            System.out.println("Request: " + jsonInputString);

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
}
}
