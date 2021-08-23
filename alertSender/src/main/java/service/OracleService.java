package service;

import model.HazelcastModel;
import model.Subscriber;
import connection.oracle.OracleConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;

public class OracleService {

    Logger logger= LogManager.getLogger();
    OracleConnection oracleConnection = new OracleConnection();
    ArrayList<Subscriber> subscribers = oracleConnection.getAllSubscribers();

    public OracleService() throws SQLException, ClassNotFoundException {
    }

    public Subscriber getRandomSubscriberByMsisdnAndPartitionKeyFromOracle(HazelcastModel hazelcastModel) throws SQLException, ClassNotFoundException {
        ArrayList<Subscriber> subscribersSelectedWithMsisdnAndPartitionKey = new ArrayList<>();
        int numberOfSelectedSubscriberEntry;
        int randomNumberToSelectSubscriber;
        int numberOfSubscribers = oracleConnection.getAllSubscribers().size();
        logger.info("hazelcast -->msisdn: "+hazelcastModel.getMsidn()+" partition id:"+ hazelcastModel.getPartitionKey()+"numberOfSubscribers"+numberOfSubscribers);

        for(int subscriberIter = 0; subscriberIter < numberOfSubscribers; subscriberIter++){

            logger.info("oracle pk: "+subscribers.get(subscriberIter).getPartitionKey());
            logger.info("hz pk: "+hazelcastModel.getPartitionKey());
            logger.info("oracle pk: "+subscribers.get(subscriberIter).getMsisdn());
            logger.info("hz pk: "+hazelcastModel.getMsidn());

            if(subscribers.get(subscriberIter).getPartitionKey() == hazelcastModel.getPartitionKey()
            && subscribers.get(subscriberIter).getMsisdn().equals(hazelcastModel.getMsidn().trim())){
                logger.info("added subscribersSelectedWithMsisdnAndPartitionKey: "+subscribers.get(subscriberIter));
                subscribersSelectedWithMsisdnAndPartitionKey.add(subscribers.get(subscriberIter));
            }
        }

        numberOfSelectedSubscriberEntry = subscribersSelectedWithMsisdnAndPartitionKey.size();
        logger.info("numberOfSelectedSubscriberEntry"+ numberOfSelectedSubscriberEntry );
        randomNumberToSelectSubscriber = GetRandom.getRandomNumber(numberOfSelectedSubscriberEntry);

       return subscribersSelectedWithMsisdnAndPartitionKey.get(randomNumberToSelectSubscriber);
    }
}
