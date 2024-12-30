package com.ademyilmaz.faultsystem.utils;

import com.ademyilmaz.faultsystem.enums.StatusType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class ResponseBuilder {

    private static final String STATUS = "status";
    private static final String REASON = "reason";

    public static ResponseEntity<Map<String, String>> buildErrorResponse(HttpStatus status, String reason) {
        return ResponseEntity.status(status).body(Map.of(
                STATUS, StatusType.FAIL.name(),
                REASON, reason
        ));
    }

    public static ResponseEntity<Map<String, String>> buildSuccessResponse() {
        return ResponseEntity.ok(Map.of(
                STATUS, StatusType.SUCCESS.name()
        ));
    }
}
