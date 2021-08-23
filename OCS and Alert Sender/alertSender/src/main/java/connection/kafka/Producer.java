package connection.kafka;

import model.UsageToSendKafka;
import config.GetConfigFile;
import config.KafkaProducerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.io.Serializable;
import java.util.Properties;

public class Producer {
    Properties kafkaConfig = new KafkaProducerConfig().getKafkaProducerConfig();
    final String topic = GetConfigFile.properties.getProperty("KAFKA_TOPIC_NAME");
    final KafkaProducer<String, Serializable> producer = new KafkaProducer<>(kafkaConfig);

    public void sendKafkaUpdateTopic(UsageToSendKafka usageToSendKafka) throws InterruptedException {
        producer.send(new ProducerRecord<>(topic,getSerializedUsageToSendKafka(usageToSendKafka)));
        Thread.sleep(3000);
    }

    private static Serializable getSerializedUsageToSendKafka(UsageToSendKafka usageToSendKafka) {
            return usageToSendKafka;
    }

}
