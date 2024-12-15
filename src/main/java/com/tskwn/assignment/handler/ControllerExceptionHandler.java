package com.tskwn.assignment.handler;

import com.tskwn.assignment.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 전역 컨트롤러 예외 처리.
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ResponseDto> handleIllegalArgumentException(final IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(false, "요청이 올바르지 않거나 데이터가 존재하지 않음: " + e.getMessage()));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ResponseDto> handleRuntimeException(final RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(false, "알수 없는 오류: " + e.getMessage()));
    }
}
