package com.example.adminflow.service;

import com.example.adminflow.mapper.OperationDataMapper;
import com.example.adminflow.model.OperationData;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "OperationDataService")
public class OperationDataService {
    @Resource(name = "OperationDataMapper")
    OperationDataMapper operationDataMapper;

    public void insetOperationLog(OperationData operationData) {
        operationDataMapper.addLog(operationData);
    }

    // 分页查询
    public List<OperationData> getAllLog(String keyword, Integer offset, Integer pageSize) {
        if (offset != null && pageSize != null) {
            offset = (offset - 1) * pageSize;
        }
        return operationDataMapper.getLogData(keyword, offset, pageSize);
    }
    // 查询总条数
    public long getTotal(String keyword){
        return operationDataMapper.getTotal(keyword);
    }
}
