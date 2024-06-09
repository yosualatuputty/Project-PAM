package yn.pam.project_pam.analytics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import yn.pam.project_pam.R;
import yn.pam.project_pam.Transaction;

public class FragmentPieChart extends Fragment {

    private DatabaseReference transactionsRef;

    public FragmentPieChart() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            // User not logged in
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            return view;
        }
        String userId = currentUser.getUid();
        transactionsRef = database.getReference("transactions").child(userId);

        transactionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Transaction> transactions = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Transaction transaction = snapshot.getValue(Transaction.class);
                    if (transaction != null) {
                        transactions.add(transaction);
                    }
                }
                displayPieChart(transactions, view);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load transactions", Toast.LENGTH_SHORT).show();
            }
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
                pieEntries.add(new PieEntry((float) transaction.getNominal(), transaction.getKategori()));
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