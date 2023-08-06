package com.example.myapplication;

import java.util.Scanner;

public class ProblemB {
    public static void main(String[] args) {
        int q, m, s, l;
        Scanner scanner = new Scanner(System.in);
        q = scanner.nextInt();
        m = scanner.nextInt();
        s = scanner.nextInt();
        l = scanner.nextInt();
        int noOfTimesToCompleteLongerBatches = 0;

        while (l % m != 0 && s > 0) {
            l++;
            s -= q;
        }
        noOfTimesToCompleteLongerBatches = (l + m - 1) / m;
        if (s < 0) {
            s = 0;
        }

        int noOfTimeToCompleteS = (s + m - 1) / m;
        System.out.println((q * noOfTimesToCompleteLongerBatches) + noOfTimeToCompleteS);
    }
}


