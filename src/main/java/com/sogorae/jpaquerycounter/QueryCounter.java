package com.sogorae.jpaquerycounter;

import java.util.List;

public class QueryCounter {

    private int count;
    private final long time;
    private final List<String> queries;

    public QueryCounter(final int count, final long time, final List<String> queries) {
        this.count = count;
        this.time = time;
        this.queries = queries;
    }

    public void addQueryCounting(String sql) {
        queries.add(sql);
        count++;
    }

    public String getQueriesToString() {
        return String.join("\n", queries);
    }

    public int getCount() {
        return count;
    }

    public long getTime() {
        return time;
    }
}
