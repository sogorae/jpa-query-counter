package com.sogorae.jpaquerycounter;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OutputFileTest {

    private final String testResult = "url: POST /api/jobs\ntime:291\ncount: 12\nselect * from jobs\n---\n";
    private String fileName;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        fileName = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        new OutputFile().write(testResult);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(fileName));
    }

    @Test
    @DisplayName("hasDuplicatedApi")
    void hasDuplicatedApi() {
        // given
        OutputFile outputFile = new OutputFile();

        // when
        outputFile.write(testResult);
        String actual = read();

        // then
        assertThat(actual).isEqualTo(testResult);
    }

    @Test
    @DisplayName("write")
    void write() {
        // given
        OutputFile outputFile = new OutputFile();

        // when
        String result = "url: PUT /api/jobs\ntime:300\ncount: 12\nselect * from jobs\n---\n";
        outputFile.write(result);
        String actual = read();

        // then
        assertThat(actual).isEqualTo(testResult + result);
    }

    private String read() {
        Path path = Path.of(fileName);
        if (!Files.exists(path)) {
            return "";
        }
        try {
            return Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
