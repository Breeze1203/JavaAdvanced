package com.example.adminflow.mapper;

import com.example.adminflow.model.OperationData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository(value = "OperationDataMapper")
public interface OperationDataMapper {
    int addLog(OperationData operationData);

    // 分页查询
    List<OperationData> getLogData(@Param("keyword")String keyword,@Param("offset")Integer offset,@Param("pageSize")Integer pageSize);

    long getTotal(@Param("keyword")String keyword);
}
