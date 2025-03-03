package com.example.orderService.config;

import com.example.common.event.CreateEventToNotification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    @Value("${spring.kafka.topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, CreateEventToNotification> kafkaTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(org.apache.kafka.clients.producer.KafkaProducer.class);
    public void sendMessageEmail(CreateEventToNotification createEventToNotification){
        LOGGER.info(String.format("Message sent -> %s", createEventToNotification.toString()));

        Message<CreateEventToNotification> message = MessageBuilder
                .withPayload(createEventToNotification)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();
        kafkaTemplate.send(message);
    }
}
