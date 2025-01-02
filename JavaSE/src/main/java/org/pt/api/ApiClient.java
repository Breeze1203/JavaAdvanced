package org.pt.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ApiClient {
    public static String sendPostRequest(String urlString, Map<String, String> params) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        conn.setRequestProperty("User-Agent", "Apifox/1.0.0");

        // 添加参数到请求体
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        conn.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(postDataBytes);
        }
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    result.append(inputLine);
                }
            }
        } else {
            throw new IOException("POST request not worked. Response Code: " + responseCode);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        try {
            String appKey = "0e8d4108-7114-49ef-a4b2-5be67e31079c";
            String secretKey = "xoz8FneD0elsvkPtg7tzkj2WFfGSh+hdjukhFwjHPXlm3mXNPLqHcuEIGNL8SoPOqF5vjfmF7GVtfg1XUdiZ9A==";
            String baseUrl = "https://szrjy-ipark.smedi.com/evh/openapi/community/listCommunityInfo";
            Map<String, String> params = new HashMap<>();
            params.put("pageNumber", "2");
            params.put("pageSize", "80");
            params.put("appKey", appKey);
            //params.put("namespaceId", "999870");
            params.put("nonce", "6140463611200");
            params.put("timestamp", "1735008685765");
            params.put("signature", SignatureUtils.computeSignature(params, secretKey));
            String response = sendPostRequest(baseUrl, params);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}