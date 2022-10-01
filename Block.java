import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Block {
    public byte[] blockID;
    public byte[] prevHash;
    public ArrayList<Transaction> setOfTransactions;

    /**
     * Creates block.
     * Calculates it's blockID.
     * @return created of all transactions and specified previous hash block.
     * @throws Exception
     */
    public static Block createBlock(ArrayList<Transaction> setOfTransactions, byte[] prevHash) throws Exception {
        Block block = new Block();
        block.setOfTransactions = setOfTransactions;
        block.prevHash = prevHash;
        block.blockID = calculateBlockID(prevHash, setOfTransactions);
        return block;
    }
    /**
     * Calculates blockID in a matter of hash.
     * @param prevHash previous block hash.
     * @param setOfTransactions all transactions that are going to be included in the block.
     * @return blockID, calculated basing on transactions hash-chain and previous block hash.
     * @throws Exception
     */
    private static byte[] calculateBlockID(byte[] prevHash, ArrayList<Transaction> setOfTransactions)
            throws Exception {
        byte[] digest = setOfTransactions.get(0).transactionID;
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        for (int i = 1; i != setOfTransactions.size(); ++i){
            byteBuffer.put(digest);
            byteBuffer.put(setOfTransactions.get(i).transactionID);
            digest = Hash.calculateHash(byteBuffer.array(), "SHA-256");
            byteBuffer.clear();
        }
        byteBuffer.put(digest);
        byteBuffer.put(prevHash);
        digest = Hash.calculateHash(byteBuffer.array(), "SHA-256");

        return digest;
    }
}
