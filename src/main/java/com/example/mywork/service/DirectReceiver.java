package com.example.mywork.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
//监听的队列名称
@RabbitListener(queues = "python-test")
public class DirectReceiver {

    @RabbitHandler
    public void process(Object testMessage) {
        System.out.println("DirectReceiver消费者收到消息1  : " + testMessage.toString());
    }

}