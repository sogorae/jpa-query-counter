package com.sogorae.jpaquerycounter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

class OutputFile {

    void outputFile(String result) {
        LocalDateTime now = LocalDateTime.now();
        String fileName = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        String file = readFile(fileName);
        if (hasDuplicatedApi(file, result)) {
            return;
        }
        try (BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, true))) {
            fw.write(result);
            fw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String readFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    boolean hasDuplicatedApi(String file, String result) {
        assert result != null;
        if (file == null) {
            return false;
        }

        String[] results = file.split("---");
        return Arrays.stream(results)
            .anyMatch(element ->
                result.split("\n")[0].equals(element.split("\n")[0]));
    }
}
