package org.pt.algo.linklist;

public class LinkedList {
    private Node head;

    // 定义节点类
    private class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    // 在链表末尾添加新节点
    public void addNode(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    // 在指定位置插入新节点
    public void insertNode(int data, int position) {
        Node newNode = new Node(data);
        if (position == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;
            for (int i = 0; i < position - 1 && current != null; i++) {
                current = current.next;
            }
            if (current == null) {
                System.out.println("指定位置超出链表范围");
            } else {
                newNode.next = current.next;
                current.next = newNode;
            }
        }
    }

    // 指定位置删除节点
    public void deleteNode(int index) {
        // 找到要删除节点的前一个节点
        Node prev = head;
        for (int i = 0; i < index - 1; i++) {
            prev = prev.next;
        }
        prev.next = prev.next.next;
    }

    // 更新节点的位置
    public void updateNode(int index, int value) {
        if (index == 0) {
            head.data = value;
        } else {
            Node current = head;
            for (int i = 0; i < index; i++) {
                current=current.next;
            }
            current.data=value;
        }
    }

    // 遍历链表并打印节点数据
    public void printList() {
        Node current = head;
        System.out.print("链表内容：");
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
    }

    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.addNode(1);
        list.addNode(2);
        list.addNode(3);
        list.insertNode(4, 1); // 在位置1插入节点值为4
        list.printList();
        list.updateNode(1,6);
        list.printList();
        list.deleteNode(1);
        list.printList();
    }
}
