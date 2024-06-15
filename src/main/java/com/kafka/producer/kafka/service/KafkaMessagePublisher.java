package com.kafka.producer.kafka.service;

import com.kafka.producer.kafka.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {
    @Autowired
    private KafkaTemplate<String,Object> template;
    @Autowired
    private KafkaTemplate<String,User> template1;


    public void sendMessageToTopic(String message){
        CompletableFuture<SendResult<String, Object>> future = template.send("my_dev_topic", message);
        future.whenComplete((result,ex)->{
          if(ex==null){
              System.out.println("Message Sent : partition ["+result.getRecordMetadata().partition()+"] Offset :"+result.getRecordMetadata().offset()+"]");
          }else{
              System.out.println("Got error :"+ex.getMessage());
          }
        });
    }

    public void sendEventToTopic(User user){

        Message<User> message = MessageBuilder.withPayload(user)
                .setHeader(KafkaHeaders.TOPIC,"my_event_topic")
                .build();
        CompletableFuture<SendResult<String, User>> future = template1.send(message);
        future.whenComplete((result,ex)->{
            if(ex==null){
                System.out.println("Event Sent : partition ["+result.getRecordMetadata().partition()+"] Offset :"+result.getRecordMetadata().offset()+"]");
            }else{
                System.out.println("Got error in event topic :"+ex.getMessage());
            }
        });
    }

}
