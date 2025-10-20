package org.pengtao;

import java.util.Map;

public record SmsRequest(
        String phone,
        String templateId,
        Map<String, String> params
) {}