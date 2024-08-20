package com.example.apartmentmanagement.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Decryptor {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    public String decrypt(String encryptedText, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

}
