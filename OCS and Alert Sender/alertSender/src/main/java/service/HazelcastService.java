package service;

import model.HazelcastModel;
import connection.hazelcast.ClientHazelcast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class HazelcastService {
    int randomNumberForMsisdnAndPartitionKey = GetRandom.getRandomForMSISDN();
    ClientHazelcast hazelcastClient = new ClientHazelcast();

    public HazelcastService() throws IOException {
    }


    public HazelcastModel getRandomMsisdnAndPartitionId() throws FileNotFoundException {
        ArrayList<HazelcastModel> allMsisdnAndPartitionKey = hazelcastClient.getMsisdnAndPartitionKeyList();
        System.out.println("allMsisdnAndPartitionKey size: " + allMsisdnAndPartitionKey.size());
        return allMsisdnAndPartitionKey.get(randomNumberForMsisdnAndPartitionKey);
    }
}
