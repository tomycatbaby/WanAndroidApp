package com.lzf.wanandroidapp.utils;

public class S {
    //
//    给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
//
//    设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
//
//    注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
//
//    示例 1:
//
//    输入: [7,1,5,3,6,4]
//    输出: 7
//    解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
//    随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
//    示例 2:
//
//    输入: [1,2,3,4,5]
//    输出: 4
//    解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
//    注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。
//    因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
//    示例 3:
//
//    输入: [7,6,4,3,1]
//    输出: 0
//    解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。

    class Solution {
        public int maxProfit(int[] prices) {
            int chiyou = 0;
            boolean maile = false;
            int sum = 0;
            for (int i = 0; i < prices.length - 1; i++) {
                //小于后一个，去持有股票
                if (prices[i] < prices[i + 1] && !maile) {
                    chiyou = prices[i];
                    maile = true;
                }
                //降了，卖出股票
                if (prices[i] > prices[i + 1] && maile) {
                    int chajia = prices[i] - chiyou;
                    sum += chajia;
                    chiyou = 0;
                    maile = false;
                }
            }
            if (maile) {
                int chajia = prices[prices.length - 1] - chiyou;
                sum += chajia;
            }
            return sum;
        }
    }

    /*    class Solution {
            public ListNode removeElements(ListNode head, int val) {
                ListNode sentinel = new ListNode(0);//前序节点，它的后面的那个就是完成的链表的头结点
                sentinel.next = head;//伪节点的后序节点的引用指向的是链表头

                ListNode prev = sentinel, curr = head;//当前节点持有需要遍历的链表的头节点引用，前序节点持有伪节点的引用
                while (curr != null) {
                    if (curr.val == val)//如果当前节点是需要删除的那个节点
                        prev.next = curr.next;//就将前序节点指向当前节点的下一个节点
                    else prev = curr;//不是当前节点，就将前序节点指向当前节点
                    curr = curr.next;//当前节点向下移一位
                }
                return sentinel.next;
            }

        }
    */
 /*   public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
*/

}
