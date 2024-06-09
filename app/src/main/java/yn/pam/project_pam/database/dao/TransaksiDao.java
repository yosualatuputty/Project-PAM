package yn.pam.project_pam.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import yn.pam.project_pam.database.entity.Transaksi;

@Dao
public interface TransaksiDao {
    @Query("SELECT * FROM transaksi")
    List<Transaksi> getAll();

    @Query("INSERT INTO transaksi (kategori, deskripsi, nominal, sumber) VALUES (:kategori, :deskripsi, :nominal, :sumber)")
    void insertAll(String kategori, String deskripsi, String nominal, String sumber);

    @Query("UPDATE transaksi SET nominal = :nominal, deskripsi = :deskripsi, " +
            "kategori = :kategori, sumber = :sumber WHERE tid = :id")
    void updateAll(String kategori, String deskripsi, String nominal, String sumber, int id);

    @Query("SELECT * FROM transaksi WHERE tid = :tid")
    Transaksi get(int tid);
    @Delete
    void delete(Transaksi transaksi);


}
