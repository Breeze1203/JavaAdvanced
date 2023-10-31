package com.example.adminflow.util;

import com.example.adminflow.model.OperationData;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class LogResult {
    public long total;
    public List<OperationData> operationData;
}
