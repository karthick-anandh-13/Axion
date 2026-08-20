package com.axion.common.dto;

import java.time.LocalDateTime;

public record ApiError(

        boolean success,

        int status,

        String error,

        String message,

        String path,

        LocalDateTime timestamp

) {

    public static ApiError of(
            int status,
            String error,
            String message,
            String path) {

        return new ApiError(
                false,
                status,
                error,
                message,
                path,
                LocalDateTime.now()
        );
    }
}