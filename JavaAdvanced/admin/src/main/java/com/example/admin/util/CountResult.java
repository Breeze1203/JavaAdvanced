package com.example.admin.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
@ToString
@AllArgsConstructor
public class CountResult {
    private List<String> numbers;
    private List<Long> count;
}
