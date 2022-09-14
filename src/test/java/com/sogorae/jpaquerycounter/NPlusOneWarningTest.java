package com.sogorae.jpaquerycounter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NPlusOneWarningTest {

    @Test
    void isWarning() {
        List<String> sqls = List.of("select ... from", "select ... from", "select ... from");

        String warningMessage = NPlusOneWarning.getWarningMessage(sqls);

        assertThat(warningMessage).isEqualTo("\nN+1이 의심됩니다."+"\n\n");
    }

    @Test
    @DisplayName("select만 일치하고, 내용이 다른 경우는 경고로 보지 않는다.")
    void onlyMatchSelect() {
        List<String> sqls = List.of("select * from", "select ... from", "select ... from");

        String warningMessage = NPlusOneWarning.getWarningMessage(sqls);

        assertThat(warningMessage).isEqualTo("\n\n");
    }
}
