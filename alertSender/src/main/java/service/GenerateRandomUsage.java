package service;

import model.HazelcastModel;
import model.Subscriber;
import model.UsageToSendKafka;
import config.GetConfigFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

public class GenerateRandomUsage {

    Logger logger= LogManager.getLogger();

    public GenerateRandomUsage() throws InterruptedException, SQLException, ClassNotFoundException, IOException {

        logger.info("***starting getRandomMsisdnPartitionKeyFromHazelcast");
        HazelcastModel hazelcastModel = getRandomMsisdnPartitionKeyFromHazelcast();

        logger.info("***starting getRandomSubscriberByMsisdnAndPartitionKeyFromOracle");
        Subscriber selectedSubscriber = getRandomSubscriberByMsisdnAndPartitionKeyFromOracle(hazelcastModel);

        logger.info("***starting generateRandomUsedAmount");
        int updatedUsedAmount = generateRandomUsedAmount(selectedSubscriber);

        logger.info("***starting sendKafka");
        sendKafka(selectedSubscriber, updatedUsedAmount);
    }

    HazelcastModel getRandomMsisdnPartitionKeyFromHazelcast() throws IOException {
        HazelcastService hazelcastService = new HazelcastService();
        return hazelcastService.getRandomMsisdnAndPartitionId();
    }

   Subscriber getRandomSubscriberByMsisdnAndPartitionKeyFromOracle(HazelcastModel hazelcastModel) throws SQLException, ClassNotFoundException {
        OracleService oracleService = new OracleService();
        Subscriber selectedSubscriber= oracleService.getRandomSubscriberByMsisdnAndPartitionKeyFromOracle(hazelcastModel);
        return selectedSubscriber;
    }
    int generateRandomUsedAmount(Subscriber selectedSubscriber){
        int randomUsedAmount = 999999999;
        int updatedUsedAmount ;
        while (randomUsedAmount < getRemainingUsage(selectedSubscriber)) {
            if (selectedSubscriber.getPackageType() == "v") {
                getRandomUsedAmount("MAX_VOICE_MINUTE");
            } else if (selectedSubscriber.getPackageType() == "d") {
                getRandomUsedAmount("MAX_DATA_MB");
            } else if (selectedSubscriber.getPackageType() == "s") {
                getRandomUsedAmount("MAX_SMS_COUNT");
            }
        }
        updatedUsedAmount = selectedSubscriber.getUsedAmount() + randomUsedAmount;
        return updatedUsedAmount;
    }

    void sendKafka(Subscriber selectedSubscriber, int updatedUsage) throws InterruptedException {
        KafkaProducerService kafkaProducerService = new KafkaProducerService();
        UsageToSendKafka usageToSendKafka = new UsageToSendKafka(selectedSubscriber.getBalKey(),selectedSubscriber.getMsisdn(),selectedSubscriber.getPartitionKey(),selectedSubscriber.getPackageId(),updatedUsage,selectedSubscriber.getStartDate(),selectedSubscriber.getEndDate());
        kafkaProducerService.sendKafkaUpdateTopic(usageToSendKafka);
    }
    int getRandomUsedAmount(String typeOfUsage){
        return GetRandom.getRandomNumber((Integer) GetConfigFile.properties.get(typeOfUsage));
    }

    int getRemainingUsage(Subscriber selectedSubscriber){
        return selectedSubscriber.getLimitOfPackage()-selectedSubscriber.getUsedAmount();
    }

}

