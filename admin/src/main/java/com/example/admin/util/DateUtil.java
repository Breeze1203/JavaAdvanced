package com.example.admin.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String format(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static String formatLog(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
