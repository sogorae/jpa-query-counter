package com.sogorae.jpaquerycounter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class OutputFile {

    static final String RESULT_SEPARATOR = String.format("---%s", "\n");
    static final String LINE_SEPARATOR = "\n";

    private static final String FILE_NAME = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 결과"));

    void write(String result) {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            eraseFile(writer);
            writeFile(result, writer);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void eraseFile(final PrintWriter writer) {
        writer.print("");
    }

    private void writeFile(final String result, final PrintWriter writer) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(result);
        bufferedWriter.flush();
    }
}
