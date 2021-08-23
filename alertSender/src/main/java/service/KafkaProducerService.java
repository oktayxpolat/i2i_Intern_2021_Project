package service;

import model.UsageToSendKafka;
import connection.kafka.Producer;

public class KafkaProducerService {
    Producer kafkaProducer = new Producer();
    void sendKafkaUpdateTopic(UsageToSendKafka usageToSendKafka) throws InterruptedException {
        kafkaProducer.sendKafkaUpdateTopic(usageToSendKafka);
    }
}
