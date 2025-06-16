package com.example.admin.util;


import com.example.admin.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

public class JwtToken {
    private static final String key = "admin_flow_pt_3548297839@qq.com";

    // 生成token
    public static String generateToken(User user) {
        String key = generateKey();
        JwtBuilder builder = Jwts.builder();
        byte[] bytes = key.getBytes();
        SecretKey secretKey = Keys.hmacShaKeyFor(bytes);
        builder.setIssuer("pt"); //颁发者
        builder.setSubject("token认证"); // 主题信息
        builder.claim("id", user.getId()); //将用户的id关联到cookie
        builder.signWith(secretKey);
        return builder.compact();
    }

    // 给密钥64位加密
    private static String generateKey() {
        byte[] originalBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] encodedBytes = Base64.getEncoder().encode(originalBytes);
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    // 验证token
    public static Integer verifyToken(String token) {
        byte[] bytes = generateKey().getBytes();
        Key secretKey = Keys.hmacShaKeyFor(bytes);
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            // Token 验证通过，可以从 claimsJws 对象中获取相关信息
            Claims claims = claimsJws.getBody();
            String id=claims.get("id").toString();
            return Integer.valueOf(id);
        }catch (Exception e){
            return -1;
        }
    }
}
