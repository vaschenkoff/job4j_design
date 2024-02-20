package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ForwardLinked<T> implements Iterable<T> {
    private int size;
    private int modCount;
    private Node<T> head;

    public void add(T value) {
        Node<T> newNode = new Node<>(value, null);
        Node<T> currentNode = head;
        if (head == null) {
            head = newNode;
        } else {
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = newNode;
        }
        size++;
        modCount++;
    }

    public T get(int index) {
        Objects.checkIndex(index, size);
        Node<T> x = head;
        for (int i = 0; i < index; i++) {
            x = x.next;
        }
        return x.item;
    }

    public T deleteFirst() {
        Node<T> h = head;
        if (h == null) {
            throw new NoSuchElementException();
        }
        T element = h.item;
        Node<T> next = h.next;
        h.item = null;
        h.next = null;
        head = next;
        size--;
        modCount++;
        return element;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int point;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                return point < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return get(point++);
            }
        };

    }

    private static class Node<T> {
        private T item;
        private Node<T> next;

        Node(T element, Node<T> next) {
            this.item = element;
            this.next = next;
        }
    }
}
