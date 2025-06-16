package org.pt.algo.demo;

import java.util.List;

public class ReverseLinkedList_206 {
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode reverseList(ListNode head) {
        ListNode c = head;
        ListNode p = null;
        while (c != null){
            // 存储当前遍历节点下一节点
            ListNode temp = c.next;
            c.next=p;
            p=c;
            c=temp;
        }
        return p;
    }
}
