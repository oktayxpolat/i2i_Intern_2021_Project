import config.GetConfigFile;
import config.KafkaConsumerConfig;
import model.UsageToSendKafka;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.voltdb.client.ProcCallException;
import voltdb.VoltdbClient;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerOCS {
    public static void main(String[] args) throws IOException, ProcCallException {
        Properties consumerConfig = new Properties();
        consumerConfig.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"34.141.15.250:9092");
        consumerConfig.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,  "serializer.KryoDeserializer");
        consumerConfig.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "serializer.KryoDeserializer");
        consumerConfig.setProperty(ConsumerConfig.GROUP_ID_CONFIG,"consumergroupUpdate");
        consumerConfig.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        Logger logger= LogManager.getLogger();
        KafkaConsumer<String, Object> consumer = new KafkaConsumer<>(consumerConfig);
        VoltdbClient voltdbClient=new VoltdbClient();
        consumer.subscribe(Collections.singletonList("updateTopic"));
        int usedAmount;

        while(true){
            ConsumerRecords<String,Object> records = consumer.poll(Duration.ofMillis(1000));
            logger.info("condumer records reveived from kafka");
            for(ConsumerRecord<String, Object> record : records){

                UsageToSendKafka usage = (UsageToSendKafka) record.value();
                logger.info("msisdn: " +usage.getMsisdn() + " partitionKey :"+usage.getPartitionKey()+" --> taken from KAFKA");

                usedAmount = voltdbClient.getUserAmountProcedure(usage.getMsisdn(),usage.getPartitionKey());
                if(usedAmount != 0){
                    usage.setUsedAmount(usage.getUsedAmount()+usedAmount);
                    voltdbClient.updateUsageProcedure(usage);
                }
            }
        }
    }
}
