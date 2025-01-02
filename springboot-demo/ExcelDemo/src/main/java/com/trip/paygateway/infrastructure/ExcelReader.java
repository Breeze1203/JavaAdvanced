package com.trip.paygateway.infrastructure;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelReader<T> {
    private final Class<T> type;

    public ExcelReader(Class<T> type) {
        this.type = type;
    }

    public List<T> readExcel(String filePath) {
        List<T> dataList = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            // Create Workbook instance holding reference to .xlsx file
            Workbook workbook = WorkbookFactory.create(file);
            // Get the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            // Get the header row
            Row headerRow = sheet.getRow(0);
            // 根据第一行列数来初始化header数组
            String[] headers = new String[headerRow.getLastCellNum()];
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                // 将第一行对应列的标识符存到对应数组下标
                headers[i] = headerRow.getCell(i).getStringCellValue();
            }
            // Iterate through each row
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                // 如果这行为空直接跳过
                if (row == null) continue;
                //通过反射创建了type表示的类的一个新实例，并且假定这个类有一个无参数的构造函数
                T obj = type.getDeclaredConstructor().newInstance();
                // Iterate through each cell
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    // 开始遍历每一行每一列
                    Cell cell = row.getCell(j);
                    // 获取对应列的标识符号
                    String header = headers[j];
                    Object value = getCellValue(cell);
                    // Find corresponding field using reflection
                    Field field = getFieldByName(header);
                    if (field != null) {
                        field.setAccessible(true);
                        field.set(obj, convertValueToType(value, type.getTypeName().getClass()));
                    }
                }

                dataList.add(obj);
            }
            workbook.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataList;
    }

    private Field getFieldByName(String name) {
        for (Field field : type.getDeclaredFields()) {
            ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
            if (annotation != null && annotation.name().equals(name)) {
                return field;
            }
        }
        return null;
    }

    private Object convertValueToType(Object value, Class<?> type) {
        if (value == null) {
            return null;
        }
        if (type == String.class) {
            return value.toString();
        } else if (type == int.class || type == Integer.class) {
            if (value instanceof String) {
                return Integer.parseInt((String) value);
            } else if (value instanceof Number) {
                return ((Number) value).intValue();
            }
        } else if (type == double.class || type == Double.class) {
            if (value instanceof String) {
                return Double.parseDouble((String) value);
            } else if (value instanceof Number) {
                return ((Number) value).doubleValue();
            }
        } else if (type == boolean.class || type == Boolean.class) {
            if (value instanceof String) {
                return Boolean.parseBoolean((String) value);
            } else if (value instanceof Boolean) {
                return value;
            }
        } else if (type == Date.class) {
            if (value instanceof Date) {
                return value;
            } else if (value instanceof String) {
                // 根据需要进行日期格式转换
                // 此处假设日期字符串为标准格式
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    return dateFormat.parse((String) value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        // 如果没有匹配的类型，则返回原始值
        return value;
    }


    private Object getCellValue(Cell cell) {
        // 初始化一个空值
        if (cell == null) {
            return null;
        }
        // 根据单元格类型进行不同的处理
        switch (cell.getCellType()) {
            case STRING:
                // 如果是字符串类型，直接返回字符串值
                return cell.getStringCellValue();
            case NUMERIC:
                // 如果是数字类型，判断是否是日期格式
                if (DateUtil.isCellDateFormatted(cell)) {
                    // 如果是日期格式，返回日期对象
                    return cell.getDateCellValue();
                } else {
                    // 否则返回数字值
                    return cell.getNumericCellValue();
                }
            case BOOLEAN:
                // 如果是布尔类型，返回布尔值
                return cell.getBooleanCellValue();
            case FORMULA:
                // 如果是公式类型，返回公式字符串
                return cell.getCellFormula();
            default:
                // 其他类型返回空值
                return null;
        }
    }


    public static void main(String[] args) {
        ExcelReader<Employee> excelReader = new ExcelReader<>(Employee.class);
        List<Employee> dataList = excelReader.readExcel("your_excel_file.xlsx");

        // Print the data
        for (Employee employee : dataList) {
            System.out.println(employee);
        }
    }

}
