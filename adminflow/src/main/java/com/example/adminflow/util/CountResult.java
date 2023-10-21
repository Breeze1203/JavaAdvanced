package com.example.adminflow.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Data
@ToString
@AllArgsConstructor
public class CountResult {
    private Set<String> numbers;
    private List<Double> scores;
}
