package org.pt;

import org.apache.ibatis.io.Resources;
import org.pt.entity.User;
import org.pt.mapper.UserMapper;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.Collection;

public class Main {

    public static void main(String[] args) throws Exception {
        // --- 准备工作：手动在MySQL中创建好数据库和表 ---
        // (这一步您已经在本地MySQL中完成了)

        // --- 第 1 步：加载配置并初始化，构建 SqlSessionFactory ---
        System.out.println(">>> 1. 开始加载MyBatis配置...");
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        System.out.println("<<< 1. MyBatis配置加载完毕，SqlSessionFactory已创建。\n");
        // =================================================================
        // !! 新增代码：打印 Configuration 中的 Mapped Statements !!
        printAllMappedStatements(sqlSessionFactory.getConfiguration());
        // =================================================================
        // --- 第 2-4 步：获取会话、创建代理、执行SQL、映射返回 ---
        try (SqlSession session = sqlSessionFactory.openSession()) {
            System.out.println(">>> 2. 获取SqlSession并创建Mapper代理...");
            UserMapper userMapper = session.getMapper(UserMapper.class);
            System.out.println("<<< 2. Mapper代理对象已创建: " + userMapper.getClass().getName() + "\n");
            User newUser = new User();
            // 为了防止重复执行主键冲突，我们换一个ID
            newUser.setId(7);
            newUser.setName("Another User");
            newUser.setEmail("another@example.com");
            System.out.println(">>> 3.1 开始执行INSERT...");
            userMapper.insertUser(newUser);
            session.commit();
            System.out.println("<<< 3.1 INSERT执行完毕并提交。\n");
            System.out.println(">>> 3.2 开始执行SELECT...");
            User foundUser = userMapper.selectUser(2);
            System.out.println("<<< 3.2 SELECT执行完毕。\n");

            System.out.println(">>> 4. 结果映射与返回...");
            System.out.println("查询结果: " + foundUser);
            System.out.println("<<< 4. 流程结束。\n");
        }
    }

    /**
     * 打印Configuration对象中所有已注册的MappedStatement信息
     * @param configuration MyBatis的全局配置对象
     */
    public static void printAllMappedStatements(Configuration configuration) {
        System.out.println("=================================================");
        System.out.println("          打印所有已加载的Mapped Statements         ");
        System.out.println("=================================================");
        // 从Configuration对象中获取所有已注册的MappedStatement
        Collection<MappedStatement> statements = configuration.getMappedStatements();
        if (statements.isEmpty()) {
            System.out.println("配置中未找到任何 Mapped Statements。");
            return;
        }

        for (MappedStatement ms : statements) {
            // MappedStatement的ID就是我们讨论的那个全局唯一的 "Key"
            String key = ms.getId();
            // 打印这个Key，它由 "namespace" + "." + "id" 组成
            System.out.println("-------------------------------------------------");
            System.out.println("  KEY: " + key);
            System.out.println("-------------------------------------------------");
            // 打印SQL指令的类型 (SELECT, INSERT, UPDATE, DELETE)
            SqlCommandType commandType = ms.getSqlCommandType();
            System.out.println("  > Command Type : " + commandType);
            // 打印SQL来源的资源文件路径
            String resource = ms.getResource();
            System.out.println("  > SQL Source   : " + resource);

            // 我们可以尝试获取SQL语句本身
            // 注意：对于动态SQL，这里获取的可能不是最终执行的SQL
            try {
                // 创建一个虚拟的参数对象来获取BoundSql
                Object parameterObject = new MapperMethod.ParamMap<>();
                String sql = ms.getBoundSql(parameterObject).getSql();
                System.out.println("  > SQL Text     :\n    " + sql.replaceAll("\\s+", " ").trim());
            } catch (Exception e) {
                System.out.println("  > SQL Text     : (动态SQL，无法直接预览)");
            }
            System.out.println(); // 打印一个空行用于分隔
        }
        System.out.println("=================================================\n");
    }
}