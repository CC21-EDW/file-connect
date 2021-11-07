package com.baloise.open.edw.fileconnect.infrastructure.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

@Service
@Slf4j
public class FileWatchingService {

    @Autowired
    private WatchService watchService;

    @Async
    @PostConstruct
    public void launchMonitoring() {
        log.info("Starting observations");
        try {
            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    log.info("Event kind: {}; File affected: {}", event.kind(), event.context());
                }
                key.reset();
            }
        } catch (InterruptedException e) {
            log.warn("interrupted exception for monitoring service");
        }
    }

    @PreDestroy
    public void stopMonitoring() {
        log.info("STOP_MONITORING");

        if (watchService != null) {
            try {
                watchService.close();
            } catch (IOException e) {
                log.error("exception while closing the monitoring service");
            }
        }
    }
}
