package com.example.admin.service;

import com.example.admin.mapper.OperationDataMapper;
import com.example.admin.model.OperationData;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "OperationDataService")
public class OperationDataService implements OperationDataMapper{
    @Resource(name = "OperationDataMapper")
    private OperationDataMapper operationDataMapper;

    /*
    添加日志
     */
    @Override
    public int insertLog(OperationData operationData) {
        return operationDataMapper.insertLog(operationData);
    }

    /*
    分页查询日志
     */
    @Override
    public List<OperationData> getLogData(String keyword, Integer offset, Integer pageSize) {
        if (offset != null && pageSize != null) {
            offset = (offset - 1) * pageSize;
        }
        return operationDataMapper.getLogData(keyword, offset, pageSize);
    }
    /*
    查询日志总条数
     */
    @Override
    public long getTotal(String keyword){
        return operationDataMapper.getTotal(keyword);
    }

    /*
    删除日志
     */
    @Override
    public Integer deleteLog() {
        return operationDataMapper.deleteLog();
    }
}
