package org.pt.algo;

/**
 * @ClassName NodeTest
 * @Author pt
 * @Description
 * @Date 2025/7/9 20:16
 **/
public class NodeTest {
    public static void main(String[] args) {

    }

}


class Node{
    public Node next;
    public Integer value;

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}