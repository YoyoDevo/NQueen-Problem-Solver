package com.example.nqueen;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

public class GeneticBoard {

    int startingPopulation;
    ArrayList<Board> population;

    public GeneticBoard(ArrayList<Board> population, int start) {
        this.startingPopulation = start;
        this.population = population;
    }

    public GeneticBoard(ArrayList<Board> population) {
        startingPopulation = population.size();
        this.population = population;
    }

    public Board selectBest() {
        AttackCompare ac = new AttackCompare();
        PriorityQueue<Board> open = new PriorityQueue<>(1, ac);
        for(Board b : population) open.offer(b);
        return open.peek();
    }

    public void create() {
        Random r = new Random();
        int size = population.size();
        int length = population.get(0).boards.length;
        for(int i = 0; i < size; i += 2) {
            int r1 = r.nextInt(size/4);
            int r2 = r.nextInt(size);
            int[] parent1 = population.get(r1).boards;
            int[] parent2 = population.get(r2).boards;
            int[] child1 = Arrays.copyOf(parent1, length);
            int[] child2 = Arrays.copyOf(parent2, length);
            int crossover = r.nextInt(length - 1);
            while(crossover == 0 || crossover == length - 1) {
                crossover = r.nextInt(length - 1);
            }
            crossover(0,crossover, child1, parent2);
            crossover(0,crossover, child2, parent1);
            int mutation = r.nextInt(length);
            if((mutation & 1) == 1) {
                int a = r.nextInt(length - 1);
                int b = r.nextInt(length - 1);
                swap(child1, a, b);
                swap(child2, b, a);
            }
            mutate(child1);
            mutate(child2);
            population.add(new Board(child1));
            population.add(new Board(child2));
        }
    }

    public void mutate(int[] a) {
        Random r = new Random();
        int crossover = r.nextInt(a.length);
        int change = r.nextInt(a.length - 1);
        if(crossover != a.length) {
            while(change == a[crossover]) change = r.nextInt(a.length - 1);
            a[crossover] = change;
        }
    }

    public void crossover(int start, int end, int[] a, int[] b) {
        for(int i = start; i < end; i++) a[i] = b[i];
    }

    public void swap(int[] a, int b, int c){
        Random r = new Random();
        while(a[b] == c) c = r.nextInt(a.length - 1);
        a[b] = c;
    }

    public ArrayList<Board> select() {
        AttackCompare ac = new AttackCompare();
        PriorityQueue<Board> open = new PriorityQueue<>(1, ac);
        for(Board b : population) open.offer(b);
        int alive = startingPopulation / 2;
        population.clear();
        for(int i = 0; i < alive; i++) population.add(open.poll());
        return population;
    }
}