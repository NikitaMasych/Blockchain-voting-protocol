import java.security.MessageDigest;

public class Hash {
    public static byte[] calculateHash(byte[] msg, String hashAlg) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(hashAlg);
        return digest.digest(msg);
    }
}
