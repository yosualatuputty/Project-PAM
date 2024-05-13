package yn.pam.project_pam.repository;
import android.content.Context;
import androidx.lifecycle.LiveData;
import yn.pam.project_pam.db.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionRepository {
    private TransactionDAO transactionDAO;
    private LiveData<List<Transaction>> allTransactions;
    private ExecutorService executorService;
    public TransactionRepository(Context context) {
        TransactionDatabase database = TransactionDatabase.getInstance(context);
        transactionDAO = database.transactionDAO();
        allTransactions = transactionDAO.getAllTransactions();
        executorService = Executors.newSingleThreadExecutor();
    }
    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }
    public void insert(Transaction transaction) {
        executorService.execute(() -> transactionDAO.insert(transaction));
    }
    public void update(Transaction transaction) {
        executorService.execute(() -> transactionDAO.update(transaction));
    }
    public void delete(Transaction transaction) {
        executorService.execute(() -> transactionDAO.delete(transaction));
    }
}
