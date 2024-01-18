package com.damonyuan.test;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.LockSupport;

class Consumer<T> implements Callable<T[]> {

    private final CircularBuffer<T> buffer;
    private final int expectedCount;

    public Consumer(final CircularBuffer<T> buffer, final int expectedCount) {
        this.buffer = buffer;
        this.expectedCount = expectedCount;
    }

    @Override
    public T[] call() throws Exception {
        final T[] items = (T[]) new Object[expectedCount];
        for (int i = 0; i < items.length; ) {
            final T item = buffer.poll();
            if (item != null) {
                items[i++] = item;
                LockSupport.parkNanos(5); // mimic slow consumer
                System.out.println("Consumed: " + item);
            }
        }
        return items;
    }
}
