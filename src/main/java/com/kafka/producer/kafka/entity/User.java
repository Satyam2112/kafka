package com.kafka.producer.kafka.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User implements Serializable {
    private int id;
    private String name;
    private String email;
    private String phone;


}
