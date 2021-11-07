package com.baloise.open.edw.fileconnect.infrastructure.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@Slf4j
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        log.error("Exception for Async execution: ", throwable);
        log.error("Method name - {}", method.getName());
        for (Object param : objects) {
            log.error("Parameter value - {}", param);
        }
    }
}
