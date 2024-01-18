package com.damonyuan.test;

public interface IRingBuffer<E> {
    boolean offer(E element);

    E poll();

    boolean isEmpty();

    boolean isFull();

    int capacity();

    int size();
}
