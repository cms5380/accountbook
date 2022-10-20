package com.charminseok.common.dto;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ResponseDto {

    @Getter
    @Builder
    private static class Body {
        private String massage;
        private Object data;
    }

    public static ResponseEntity<?> response(HttpStatus status, String msg, Object data) {
        Body body = Body.builder()
                .data(data)
                .massage(msg)
                .build();

        return new ResponseEntity<>(body, status);
    }
}
