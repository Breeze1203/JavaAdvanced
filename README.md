# JavaAdvanced
微服务消息中间件学习

#### 项目访问人数折线图实现
纪录问题：关于项目访问折线图数据展示，横坐标 项目访问时间 纵坐标 项目访问人数 实现过程
1:起初利用redis里面的有序集合zset实现，将日期作为number,访问人数作为score,项目每次登录score自动增一，然而出现一个问题
就是横纵坐标必须是有序的，当两个numer的score一致时，就有可能查询出这种情况
2023-10-21 1
2023-10-23 1
2023-10-22 3
因为zset是按score排序的，所有这种数据结构在此时并不合适，转而用hash来实现
具体实现代码
、、、
String format = DateUtil.format(new Date());
            // 每次用户登录成功，访问次数加一
            HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
            String s = hashOps.get("loginCount", format);
            // 如果s不为null,就将起初的值先转为long，然后加一
            Long count = s != null ? Long.parseLong(s) + 1 : 1;
            hashOps.put("loginCount",format,count.toString());

@GetMapping("/getCount")
    public CountResult getAccessCount() {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        List<String> numbers=new ArrayList<>();
        List<Long> score=new ArrayList<>();
        // 获取指定范围的字段和对应的值
        List<String> rangeValues = new ArrayList<>();
        Set<String> keys = hashOps.keys("loginCount");
        // 将set集合里面的元素放入list集合
        rangeValues.addAll(keys);
        for (int i=rangeValues.size()-5;i<rangeValues.size();i++){
            String s = rangeValues.get(i);
            numbers.add(s);
            String s1 = hashOps.get("loginCount", s);
            long l = Long.parseLong(s1);
            score.add(l);
        }
        return new CountResult(numbers, score);
    }
    、、、
