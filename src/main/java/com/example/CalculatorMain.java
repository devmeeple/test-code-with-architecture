package com.example;

/**
 * 1. 값을 입력받는다.
 * 2. 값을 저장한다.
 * 3. 입력 받은 값을 공백으로 분리한다.
 */

public class CalculatorMain {
    public static void main(String[] args) {

        String[] parts = new CalculationRequestReader().read();

        String operator = parts[1];
        long num1 = Long.parseLong(parts[0]);
        long num2 = Long.parseLong(parts[2]);

        long answer = new Calculator().calculate(num1, operator, num2);
        System.out.println("계산 결과: " + answer);

    }
}
