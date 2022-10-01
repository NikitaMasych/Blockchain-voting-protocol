 import java.security.PrivateKey;
    import java.security.PublicKey;
    import java.security.Signature;

    public class SIGNATURE{
        public static byte[] signData(PrivateKey prk, byte[] message) throws  Exception{
            Signature signature = Signature.getInstance("SHA-256withECDSA");
            signature.initSign(prk);
            signature.update(message);
            return signature.sign();
        }
        public static boolean verifySignature(byte[] signedMessage, byte[] message, PublicKey pbk)
                throws Exception{
            Signature signature = Signature.getInstance("SHA-256withECDSA");
            signature.initVerify(pbk);
            signature.update(message);
            return signature.verify(signedMessage);

        }
    }
