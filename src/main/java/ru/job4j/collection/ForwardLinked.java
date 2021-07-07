package ru.job4j.collection;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Stack;

public class ForwardLinked<T> implements Iterable {
    private Node<T> head;
    private Node<T> tail;

    public void add(T value) {
        Node<T> node = new Node<>(value, null);
        if (head == null) {
            head = node;
            return;
        }
        Node<T> tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = node;
    }

    public T deleteTail() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        T oldValue = tail.value;
        tail = head.next;
        return oldValue;
    }

    public T deleteFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        T oldValue = head.value;
        head = head.next;
        return oldValue;
    }

    public boolean revert() {
        if (head != null && head.next != null) {
            tail = head;
            Node<T> current = head.next;
            head.next = null;
            while (current != null) {
                Node<T> next = current.next;
                current.next = head;
                head = current;
                current = next;
            }
            return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> node = head;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T value = node.value;
                node = node.next;
                return value;
            }
        };
    }

    private static class Node<T> {
        T value;
        Node<T> next;

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        ForwardLinked<Integer> fl = new ForwardLinked<>();
        fl.add(1);
        fl.add(2);
        fl.add(3);
        fl.add(4);
        fl.revert();
        Iterator<Integer> it = fl.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        fl.deleteFirst();
        Iterator<Integer> it2 = fl.iterator();
        while (it2.hasNext()) {
            System.out.println(it2.next());
        }
    }
}
