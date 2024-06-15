package com.kafka.producer.kafka.controller;

import com.kafka.producer.kafka.entity.User;
import com.kafka.producer.kafka.service.KafkaMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.IntStream;

@RestController
@RequestMapping("/producer")
public class KafkaProducerController {

    @Autowired
    private KafkaMessagePublisher publisher;
    @GetMapping("/publish/{message}")
    public ResponseEntity<?> publishMessage(@PathVariable String message){
        IntStream.rangeClosed(1,1).forEach(i-> publisher.sendMessageToTopic(message+"_"+i));
        return ResponseEntity.ok("Message Published ..");
    }

    @PostMapping("/publish/user")
    public ResponseEntity<?> sentEvent(@RequestBody User user){
        IntStream.rangeClosed(1,10).forEach(i-> publisher.sendEventToTopic(user));
        return ResponseEntity.ok("Event Published ..");
    }
}
