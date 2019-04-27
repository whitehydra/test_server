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
    String test;
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

    public static String transliterate(String message){
        char[] abcCyr =   {'а','б','в','г','д','е','ё', 'ж','з','и','й','к','л','м','н','о','п','р',
                'с','т','у','ф','х', 'ц','ч', 'ш','щ','ъ','ы','ь','э', 'ю','я','А','Б','В','Г','Д','Е',
                'Ё', 'Ж','З','И','Й','К','Л','М','Н','О','П','Р','С','Т','У','Ф','Х', 'Ц', 'Ч','Ш', 'Щ',
                'Ъ','Ы','Ь','Э','Ю','Я',' '};
        String[] abcLat = {"a","b","v","g","d","e","e","zh","z","i","y","k","l","m","n","o","p","r",
                "s","t","u","f","h","ts","ch","sh","sch", "","i", "","e","ju","ja","A","B","V","G","D",
                "E","E","Zh","Z","I","Y","K","L","M","N","O","P","R","S","T","U","F","H","Ts","Ch","Sh",
                "Sch", "","I", "","E","Ju","Ja","_"};
        boolean find = false;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            for (int x = 0; x < abcCyr.length; x++ ) {
                if (message.charAt(i) == abcCyr[x]) {
                    builder.append(abcLat[x]);
                    find = true;
                }
            }
            if(!find)builder.append(message.charAt(i));
            find = false;
        }
        return builder.toString();
    }

}
