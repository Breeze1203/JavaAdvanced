package org.pt.object;

public class User {
    public static void main(String[] args) {
        Student student = new Student();
        System.out.println(student.getScore());
        Student student1 = new Student();
        System.out.println(student1.getScore());
        /*
        属于Student类本身，而不是类的某个特定实例。因此，score是所有Student对象共享的
         */
        Person person = new Person();
        person.setName("pengtao");
        System.out.println(person.toString());
        person.setName("caonima");
        System.out.println(person.toString());
        Person person1 = new Person();
        person1.setName("person1");
        System.out.println(person1.toString());



        System.out.println("--------");
        Demo demo = new Demo();
        int a=5;
        demo.add(a);
        System.out.println(a);
        Person person2 = new Person();
        System.out.println("地址1"+person2.toString());
        //person2.setEmail("修改前的email");
        demo.getEmailInfo(person2);
        System.out.println(person2.getEmail());

    }

    // 定义内部类Student
    public static class Student {
        // int类型的成员变量，初始值为10
        private static int score = 10;
        // 构造方法，在构造时自增
        public Student() {
            score++; // 自增操作
        }

        // 可以添加一个方法来获取score的值
        public int getScore() {
            return score;
        }
    }
}
