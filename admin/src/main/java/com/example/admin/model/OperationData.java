package com.example.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OperationData implements Serializable {
    @Serial
    private static final long serialVersionUID= 1L;
    private Integer id;
    private String user;
    private String operation;
    private String date;
    private String type;
}
