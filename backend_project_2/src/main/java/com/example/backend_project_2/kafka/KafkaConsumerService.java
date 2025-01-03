package com.example.backend_project_2.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "order-details", groupId = "order-detail-group")
    public void listen(String message) {
        System.out.println("Received Kafka message: " + message);
        // Handle the message (e.g., logging, triggering other services)
    }
}



