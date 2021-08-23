package connection.hazelcast;

import model.HazelcastModel;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


public class ClientHazelcast {
    final ClientConfig config = new XmlClientConfigBuilder("src/main/resources/hazelcast-client.xml").build();
   // HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
    HazelcastInstance client = HazelcastClient.newHazelcastClient(config);
    IMap<String,Integer> map = client.getMap("Balance");
    Collection<Map.Entry<String, Integer>> keyset = map.entrySet();
    ArrayList<HazelcastModel> allMsisdnAndPartitionKey= new ArrayList<HazelcastModel>();

    public ClientHazelcast() throws IOException {
    }

    public ArrayList<HazelcastModel> getMsisdnAndPartitionKeyList() {

        System.out.println("size hazelcast map:" + map.size());
        for (Map.Entry<String, Integer> entry : map.entrySet()) {

            System.out.println("in ClientHazelcast : " + entry.getKey() + entry.getValue());
            this.allMsisdnAndPartitionKey.add(new HazelcastModel(entry.getKey(), entry.getValue()));
        }
        return allMsisdnAndPartitionKey;
    }
}
