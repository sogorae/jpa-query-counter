package com.sogorae.jpaquerycounter;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OutputFileTest {

    @Test
    @DisplayName("hasDuplicatedApi")
    void hasDuplicatedApi() {
        // given
        OutputFile outputFile = new OutputFile();

        // when
        boolean actual = outputFile.hasDuplicatedApi("url: POST /api/jobs\ntime:291\ncount: 12\nselect * from jobs\n",
            "url: POST /api/jobs\ntime:99\ncount: 12\nselect * from jobs\n");

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("write")
    void write() {
        // given
        OutputFile outputFile = new OutputFile();

        // when
        outputFile.outputFile("url: POST /api/jobs\ntime:291\ncount: 12\nselect * from jobs\n");

        // then

    }

}
