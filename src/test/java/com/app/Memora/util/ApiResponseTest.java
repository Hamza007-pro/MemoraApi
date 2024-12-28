package com.app.Memora.util;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    void testNoArgsConstructor() {
        ApiResponse<String> response = new ApiResponse<>();
        assertNotNull(response);
        assertEquals(response.getTimestamp(), LocalDateTime.of(2024, 11, 29, 18, 26, 43));
        assertNull(response.getData());
        assertNull(response.getMessage());
        assertFalse(response.isSuccess());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime timestamp = LocalDateTime.now();
        ApiResponse<String> response = new ApiResponse<>(true, "Success", "Data", timestamp);
        assertNotNull(response);
        assertEquals(true, response.isSuccess());
        assertEquals("Success", response.getMessage());
        assertEquals("Data", response.getData());
        assertEquals(timestamp, response.getTimestamp());
    }

    @Test
    void testPartialArgsConstructor() {
        ApiResponse<String> response = new ApiResponse<>(true, "Success", "Data");
        assertNotNull(response);
        assertEquals(true, response.isSuccess());
        assertEquals("Success", response.getMessage());
        assertEquals("Data", response.getData());
        assertEquals(response.getTimestamp(), LocalDateTime.of(2024, 11, 29, 18, 26, 43));
    }

    @Test
    void testSettersAndGetters() {
        ApiResponse<String> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage("Success");
        response.setData("Data");
        LocalDateTime timestamp = LocalDateTime.now();
        response.setTimestamp(timestamp);

        assertEquals(true, response.isSuccess());
        assertEquals("Success", response.getMessage());
        assertEquals("Data", response.getData());
        assertEquals(timestamp, response.getTimestamp());
    }
}