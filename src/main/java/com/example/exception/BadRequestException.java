package com.example.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super("유효하지 않은 입력입니다");
    }
}
