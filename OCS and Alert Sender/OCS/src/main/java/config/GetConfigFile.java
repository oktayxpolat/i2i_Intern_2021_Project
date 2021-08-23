package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetConfigFile {

    public final static Properties properties = new Properties();

    public static void configFile() throws IOException {
        InputStream inputConfigFile = new FileInputStream("src/main/resources/config.properties");
        properties.load(inputConfigFile);
    }
}
