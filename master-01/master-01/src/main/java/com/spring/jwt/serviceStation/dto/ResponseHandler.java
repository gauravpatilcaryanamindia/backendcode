package com.spring.jwt.serviceStation.dto;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

    public static ResponseEntity<ResponseDto> generateResponse(
            String message,
            HttpStatus status,
            Object data) {

        ResponseDto responseDto =
                new ResponseDto(status.is2xxSuccessful() ? "success" : "error",
                        message,
                        data);

        return new ResponseEntity<>(responseDto, status);
    }
}
