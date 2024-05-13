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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPieChart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPieChart extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TransactionRepository transactionRepository;

    public FragmentPieChart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPieChart.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPieChart newInstance(String param1, String param2) {
        FragmentPieChart fragment = new FragmentPieChart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);

        // Inisialisasi repository
        transactionRepository = new TransactionRepository(requireContext().getApplicationContext());

        // Ambil data transaksi dari repository
        transactionRepository.getAllTransactions().observe(getViewLifecycleOwner(), transactions -> {
            // Tampilkan data di dalam PieChart
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