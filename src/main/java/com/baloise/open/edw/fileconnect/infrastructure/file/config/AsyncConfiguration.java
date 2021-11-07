package com.baloise.open.edw.fileconnect.infrastructure.file.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AsyncConfiguration {
    @Value("${edw.file-watcher.core-pool-size}")
    private Integer corePoolSize;

    @Value("${edw.file-watcher.max-pool-size}")
    private Integer maxPoolSize;

    @Value("${edw.file-watcher.queue-capacity}")
    private Integer queueCapacity;

    @Value("${edw.file-watcher.thread-name-prefix}")
    private String threadNamePrefix;
}
