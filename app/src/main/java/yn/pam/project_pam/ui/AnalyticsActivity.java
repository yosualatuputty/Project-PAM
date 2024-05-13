package yn.pam.project_pam.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import yn.pam.project_pam.R;
import yn.pam.project_pam.db.Transaction;
import yn.pam.project_pam.repository.TransactionRepository;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AnalyticsActivity extends AppCompatActivity {

    private PieChart pieChart;
    private RecyclerView recyclerView;
    private Button btBack;
    private TransactionRepository transactionRepository;
    private TransactionAdapter transactionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analytics_activity);

        pieChart = findViewById(R.id.piechart);
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);

        recyclerView = findViewById(R.id.rv_analytics);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        transactionAdapter = new TransactionAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(transactionAdapter);

        transactionRepository = new TransactionRepository(this);
        transactionRepository.getAllTransactions().observe(this, transactions -> {
            displayPieChart(transactions);
            displayTransactions(transactions);
        });

        btBack = findViewById(R.id.bt_back);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnalyticsActivity.this, DataNew.class);
                startActivity(intent);
            }
        });

    }
    private void displayPieChart(List<Transaction> transactions) {
        if (transactions != null && transactions.size() > 0) {
            List<PieEntry> pieEntries = new ArrayList<>();

            for (Transaction transaction : transactions) {
                pieEntries.add(new PieEntry((float) transaction.getNominal(), transaction.getCategory()));
            }

            PieDataSet dataSet = new PieDataSet(pieEntries, "Categories");
            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            dataSet.setValueTextSize(12f);

            dataSet.setValueFormatter(new ValueFormatter() {
                private final DecimalFormat format = new DecimalFormat("Rp ###,###");

                @Override
                public String getFormattedValue(float value) {
                    return format.format(value);
                }
            });

            PieData data = new PieData(dataSet);
            pieChart.setData(data);
            pieChart.invalidate();
        } else {
            Toast.makeText(this, "No transactions found", Toast.LENGTH_SHORT).show();
        }
    }
    private void displayTransactions(List<Transaction> transactions) {
        if (transactions != null) {
            transactionAdapter.setData(transactions);
        }
    }
    public void deleteTransaction(Transaction transaction) {
        transactionRepository.delete(transaction);
        Toast.makeText(this, "Transaction deleted", Toast.LENGTH_SHORT).show();
    }
}

