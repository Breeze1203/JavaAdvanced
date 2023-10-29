package com.example.adminflow.mapper;

import com.example.adminflow.model.LoginData;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository(value = "LogDataMapper")
public interface LogDataMapper {
    int insertLog(LoginData loginData);

    List<LoginData> getLogData();
}
