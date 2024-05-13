package yn.pam.project_pam.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import yn.pam.project_pam.R;
import yn.pam.project_pam.db.Transaction;
import yn.pam.project_pam.repository.TransactionRepository;

import java.text.DecimalFormat;

public class UpdateDataActivity extends AppCompatActivity {
    private EditText etCategory, etNominal, etDescription;
    private Button btUpdateData;
    private Transaction transaction;
    private DecimalFormat decimalFormat;
    private TransactionRepository transactionRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_data);

        etCategory = findViewById(R.id.et_categoryupdate);
        etNominal = findViewById(R.id.et_nominalupdate);
        etDescription = findViewById(R.id.et_descriptionupdate);
        btUpdateData = findViewById(R.id.bt_updateData);

        decimalFormat = new DecimalFormat("Rp ###,###");
        transactionRepository = new TransactionRepository(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("transaction")) {
            transaction = (Transaction) intent.getSerializableExtra("transaction");
            if (transaction != null) {
                etCategory.setText(transaction.getCategory());
                etNominal.setText(decimalFormat.format(transaction.getNominal()));
                etDescription.setText(transaction.getDescription());
            }
        }

        btUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    private void updateData() {
        if (transaction != null) {
            String category = etCategory.getText().toString().trim();
            String nominalStr = etNominal.getText().toString().trim().replace("Rp", "").replace(",", "");
            double nominal = Double.parseDouble(nominalStr);
            String description = etDescription.getText().toString().trim();

            transaction.setCategory(category);
            transaction.setNominal(nominal);
            transaction.setDescription(description);

            transactionRepository.update(transaction);

            Intent intent = new Intent(UpdateDataActivity.this, AnalyticsActivity.class);
            startActivity(intent);

            finish();
        }
    }
}
