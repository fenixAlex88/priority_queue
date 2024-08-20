package ru.clevertec.utils.queue;

public interface CustomQueue<E> {
    boolean add(E e);
    E poll();
    E peek();
    int size();
    boolean isEmpty();
}
