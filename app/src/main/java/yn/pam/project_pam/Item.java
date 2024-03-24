package yn.pam.project_pam;

public class Item {
    String kategori;
    String deskripsi;
    String nominal;
    String sumber;
    int logo;

    public Item(String kategori, String deskripsi, String nominal, String sumber, int logo) {
        this.kategori = kategori;
        this.deskripsi = deskripsi;
        this.nominal = nominal;
        this.sumber = sumber;
        this.logo = logo;
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

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
