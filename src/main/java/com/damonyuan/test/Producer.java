package com.damonyuan.test;

import java.util.concurrent.locks.LockSupport;

class Producer<T> implements Runnable {

    private final CircularBuffer<T> buffer;
    private final T[] items;

    public Producer(final CircularBuffer<T> buffer, final T[] items) {
        this.buffer = buffer;
        this.items = items;
    }

    @Override
    public void run() {

        for (int i = 0; i < items.length; ) {
            if (buffer.offer(items[i])) {
                System.out.println("Produced: " + items[i]);
                i++;
                LockSupport.parkNanos(3); // give other producer a chance to insert
            } else {
                System.out.println("Produced failed: " + items[i]);
            }
        }
    }
}
