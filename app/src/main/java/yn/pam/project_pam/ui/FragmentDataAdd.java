package yn.pam.project_pam.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import yn.pam.project_pam.R;
import yn.pam.project_pam.db.Transaction;
import yn.pam.project_pam.repository.TransactionRepository;

public class FragmentDataAdd extends Fragment {

    private EditText etCategory, etNominal, etDescription;
    private Button btAddData;
    private TransactionRepository transactionRepository;

    public FragmentDataAdd() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_data_add, container, false);

        transactionRepository = new TransactionRepository(requireContext().getApplicationContext());

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

        double nominal = Double.parseDouble(nominalText);

        Transaction transaction = new Transaction(category, nominal, description);

        if (transactionRepository != null) {
            transactionRepository.insert(transaction);
            Toast.makeText(getContext(), "Data added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Failed to add data", Toast.LENGTH_SHORT).show();
        }

        etCategory.setText("");
        etNominal.setText("");
        etDescription.setText("");
    }
}