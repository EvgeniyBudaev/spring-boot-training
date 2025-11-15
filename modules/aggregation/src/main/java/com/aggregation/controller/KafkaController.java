package com.aggregation.controller;

import com.aggregation.aspect.LogMethodExecutionTime;
import com.aggregation.entity.MessageEntity;
import com.aggregation.kafka.producer.MessageKafkaProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/kafka")
public class KafkaController {
    private final MessageKafkaProducer messageKafkaProducer;

    public KafkaController(MessageKafkaProducer messageKafkaProducer) {
        this.messageKafkaProducer = messageKafkaProducer;
    }

    @PostMapping
    @LogMethodExecutionTime
    public MessageEntity sendMessage(@RequestBody MessageEntity messageEntity) {
        messageKafkaProducer.sendMessageToKafka(messageEntity);
        return messageEntity;
    }
}
