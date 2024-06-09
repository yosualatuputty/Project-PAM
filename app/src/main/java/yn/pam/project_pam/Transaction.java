package yn.pam.project_pam;

public class Transaction {

    private  String kategori;

    private String deskripsi;

    private int logo;

    private int id;

    private double nominal;

    private String sumber;

    public Transaction(String kategori, String deskripsi, double nominal, String sumber) {
        this.kategori = kategori;
        this.deskripsi = deskripsi;
        this.nominal = nominal;
        this.sumber = sumber;
    }

    public Transaction() {

    }



    public String getSumber() {
        return sumber;
    }

    public void setSumber(String sumber) {
        this.sumber = sumber;
    }



    public double getNominal() {
        return nominal;
    }

    public void setNominal(double nominal) {
        this.nominal = nominal;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
