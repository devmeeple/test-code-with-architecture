package com.example;

/**
 * 1. 값을 입력받는다.
 * 2. 값을 저장한다.
 * 3. 입력 받은 값을 공백으로 분리한다.
 */

public class CalculatorMain {
    public static void main(String[] args) {

        CalculationRequest calculationRequest = new CalculationRequestReader().read();

        long answer = new Calculator().calculate(
                calculationRequest.getNum1(),
                calculationRequest.getOperator(),
                calculationRequest.getNum2()
        );
        System.out.println("계산 결과: " + answer);

    }
}
