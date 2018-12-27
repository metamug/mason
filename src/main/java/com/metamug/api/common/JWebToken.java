/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.api.common;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

/**
 *
 * @author user
 */
public class JWebToken {

    private static final String SECRET_KEY = "";
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private final static String ISSUER = "mason.metamug.net";
    JSONObject header = new JSONObject("{\"alg\":\"HS256\",\"typ\":\"JWT\"}");
    JSONObject payload = new JSONObject();
    

    /**/
    public JWebToken(String sub, String aud, long expires) {
        payload.append("sub", sub);
        payload.append("aud", aud);
        payload.append("iat", System.currentTimeMillis());
        payload.append("exp", expires);
        payload.append("iss", ISSUER);
        payload.append("jti", UUID.randomUUID().toString());
    }

    public String getToken() throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException{
        return encode(header)+"."+encode(payload)+ HMACSHA256(encode(header)+"."+encode(payload));
    }
    
    public boolean verify(String token) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException{
        String[] parts = token.split("\\.");
        return parts[2].equals(HMACSHA256(parts[0]+"."+parts[1]));
    }
    
    private String encode(JSONObject obj) throws UnsupportedEncodingException{
        return new String(Base64.getEncoder().encode(obj.toString().getBytes("UTF-8")));
    }
    
    

    /**
     * Sign with HMAC SHA256 (HS256)
     * @param data
     * @return
     * @throws Exception 
     */    
    private String HMACSHA256(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException{
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return bytesToHex(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}
