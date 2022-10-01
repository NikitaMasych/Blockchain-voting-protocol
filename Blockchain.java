import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Blockchain {
    public static Blockchain INSTANCE = new Blockchain(); // Blockchain is singleton
    public ArrayList<Block> blockHistory;
    private Blockchain(){
        try {
            initBlockchain();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Creates genesis block and add it to empty blockchain.
     * @throws Exception
     */
    public static void initBlockchain() throws Exception {
        Account creator = new Account();
        Operation startOP = Operation.createOperation(creator,
                "This is where the fun begins..".getBytes(StandardCharsets.UTF_8));
        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(startOP);
        Transaction startTX = Transaction.createTransaction(operations);
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(startTX);
        Block genesisBlock = Block.createBlock(transactions, new byte[32]);
        Blockchain.INSTANCE.blockHistory.add(genesisBlock);
    }
    /**
     * Performs block validation.
     * If block is valid, in all accounts,
     * mentioned in all operations, voted gets marked as true.
     * @param block intended block which need to be validated.
     */
    public static void validateBlock(Block block){
        // need some consensus approach
        Blockchain.INSTANCE.blockHistory.add(block);
        for(Transaction tx : block.setOfTransactions){
            for (Operation op : tx.operations){
                op.account.voted = true;
            }
        }
    }
}
