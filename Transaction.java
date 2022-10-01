import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Transaction {
    public byte[] transactionID;
    public ArrayList<Operation> operations;
    int nonce;
    private Transaction(){

    }
    public static Transaction createTransaction(ArrayList<Operation> operations) throws Exception {

        for (Operation op : operations){
            if (!Operation.verifyOperation(op))
                throw new RuntimeException("Invalid transaction");
        }
        Transaction transaction = new Transaction();
        transaction.transactionID = transaction.calculateTransactionID();
        transaction.operations = operations;
        transaction.nonce = SecureRandom.getInstanceStrong().nextInt();
        return transaction;
    }
    byte[] calculateTransactionID() throws Exception {
        byte[] digest = operations.get(0).hash();
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        for (int i = 1; i != operations.size(); ++i){
            byteBuffer.put(digest);
            byteBuffer.put(operations.get(i).hash());
            digest = Hash.calculateHash(byteBuffer.array(), "SHA-256");
            byteBuffer.clear();
        }
        return digest;
    }
}
