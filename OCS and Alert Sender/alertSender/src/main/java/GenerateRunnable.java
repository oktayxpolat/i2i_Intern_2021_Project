import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.GenerateRandomUsage;
import service.GetRandom;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class GenerateRunnable implements Runnable
{
    Logger logger= LogManager.getLogger();

    @Override
    public void run() {
        while (true) {
            logger.info("generateRandomUsage starting..");
            try {
                new GenerateRandomUsage();
                Thread.sleep(3000);
            } catch (InterruptedException | SQLException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }

        }
    }
}
