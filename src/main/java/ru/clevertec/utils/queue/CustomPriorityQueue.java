package ru.clevertec.utils.queue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class CustomPriorityQueue<E> implements CustomQueue<E> {
    private static final int INITIAL_CAPACITY = 8;
    private E[] heap;
    private int size = 0;
    private final Comparator<? super E> comparator;

    @SuppressWarnings("unchecked")
    public CustomPriorityQueue(int capacity, Comparator<? super E> comparator) {
        if (capacity < 0)
            throw new IllegalArgumentException();
        this.heap = (E[]) new Object[capacity];
        this.comparator = comparator;
    }

    public CustomPriorityQueue() {
        this(INITIAL_CAPACITY, null);
    }

    public CustomPriorityQueue(int capacity) {
        this(capacity, null);
    }

    public CustomPriorityQueue(Comparator<? super E> comparator) {
        this(INITIAL_CAPACITY, comparator);
    }

    public CustomPriorityQueue(E[] heap) {
        this(heap.length, null);
        for (E el : heap) {
            add(el);
        }
    }

    public CustomPriorityQueue(E[] heap, Comparator<? super E> comparator) {
        this(heap.length, comparator);
        for (E el : heap) {
            add(el);
        }
    }

    public CustomPriorityQueue(Collection<? extends E> collection) {
        this(collection.size(), null);
        for (E el : collection) {
            add(el);
        }
    }

    public CustomPriorityQueue(Collection<? extends E> collection, Comparator<? super E> comparator) {
        this(collection.size(), comparator);
        for (E el : collection) {
            add(el);
        }
    }

    @Override
    public boolean add(E el) {
        if (el == null) {
            throw new IllegalArgumentException("Element should not be null");
        }
        if (size == heap.length) {
            resize();
        }
        heap[size] = el;
        siftUp(size);
        size++;
        return true;
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }
        E result = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        siftDown(0);
        return result;
    }

    @Override
    public E peek() {
        return size == 0 ? null : heap[0];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (compare(heap[index], heap[parentIndex]) >= 0) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    private void siftDown(int index) {
        int leftChildIndex;
        while ((leftChildIndex = 2 * index + 1) < size) {
            int rightChildIndex = leftChildIndex + 1;
            int smallerChildIndex = leftChildIndex;
            if (rightChildIndex < size && compare(heap[rightChildIndex], heap[leftChildIndex]) < 0) {
                smallerChildIndex = rightChildIndex;
            }
            if (compare(heap[index], heap[smallerChildIndex]) <= 0) {
                break;
            }
            swap(index, smallerChildIndex);
            index = smallerChildIndex;
        }
    }

    @SuppressWarnings("unchecked")
    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        } else if (e1 instanceof Comparable && e2 instanceof Comparable) {
            return ((Comparable<? super E>) e1).compareTo(e2);
        } else {
            throw new IllegalArgumentException("Elements must be comparable or a comparator must be provided");
        }
    }

    private void swap(int i, int j) {
        E temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void resize() {
        heap = Arrays.copyOf(heap, heap.length == 0 ? INITIAL_CAPACITY : heap.length * 2);
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        int elementsInLevel = 1;
        int index = 0;
        while (index < size) {
            for (int i = 0; i < elementsInLevel && index < size; i++) {
                sb.append(heap[index++]).append(" ");
            }
            sb.append("\n");
            elementsInLevel *= 2;
        }
        return sb.toString();
    }
}