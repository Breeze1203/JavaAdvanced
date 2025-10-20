package org.pengtao;

public record SmsResponse(
        boolean success,
        String messageId,
        Object rawResult // 原始厂商响应
) {}