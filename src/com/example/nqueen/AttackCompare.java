package com.example.nqueen;


import java.util.Comparator;

public class AttackCompare implements Comparator<Board> {
    @Override
    public int compare(Board x, Board y) {
        return x.attacks - y.attacks;
    }
}
