package com.aggregationkafkaconsumer.config;

import com.aggregationkafkaconsumer.entity.MessageEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, MessageEntity> consumerFactory(
            ObjectMapper objectMapper
    ) {
        Map<String, Object> configProperties = new HashMap<>();
        configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:10095");
        configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group-id");

        JsonDeserializer<MessageEntity> jsonDeserializer
                = new JsonDeserializer<>(MessageEntity.class, objectMapper);

        return new DefaultKafkaConsumerFactory<>(
                configProperties,
                new StringDeserializer(),
                jsonDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MessageEntity> kafkaListenerContainerFactory(
            ConsumerFactory<String, MessageEntity> consumerFactory
    ) {
        var containerFactory = new ConcurrentKafkaListenerContainerFactory<String, MessageEntity>();
        containerFactory.setConcurrency(1);
        containerFactory.setConsumerFactory(consumerFactory);

        return containerFactory;
    }
}
