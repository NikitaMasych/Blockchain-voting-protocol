import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;

public class Account {
    public byte[] address;
    private SecretKey IDEncryptionKey;
    private byte[] IV;
    private KeyPair signatureKeyPair;
    private PublicKey choiceEncryptionKey;
    boolean voted = false;

    /**
     * Generates user address.
     * Generates key pair for signing and verifying.
     * Obtains public key for encrypting the choice.
     */
    Account() throws Exception {
        generateAddress();
        generateSignatureKeyPair();
        Server.INSTANCE.addUser(address);
        getChoiceEncryptionKey();
    }
    /**
     * Generates user address.
     * First, gets scanned passportID and verified eligibility to vote,
     * if not eligible, exception is thrown.
     * Secondary, user obtain symmetric key for AES_GBC, which is used to encrypt passportID.
     * Lastly, encrypted ID is hashed with SHA-256, creating unique address.
     * @throws Exception if user is restricted to vote or for encryption, hashing procedures issues.
     */
    private void generateAddress() throws Exception {
        String userID = Server.getPassportID();

        IDEncryptionKey = Encryption.AES_GCM.generateSecretKey();
        IV = Encryption.AES_GCM.generateIV();

        // provably revise encoding
        byte[] encryptedUserID = Encryption.AES_GCM.encrypt(
                userID.getBytes(StandardCharsets.UTF_8), IDEncryptionKey, IV);

        address = Hash.calculateHash(encryptedUserID, "SHA-256");
    }
    /**
     * Creates key pair for signing and verifying.
     */
    private void generateSignatureKeyPair() throws Exception {
        signatureKeyPair = KeyPair.genKeyPairEC();
    }
    /**
     * Obtains public key for encrypting the choice.
     */
    private void getChoiceEncryptionKey() {
        choiceEncryptionKey = Server.INSTANCE.provideEncryptionKey(address);
    }
    public byte[] signData(byte[] message) throws Exception {
        return SIGNATURE.signData(signatureKeyPair.privateKey, message);
    }
    public boolean verifyData(byte[] signedMessage, byte[] message) throws Exception {
        return SIGNATURE.verifySignature(signedMessage, message, signatureKeyPair.publicKey);
    }
}
