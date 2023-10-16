package com.example.rbacdemo.config;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

//@Configuration
public class JacksonConfig {
    /*
    自定义objectMapper转换日期格式
     */
    //@Bean
    public ObjectMapper getObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        return mapper;
    }
}
