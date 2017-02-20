package ar.com.manusoftar.CryptoTest;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;


public class Security {
    public static String encrypt(String input, String key){
        byte[] crypted = null;
        try{
            SecretKey skey = getKeyFromPasspharase(key);
            //SecretKeySpec skey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes());
        }catch(Exception e){
            System.out.println(e.toString());
        }

        return new String(Base64.encodeBase64(crypted));
    }

    public static String decrypt(String input, String key){

        byte[] output = null;
        try{
            SecretKey skey = getKeyFromPasspharase(key);
            //SecretKeySpec skey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            output = cipher.doFinal(Base64.decodeBase64(input));
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return new String(output);
    }

    private static SecretKey getKeyFromPasspharase(String password){
//        try {
//            MessageDigest sha = MessageDigest.getInstance("SHA-256");
//            byte[] key = sha.digest(password.getBytes("UTF-8"));
//            //key = Arrays.copyOf(key,16);
//            return key;
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(-1);
//        }
//
//        System.exit(-1);
//        return new byte[0];
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] salt = "este es el salt".getBytes("UTF-8");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
            return secret;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void main(String[] args) {
      //  String key = "1234567891234567";
      //  String data = "example";
      //  System.out.println(Security.decrypt(Security.encrypt(data, key), key));
      //  System.out.println(Security.encrypt(data, key));
    }
}