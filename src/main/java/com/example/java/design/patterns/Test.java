package com.example.java.design.patterns;

public class Test {

    public static void main(String[] args) {
        backToOrigin(10);
        System.out.println("hello world");
    }

    // 双向环形链表上回到原点走N步的路径数，只提供思路即可
    public static int backToOrigin(int n) {
        // n 为走的步数

        // 点的个数
        int length = 10;
        int[][] dp = new int[n+1][length];
        dp[0][0] = 1;
        // 走 i 步
        for(int i = 1; i <= n; ++i){
            // 到点 j
            for(int j = 0; j < length; ++j){
                // 走n步到0的方案数=走n-1步到1的方案数+走n-1步到9的方案数
                dp[i][j] = dp[i-1][(j - 1 + length) % length] + dp[i-1][(j + 1) % length];
                System.out.println(dp[i][j]);
            }
        }
        return dp[n][0];
    }

}

