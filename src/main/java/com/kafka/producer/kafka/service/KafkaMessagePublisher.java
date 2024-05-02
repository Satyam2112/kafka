package com.kafka.producer.kafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {
    @Autowired
    private KafkaTemplate<String,Object> template;

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

}
