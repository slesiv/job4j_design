package ru.job4j.collection;

import java.util.Iterator;

public class SimpleStack<T> {
    private ForwardLinked<T> linked = new ForwardLinked<T>();

    public T pop() {
        return linked.deleteFirst();
    }

    public void push(T value) {
        linked.add(value);
    }

    public static void main(String[] args) {
        SimpleStack<Integer> ss = new SimpleStack<>();
        ss.push(1);
        ss.push(2);
        ss.push(3);
        ss.push(4);
        ss.pop();
        Iterator<Integer> it = ss.linked.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
