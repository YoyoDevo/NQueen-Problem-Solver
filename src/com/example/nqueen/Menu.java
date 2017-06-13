package com.example.nqueen;


import java.util.Scanner;

public class Menu {

    static int size = 20;

    public static void main(String[] args) {
        int solved = 0;
        int averageCost = 0;
        int averageAttacks = 0;
        int n = 0;
        long startTime, endTime;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a number of N-Queen instances to solve (>=100):");
        while (n < 100) {
            n = sc.nextInt();
            if (n < 100) System.out.println("Enter a number greater than or equal to 100");
        }
        RandomBoard rb = new RandomBoard(size, n);
        RandomBoard rbCopy = rb;
        System.out.println("==============================================");
        System.out.println("Straight-Forward Steepest-Ascent Hill Climbing");
        startTime = System.currentTimeMillis();
        for (Board b: rb.boards) {
            int cost = 1;
            Board temp = b.solveClimb();
            int parent = temp.attacks;
            while (temp.attacks != 0) {
                temp = temp.solveClimb();
                cost++;
                if (parent == temp.attacks) break;
                parent = temp.attacks;
            }
            averageCost += cost;
            averageAttacks += temp.attacks;
            if (temp.attacks == 0) solved++;
        }
        endTime = System.currentTimeMillis();
        System.out.println("Attempted to solve " + n + " boards");
        System.out.printf("Solve Percentage: %.2f%%\n", solved/(double)n * 100);
        System.out.println("Average search cost:" + averageCost/(double)n);
        System.out.println("Average Queens Still Attacking at the End: " + averageAttacks/(double)n);
        System.out.println("Average time to solve:" + ((endTime - startTime)/(double)n) + " ms");
        System.out.println("Total time to solve all boards: " + (endTime - startTime) + " ms");
        System.out.println("==============================================");
        System.out.println("Genetic Algorithm");
        GeneticBoard gb = new GeneticBoard(rbCopy.boards);
        startTime = System.currentTimeMillis();
        gb.select();
        int generations = 0;
        while(true) {
            gb.select();
            gb.create();
            generations++;
            Board best = gb.selectBest();
            if(best.attacks == 0) {
                endTime = System.currentTimeMillis();
                System.out.println("Generations passed to solve this board: " + generations);
                System.out.println("Total time to solve this board: " + (endTime - startTime) + " ms");
                break;
            }
            if(generations > 10000) {
                gb = new GeneticBoard(rbCopy.boards);
            }
        }
    }
}