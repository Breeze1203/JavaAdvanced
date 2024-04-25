package com.example.springbootdynamic;

import com.example.springbootdynamic.entity.Student;
import com.example.springbootdynamic.service.StudentService;
import com.example.springbootdynamic.service.impl.TransactionServiceImpl;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@SpringBootTest
class SpringBootDynamicApplicationTests {

    @Resource(name = "StudentService")
    StudentService service;


    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    void contextLoads() {
        List<Student> allStudent = service.getAllStudent();
        System.out.println(allStudent);
    }

    @Test
    void testAffairs(){
        System.out.println("未插入前:"+service.getAllStudent().size());
        transactionTemplate.execute(new TransactionCallback() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                service.addStudent(new Student("pt","3548297839@qq.com","深圳",22));
                System.out.println("插入后:"+service.getAllStudent().size());
                status.setRollbackOnly();
                return "完成";
            }
        });
        System.out.println("回滚后:"+service.getAllStudent().size());
    }
    @Test
    void test(){
        //设置事务传播属性
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 设置事务的隔离级别,设置为读已提交（默认是ISOLATION_DEFAULT:使用的是底层数据库的默认的隔离级别）
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        // 设置是否只读，默认是false
        transactionTemplate.setReadOnly(true);
        // 默认使用的是数据库底层的默认的事务的超时时间
        transactionTemplate.setTimeout(30000);

        System.out.println("未插入前:"+service.getAllStudent().size());
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                service.addStudent(new Student("pt","3548297839@qq.com","深圳",22));
                System.out.println("插入后:"+service.getAllStudent().size());
                status.setRollbackOnly();
            }
        });
        System.out.println("回滚后:"+service.getAllStudent().size());
    }

    @Resource
    private PlatformTransactionManager transactionManager;

    @Test
    void test01(){
        // 来定义事务的各种属性 然后将其传递给事务管理
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        // 设置事务属性
        defaultTransactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        // 设置事务的传播特性
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        defaultTransactionDefinition.setTimeout(30); // 设置超时时间为30秒
        defaultTransactionDefinition.setReadOnly(false); // 设置为读写事务
        // 开启事务
        TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);
        try {
            // 在这里执行你的事务操作
            // 例如：数据库操作、调用其他事务方法等
            // 提交事务
            transactionManager.commit(status);
        } catch (Exception ex) {
            // 发生异常时回滚事务
            transactionManager.rollback(status);
            throw ex;
        }
    }

    @Resource
    TransactionServiceImpl transactionService;

    @Test
    void test02(){
        transactionService.addUser();
    }

}
