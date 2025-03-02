import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CardDatabase {
    private static CardDatabase instance;
    private Map<String, String> cardDatabase = new HashMap<>();
    private final String FILE_NAME = "cards.txt";

    private CardDatabase() {
        loadCardDatabase();
    }

    public static synchronized CardDatabase getInstance() {
        if (instance == null) {
            instance = new CardDatabase();
        }
        return instance;
    }

    private void loadCardDatabase() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] cardData = line.split(" ");
                if (cardData.length == 2) {
                    cardDatabase.put(cardData[0], cardData[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading card data: " + e.getMessage());
        }
    }

    public boolean addCard(String cardID, String password) {
        if (cardDatabase.containsKey(cardID)) {
            return false;
        }
        cardDatabase.put(cardID, password);
        return saveToFile();
    }

    public boolean modifyCard(String cardID, String newPassword) {
        if (!cardDatabase.containsKey(cardID)) {
            return false;
        }
        cardDatabase.put(cardID, newPassword);
        return saveToFile();
    }

    public boolean revokeCard(String cardID) {
        if (!cardDatabase.containsKey(cardID)) {
            return false;
        }
        cardDatabase.remove(cardID);
        return saveToFile();
    }

    private boolean saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, String> entry : cardDatabase.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error saving card data: " + e.getMessage());
            return false;
        }
    }
}
