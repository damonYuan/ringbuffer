package com.damonyuan.test;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertArrayEquals;

public class ProducerConsumerLiveTest {

    private final String[] shapes = { "Circle", "Triangle", "Rectangle", "Square", "Rhombus", "Trapezoid", "Pentagon", "Pentagram", "Hexagon", "Hexagram" };

    @Test
    public void givenACircularBuffer_whenInterleavingProducerConsumer_thenElementsMatch() throws Exception {
        CircularBuffer<String> buffer = new CircularBuffer<String>(shapes.length);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(new Producer<String>(buffer, shapes));
        Future<String[]> consumed = executorService.submit(new Consumer<String>(buffer, shapes.length));

        Object[] shapesConsumed = consumed.get(5L, TimeUnit.SECONDS);
        String[] shapesConsumedStringArray = Arrays.stream(shapesConsumed).toArray(String[]::new);
        assertArrayEquals(shapes, shapesConsumedStringArray);
    }
}
