package com.denis.test.server.other;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Random;

public class Functions {
    public static String generateHash(String input) throws Exception{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for(byte anEncodedHash : encodedHash){
            hexString.append(Integer.toHexString(0xff & anEncodedHash));
        }
        return hexString.toString();
    }


    public static String generateString(){
        byte[] array = new byte[16];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }
}
