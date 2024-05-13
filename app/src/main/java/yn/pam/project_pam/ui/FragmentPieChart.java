package yn.pam.project_pam.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import yn.pam.project_pam.R;
import yn.pam.project_pam.db.Transaction;
import yn.pam.project_pam.repository.TransactionRepository;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class FragmentPieChart extends Fragment {

    private TransactionRepository transactionRepository;

    public FragmentPieChart() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);

        transactionRepository = new TransactionRepository(requireContext().getApplicationContext());

        transactionRepository.getAllTransactions().observe(getViewLifecycleOwner(), transactions -> {
            displayPieChart(transactions, view);
        });

        return view;
    }

    private void displayPieChart(List<Transaction> transactions, View view) {

        PieChart pieChart = view.findViewById(R.id.piechart);
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);

        if (transactions != null && transactions.size() > 0) {
            List<PieEntry> pieEntries = new ArrayList<>();

            for (Transaction transaction : transactions) {
                pieEntries.add(new PieEntry((float) transaction.getNominal(), transaction.getCategory()));
            }

            PieDataSet dataSet = new PieDataSet(pieEntries, "Categories");
            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            dataSet.setValueTextSize(12f);

            PieData data = new PieData(dataSet);
            pieChart.setData(data);
            pieChart.invalidate();
        } else {
            Toast.makeText(getContext(), "No transactions found", Toast.LENGTH_SHORT).show();
        }
    }
}