import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class Encryption {
    public static class AES_GCM{
        public static final int AES_KEY_SIZE = 256;
        public static final int GCM_IV_LENGTH = 12;
        public static final int GCM_TAG_LENGTH = 16;
        /**
         * Generates random key for the AES.
         * @return generated key
         * @throws Exception if no specified algorithm exists for key generation.
         */
        public static SecretKey generateSecretKey() throws Exception {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(AES_KEY_SIZE);
            return keyGenerator.generateKey();
        }
        /**
         * Generates initialisation vector.
         * @return randomly generated IV.
         */
        public static byte[] generateIV(){
            byte[] IV = new byte[GCM_IV_LENGTH];
            SecureRandom random = new SecureRandom();
            random.nextBytes(IV);
            return  IV;
        }
        /**
         * Encrypts plaintext using AES/GBC mode.
         * @return ciphertext.
         * @throws Exception if issues with identifying spec params occurred.
         */
        public static byte[] encrypt(byte[] plaintext, SecretKey key, byte[] IV) throws Exception {
            // Get Cipher Instance
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            // Create SecretKeySpec
            SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

            // Create GCMParameterSpec
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);

            // Initialize Cipher for ENCRYPT_MODE
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

            // Perform Encryption
            return cipher.doFinal(plaintext);
        }
    }
    public static class RSA{
        /**
         * Encrypts plaintext using RSA/ECB/OAEPWithSHA-256AndMGF1Padding
         * @return ciphertext.
         * @throws Exception if issues with identifying the algorithm occurred.
         */
        public static byte[] encrypt(byte[] plaintext, PublicKey key) throws Exception{
            // Get Cipher Instance
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");

            // Initialize Cipher for ENCRYPT_MODE
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // Perform Encryption
            return cipher.doFinal(plaintext);
        }
        /**
         * Decrypts plaintext using RSA/ECB/OAEPWithSHA-256AndMGF1Padding
         * @return plaintext.
         * @throws Exception if issues with identifying the algorithm occurred.
         */
        public static byte[] decrypt(byte[] ciphertext, PrivateKey key) throws Exception{
            // Get Cipher Instance
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");

            // Initialize Cipher for DECRYPT_MODE
            cipher.init(Cipher.DECRYPT_MODE, key);

            // Perform Encryption
            return cipher.doFinal(ciphertext);
        }
    }
}
