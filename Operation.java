import java.nio.ByteBuffer;
public class Operation {
    public Account account;
    private byte[] signedChoice;
    public byte[] encryptedChoice;

    private Operation(){}
    public static Operation createOperation(Account sender, byte[] encryptedChoice) throws Exception {
        Operation operation = new Operation();

        operation.account = sender;
        operation.encryptedChoice = encryptedChoice;
        operation.signedChoice = sender.signData(encryptedChoice);

        return operation;
    }
    public static boolean verifyOperation(Operation operation) throws Exception {
        if (operation.account.voted)
            return false;
        return operation.account.verifyData(operation.signedChoice, operation.encryptedChoice);
    }
    public byte[] hash() throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.allocate(account.address.length + signedChoice.length + encryptedChoice.length);
        byteBuffer.put(account.address);
        byteBuffer.put(signedChoice);
        byteBuffer.put(encryptedChoice);
        return Hash.calculateHash(byteBuffer.array(), "SHA-256");
    }
}
