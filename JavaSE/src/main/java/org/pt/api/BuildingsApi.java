package org.pt.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.pt.api.ApiClient.sendPostRequest;

/**
 * @ClassName BuildingsApi
 * @Author pt
 * @Description
 * @Date 2024/12/25 09:10
 **/
public class BuildingsApi {
    public static void main(String[] args) {
        try {
            String appKey = "0e8d4108-7114-49ef-a4b2-5be67e31079c";
            String secretKey = "xoz8FneD0elsvkPtg7tzkj2WFfGSh+hdjukhFwjHPXlm3mXNPLqHcuEIGNL8SoPOqF5vjfmF7GVtfg1XUdiZ9A==";
            String baseUrl = "https://szrjy-ipark.smedi.com/spacebase/openapi/address/listBuildings";
            Map<String, String> params = new HashMap<>();
            params.put("pageNumber", "1");
            params.put("pageSize", "10");
            params.put("appKey", appKey);
            //params.put("namespaceId", "999870");
            params.put("nonce", "6140463611200");
            params.put("timestamp", "1735008685765");
            params.put("communityId","240111044332061387");
            params.put("signature", SignatureUtils.computeSignature(params, secretKey));
            String response = sendPostRequest(baseUrl, params);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
