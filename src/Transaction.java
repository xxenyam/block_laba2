import java.security.*;
import java.util.Base64;

public class Transaction {
    private String input; // Вхідна адреса
    private String output; // Вихідна адреса
    private double amount; // Сума транзакції
    private long txTimestamp; // Часова мітка
    private String txHash; // Геш транзакції
    private String signature; // Цифровий підпис

    // Конструктор
    public Transaction(String input, String output, double amount) throws NoSuchAlgorithmException {
        this.input = input;
        this.output = output;
        this.amount = amount;
        this.txTimestamp = System.currentTimeMillis();
        this.txHash = calculateHash();
    }

    // Метод для обчислення гешу транзакції
    private String calculateHash() throws NoSuchAlgorithmException {
        String dataToHash = input + output + amount + txTimestamp;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(dataToHash.getBytes());
        return Base64.getEncoder().encodeToString(hashBytes);
    }

    // Статичний метод для обчислення гешу на основі рядка
    public static String calculateHash(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data.getBytes());
        return Base64.getEncoder().encodeToString(hashBytes);
    }

    // Метод для підписання транзакції
    public void signTransaction(PrivateKey privateKey) throws Exception {
        Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");
        ecdsaSign.initSign(privateKey);
        ecdsaSign.update(txHash.getBytes());
        byte[] signatureBytes = ecdsaSign.sign();
        this.signature = Base64.getEncoder().encodeToString(signatureBytes);
    }

    // Метод для перевірки підпису транзакції
    public boolean verifyTransaction(PublicKey publicKey) throws Exception {
        Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA");
        ecdsaVerify.initVerify(publicKey);
        ecdsaVerify.update(txHash.getBytes());
        return ecdsaVerify.verify(Base64.getDecoder().decode(signature));
    }

    @Override
    public String toString() {
        return "Transaction{\n" +
                "  input='" + input + "',\n" +
                "  output='" + output + "',\n" +
                "  amount=" + amount + ",\n" +
                "  txTimestamp=" + txTimestamp + ",\n" +
                "  txHash='" + txHash + "',\n" +
                "  signature='" + (signature != null ? signature : "None") + "'\n" +
                '}';
    }
}



