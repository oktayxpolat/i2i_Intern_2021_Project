package config;

import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

public class KafkaProducerConfig {

    final String bootstrapServers = GetConfigFile.properties.getProperty("BOOTSTRAP_SERVER_KAFKA");
    final String clientId = "consumergroupUpdate";

    public Properties getKafkaProducerConfig(){
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        properties.setProperty(ProducerConfig.CLIENT_ID_CONFIG,clientId);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "serializer.KryoSerializer");
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"serializer.KryoSerializer");
        return properties;
    }

}
