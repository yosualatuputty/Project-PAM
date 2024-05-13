package yn.pam.project_pam.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Transaction")
public class Transaction implements Serializable {
    @ColumnInfo(name = "transaction_id")
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "category")
    String category;
    @ColumnInfo(name = "nominal")
    double nominal;
    @ColumnInfo(name = "description")
    String description;
    @Ignore
    public Transaction(){}

    public Transaction(String category, double nominal, String description){
        this.category = category;
        this.nominal = nominal;
        this.description = description;
        this.id = 0;
    }

    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}
    public double getNominal() {return nominal;}
    public void setNominal(double nominal) {this.nominal = nominal;}
    public void setDescription(String description) {this.description = description;}
    public String getDescription() {return description;}
}
