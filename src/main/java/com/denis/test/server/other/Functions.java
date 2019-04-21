package com.denis.test.server.other;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

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

    public static String getBaseURL() {

        String baseEnvLinkURL=null;
        HttpServletRequest currentRequest =
                ((ServletRequestAttributes) RequestContextHolder.
                        currentRequestAttributes()).getRequest();
        // lazy about determining protocol but can be done too
        baseEnvLinkURL = "http://" + currentRequest.getLocalName();
        if(currentRequest.getLocalPort() != 80) {
            baseEnvLinkURL += ":" + currentRequest.getLocalPort();
        }
        if(!StringUtils.isEmpty(currentRequest.getContextPath())) {
            baseEnvLinkURL += currentRequest.getContextPath();
        }
        return baseEnvLinkURL;
    }


    public static <T> List<T> removeDublicates(List<T> list){
        Set<T> set = new LinkedHashSet<>(list);
        list.clear();
        list.addAll(set);
        return list;
    }

}
