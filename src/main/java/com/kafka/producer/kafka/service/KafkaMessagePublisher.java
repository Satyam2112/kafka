package com.kafka.producer.kafka.service;

import com.kafka.producer.kafka.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {
    @Autowired
    private KafkaTemplate<String,Object> template;
    @Autowired
    private KafkaTemplate<String,User> template1;

    private static final String eventTopic = "my_event_topic";
    private static final String devTopic = "my_dev_topic";


    public void sendMessageToTopic(String message){
        CompletableFuture<SendResult<String, Object>> future = template.send(devTopic, message);
        future.whenComplete((result,ex)->{
          if(ex==null){
              System.out.println("Message Sent : partition ["+result.getRecordMetadata().partition()+"] Offset :"+result.getRecordMetadata().offset()+"]");
          }else{
              System.out.println("Got error :"+ex.getMessage());
          }
        });
    }

    public void sendEventToTopic(User user){
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put(KafkaHeaders.TOPIC,eventTopic);
        Message<User> message ;
        if(user.getId()%2==0)
            message = MessageBuilder.withPayload(user)
                    .setHeader(KafkaHeaders.TOPIC,eventTopic)
                    .setHeader(KafkaHeaders.PARTITION,2)
                    .build();
        else
            message = MessageBuilder.withPayload(user)
                    .setHeader(KafkaHeaders.TOPIC,eventTopic)
                    .setHeader(KafkaHeaders.PARTITION,1)
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
