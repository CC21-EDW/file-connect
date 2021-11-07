/*
 * Copyright 2018 - 2021 Baloise Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baloise.open.edw.fileconnect.infrastructure;

import com.baloise.open.edw.fileconnect.infrastructure.file.config.FileWatcherConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.nio.file.*;

@SpringBootApplication
@EnableAsync
@Slf4j
public class FileConnectApp {
    public static void main(String[] args) {
        SpringApplication.run(FileConnectApp.class, args);
    }

    @Autowired
    private FileWatcherConfiguration configuration;

    @Bean
    public WatchService watchService() {
        log.info("watching files in directory {}.", configuration.getSourceDir());

        WatchService watchService = null;
        for (String currentPath : configuration.getSourceDir()) {
            try {
                watchService = FileSystems.getDefault().newWatchService();
                Path path = Paths.get(currentPath);

                if (!Files.exists(path)) {
                    throw new RuntimeException("Configured path does not exist: " + path);
                }

                if (!Files.isDirectory(path)) {
                    throw new RuntimeException("Configured path is not a directory: " + path);
                }

                path.register(
                        watchService,
                        StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_MODIFY,
                        StandardWatchEventKinds.ENTRY_CREATE
                );
            } catch (IOException watcherCreationProblem) {
                log.error("Creating the watcher failed for some reason:", watcherCreationProblem);
            }
        }
        return watchService;
    }
}
