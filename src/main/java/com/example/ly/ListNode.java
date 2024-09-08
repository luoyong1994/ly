package com.example.ly;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

/**
 * @description: ListNode
 * @author: ly
 * @create: 2024-07-21 14:18
 **/
@ConditionalOnBean
public class ListNode {

    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }

}
