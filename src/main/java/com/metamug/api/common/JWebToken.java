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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

/**
 *
 * @author user
 */
public class JWebToken {

    private static final String SECRET_KEY = "FREE_MASON"; //@TODO Add Signature here
    private final static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private final static String ISSUER = "mason.metamug.net";
    private static String HEADER = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
    JSONObject payload = new JSONObject();
    private String signature;
    private String encodedHeader;
   

    public JWebToken(String sub, String aud, long expires) {
        encodedHeader = encode(new JSONObject(HEADER));
        payload.put("sub", sub);
        payload.put("aud", aud);
        payload.put("iat", System.currentTimeMillis());
        payload.put("exp", expires);
        payload.put("iss", ISSUER);
        payload.put("jti", UUID.randomUUID().toString()); //how do we use this?
        signature = HMACSHA256(encodedHeader + "." + encode(payload));
    }

    public JWebToken(String token) throws NoSuchAlgorithmException {
        String[] parts = token.split("\\.");
        if(parts.length !=3) throw new IllegalArgumentException("Invalid Token format");        
        if(Base64.getDecoder().decode(parts[0]).equals(HEADER)){
            encodedHeader = parts[0];
        }else{
            throw new NoSuchAlgorithmException("JWT Header is Incorrect");        
        }
        payload = new JSONObject(Base64.getDecoder().decode(parts[1]));
        signature = parts[2];
    }

    @Override
    public String toString() {
            return encodedHeader + "." + encode(payload) + "." + signature;
    }

    public boolean isValid() {
        return payload.getLong("exp") > System.currentTimeMillis() //token not expired
                && signature.equals(HMACSHA256(encodedHeader + "." + payload)); //signature matched
    }

    public String getSubject() {
        return payload.getString("sub");
    }

    public String getAudience() {
        return payload.getString("aud");
    }

    private static String encode(JSONObject obj){
        try {
            return new String(Base64.getEncoder().encode(obj.toString().getBytes("UTF-8")));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(JWebToken.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Sign with HMAC SHA256 (HS256)
     *
     * @param data
     * @return
     * @throws Exception
     */
    private String HMACSHA256(String data){
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            return new String(Base64.getEncoder().encode(sha256_HMAC.doFinal(data.getBytes("UTF-8"))));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException ex) {
            Logger.getLogger(JWebToken.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

}
