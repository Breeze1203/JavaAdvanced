package org.pt.algo.stack;

import java.util.ArrayList;
import java.util.List;

public class ArrayStack<E> {
    private List<E> list;
    public ArrayStack(){
        list=new ArrayList<>();
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }
    /*
    出栈
     */
    public E pop(){
        E e = list.remove(list.size()-1);
        return e;
    }

    /*
    入栈
     */
    public void push(E e){
        list.add(e);
    }

    /*
    访问栈顶元素
     */
    public E peek(){
        return list.get(list.size() - 1);
    }

    public static void main(String[] args) {
        ArrayStack<String> stringArrayStack = new ArrayStack<>();
        stringArrayStack.push("我");
        stringArrayStack.push("喜");
        stringArrayStack.push("欢");
        stringArrayStack.push("张");
        stringArrayStack.push("嘉");
        System.out.println(stringArrayStack.peek());
    }

}
