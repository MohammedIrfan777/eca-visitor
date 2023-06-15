package com.eca.visitor.service.notification;

import com.eca.visitor.dto.VisitorKafkaMessageDTO;
import com.eca.visitor.utils.JsonUtils;
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

@Service
@Slf4j
@ConditionalOnExpression("${app.visitor.kafka.enabled}")
@AllArgsConstructor
@NoArgsConstructor
public class VisitorKafkaNotificationService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.visitor.kafka.topic.name}")
    private String topicName;

    @Value("${app.visitor.kafka.enabled}")
    private boolean kafkaEnabled;

    public void sendNotification(String data) {
        if (kafkaEnabled) {
            log.info("Message sent to Kafka topic:{} for Visitor Notification is :{}", topicName, data);
            kafkaTemplate.send(topicName,data);
        }
    }

}
