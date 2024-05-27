package yn.pam.project_pam.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import yn.pam.project_pam.R;

public class UpdateDataActivity extends AppCompatActivity {

    private EditText etCategory, etDescription, etNominal;
    private Button btnUpdate;
    private String transactionId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_data);

        etCategory = findViewById(R.id.et_categoryupdate);
        etDescription = findViewById(R.id.et_descriptionupdate);
        etNominal = findViewById(R.id.et_nominalupdate);
        btnUpdate = findViewById(R.id.bt_updateData);

        transactionId = getIntent().getStringExtra("transactionId");
        String category = getIntent().getStringExtra("category");
        String description = getIntent().getStringExtra("description");
        double nominal = getIntent().getDoubleExtra("nominal", 0.0);

        etCategory.setText(category);
        etDescription.setText(description);
        etNominal.setText(String.valueOf(nominal));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTransaction();
            }
        });
    }

    private void updateTransaction() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String category = etCategory.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String nominalString = etNominal.getText().toString().trim();

        if (TextUtils.isEmpty(category) || TextUtils.isEmpty(description) || TextUtils.isEmpty(nominalString)) {
            Toast.makeText(UpdateDataActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        double nominal = Double.parseDouble(nominalString);

        DatabaseReference transactionRef = FirebaseDatabase.getInstance().getReference("transactions").child(userId).child(transactionId);

        transactionRef.child("category").setValue(category);
        transactionRef.child("description").setValue(description);
        transactionRef.child("nominal").setValue(nominal);

        Toast.makeText(UpdateDataActivity.this, "Transaction updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
