package com.sogorae.jpaquerycounter;

import static com.sogorae.jpaquerycounter.OutputFile.*;

import java.util.ArrayList;
import java.util.Objects;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaInspector implements StatementInspector {

    private static final Logger log = LoggerFactory.getLogger(JpaInspector.class);

    private static final ThreadLocal<QueryCounter> queryCounters = new ThreadLocal<>();

    void start() {
        queryCounters.set(new QueryCounter(0, System.currentTimeMillis(), new ArrayList<>()));
    }

    void clear() {
        queryCounters.remove();
    }

    @Override
    public String inspect(final String sql) {
        log.info("sql = " + sql);
        QueryCounter queryCounter = queryCounters.get();
        if (Objects.nonNull(queryCounter)) {
            queryCounter.addQueryCounting(sql);
        }
        return sql;
    }

    public String getQueriesResult() {
        QueryCounter queryCounter = queryCounters.get();
        return "count : " + queryCounter.getCount() + LINE_SEPARATOR + queryCounter.getResult();
    }

    public long getDuration(final long currentTimeMillis) {
        QueryCounter queryCounter = queryCounters.get();
        return currentTimeMillis - queryCounter.getTime();
    }
}
