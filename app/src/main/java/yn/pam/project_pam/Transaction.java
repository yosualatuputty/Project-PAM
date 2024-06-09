package yn.pam.project_pam;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "transactions")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private String id;
    private String category;
    private String wallet;
    private String amount;
    private String description;
    private int logo;

    public Transaction(){}

    public Transaction(String id,String category, String wallet, String amount, String description, int logo) {
        this.id = id;
        this.category = category;
        this.wallet = wallet;
        this.amount = amount;
        this.description = description;
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("category", category);
        result.put("wallet", wallet);
        result.put("amount", amount);
        result.put("description", description);
        result.put("logo", logo);
        return result;
    }

}