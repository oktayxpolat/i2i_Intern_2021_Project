package connection.oracle;

import model.Subscriber;
import config.GetConfigFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;


public class OracleConnection {
    Logger logger = LogManager.getLogger();
    Connection oracleConnection;

    public OracleConnection() throws ClassNotFoundException, SQLException {
        Class.forName(GetConfigFile.properties.getProperty("DRIVER_NAME_ORACLE"));

        oracleConnection= DriverManager.getConnection(
                GetConfigFile.properties.getProperty("DATABASE_CONNECTION_URL"),
                GetConfigFile.properties.getProperty("DATABASE_USER_NAME"),
                GetConfigFile.properties.getProperty("DATABASE_PASSWORD"));
        logger.info("Database is connected");
    }

    public ArrayList<Subscriber> getAllSubscribers() throws ClassNotFoundException, SQLException {
        ArrayList<Subscriber> subscribers = new ArrayList<>();
        Statement stmt=oracleConnection.createStatement();
        ResultSet rs=stmt.executeQuery("SELECT b.bal_key, b.msısdn,b.partition_key, p.package_ıd,b.used_amount,p.package_type,p.package_lımıt,b.begin_date, b.end_date FROM ınterns.balance b, ınterns.packages p WHERE b.package_ıd = p.package_ıd");
        while(rs.next()) {
           Subscriber subscriberToAdd = new Subscriber(rs.getInt("bal_key"),rs.getString("msisdn"),rs.getInt("partition_key"),rs.getInt("package_id"),rs.getString("package_type"),rs.getInt("used_amount"),rs.getInt("package_lımıt"),rs.getDate("begin_date"),rs.getDate("end_date"));
           subscribers.add(subscriberToAdd);
           logger.info("new subscriber taken from db : "+ subscriberToAdd.getBalKey()+"-"+subscriberToAdd.getMsisdn()+"-"+subscriberToAdd.getPartitionKey()+"-"+subscriberToAdd.getPackageId()+"-"+subscriberToAdd.getPackageType()+"-"+subscriberToAdd.getUsedAmount()+"-"+subscriberToAdd.getUsedAmount()+"-"+subscriberToAdd.getStartDate()+"-"+subscriberToAdd.getEndDate());
        }
        return subscribers;
    }
}

