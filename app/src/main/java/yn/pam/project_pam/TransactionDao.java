package yn.pam.project_pam;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransactionDao {
    @Insert
    void insert(Transaction transaction);

    @Query("SELECT * FROM transactions")
    List<Transaction> getAllTransactions();

    @Query("DELETE FROM transactions WHERE id = :transactionId")
    void deleteTransactionById(int transactionId);

    @Update
    void update(Transaction transaction);
}