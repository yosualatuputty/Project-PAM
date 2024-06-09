package yn.pam.project_pam;

import static android.content.Intent.getIntent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import yn.pam.project_pam.database.AppDatabase;
import yn.pam.project_pam.database.entity.Transaksi;

public class EditActivityFragment extends Fragment {
    ImageView closeButton;
    Button continueButton;
    private AppDatabase database;

    String kategori, deskripsi, nominal, sumber, id;

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    public static EditActivityFragment newInstance(String transactionId, String kategori, String deskripsi, String nominal, String sumber) {
        EditActivityFragment fragment = new EditActivityFragment();
        Bundle args = new Bundle();
        args.putString("id", transactionId);
        args.putString("kategori", kategori);
        args.putString("deskripsi", deskripsi);
        args.putString("nominal", nominal);
        args.putString("sumber", sumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_layout, container, false);
        // Initialize views and handle editing
//        database = AppDatabase.getInstance(getContext());

        if (getArguments() != null) {
            id = getArguments().getString("id", "");
            kategori = getArguments().getString("kategori", "");
            deskripsi = getArguments().getString("deskripsi", "");
            nominal = getArguments().getString("nominal", "");
            sumber = getArguments().getString("sumber", "");

        }
//        Transaksi transaksi = database.transaksiDao().get(id);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        closeButton = view.findViewById(R.id.closeButton_edit);
        continueButton = view.findViewById(R.id.bt_continueEdit);
//        String jsonItem = getIntent().getStringExtra("itemData");

        Spinner spinner_cat = view.findViewById(R.id.sp_category);
        Spinner spinner_wall = view.findViewById(R.id.sp_wallet);
        EditText amount = view.findViewById(R.id.et_amount);
        EditText desc = view.findViewById(R.id.et_desc);

        String[] category = {"Food", "Health", "Income", "Transport"};
        String[] wallet = {"Mandiri", "Cash"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, category );
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, wallet );

        spinner_cat.setAdapter(adapter);
        spinner_wall.setAdapter(adapter2);
        amount.setText(nominal);
        desc.setText(deskripsi);

        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(kategori)) {
                spinner_cat.setSelection(i);
                break;
            }
        }

        for (int i = 0; i < adapter2.getCount(); i++) {
            if (adapter2.getItem(i).equals(sumber)) {
                spinner_wall.setSelection(i);
                break;
            }
        }
//
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
//                database.transaksiDao().updateAll(spinner_cat.getSelectedItem().toString(), desc.getText().toString(),
//                        amount.getText().toString(), spinner_wall.getSelectedItem().toString(), transaksi.tid );

                Transaction updatedTransaction= new Transaction(spinner_cat.getSelectedItem().toString(), desc.getText().toString(),
                        Double.parseDouble(amount.getText().toString()), spinner_wall.getSelectedItem().toString());

                updateData(id, updatedTransaction);

                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new TransactionFragment())
                        .commit();
            }
        });


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new TransactionFragment())
                        .commit();
            }
        });


        return view;


    }

    private void updateData(String transactionId, Transaction updatedTransaction) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference transactionRef = FirebaseDatabase.getInstance().getReference("transactions").child(userId).child(transactionId);
        transactionRef.setValue(updatedTransaction)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Transaction updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed to update transaction", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
