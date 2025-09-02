package org.pt;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @ClassName LogConsumerService
 * @Author pt
 * @Description
 * @Date 2025/8/31 21:31
 **/
@Service
public class LogConsumerService {
    @Autowired
    private InfluxDBClient influxDBClient;
    @Value("${influx.org}") private String org;
    @Value("${influx.bucket}") private String bucket;

    @KafkaListener(topics = "app-logs", groupId = "log-consumer-group")
    public void consume(LogMessage message) {
        System.out.println("Consumed log: " + message.toString());
        try (WriteApi writeApi = influxDBClient.getWriteApi()) {
            Point point = Point.measurement("log")
                    .addTag("level", message.getLevel())
                    .addField("content", message.getContent())
                    .addField("message_id", message.getId()) // 将唯一ID作为字段存入
                    .time(message.getTimestamp(), WritePrecision.MS);

            writeApi.writePoint(bucket, org, point);
            System.out.println("Successfully wrote to InfluxDB.");
        }
    }
}