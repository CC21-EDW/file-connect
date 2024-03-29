package com.baloise.open.edw.fileconnect.infrastructure.kafka;

import com.baloise.open.edw.fileconnect.infrastructure.kafka.config.KafkaConfiguration;
import com.baloise.open.edw.infrastructure.kafka.Config;
import com.baloise.open.edw.infrastructure.kafka.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class KafkaController {

    @Autowired
    private KafkaConfiguration configuration;

    private Producer activityProducer;

    @PostConstruct
    public void initActivityProducer() throws ExecutionException, InterruptedException {
        Properties specificProperties = new Properties();
        specificProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, configuration.getActivityValueSerializer());
        specificProperties.put(Config.SCHEMA_SERVER_CONFIG_KEY, configuration.getSchemaRegistryUrl());

        activityProducer = Producer.create(specificProperties, configuration.getActivityTopicName(), configuration.getSystemId());
    }

   /* public void pushActivities(List<ActivityDto> activities) {
        for (ActivityDto currentActivity : activities) {
            activityProducer.pushEvent(currentActivity);
        }
    }*/

    @PreDestroy
    public void disconnectFromWorkflow() {
        activityProducer.pushStatusShutdown();
    }
}
