package com.example;

import com.example.exception.InvalidOperatorException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CalculatorTest {

    @DisplayName("덧셈 연산을 할 수 있다")
    @Test
    public void calculate() {
        // given
        long num1 = 2;
        String operator = "+";
        long num2 = 3;

        Calculator calculator = new Calculator();

        // when
        long result = calculator.calculate(num1, operator, num2);

        // then
        assertThat(result).isEqualTo(5);
    }

    @DisplayName("뺄셈 연산을 할 수 있다")
    @Test
    public void calculate2() {
        // given
        long num1 = 2;
        String operator = "-";
        long num2 = 3;

        Calculator calculator = new Calculator();

        // when
        long result = calculator.calculate(num1, operator, num2);

        // then
        assertThat(result).isEqualTo(-1);
    }

    @DisplayName("곱셈 연산을 할 수 있다")
    @Test
    public void calculate3() {
        // given
        long num1 = 2;
        String operator = "*";
        long num2 = 3;

        Calculator calculator = new Calculator();

        // when
        long result = calculator.calculate(num1, operator, num2);

        // then
        assertThat(result).isEqualTo(6);
    }

    @DisplayName("나눗셈 연산을 할 수 있다")
    @Test
    public void calculate4() {
        // given
        long num1 = 6;
        String operator = "/";
        long num2 = 3;

        Calculator calculator = new Calculator();

        // when
        long result = calculator.calculate(num1, operator, num2);

        // then
        assertThat(result).isEqualTo(2);
    }

    @DisplayName("잘못된 연산자를 입력하면 에러가 발생한다")
    @Test
    public void error() {
        // given
        long num1 = 6;
        String operator = "x";
        long num2 = 3;

        Calculator calculator = new Calculator();

        // when

        // then
        assertThatThrownBy(() -> calculator.calculate(num1, operator, num2))
                .isInstanceOf(InvalidOperatorException.class)
                .hasMessage("잘못된 연산자 입니다. 다음 4개의 연산자 중 하나를 입력해주세요. (+, -, *, /)");
    }
}
