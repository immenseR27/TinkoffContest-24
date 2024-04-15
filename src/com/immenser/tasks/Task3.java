package com.immenser.tasks;

import java.util.Objects;
import java.util.Scanner;
import java.util.TreeSet;

public class Task3 {
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        Node root = null;   // корневой узел
        int n = stdin.nextInt();    // кол-во строк
        for (int i = 0; i < n; i++) {   // для каждой строки
            String[] names = stdin.next().split("/");
            if (root == null) { // задаем имя корневого узла
                root = new Node(names[0]);
            }
            buildTree(root, names); // на основе строки заполняем дерево потомков от корневого узла
        }
        assert root != null;
        // проходим по дереву и по порядку выводим все узлы
        recoursePrint(root, new StringBuilder());
    }

    public static void recoursePrint(Node parentNode, StringBuilder ws) {
        // выводим имя текущего узла
        System.out.println(ws + parentNode.getName());
        // переходим на уровень ниже
        ws.append("  ");
        // для каждого из дочерних узлов текущего узла
        for (Node childNode : parentNode.getChildren()) {
            if (childNode.getChildren().size() != 0) {  // если у дочернего узла есть потомки
                recoursePrint(childNode, ws);   // вызываем для него recoursePrint
            }
            else {  // если у дочернего узла нет потомков
                System.out.println(ws + childNode.getName());   // выводим его имя
            }
        }
        ws.delete(0, 2);    // возвращаемся на уровень выше
    }

    // функция строит дерево потомков
    public static void buildTree(Node root, String[] names) {
        Node parentNode = root;
        for (int i = 0; i < names.length - 1; i++) {
            Node childNode = new Node(names[i + 1]);
            // если потомок с таким именем уже добавлен, объект с соответствующим именем становится родительским узлом
            if (parentNode.getChildren().contains(childNode)){
                parentNode = getChild(parentNode, childNode);
            }
            // если потомка с таким именем еще нет, родительским узлом становится новый объект с таким именем
            else {
                parentNode.getChildren().add(childNode);
                parentNode = childNode;
            }
        }
    }

    // функция возвращает для родительского узла дочерний узел с именем childNode.getName(), если он существует
    public static Node getChild(Node parentNode, Node childNode) {
        for (Node node : parentNode.getChildren()) {
            if (node.equals(childNode)) {
                return node;
            }
        }
        return null;
    }
}

class Node implements Comparable<Node>{
    private String name;    // имя узла
    private TreeSet<Node> children; // прямые потомки узла

    public Node(String name) {
        this.name = name;
        this.children = new TreeSet<>();
    }

    public String getName() {
        return name;
    }

    public TreeSet<Node> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Node node = (Node) obj;
        return Objects.equals(this.name, node.name);
    }

    @Override
    public int compareTo(Node node) {
        return this.name.compareTo(node.name);
    }
}
