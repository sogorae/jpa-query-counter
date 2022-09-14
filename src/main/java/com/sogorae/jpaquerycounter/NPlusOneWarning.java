package com.sogorae.jpaquerycounter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NPlusOneWarning {

    private static final int WARNING_STANDARD_COUNT = 2;
    private static final String WARNING_STANDARD_WARDING = "select";
    private static final String WARNING_MESSAGE = "\nN+1이 의심됩니다.";
    private static final String LINE_BREAK = "\n\n";

    public static String getWarningMessage(List<String> sql) {
        if (isWarning(sql)) {
            return WARNING_MESSAGE + LINE_BREAK;
        }
        return LINE_BREAK;
    }

    private static boolean isWarning(List<String> sql) {
        final Map<String, Integer> nPlusOneWaring = createNPlusOneWaring(sql);
        for (Integer value : nPlusOneWaring.values()) {
            if (value > WARNING_STANDARD_COUNT) {
                return true;
            }
        }
        return false;
    }

    private static Map<String, Integer> createNPlusOneWaring(final List<String> sql) {
        final Map<String, Integer> nPlusOneWaring = new HashMap<>();
        sql.stream()
                .filter(i -> i.contains(WARNING_STANDARD_WARDING))
                .forEach(i -> nPlusOneWaring.put(i, nPlusOneWaring.getOrDefault(i, 0) + 1));
        return nPlusOneWaring;
    }
}
