package com.springboot.blog;
import org.apache.tomcat.util.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.spec.KeySpec;

public class EncryptionUtil {
    private static final byte[] SALT = {(byte) 0x21, (byte) 0x21, (byte) 0xF0, (byte) 0x55, (byte) 0xC3, (byte) 0x9F, (byte) 0x5A, (byte) 0x75};
    private final static int ITERATION_COUNT = 31;

    public static String encode(String input) {
        try {
            KeySpec keySpec = new PBEKeySpec(null, SALT, ITERATION_COUNT);
            Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
            cipher.init(Cipher.ENCRYPT_MODE, SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec), new PBEParameterSpec(SALT, ITERATION_COUNT));
            return Base64.encodeBase64URLSafeString(cipher.doFinal(input.getBytes()));
        } catch (Exception e) {
            return "";
        }
    }

    public static String decode(String token) {
        try {
            Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
            cipher.init(Cipher.DECRYPT_MODE, SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(new PBEKeySpec(null, SALT, ITERATION_COUNT)), new PBEParameterSpec(SALT, ITERATION_COUNT));
            return new String(cipher.doFinal(Base64.decodeBase64(token)));
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String encoded = encode("admin");
        String decoded = decode(encoded);
        System.out.println("Original: rashid");
        System.out.println("Encoded: " + encoded);
        System.out.println("Decoded: " + decoded);
    }
}

