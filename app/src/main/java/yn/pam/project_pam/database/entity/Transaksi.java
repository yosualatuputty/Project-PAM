package yn.pam.project_pam.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Transaksi {
    @PrimaryKey public int tid;

    public String kategori;
    public String deskripsi;

    public String nominal;
    public String sumber;

}


