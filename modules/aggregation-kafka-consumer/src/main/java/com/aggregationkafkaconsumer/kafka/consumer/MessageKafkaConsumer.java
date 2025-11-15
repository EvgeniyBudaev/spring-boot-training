package com.aggregationkafkaconsumer.kafka.consumer;

import com.aggregationkafkaconsumer.entity.MessageEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageKafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(MessageKafkaConsumer.class);

    @KafkaListener(topics = "messages")
    public void consumeMessage(MessageEntity messageEntity) {
        log.info("Received MessageEntity={}", messageEntity);
        System.out.println("Received MessageEntity=" + messageEntity);
    }
}
