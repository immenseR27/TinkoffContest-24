package com.immenser.tasks;

import java.util.ArrayList;
import java.util.Scanner;

public class Task4 {
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        int n = stdin.nextInt();    // размер матрицы
        String direction = stdin.nextLine().trim(); // направление поворота
        Point[][] matrix = new Point[n][n]; // исходная матрица

        for (int i = 0; i < n; i++) {   // заполнение матрицы
            for (int j = 0; j < n; j++) {
                long value = stdin.nextLong();
                matrix[i][j] = new Point(i, j, value);
            }
        }

        int count = 0;
        ArrayList<String> coords = new ArrayList<>();   // список координат точек, которые нужно поменять местами
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!matrix[i][j].isChanged()){ // если точка еще не обработана
                    // p1-p4 - точки, которые смещаются друг за другом при повороте
                    Point p1;
                    Point p2;
                    Point p3;
                    Point p4;
                    if (direction.equals("R")) {    // поворот вправо
                        p2 = matrix[j][n - i - 1];
                        p4 = matrix[n - j - 1][i];
                    }
                    else {  // поворот влево
                        p2 = matrix[n - j - 1][i];
                        p4 = matrix[j][n - i - 1];
                    }
                    p1 = matrix[i][j];
                    p3 = matrix[n - i - 1][n - j - 1];

                    p1.setChanged(true);
                    p2.setChanged(true);
                    p3.setChanged(true);
                    p4.setChanged(true);

                    long v1 = p1.getValue();
                    long v2 = p2.getValue();
                    long v3 = p3.getValue();
                    long v4 = p4.getValue();

                    if (!(v1 == v2 && v2 == v3 && v3 == v4)) {  // если хотя бы две точки нужно менять местами
                        // если все соседние точки попарно не равны
                        if (v1 != v2 && v2 != v3 && v3 != v4 && v4 != v1) {
                            count += 3;
                            tripleReplace(p1, p2, p3, p4, coords);
                        }
                        // если равны только две какие-то соседние точки
                        if (v1 == v2 ^ v2 == v3 ^ v3 == v4 ^ v4 == v1) {
                            count += 2;
                            if (v1 == v2) {
                                doubleReplace(p1, p3, p4, coords, direction);
                                break;
                            }
                            if (v2 == v3) {
                                doubleReplace(p1, p2, p4, coords, direction);
                                break;
                            }
                            if (v3 == v4) {
                                doubleReplace(p1, p2, p3, coords, direction);
                                break;
                            }
                            doubleReplace(p2, p3, p4, coords, direction);
                        }
                        // если равны три точки или попарно равны по две соседние точки
                        if (v1 == v2 && v2 == v3 || v2 == v3 && v3 == v4 || v3 == v4 && v4 == v1 || v4 == v1 && v1 == v2
                                || v1 == v2 && v3 == v4 || v4 == v1 && v2 == v3){
                            count += 1;
                            if (v1 == v2 && v2 == v3) {
                                singleReplace(p1, p4, coords);
                                break;
                            }
                            if (v2 == v3 && v3 == v4) {
                                singleReplace(p1, p2, coords);
                                break;
                            }
                            if (v3 == v4 && v4 == v1) {
                                singleReplace(p2, p3, coords);
                                break;
                            }
                            if (v4 == v1 && v1 == v2) {
                                singleReplace(p3, p4, coords);
                                break;
                            }
                            if (v1 == v2) {
                                singleReplace(p1, p3, coords);
                                break;
                            }
                            singleReplace(p2, p4, coords);
                        }
                    }
                }
            }
        }
        System.out.println(count);
        for (String str : coords){
            System.out.println(str);
        }
    }

    // функция меняет местами 2 точки (1 перестановка)
    public static void singleReplace(Point p1, Point p2, ArrayList<String> coords){
        long temp = p1.getValue();
        p1.setValue(p2.getValue());
        p2.setValue(temp);
        coords.add(coordsString(p1, p2));
    }

    // функция меняет местами 3 точки в зависимости от направления поворота (2 перестановки)
    public static void doubleReplace(Point p1, Point p2, Point p3, ArrayList<String> coords, String direction){
        long temp = p1.getValue();
        p1.setValue(p2.getValue());
        p2.setValue(p3.getValue());
        p3.setValue(temp);
        if (direction.equals("R")) {
            coords.add(coordsString(p1, p2));
            coords.add(coordsString(p2, p3));
        }
        else {
            coords.add(coordsString(p1, p3));
            coords.add(coordsString(p3, p2));
        }
    }

    // функция по очереди меняет местами все 4 точки (3 перестановки)
    public static void tripleReplace(Point p1, Point p2, Point p3, Point p4, ArrayList<String> coords){
        long temp = p1.getValue();
        p1.setValue(p4.getValue());
        p4.setValue(p3.getValue());
        p3.setValue(p2.getValue());
        p2.setValue(temp);
        coords.add(coordsString(p1, p4));
        coords.add(coordsString(p4, p3));
        coords.add(coordsString(p3, p2));
    }

    public static String coordsString(Point p1, Point p2){
        return String.format("%d %d %d %d", p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
}

class Point {
    private int x;
    private int y;
    private long value;
    private boolean changed;    // флаг для проверки, обработан ли элемент матрицы

    public Point(int x, int y, long value) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.changed = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    @Override
    public boolean equals(Object ob) {
        if (ob == this) {
            return  true;
        }
        if (ob.getClass() != this.getClass()) {
            return false;
        }
        return this.value == ((Point) ob).value;
    }
}
