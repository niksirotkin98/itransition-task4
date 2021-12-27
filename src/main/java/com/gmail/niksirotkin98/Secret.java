package com.gmail.niksirotkin98;

import java.security.SecureRandom;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Secret {
    
    private String message;
    private byte[] secretKey;
    private byte[] hmac = null;
    
    Secret(){
        this.message = "";
        this.secretKey = generateSecretKey();
    }
    
    Secret(String message){
        this();
        this.message = message;
    }
    
    Secret(byte[] secretKey, String message){
        this( message );
        this.secretKey = secretKey;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setSecretKey(byte[] secretKey) {
        this.secretKey = secretKey;
    }
    
    public byte[] getSecretKey() {
        return this.secretKey;
    }
    
    public byte[] getHMAC() {
        return this.hmac = generateHMAC( this.secretKey, this.message.getBytes());
    }
    
    public static byte[] generateSecretKey() {
        SecureRandom sr = new SecureRandom();
        byte[] bytes = new byte[32];
        sr.nextBytes(bytes);
        return bytes;
    }
    
    public static byte[] generateHMAC(byte[] secretKey, byte[] mes) {
        byte[] hmac = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec sks = new SecretKeySpec(secretKey, "HmacSHA256");
            mac.init(sks);
            hmac = mac.doFinal(mes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate hmac-sha256", e);
        }
        return hmac;
    }
}
