package com.kafka.producer.kafka.controller;

import com.kafka.producer.kafka.service.KafkaMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RestController
@RequestMapping("/producer")
public class KafkaProducerController {

    @Autowired
    private KafkaMessagePublisher publisher;
    @GetMapping("/publish/{message}")
    public ResponseEntity<?> publishMessage(@PathVariable String message){
        IntStream.rangeClosed(1,10000).forEach(i-> publisher.sendMessageToTopic(message+"_"+i));
        return ResponseEntity.ok("Message Published ..");
    }
}
