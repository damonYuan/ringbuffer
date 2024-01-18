package com.damonyuan.test;

public class CircularBuffer<E> implements IRingBuffer<E> {

    private static final int DEFAULT_CAPACITY = 8;

    private final int capacity;
    private final E[] data;
    private volatile int writeSequence, readSequence;

    @SuppressWarnings("unchecked")
    public CircularBuffer(final int capacity) {
        this.capacity = (capacity < 1) ? DEFAULT_CAPACITY : capacity;
        this.data = (E[]) new Object[capacity];
        this.readSequence = 0;
        this.writeSequence = -1;
    }

    @Override
    public boolean offer(final E element) {
        synchronized (this) { // guarantee for multi-producer case
            if (isNotFull()) {
                /**
                 * A write to the volatile field writeSequence guarantees that the writes to the buffer happen before updating the sequence.
                 * At the same time, the volatile visibility guarantee ensures that the consumer will always see the latest value of writeSequence.
                 */
                int nextWriteSeq = writeSequence + 1;
                data[nextWriteSeq % capacity] = element;
                writeSequence++;
                return true;
            }
            return false;
        }
    }

    @Override
    public E poll() {
        synchronized (this) { // guarantee for multi-consumer case
            if (isNotEmpty()) {
                E nextValue = data[readSequence % capacity];
                readSequence++;
                return nextValue;
            }
            return null;
        }
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int size() {
        return (writeSequence - readSequence) + 1;
    }

    @Override
    public boolean isEmpty() {
        return writeSequence < readSequence;
    }

    @Override
    public boolean isFull() {
        return size() >= capacity;
    }

    private boolean isNotEmpty() {
        return !isEmpty();
    }

    private boolean isNotFull() {
        return !isFull();
    }
}
