package com.example.admin.util;

import com.example.admin.model.OperationData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class LogResult {
    public long total;
    public List<OperationData> operationData;
}
