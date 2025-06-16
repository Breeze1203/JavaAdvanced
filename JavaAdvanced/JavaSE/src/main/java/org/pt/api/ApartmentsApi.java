package org.pt.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.pt.api.ApiClient.sendPostRequest;

/**
 * @ClassName ApartmentsApi
 * @Author pt
 * @Description
 * @Date 2024/12/25 09:19
 **/
public class ApartmentsApi {
    public static void main(String[] args) {
        try {
            String appKey = "0e8d4108-7114-49ef-a4b2-5be67e31079c";
            String secretKey = "xoz8FneD0elsvkPtg7tzkj2WFfGSh+hdjukhFwjHPXlm3mXNPLqHcuEIGNL8SoPOqF5vjfmF7GVtfg1XUdiZ9A==";
            String baseUrl = "https://szrjy-ipark.smedi.com/spacebase/openapi/address/listApartments";
            Map<String, String> params = new HashMap<>();
            params.put("pageNumber", "1");
            params.put("pageSize", "10");
            params.put("appKey", appKey);
            params.put("propertyNo", "001");
            params.put("nonce", "6140463611200");
            params.put("timestamp", "1735008685765");
            params.put("buildingId","225");
            params.put("floorNumber","2");
            params.put("signature", SignatureUtils.computeSignature(params, secretKey));
            String response = sendPostRequest(baseUrl, params);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
