package org.pt.string;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

public class Immutable {
    public static void main(String[] args) {
        Calendar calendar=Calendar.getInstance();
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-nn");
        String s = dateFormat.format(date);
        calendar.add(Calendar.DAY_OF_MONTH,10);
        System.out.println(date.getTime());
        HashMap<String, Integer> m = new HashMap<>();
        Set<Map.Entry<String, Integer>> entries = m.entrySet();
    }
}
