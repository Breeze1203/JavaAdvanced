package org.pt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @ClassName LogProducerService
 * @Author pt
 * @Description
 * @Date 2025/8/31 21:32
 **/
@Service
public class LogProducerService {
    @Autowired
    private KafkaTemplate<String, LogMessage> kafkaTemplate;
    private static final String TOPIC = "app-logs";

    public void sendLog(String level, String content) {
        LogMessage message = new LogMessage();
        message.setId(UUID.randomUUID().toString());
        message.setTimestamp(System.currentTimeMillis());
        message.setLevel(level);
        message.setContent(content);
        kafkaTemplate.send(TOPIC, message.getId(), message);
        System.out.println("Sent log: " + content);
    }
}
