package com.scottlogic.gradtrader;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.google.inject.Singleton;

@Singleton
public class ScheduledExecutor extends ScheduledThreadPoolExecutor {

    private final static int poolSize = 10;

    public ScheduledExecutor() {
        super(poolSize);
    }

}
