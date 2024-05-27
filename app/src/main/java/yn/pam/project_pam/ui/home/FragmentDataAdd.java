package yn.pam.project_pam.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import yn.pam.project_pam.R;
import yn.pam.project_pam.db.Transaction;

public class FragmentDataAdd extends Fragment {

    private EditText etCategory, etNominal, etDescription;
    private Button btAddData;
    private DatabaseReference transactionsRef;

    public FragmentDataAdd() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_add, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        transactionsRef = database.getReference("transactions");

        etCategory = view.findViewById(R.id.et_category);
        etNominal = view.findViewById(R.id.et_nominal);
        etDescription = view.findViewById(R.id.et_description);

        btAddData = view.findViewById(R.id.bt_addData);
        btAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        return view;
    }

    private void addData() {
        String category = etCategory.getText().toString().trim();
        String nominalText = etNominal.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (category.isEmpty() || nominalText.isEmpty() || description.isEmpty()) {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        double nominal;
        try {
            nominal = Double.parseDouble(nominalText);
        } catch (NumberFormatException e) {
            etNominal.setError("Invalid number");
            return;
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        DatabaseReference userTransactionsRef = transactionsRef.child(userId);

        String transactionId = userTransactionsRef.push().getKey();
        Transaction transaction = new Transaction(transactionId, category, nominal, description);

        if (transactionId != null) {
            userTransactionsRef.child(transactionId).setValue(transaction)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Transaction saved", Toast.LENGTH_SHORT).show();
                                clearFields();
                            } else {
                                Toast.makeText(getContext(), "Failed to save transaction", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void clearFields() {
        etCategory.setText("");
        etNominal.setText("");
        etDescription.setText("");
    }
}
