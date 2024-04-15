package com.immenser.tasks;

import java.util.LinkedList;
import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        int n = stdin.nextInt();    // кол-во оценок
        LinkedList<Integer> window = new LinkedList<>();
        int maxFives = slide(window, -1, 0, n, stdin);
        System.out.println(maxFives);
    }

    public static int[] fillWindow(LinkedList<Integer> window, int counter, int n, Scanner stdin) {
        if (counter <= n - 7) { // если количество оставшихся оценок не меньше 7
            if (window.size() > 0) {    // освобождаем окно, если в нем есть элементы
                window.clear();
            }
            int fives = 0;  // счетчик "5"
            int m = 10;    // текущая оценка
            while (m > 3 && window.size() < 7) {    // пока оценки выше "3" и окно не заполнено
                m = stdin.nextInt();
                counter++;  // увеличиваем счетчик оценок
                window.add(m);  // добавляем оценку в окно
                if (m == 5) {   // и при необходимости увеличиваем счетчик "5"
                    fives++;
                }
            }
            int[] windowParams = new int[] {counter, fives};    // возвращаемое значение - счетчик оценок и кол-во "5"
            if (m <= 3) {   // если оценка не выше "3", повторяем все, начиная со следующей оценки (если она есть)
                windowParams = fillWindow(window, counter, n, stdin);
            }
            return windowParams;
        }
        else {
            return null;    // если не существует окна, удовлетворяющего условиям
        }
    }

    public static int slide(LinkedList<Integer> window, int maxFives, int counter, int n, Scanner stdin) {
        if (counter <= n - 7) {         // если количество оставшихся оценок не меньше 7
            int[] windowParams = fillWindow(window, counter, n, stdin); // заполняем окно
            if (windowParams != null) { // если существует окно, удовлетворяющее условиям
                counter = windowParams[0];  // номер текущей оценки
                int fives = windowParams[1];    // кол-во "5" в полученном окне
                maxFives = Math.max(fives, maxFives);   // сравниваем maxFives с количеством "5" в полученном окне
                int m = 10;    // текущая оценка
                while (m > 3 && counter < n) {  // пока оценки выше "3"
                    m = stdin.nextInt();
                    counter++;
                    int firstMark = window.get(0);  // получаем первую оценку окна
                    if (firstMark == 5) {   // и при необходимости уменьшаем счетчик "5"
                        fives--;
                    }
                    window.remove(0);   // удаляем оценку из начала окна
                    window.add(m);  // добавляем новую оценку в конец окна
                    if (m == 5) {   // и при необходимости увеличиваем счетчик "5"
                        fives++;
                    }
                    maxFives = Math.max(fives, maxFives);   // сравниваем maxFives с количеством "5" в новом окне
                }
                if (m <= 3) {   // если оценка не выше "3", повторяем все, начиная со следующей оценки (если она есть)
                    maxFives = slide(window, maxFives, counter, n, stdin);
                }
            }
        }
        return maxFives;
    }
}