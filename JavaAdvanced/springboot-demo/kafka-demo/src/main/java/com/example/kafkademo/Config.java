package com.example.kafkademo;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import org.springframework.boot.jackson.JsonComponent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName Config
 * @Author pt
 * @Description
 * @Date 2025/1/16 13:34
 **/
@JsonComponent
public class Config {

    public static class Serializer extends JsonSerializer<Channel> {


        @Override
        public void serialize(Channel channel, JsonGenerator json, SerializerProvider serializerProvider) throws IOException {
            json.writeNumber(channel.getCode());

        }
    }

    public static class Deserializer extends JsonDeserializer<Channel> {
        @Override
        public Channel deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            int i = jsonParser.getIntValue();
            return Channel.getValue(i);
        }
    }

    public static class DateSerializer extends JsonSerializer<Date> {
        private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd 00:00:00");

        @Override
        public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            String formattedDate = dateFormat.format(date);
            jsonGenerator.writeString(formattedDate);
        }
    }
    public static class DateDeserializer extends JsonDeserializer<Date> {
        private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd 00:00:00");

        @Override
        public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            String dateStr = jsonParser.getText().trim();
            try {
                return dateFormat.parse(dateStr);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

}