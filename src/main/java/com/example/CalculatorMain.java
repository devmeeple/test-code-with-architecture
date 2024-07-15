package com.example;

import java.util.Scanner;

/**
 * 1. 값을 입력받는다.
 * 2. 값을 저장한다.
 * 3. 입력 받은 값을 공백으로 분리한다.
 */

public class CalculatorMain {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("2개의 숫자와 연산자를 입력하세요 (e.g 1 + 2): ");
        String result = scanner.nextLine();

        String[] parts = result.split(" ");

        String operator = parts[1];
        long num1 = Long.parseLong(parts[0]);
        long num2 = Long.parseLong(parts[2]);

        long answer = new Calculator().calculate(num1, operator, num2);
        System.out.println("계산 결과: " + answer);

    }
}
