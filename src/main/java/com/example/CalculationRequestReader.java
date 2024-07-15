package com.example;

import java.util.Scanner;

public class CalculationRequestReader {

    public String[] read() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("2개의 숫자와 연산자를 입력하세요 (e.g 1 + 2): ");
        String result = scanner.nextLine();

        return result.split(" ");
    }
}
