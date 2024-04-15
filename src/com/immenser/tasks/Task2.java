package com.immenser.tasks;

import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        int m = stdin.nextInt();
        int n = stdin.nextInt();
        long[][] output = new long[n][m];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                long x = stdin.nextLong();
                output[j][m - i - 1] = x;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(output[i][j] + " ");
            }
            System.out.println();
        }
    }
}
