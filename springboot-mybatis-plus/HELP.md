### springboot集成mybatis-plus
1.创建springboot工程<br>
2.添加依赖<br>
```
<dependency>
     <groupId>com.baomidou</groupId>
     <artifactId>mybatis-plus-boot-starter</artifactId>
     <version>${mybatis.version}</version>
</dependency>
<dependency>
     <groupId>mysql</groupId>
     <artifactId>mysql-connector-java</artifactId>
     <version>${mysql.version}</version>
</dependency>
```
3.配置数据库信息及映射规则<br>
```
server.port=8088
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.url=jdbc:mysql://localhost:3306/admin?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=3548297839e
```
```
package com.example.mybatisplus.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.model.User;

public interface UserDao extends BaseMapper<User> {
}
```
```
实体类映射<br>
@ToString
@AllArgsConstructor
@Data
@TableName(value = "user")
@NoArgsConstructor
public class User {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "name",jdbcType = JdbcType.VARCHAR)
    private String name;
    @TableField(value = "age",jdbcType = JdbcType.INTEGER)
    private Integer age;
    @TableField(value = "date",jdbcType = JdbcType.DATE)
    private Date date;
    @TableField(value = "departmentId",jdbcType = JdbcType.INTEGER)
    private Integer departmentId;
}
```
4.测试<br>
#### 增(传映射好的实体类对象)
```
@Autowired
private UserDao userDao;
    @Test
    void contextLoads() {
        // 插入
        int result = userDao.insert(new User(null, "mysql", 23, new Date(), 3));
        System.out.println(result);
    }

```
#### 查
```
// 查询所有
List<User> user = userDao.selectList(null);
System.out.println(user);
--------------------------------------------------------------------
// 查询id为1的主键
User user1 = userDao.selectById(1);
System.out.println(user1);
--------------------------------------------------------------------
// 只查询表中为name的字段，其它属性为null
QueryWrapper<User> wrapper = new QueryWrapper<>();
wrapper.select("name");
List<User> users = userDao.selectList(wrapper);
System.out.println(users);

[User(id=null, name=nacos, age=null, date=null, departmentId=null),
User(id=null, name=dubbo, age=null, date=null, departmentId=null),
User(id=null, name=rocketmq, age=null, date=null, departmentId=null), 
User(id=null, name=redis, age=null, date=null, departmentId=null), 
User(id=null, name=mysql, age=null, date=null, departmentId=null)]  
--------------------------------------------------------------------
// 查询字段name为nacos的数据
QueryWrapper<User> wrapper1 = new QueryWrapper<>();
wrapper1.eq("name", "nacos");
List<User> users1 = userDao.selectList(wrapper1);
System.out.println(users1);

[User(id=1, name=nacos, age=23, date=Tue Dec 26 00:00:00 CST 2023, departmentId=null)]
--------------------------------------------------------------------
// 根据主键批量查询
List<Integer> ids=new ArrayList<>();
ids.add(1);
ids.add(3);
ids.add(4);
List<User> users2 = userDao.selectBatchIds(ids);
System.out.println(users2);

[User(id=1, name=nacos, age=23, date=Tue Dec 26 00:00:00 CST 2023, departmentId=null), 
User(id=3, name=rocketmq, age=23, date=Tue Dec 26 00:00:00 CST 2023, departmentId=1), 
User(id=4, name=redis, age=23, date=Tue Dec 26 00:00:00 CST 2023, departmentId=2)]


```



