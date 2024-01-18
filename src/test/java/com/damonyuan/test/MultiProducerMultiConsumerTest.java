package com.damonyuan.test;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class MultiProducerMultiConsumerTest {
    private final String[] shapes = {"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A0"};
    private final String[] numbers = {"B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B0"};

    @Test
    public void givenMultipleProducerAndSingleConsumer_whenStartPubSub_thenItShouldSucceed() throws Exception {
        CircularBuffer<String> buffer = new CircularBuffer<String>(shapes.length);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.submit(new Producer<String>(buffer, shapes));
        executorService.submit(new Producer<String>(buffer, numbers));
        Future<String[]> consumed = executorService.submit(new Consumer<String>(buffer,
                shapes.length + numbers.length));

        Object[] shapesConsumed = consumed.get(5L, TimeUnit.SECONDS);
        assertEquals(shapes.length + numbers.length, shapesConsumed.length);
    }

    @Test
    public void givenMultipleProducerAndMultiConsumer_whenStartPubSub_thenItShouldSucceed() throws Exception {
        CircularBuffer<String> buffer = new CircularBuffer<String>(shapes.length);

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        executorService.submit(new Producer<String>(buffer, shapes));
        executorService.submit(new Producer<String>(buffer, numbers));
        Future<String[]> consumed = executorService.submit(new Consumer<String>(buffer, shapes.length));
        Future<String[]> consumed2 = executorService.submit(new Consumer<String>(buffer, numbers.length));

        Object[] shapesConsumed = consumed.get(5L, TimeUnit.SECONDS);
        Object[] numbersConsumed = consumed2.get(5L, TimeUnit.SECONDS);
        assertEquals(shapes.length + numbers.length, shapesConsumed.length + numbersConsumed.length);
    }
}
