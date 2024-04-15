package com.immenser.tasks;

import java.util.ArrayList;
import java.util.Scanner;

public class Task5 {
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        int n = stdin.nextInt();    // кол-во строк
        char[][] forest = new char[n + 2][5];
        for(int i = 0; i < n + 2; i++) {    // заполнение рамки
            for(int j = 0; j < 5; j++) {
                if (i == 0 || i == n + 1 || j == 0 || j == 4) {
                    forest[i][j] = 'X';
                }
            }
        }
        for(int i = 1; i < n + 1; i++) {    // заполнение леса
            char[] row = stdin.next().toCharArray();
            System.arraycopy(row, 0, forest[i], 1, 3);
        }
        stdin.close();

        int max = 0;
        for(int i = 1; i < 4; i++) {    // поиск максимального кол-ва грибов для каждой из 3-х возможных точек старта
            Cell cell = new Cell(1, i, forest[1][i]);
            max = Math.max(max, findMax(cell, forest, 0, 0));
        }
        System.out.println(max);
    }

    // функция возвращает максимальное кол-во грибов среди всех маршрутов из определенной точки старта
    public static int findMax(Cell cell, char[][] forest, int count, int max) {
        if (cell.value() != 'W') {  // если в текущей клетке куст, ничего не происходит
            if (cell.value() == 'C') {
                count++;
            }
            max = Math.max(count, max);
            // получаем для текущей клетки список доступных клеток
            ArrayList<Cell> availableCells = getAvailableCells(cell, forest);
                // для каждой из доступных клеток считаем кол-во грибов
                for (Cell availableCell : availableCells) {
                    max = findMax(availableCell, forest, count, max);
                }
        }
        return max;
    }

    // функция возвращает список доступных (среди соседних) клеток для текущей клетки
    public static ArrayList<Cell> getAvailableCells(Cell cell, char[][] forest) {
        ArrayList<Cell> availableCells = new ArrayList<>();
        for(Cell neighbourCell : getNeighbourCells(cell, forest)) {
            if (neighbourCell.value() != 'X') {
                availableCells.add(neighbourCell);
            }
        }
        return availableCells;
    }

    // функция возвращает список соседних клеток для текущей клетки
    public static ArrayList<Cell> getNeighbourCells(Cell cell, char[][] forest) {
        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(new Cell(cell.i() + 1, cell.j() - 1, forest[cell.i() + 1][cell.j() - 1]));
        cells.add(new Cell(cell.i() + 1, cell.j(), forest[cell.i() + 1][cell.j()]));
        cells.add(new Cell(cell.i() + 1, cell.j() + 1, forest[cell.i() + 1][cell.j() + 1]));
        return cells;
    }
}

record Cell(int i, int j, char value) {}
