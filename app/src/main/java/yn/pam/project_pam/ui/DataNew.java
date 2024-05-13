package yn.pam.project_pam.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import yn.pam.project_pam.R;
import yn.pam.project_pam.db.Transaction;
import yn.pam.project_pam.repository.TransactionRepository;

public class DataNew extends AppCompatActivity {

    private EditText etCategory, etNominal, etDescription;
    private Button btAddData, btGoAnalytics;
    private TransactionRepository transactionRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newdata);

        etCategory = findViewById(R.id.et_category);
        etNominal = findViewById(R.id.et_nominal);
        etDescription = findViewById(R.id.et_description);
        btAddData = findViewById(R.id.bt_addData);
        btGoAnalytics = findViewById(R.id.bt_goanalytics);

        transactionRepository = new TransactionRepository(this);
        btAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDataToDatabase();
            }
        });
        btGoAnalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataNew.this, AnalyticsActivity.class));
            }
        });

    }
    private void addDataToDatabase(){
        String category = etCategory.getText().toString().trim();
        double nominal = Double.parseDouble(etNominal.getText().toString().trim());
        String description = etDescription.getText().toString().trim();

        Transaction transaction = new Transaction(category, nominal, description);
        transactionRepository.insert(transaction);

        Toast.makeText(DataNew.this, "Data added successfully", Toast.LENGTH_SHORT).show();

        etCategory.setText("");
        etNominal.setText("");
        etDescription.setText("");
    }
}
