package com.sogorae.jpaquerycounter;

import java.util.concurrent.atomic.AtomicLong;

public class Counter {

    private final AtomicLong count;
    private final Long time;

    public Counter(final AtomicLong count, final Long time) {
        this.count = count;
        this.time = time;
    }

    public AtomicLong getCount() {
        return count;
    }

    public Long getTime() {
        return time;
    }
}
