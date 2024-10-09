import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Main {
    public static void main(String[] args) {
        try {
            // Генерація ключів для підпису
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            keyGen.initialize(new ECGenParameterSpec("secp256r1"), new SecureRandom());
            KeyPair keyPair = keyGen.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            // Створення блокчейну
            Blockchain blockchain = new Blockchain();
            System.out.println("=== Тестування Blockchain ===");

            // Створення та додавання кількох блоків з транзакціями
            for (int i = 1; i <= 3; i++) {
                Block block = new Block(i, (i - 1) == 0 ? "0" : blockchain.getLastBlock().getBlockHash());
                System.out.println("=== Генерація блоку " + i + " ===");

                // Визначення базового номера користувача
                int baseUserNumber = (i - 1) * 2 + 1;

                // Додаємо 2 транзакції до кожного блоку
                for (int j = 1; j <= 2; j++) {
                    String sender = "User" + (baseUserNumber + j - 1);
                    String recipient = "User" + (baseUserNumber + j);

                    // Визначаємо різні суми для кожної транзакції
                    double amount = (i * 100) + (j * 25); // Змінюємо формулу для різних сум
                    Transaction tx = new Transaction(sender, recipient, amount); // Використовуємо різну суму
                    tx.signTransaction(privateKey);
                    block.addTransaction(tx);
                    System.out.println("Додана " + tx);
                }

                // Додаємо блок до блокчейну
                blockchain.addBlock(block);
                System.out.println("=== Блок " + i + " додано до блокчейну ===");
                System.out.println("====================================");
            }

            // Виведення блокчейну
            System.out.println("\n=== Виведення всього блокчейну ===");
            System.out.println(blockchain);

            // Верифікація підписів для кожної транзакції у всіх блоках
            System.out.println("=== Верифікація підписів транзакцій ===");
            for (Block block : blockchain.getBlocks()) {
                for (Transaction tx : block.getTransactions()) {
                    System.out.println("Підпис транзакції: " + tx.verifyTransaction(publicKey));
                }
            }

        } catch (Exception e) {
            System.err.println("Виникла помилка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}







