package org.pt.algo.stack;

public class LinkListStack {
    /*
    栈顶元素
     */
    private ListNode StackHead;
    /*
    栈的深度
     */
    private int size=0;

    class ListNode{
        private Integer value;
        private ListNode next;

        public ListNode(Integer value, ListNode node) {
            this.value = value;
            this.next = node;
        }

        public ListNode(Integer value) {
            this(value,null);
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public ListNode getNode() {
            return next;
        }

        public void setNode(ListNode node) {
            this.next = node;
        }

        @Override
        public String toString() {
            return  value.toString();
        }
    }

    /*
    出栈
     */
    public int pop(){
        int num=peek();
        StackHead=StackHead.next;
        size--;
        return num;
    }
    /*
    入栈
     */
    public void push(int num){
        ListNode listNode = new ListNode(num);
        listNode.next=StackHead;
        StackHead=listNode;
        size++;
    }

    /* 访问栈顶元素 */
    public int peek() {
        return StackHead.value;
    }

    @Override
    public String toString() {
        return StackHead+"->";
    }

    public static void main(String[] args) {
        LinkListStack linkListStack = new LinkListStack();
        linkListStack.push(1);
        linkListStack.push(2);
        linkListStack.push(3);
        linkListStack.push(4);
        System.out.println(linkListStack.pop());
        System.out.println(linkListStack.pop());
        System.out.println(linkListStack.pop());
        System.out.println(linkListStack.pop());
    }
}
