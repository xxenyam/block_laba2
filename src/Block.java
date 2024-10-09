import java.util.ArrayList;
import java.util.List;

public class Block {
    private int version;
    private String prevHash; // Геш попереднього блоку
    private long timestamp; // Часова мітка
    private List<Transaction> transactions; // Список транзакцій

    public Block(int version, String prevHash) {
        this.version = version;
        this.prevHash = prevHash;
        this.timestamp = System.currentTimeMillis();
        this.transactions = new ArrayList<>(); // Ініціалізація списку транзакцій
    }

    // Метод для додавання транзакції
    public void addTransaction(Transaction tx) {
        transactions.add(tx);
    }

    public String getBlockHash() throws Exception {
        // Метод для обчислення гешу блоку
        // Тут може бути реалізація обчислення гешу блоку на основі версії, попереднього хешу, мітки часу та транзакцій
        // Для простоти реалізуй тут обчислення гешу на основі версії і попереднього хешу
        StringBuilder blockData = new StringBuilder();
        blockData.append(version).append(prevHash).append(timestamp);
        for (Transaction tx : transactions) {
            blockData.append(tx.toString());
        }
        return Transaction.calculateHash(blockData.toString());
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        StringBuilder blockString = new StringBuilder();
        blockString.append("Block{\n")
                .append("  version=").append(version).append(",\n")
                .append("  prevHash='").append(prevHash).append("',\n")
                .append("  timestamp=").append(timestamp).append(",\n")
                .append("  transactions=").append(transactions.size()).append("\n")
                .append("}");
        return blockString.toString();
    }
}



