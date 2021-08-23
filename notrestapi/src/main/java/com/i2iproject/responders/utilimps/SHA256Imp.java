package com.i2iproject.responders.utilimps;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.i2iproject.responders.utils.SHA256;

@Component
public class SHA256Imp implements SHA256 {
	public String getSHA256(String messageToHash) {
		try {
			byte[] shaValue = getSHA(messageToHash);
			if(shaValue != null)
				return toHexString(shaValue);
			return null;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("HASHING HATASI");
		}
	}
	
	@Override
	public String getRandomSHA256() {
		final int randomLimit = 1000052133;
		LocalDateTime partOfHashedValue = LocalDateTime.now();
		Random randomNumberGenerator = new Random();
		int randomInt = randomNumberGenerator.nextInt(randomLimit);
		String stringToBeHashed = randomInt + partOfHashedValue.toString() + randomInt;
		return getSHA256(stringToBeHashed);
	}
	
	private static byte[] getSHA(String input) throws NoSuchAlgorithmException
    { 
		if(input != null) {
	        MessageDigest md = MessageDigest.getInstance("SHA-256"); 
	        return md.digest(input.getBytes(StandardCharsets.UTF_8)); 
		}
		return null;
    }
   
    private static String toHexString(byte[] hash)
    {	  
        BigInteger number = new BigInteger(1, hash); 
  
        StringBuilder hexString = new StringBuilder(number.toString(16)); 
  
        while (hexString.length() < 32) 
        { 
            hexString.insert(0, '0'); 
        } 
        return hexString.toString(); 
    }

}
