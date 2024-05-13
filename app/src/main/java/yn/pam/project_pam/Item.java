package yn.pam.project_pam;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("kategori")
    private String kategori;
    @SerializedName("deskripsi")
    private String deskripsi;
    @SerializedName("nominal")
    private String nominal;
    @SerializedName("sumber")
    private String sumber;
    @SerializedName("logo")
    private int logo;

    @SerializedName("id")
    private int id;


    public Item(String kategori, String deskripsi, String nominal, String sumber, int id) {
        this.kategori = kategori;
        this.deskripsi = deskripsi;
        this.nominal = nominal;
        this.sumber = sumber;
        this.logo = logo;
        this.id = id;
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

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getSumber() {
        return sumber;
    }

    public void setSumber(String sumber) {
        this.sumber = sumber;
    }

    public int getLogo() {
        return logo;
    }

    public int getId() {
        return id;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
