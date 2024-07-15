package com.example;

import com.example.exception.BadRequestException;
import com.example.exception.InvalidOperatorException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CalculationRequestTest {

    @DisplayName("유효한 숫자를 파싱할 수 있다")
    @Test
    void shouldParseValidNumber() {
        // given
        String[] parts = new String[]{"2", "+", "3"};

        // when
        CalculationRequest sut = new CalculationRequest(parts);

        // then
        assertThat(sut.getNum1()).isEqualTo(2);
        assertThat(sut.getOperator()).isEqualTo("+");
        assertThat(sut.getNum2()).isEqualTo(3);
    }

    @DisplayName("유효한 세자리 숫자를 파싱할 수 있다")
    @Test
    void shouldParseValidThreeNumbers() {
        // given
        String[] parts = new String[]{"232", "+", "123"};

        // when
        CalculationRequest sut = new CalculationRequest(parts);

        // then
        assertThat(sut.getNum1()).isEqualTo(232);
        assertThat(sut.getOperator()).isEqualTo("+");
        assertThat(sut.getNum2()).isEqualTo(123);
    }

    @DisplayName("문자열 배열의 길이가 3이 아닐 경우 에러를 던진다")
    @Test
    void shouldBadRequestException() {
        // given
        String[] parts = new String[]{"232", "+"};

        // when + then
        assertThatThrownBy(() -> new CalculationRequest(parts)).isInstanceOf(BadRequestException.class);
    }

    @DisplayName("유효한 연산자(+, -, *, /)를 입력하지 않으면 에러를 던진다")
    @Test
    void shouldInvalidOperatorException() {
        // given
        String[] parts = new String[]{"232", "x", "123"};

        // when + then
        assertThatThrownBy(() -> new CalculationRequest(parts)).isInstanceOf(InvalidOperatorException.class);
    }

    @DisplayName("유효하지 않은 길이의 연산자(+, -, *, /)를 입력하지 않으면 에러를 던진다")
    @Test
    void shouldInvalidOperatorException2() {
        // given
        String[] parts = new String[]{"232", "++", "123"};

        // when + then
        assertThatThrownBy(() -> new CalculationRequest(parts)).isInstanceOf(InvalidOperatorException.class);
    }

}
