import config.GetConfigFile;

import java.io.IOException;

public class RandomUsageGenerator {

    public static void main(String[] args) throws IOException {

        GetConfigFile.configFile();
        GenerateRunnable generateRunnable= new GenerateRunnable();
        generateRunnable.run();
    }
}
