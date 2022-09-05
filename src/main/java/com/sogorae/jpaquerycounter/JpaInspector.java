package com.sogorae.jpaquerycounter;

import java.util.concurrent.atomic.AtomicLong;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaInspector implements StatementInspector {

    private static final Logger log = LoggerFactory.getLogger(JpaInspector.class);

    private static ThreadLocal<Counter> queryCount = new ThreadLocal<>();

    void start() {
        queryCount.set(new Counter(new AtomicLong(0), System.currentTimeMillis()));
    }

    void clear() {
        queryCount.remove();
    }

    public Counter getCount() {
        return queryCount.get();
    }

    @Override
    public String inspect(final String sql) {
        log.info("sql = " + sql);
        Counter counter = queryCount.get();
        if (counter != null) {
            AtomicLong count = counter.getCount();
            count.addAndGet(1);
        }
        return sql;
    }
}
