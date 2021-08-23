package config;

import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;

public class KafkaConsumerConfig {
    final String bootstrapServers = "34.141.15.250:9092";
    final String consumerGroupId = "consumergroupUpdate";

    public Properties getKafkaConsumerConfig(){
        Properties consumerConfig = new Properties();
        consumerConfig.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        consumerConfig.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,  "serializer.KryoDeserializer");
        consumerConfig.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "serializer.KryoDeserializer");
        consumerConfig.setProperty(ConsumerConfig.GROUP_ID_CONFIG,consumerGroupId);
        consumerConfig.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        return consumerConfig;
    }
}
