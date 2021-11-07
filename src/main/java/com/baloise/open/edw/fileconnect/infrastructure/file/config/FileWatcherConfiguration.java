package com.baloise.open.edw.fileconnect.infrastructure.file.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class FileWatcherConfiguration {
    @Value("${edw.file-watcher.source-dir}")
    private String[] sourceDir;
}
