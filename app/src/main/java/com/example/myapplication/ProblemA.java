package com.example.myapplication;

import java.util.Scanner;

public class ProblemA {

    public static void main(String[] args) {
        float x1, y1, x2, y2, p;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            String[] inputs = line.split(" ");
            if (inputs.length == 5) {
                boolean areNegativeValues = false;
                for(String str : inputs) {
                    if(Float.parseFloat(str)< 0.0) {
                        areNegativeValues = true;
                        break;
                    }
                }
                if(!areNegativeValues) {
                    x1 = Float.parseFloat(inputs[0]);

                    y1 = Float.parseFloat(inputs[1]);
                    x2 = Float.parseFloat(inputs[2]);
                    y2 = Float.parseFloat(inputs[3]);
                    p = Float.parseFloat(inputs[4]);

                    double shortestPath = Math.pow(Math.pow(Math.abs(x1 - x2), p) + Math.pow(Math.abs(y1 - y2), p), 1.0/p);
                    System.out.println(String.format("%.10f", shortestPath));
                }
            } else {
                break;
            }
        }
    }
}
