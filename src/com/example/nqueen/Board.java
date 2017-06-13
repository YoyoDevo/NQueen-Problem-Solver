package com.example.nqueen;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {

    public static final int N_QUEENS = 20;
    int[] boards;
    int attacks;
    Random rand = new Random();

    public Board() {
        boards = new int[N_QUEENS];
        for (int i = 0; i < N_QUEENS; i++) {
            boards[i] = i;
        }
        attacks = getAttacks();
    }

    public Board(int[] boards) {
        this.boards = Arrays.copyOf(boards, boards.length);
        attacks = getAttacks();
    }

    private int getAttacks() {
        int result = 0;
        for (int i = 0; i < boards.length; i++) {
            for (int j = i + 1; j < boards.length; j++) {
                if ((boards[i] == boards[j]) || (Math.abs(i - j) == Math.abs(boards[i] - boards[j]))) result++;
            }
        }
        return result;
    }

    private int getAttacks(int row, int col) {
        int result = 0;
        for (int i = 0; i < boards.length; i++) {
            if (i == col) continue;
            int j = boards[i];
            if (j == row || Math.abs(j-row) == Math.abs(i-col)) result++;
        }
        return result;
    }

    public Board solveClimb() {
        int min = attacks;
        Board result = this;
        for (int i = 0; i < boards.length; i++) {
            int previous = boards[i];
            for (int j = 0; j < boards.length; j++) {
                if(boards[i] != j) {
                    swap(i,j);
                    int temp = getAttacks();
                    if(temp <= min) {
                        result = new Board(boards);
                        min = temp;
                    }
                    swap(i,previous);
                    if(temp == 0) break;
                }
            }
        }
        return result;
    }

    public void swap(int index, int move) {
        boards[index] = move;
    }
}
