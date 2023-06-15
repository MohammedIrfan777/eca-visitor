package com.eca.visitor.notification;

import com.eca.visitor.dto.VisitorKafkaMessageDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import utils.JsonUtils;

@Service
@Slf4j
@ConditionalOnExpression("${app.visitor.kafka.enabled}")
@AllArgsConstructor
@NoArgsConstructor
public class VisitorKafkaNotificationService {

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.visitor.kafka.topic.name}")
    private String topicName;

    @Autowired
    private JsonUtils jsonUtils;

    public void sendMessage(VisitorKafkaMessageDTO data){

        log.info("Message sent to Kafka for Visitor Notification is {}", data.toString());
        Message<String> message = MessageBuilder
                .withPayload(jsonUtils.toJson(data))
                .setHeader(KafkaHeaders.TOPIC,topicName)
                .build();
        kafkaTemplate.send(message);
    }

}
