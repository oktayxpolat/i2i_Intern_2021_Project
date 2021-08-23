package voltdb;

import config.GetConfigFile;
import model.UsageToSendKafka;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.voltdb.*;
import org.voltdb.client.*;

import java.io.IOException;

public class VoltdbClient {
    Logger logger= LogManager.getLogger();

    final ClientConfig config = new ClientConfig("interncell","i2icom");
    org.voltdb.client.Client client;

    public VoltdbClient() throws IOException {
        client = ClientFactory.createClient(config);
        client.createConnection("34.107.82.147",21212) ;
    }

    public int getUserAmountProcedure(String msisdn, int partitionKey) throws IOException, ProcCallException {

        int usedAmount=0;
        ClientResponse response = client.callProcedure("GetUserAmount",partitionKey, msisdn);
        if (response.getStatus() != ClientResponse.SUCCESS){
            System.err.println(response.getStatusString());
            System.exit(-1);
        }

        VoltTable [] results = response.getResults();
        for (int i = 0; i < results.length; i++) {
            VoltTableRow row = results[i].fetchRow(i);
            usedAmount = (int)row.get("USED_AMOUNT", VoltType.INTEGER);
            logger.info("msisdn: " +msisdn + " partitionKey :"+partitionKey+" --> usedAmout "+ usedAmount);
        }
        return usedAmount;
    }

    public void updateUsageProcedure(UsageToSendKafka usage) throws IOException, ProcCallException {
        client.callProcedure("UpdateUserAmount", usage.getUsedAmount(),usage.getPartitionKey(),usage.getMsisdn(),usage.getBalKey());
        // UPDATE customer_information SET used_amount=?, last_update_epoch=NOW()   WHERE partition_key=? AND msisdn=? AND bal_key=?;
        logger.info("msisdn: " +usage.getMsisdn() + " partitionKey :"+usage.getPartitionKey()+" --> usedAmount updated in VOLTDB");
    }
}

