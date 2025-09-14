package com.example.event_booking_system.config;

import com.example.event_booking_system.model.app.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class AccessDeniedConfig implements AccessDeniedHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        BaseResponse<?> errorResponse = BaseResponse.forbiddenAccess("You don’t have permission to access this resource");

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
