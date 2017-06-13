package com.example.nqueen;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class RandomBoard {

    ArrayList<Board> boards = new ArrayList<Board>();
    int[] board;

    public RandomBoard(int size, int n) {
        board = new int[size];
        for(int i = 0; i < size; i++) {
            board[i] = i;
        }
        HashSet<String> set = new HashSet<>();
        int[] copy = Arrays.copyOf(board, board.length);
        int i = 0;
        while(true) {
            String temp = Arrays.toString(copy);
            if(set.add(temp)) {
                boards.add(new Board(copy));
                i++;
                if(i == n) {
                    break;
                }
            }
            shuffle(copy);
        }
    }

    public static void shuffle(int[] board) {
        int length = board.length;
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            int randy = i + r.nextInt(length - i);
            int rand = r.nextInt();
            if((rand & 1) == 1) {
                board[i] = randy;
            }
            else {
                swap(board, i, randy);
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}

