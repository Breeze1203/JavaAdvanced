package org.pt.algo.hash;

import java.util.ArrayList;
import java.util.List;

public class ArrayHashMap {
    private static final List<Pair> buckets = new ArrayList<>(100);

    public ArrayHashMap(List<Pair> list) {
        for (int i = 0; i < 100; i++) {
            list.add(null);
        }
    }

    /* 哈希函数 */
    private int hashFunc(int key) {
        return key % 100;
    }

    /*
    添加
     */
    public void put(int key, String value) {
        Pair pair = new Pair(key, value);
        int i = hashFunc(key);
        buckets.add(i, pair);
    }

    /*
    取出
     */
    public String get(int key) {
        int i = hashFunc(key);
        return buckets.get(i).getValue();
    }

    /*
    获取所有的键
     */
    public List<Integer> getAllKey() {
        ArrayList<Integer> keys = new ArrayList<>();
        for (Pair p : buckets) {
            if (p != null) {
                keys.add(p.getKey());
            }
        }
        return keys;
    }

    /*
    获取所有的值
     */
    public List<String> getAllValue() {
        ArrayList<String> values = new ArrayList<>();
        for (Pair p : buckets) {
            if (p != null) {
                values.add(p.getValue());
            }
        }
        return values;
    }

    /*
    获取所有键值对
     */
    public List<Pair> pairSet() {
        ArrayList<Pair> pairs = new ArrayList<>();
        for (Pair p : buckets) {
            if (p != null) {
                pairs.add(p);
            }
        }
        return pairs;
    }

    /*
    覆盖掉对应键的值
     */
    public void setKey(int index,String value){
        int i = hashFunc(index);
        buckets.get(i).setValue(value);
    }
}
