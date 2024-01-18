package com.damonyuan.test;

import java.util.concurrent.atomic.AtomicInteger;

public class LockFreeRingBuffer<E> implements IRingBuffer<E> {
    private static final int DEFAULT_CAPACITY = 8;
    private final int capacity;
    private final int mask;
    private final E[] data;
    private AtomicInteger head; // next read
    private AtomicInteger tail; // next write

    public LockFreeRingBuffer(final int capacity) {
        this.capacity = (capacity < 1) ? DEFAULT_CAPACITY : findPowerOfTwo(capacity);
        this.mask = capacity - 1;
        this.data = (E[]) new Object[capacity];
        this.head = new AtomicInteger(0);
        this.tail = new AtomicInteger(0);
    }

    private int findPowerOfTwo(final int capacity) {
        int result = capacity - 1;
        result |= result >> 1;
        result |= result >> 2;
        result |= result >> 4;
        result |= result >> 8;
        result |= result >> 16;
        result++;
        return result;
    }

    @Override
    public boolean offer(final E element) {
        return false;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int size() {
        return 0;
    }
}
