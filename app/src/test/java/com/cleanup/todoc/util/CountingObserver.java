package com.cleanup.todoc.util;

import androidx.lifecycle.Observer;

import java.util.concurrent.CountDownLatch;

public class CountingObserver<T> implements Observer<T> {
    private final CountDownLatch latch = new CountDownLatch(1);
    private T value;

    @Override
    public void onChanged(T t) {
        value = t;
        latch.countDown();
    }

    public T getValue() throws InterruptedException {
        latch.await();
        return value;
    }
}
