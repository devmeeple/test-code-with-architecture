package com.example.exception;

public class InvalidOperatorException extends RuntimeException {
    public InvalidOperatorException() {
        super("잘못된 연산자 입니다. 다음 4개의 연산자 중 하나를 입력해주세요. (+, -, *, /)");
    }
}
