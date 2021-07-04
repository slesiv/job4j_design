package ru.job4j.collection.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleLinkedList<E> implements List {
    private int size = 0;
    private int modCount = 0;
    private Node<E> first;
    private Node<E> last;

    public SimpleLinkedList() {
        this.first = null;
        this.last = null;
    }

    @Override
    public void add(Object value) {
        /* This is my first implementation, it worked, but after checking in LinkedList, I fixed it to the following
        After checking by the mentor, I will delete the old implementation
        if (size == 0) {
            first = new Node<E>(null, (E) value, null);
            last = first;
        } else if (size == 1) {
            last = new Node<E>(first, (E) value, null);
            first.next = last;
        } else {
            Node<E> newElement = new Node<E>(last, (E) value, null);
            last.next = newElement;
        }*/
        Node<E> l = last;
        Node<E> newElement = new Node<E>(l, (E) value, null);
        last = newElement;
        if (l == null) {
            first = newElement;
        } else {
            l.next = newElement;
        }
        size++;
        modCount++;
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);

        if (index < (size >> 1)) { // index < (size >> 1) <-> index < (size / 2)
            Node<E> elementX = first;
            for (int i = 0; i < index; i++) {
                elementX = elementX.next;
            }
            return elementX.item;
        } else {
            Node<E> elementX = last;
            for (int i = size - 1; i > index; i--) {
                elementX = elementX.prev;
            }
            return elementX.item;
        }
    }

    @Override
    public Iterator iterator() {
        return new Iterator<E>() {
            private int expectedModCount = modCount;
            protected int indexIt = 0;
            Node<E> elementIt = first;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return size > indexIt;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (indexIt == 0) {
                    indexIt++;
                    return elementIt.item;
                }
                indexIt++;
                elementIt = elementIt.next;
                return elementIt.item;
            }
        };
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        SimpleLinkedList sl = new SimpleLinkedList();
        sl.add(1);
        sl.add(2);
        Iterator<Integer> list = sl.iterator();
        while (list.hasNext()) {
            System.out.println(list.next());
        }
    }
}
