import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> blockchain;

    public Blockchain() {
        blockchain = new ArrayList<>(); // Ініціалізація списку блоків
    }

    // Метод для додавання нового блоку
    public void addBlock(Block newBlock) {
        blockchain.add(newBlock);
    }

    // Метод для отримання останнього блоку
    public Block getLastBlock() {
        if (blockchain.isEmpty()) {
            return null;
        }
        return blockchain.get(blockchain.size() - 1);
    }

    // Метод для отримання блока за індексом
    public Block getBlock(int index) {
        if (index < 0 || index >= blockchain.size()) {
            return null;
        }
        return blockchain.get(index);
    }

    // Метод для отримання всіх блоків
    public List<Block> getBlocks() {
        return blockchain;
    }

    @Override
    public String toString() {
        StringBuilder chainString = new StringBuilder();
        for (Block block : blockchain) {
            chainString.append(block.toString()).append("\n");
        }
        return chainString.toString();
    }
}


