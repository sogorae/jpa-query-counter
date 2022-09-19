package com.sogorae.jpaquerycounter;

import static com.sogorae.jpaquerycounter.OutputFile.*;

import java.util.List;

public class QueryCounter {

    private final long time;
    private final List<String> queries;
    private int count;

    public QueryCounter(final int count, final long time, final List<String> queries) {
        this.count = count;
        this.time = time;
        this.queries = queries;
    }

    public void addQueryCounting(String sql) {
        queries.add(sql);
        count++;
    }

    public String getResult() {
        String result = String.join(LINE_SEPARATOR, queries);
        return result + NPlusOneWarning.getWarningMessage(queries) +
            RESULT_SEPARATOR;
    }

    public int getCount() {
        return count;
    }

    public long getTime() {
        return time;
    }
}
