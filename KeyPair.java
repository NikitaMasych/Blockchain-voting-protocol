import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class KeyPair {
    public PrivateKey privateKey;
    public PublicKey publicKey;
    private KeyPair() {

    }
    /**
     * Generates private and public key via Elliptic curve
     * Uses 256-bit prime field random Weierstrass curve.
     */
    public static KeyPair genKeyPairEC() throws Exception{
        KeyPair keyPair = new KeyPair();

        ECGenParameterSpec spec = new ECGenParameterSpec("secp256r1");
        KeyPairGenerator generator = KeyPairGenerator.getInstance("EC");
        generator.initialize(spec, new SecureRandom());
        java.security.KeyPair keypair = generator.generateKeyPair();

        keyPair.privateKey = keypair.getPrivate();
        keyPair.publicKey = keypair.getPublic();

        return keyPair;
    }
    /**
     * Generates private and public key for RSA.
     * Key size is 2048 bits.
     */
    public static KeyPair genKeyPairRSA() throws Exception{
        KeyPair keyPair = new KeyPair();

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        java.security.KeyPair keypair = generator.generateKeyPair();

        keyPair.privateKey = keypair.getPrivate();
        keyPair.publicKey = keypair.getPublic();

        return keyPair;
    }
}
