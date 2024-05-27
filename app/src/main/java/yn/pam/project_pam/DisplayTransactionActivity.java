package yn.pam.project_pam.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import yn.pam.project_pam.R;
import yn.pam.project_pam.Transaction;
import yn.pam.project_pam.TransactionDatabase;
import yn.pam.project_pam.adapter.HomeAdapter;
import yn.pam.project_pam.model.WalletModel;

public class DisplayTransactionActivity extends AppCompatActivity {

    private TransactionDatabase transactionDatabase;
    private RecyclerView recyclerView;

    public static List<Transaction> items = new ArrayList<Transaction>();
    private static ArrayList<WalletModel> walletList;
    private List<Transaction> previousTransactions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_transaction);

        walletList = generateSampleData2();
        recyclerView = findViewById(R.id.rv_transactionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new HomeAdapter(this, items));

        transactionDatabase = Room.databaseBuilder(getApplicationContext(),
                TransactionDatabase.class, "transaction-db").build();

        FloatingActionButton addTransaction = findViewById(R.id.addTransaction);

        addTransaction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DisplayTransactionActivity.this, AddTransactionActivity.class);
                startActivity(intent);
            }
        });
        loadTransactions();
    }

    public static ArrayList<WalletModel> getWalletList() {
        return walletList;
    }

    private ArrayList<WalletModel> generateSampleData2() {
        ArrayList<WalletModel> wallets = new ArrayList<>();
        WalletModel.initWallet();
        for (WalletModel wallet : WalletModel.getWalletArrayList()) {
            wallets.add(new WalletModel(wallet.getId(), wallet.getName()));
        }
        return wallets;
    }

    private void loadTransactions() {
        new AsyncTask<Void, Void, List<Transaction>>() {
            @Override
            protected List<Transaction> doInBackground(Void... voids) {
                return transactionDatabase.transactionDao().getAllTransactions();
            }

            @Override
            protected void onPostExecute(List<Transaction> transactions) {
                super.onPostExecute(transactions);
                items = transactions; // Set items ke data yang baru
                displayTransactions(transactions);
            }
        }.execute();
    }



    public void displayTransactions(List<Transaction> transactions) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new HomeAdapter(this, transactions));
    }
}