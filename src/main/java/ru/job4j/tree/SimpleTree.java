package ru.job4j.tree;

import java.util.*;
import java.util.function.Predicate;

public class SimpleTree<E> implements Tree<E> {
    private final Node<E> root;

    public SimpleTree(final E root) {
        this.root = new Node<>(root);
    }

    @Override
    public boolean add(E parent, E child) {
        if (findBy(child).isPresent()) {
            return false;
        }

        Optional<Node<E>> elParent = findBy(parent);
        if (elParent.isPresent()) {
            Node<E> nodeParent = elParent.get();
            nodeParent.children.add(new Node<>(child));
        }
        return true;
    }

    @Override
    public boolean isBinary() {
        Optional<Node<E>> rsl = findByPredicate((Node<E> el) -> el.children.size() > 2);
        return rsl.isEmpty();
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        return findByPredicate((Node<E> el) -> el.value.equals(value));
    }

    private Optional<Node<E>> findByPredicate(Predicate<Node<E>> condition) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (condition.test(el)) {
                rsl = Optional.of(el);
                break;
            }
            data.addAll(el.children);
        }
        return rsl;
    }
}