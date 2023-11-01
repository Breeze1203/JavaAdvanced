package com.example.admin.service;

import com.example.admin.mapper.OperationDataMapper;
import com.example.admin.model.OperationData;
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

    public Integer deleteLog() {
        return operationDataMapper.deleteLog();
    }
}
