package service;

import config.GetConfigFile;

import java.util.Random;

public class GetRandom {
    static public int getRandomNumber(int upperBoundToGenerate){
        Random random = new Random();
        return  random.nextInt(upperBoundToGenerate);
    }

    static public int getRandomForMSISDN(){
        return  GetRandom.getRandomNumber(Integer.parseInt(GetConfigFile.properties.getProperty("MSISDN_MAX_COUNT")));
    }


}
