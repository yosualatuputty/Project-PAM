package yn.pam.project_pam.db;

public class Transaction {
    String id;
    String category;
    double nominal;
    String description;

    public Transaction(){}

    public Transaction(String id,String category, double nominal, String description){
        this.id = id;
        this.category = category;
        this.nominal = nominal;
        this.description = description;
    }
    public void setId(String id) {this.id = id;}
    public String getId() {return id;}
    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}
    public double getNominal() {return nominal;}
    public void setNominal(double nominal) {this.nominal = nominal;}
    public void setDescription(String description) {this.description = description;}
    public String getDescription() {return description;}
}
