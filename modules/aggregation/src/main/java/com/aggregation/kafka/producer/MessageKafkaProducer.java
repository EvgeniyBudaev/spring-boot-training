package com.aggregation.kafka.producer;

import com.aggregation.entity.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageKafkaProducer {
    private final KafkaTemplate<String, MessageEntity> kafkaTemplate;

    public MessageKafkaProducer(KafkaTemplate<String, MessageEntity> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageToKafka(MessageEntity message) {
        kafkaTemplate.send("messages", message);
        log.info("Message sent to Kafka: {}", message);
    }
}
