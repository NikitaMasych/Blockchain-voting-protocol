import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static final Server INSTANCE = new Server(); // Server is singleton
    private final HashMap<byte[], KeyPair> voters = new HashMap<>();
    public final ArrayList<String> ballotList = new ArrayList<>();
    private final Map<String, Integer> pool = new HashMap<String, Integer>(){{
        for (String b : ballotList) put(b, 0);
    }};
    private Server(){
        // do something
        setBallotList();
    }
    /**
     * Checks whether passportID is valid and user is allowed to vote.
     * @param passportID denotes string containing passport ID.
     * @return true if user is allowed to vote, otherwise - false.
     */
    private static boolean verify(String passportID){
        // do some work
        return true;
    }
    /**
     * Function intends to interact with external device to get passportID.
     * @return string as an passportID.
     */
    private static String scanPassportID(){
        String passportID = "some id"; // do some work;
        return passportID;
    }
    /**
     * Scans and verifies passportID.
     * @return correct passportID in string format.
     * @throws RuntimeException if user is not eligible to vote / passportID invalid.
     */
    public static String getPassportID() throws RuntimeException{
        String passportID = scanPassportID();
        if (!verify(passportID))
            throw new RuntimeException("Not eligible to vote!");
        return passportID;
    }
    /**
     * Adds user to the database.
     * Generates RSA key pair, which will be used for encryption and decryption of the choice.
     * @param address intended user account address.
     */
    public void addUser(byte[] address) throws Exception {
        KeyPair keyPair = KeyPair.genKeyPairRSA();
        voters.put(address, keyPair);
    }
    /**
     * Gets public key for specified user.
     * @param address intended account address.
     * @return public key, necessary for the encryption of desired choice.
     */
    public PublicKey provideEncryptionKey(byte[] address) {
        return voters.get(address).publicKey;
    }
    private void setBallotList(){
        // do something
    }
    public void countVotes() throws Exception {
        // wonderful code I know
        for (Block b : Blockchain.INSTANCE.blockHistory){
            for (Transaction tx : b.setOfTransactions){
                for (Operation op : tx.operations){
                    String ballot = new String(
                            Encryption.RSA.decrypt(op.encryptedChoice, voters.get(op.account.address).privateKey),
                            StandardCharsets.UTF_8);
                    if (pool.get(ballot) == null) // guarantees that no invalid ballots will be counted
                        throw new Exception();
                    pool.put(ballot, pool.get(ballot) + 1);
                }
            }
        }
    }
}
