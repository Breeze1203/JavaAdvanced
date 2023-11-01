package com.example.admin.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
public class OperationData {
    private Integer id;
    private String user;
    private String operation;
    private String date;
    private String type;
}
