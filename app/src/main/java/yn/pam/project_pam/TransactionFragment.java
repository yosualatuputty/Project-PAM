package yn.pam.project_pam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import yn.pam.project_pam.database.AppDatabase;
import yn.pam.project_pam.database.entity.Transaksi;

import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TransactionFragment extends Fragment implements Adapter.EditClickListener {

    List<Transaksi> items = new ArrayList<Transaksi>();

    private AppDatabase database;

    private RecyclerView recyclerView;
    private Adapter adapter;

    private DatabaseReference databaseReference;

    private FirebaseDatabase firebaseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
//        database = AppDatabase.getInstance(getContext());
//        items.clear();
//        items.addAll(database.transaksiDao().getAll());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter = new Adapter(getContext(), items, this);
        readData();
        return view;
    }

    private void readData() {
        DatabaseReference notesRef = databaseReference.child("transaction");
        notesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Transaction> transactionList = new ArrayList<>();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Transaction transaction = snapshot.getValue(Transaction.class);
                    String key = snapshot.getKey();
                    keys.add(key);
                    transactionList.add(transaction);
                }

                Adapter adapter = new Adapter(getContext(), transactionList, keys, TransactionFragment.this, databaseReference);


                if(transactionList.isEmpty()) {
                    Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                } else {
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to read data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onEditClicked(String transactionId, String kategori, String deskripsi, String nominal, String sumber) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, EditActivityFragment.newInstance(transactionId, kategori, deskripsi, nominal, sumber));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
