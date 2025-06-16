package org.pt.api;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * @ClassName SignatureUtils
 * @Author pt
 * @Description
 * @Date 2024/12/24 09:21
 **/
public class SignatureUtils {
    public static String computeSignature(Map<String, String> params, String secretKey) {
        assert (params != null);
        assert (secretKey != null);
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            byte[] rawKey = Base64.getDecoder().decode(secretKey);
            SecretKeySpec keySpec = new SecretKeySpec(rawKey, "HmacSHA1");
            mac.init(keySpec);
            List<String> keyList = new ArrayList<>(params.keySet());
            Collections.sort(keyList);
            for (String key : keyList) {
                mac.update(key.getBytes("UTF-8"));
                String val = params.get(key);
                if (val != null && !val.isEmpty())
                    mac.update(val.getBytes("UTF-8"));
            }
            byte[] encryptedBytes = mac.doFinal();
            String signature = Base64.getUrlEncoder().withoutPadding().encodeToString(encryptedBytes);
            return signature;
        } catch (Exception e) {
            throw new RuntimeException("Error while computing signature", e);
        }
    }

    public static boolean verifySignature(Map<String, String> params, String secretKey, String signatureToVerify) {
        String signature = computeSignature(params, secretKey);
        return signature.equals(signatureToVerify);
    }
}
