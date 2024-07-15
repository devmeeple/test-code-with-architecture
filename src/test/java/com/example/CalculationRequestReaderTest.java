package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

class CalculationRequestReaderTest {

    @DisplayName("사용자로부터 입력 받는다")
    @Test
    void read() {
        // given
        CalculationRequestReader calculationRequestReader = new CalculationRequestReader();

        // when
        System.setIn(new ByteArrayInputStream("2 + 3".getBytes()));
        String[] result = calculationRequestReader.read();

        // then
        assertThat(result[0]).isEqualTo("2");
        assertThat(result[1]).isEqualTo("+");
        assertThat(result[2]).isEqualTo("3");
    }
}
