package org.pt.algo.demo;

/*
给你两个单链表的头节点 headA 和 headB ，
请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
 */
public class InterLinkedList_160 {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
    /*
    遍历第一个链表，遍历第二个链表，走的步数是一样的
     */

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode A = headA, B = headB;
        while (A != B) {
            if (A != null) {
                A = A.next;
            } else {
                A = headB;
            }
            if (B != null) {
                B = B.next;
            } else {
                B = headA;
            }
        }

        return A;
    }
}
