package com.example.mysql_crud.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    // Before the request is handled
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("startTime", Instant.now().toEpochMilli());
        logger.info("Incoming request: {} {} from IP: {}", request.getMethod(), request.getRequestURI(), request.getRemoteAddr());
        return true; // continue processing
    }

    // After the request is handled
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, @Nullable ModelAndView modelAndView) {
        long startTime = (long) request.getAttribute("startTime");
        long duration = Instant.now().toEpochMilli() - startTime;

        logger.info("Completed request: {} {} | Status: {} | Duration: {} ms",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                duration);
    }

    // After completion (including exceptions)
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, @Nullable Exception ex) {
        if (ex != null) {
            logger.error("Exception occurred while processing request: {} {}",
                    request.getMethod(), request.getRequestURI(), ex);
        }
    }
}
