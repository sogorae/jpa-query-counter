package com.sogorae.jpaquerycounter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

class OutputFile {

    static final String RESULT_SEPARATOR = String.format("---%s", System.lineSeparator());
    static final String LINE_SEPARATOR = System.lineSeparator();

    private static final String FILE_NAME = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));

    private final String file;

    OutputFile() {
        this.file = read();
    }

    void write(String result) {
        if (hasDuplicatedApi(result)) {
            return;
        }
        try (BufferedWriter fw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            fw.write(result);
            fw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String read() {
        Path path = Path.of(FILE_NAME);
        if (!Files.exists(path)) {
            return "";
        }
        try {
            return Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private boolean hasDuplicatedApi(String result) {
        assert result != null;
        if (file.isEmpty()) {
            return false;
        }

        String[] results = file.split(RESULT_SEPARATOR);
        return Arrays.stream(results)
            .anyMatch(element ->
                result.split(LINE_SEPARATOR)[0].equals(element.split(LINE_SEPARATOR)[0]));
    }
}
