package com.cleanup.todoc.util;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.cleanup.todoc.model.Task;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LiveDataTestUtil {

    public static List<Task> getOrAwaitValue(final LiveData<List<Task>> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<List<Task>> observer = new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                data[0] = tasks;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        if (!latch.await(2, TimeUnit.SECONDS)) {
            throw new RuntimeException("LiveData value was never set.");
        }
        return (List<Task>) data[0];
    }
}
