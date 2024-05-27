package yn.pam.project_pam.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import yn.pam.project_pam.db.Transaction;

public class FragmentTransactionList extends Fragment {

    private RecyclerView recyclerView;
    private TransactionAdapter transactionAdapter;
    private DatabaseReference transactionsRef;

    public FragmentTransactionList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_list, container, false);

        recyclerView = view.findViewById(R.id.rv_transactionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            // User not logged in
            return view;
        }
        String userId = currentUser.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        transactionsRef = database.getReference("transactions").child(userId);

        transactionAdapter = new TransactionAdapter(getContext());
        recyclerView.setAdapter(transactionAdapter);

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
                transactionAdapter.setData(transactions);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle cancelled event
            }
        });

        return view;
    }
}