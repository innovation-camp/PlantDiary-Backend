package com.sparta.plantdiary.error;

import com.sparta.plantdiary.dto.ResponseDto;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ForbiddenException extends Exception{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDto<?> handleValidationExceptions(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseDto.fail("FORBIDDEN", errorMessage);
    }

    public ForbiddenException(String s) {
        super(s);
    }
}
