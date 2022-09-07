package com.sogorae.jpaquerycounter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaInspector implements StatementInspector {

    private static final Logger log = LoggerFactory.getLogger(JpaInspector.class);

    private static final ThreadLocal<Counter> queryCount = new ThreadLocal<>();
    private static final List<String> sqlQueries = new ArrayList<>();

    void start() {
        queryCount.set(new Counter(new AtomicLong(0), System.currentTimeMillis()));
    }

    void clear() {
        queryCount.remove();
        sqlQueries.clear();
    }

    public Counter getCount() {
        return queryCount.get();
    }

    @Override
    public String inspect(final String sql) {
        log.info("sql = " + sql);
        queryCounting();
        sqlQueries.add(sql);
        return sql;
    }

    private void queryCounting() {
        Counter counter = getCount();
        if (counter != null) {
            AtomicLong count = counter.getCount();
            count.addAndGet(1);
        }
    }

    public String getResult() {
        StringBuilder result = new StringBuilder();
        if (warningQuery()) {
            result.append("\n").append("N+1 쿼리 위험 감지");
        }
        result.append(String.join("\n", sqlQueries));
        return result.toString();
    }

    private boolean warningQuery() {
        long count = sqlQueries.stream()
                .filter(i -> i.toLowerCase().contains("join"))
                .count();
        return count > 2;
    }
}
