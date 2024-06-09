package yn.pam.project_pam;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import yn.pam.project_pam.database.AppDatabase;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TambahActivity extends AppCompatActivity {
    ImageView closeButton;
    Button continueButton;

    EditText amount, desc;
    AppDatabase database;

    Spinner spinner_cat, spinner_wall;

    DatabaseReference transactionsRef;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);

//        database = AppDatabase.getInstance(getApplicationContext());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        transactionsRef = database.getReference("transactions");
        closeButton = findViewById(R.id.closeButton_add);
        continueButton = findViewById(R.id.bt_add);
//        String jsonItem = getIntent().getStringExtra("itemData");

        spinner_cat = findViewById(R.id.sp_category);
        spinner_wall = findViewById(R.id.sp_wallet);
        amount = findViewById(R.id.et_amount);
        desc = findViewById(R.id.et_desc);

        String[] category = {"Food", "Health", "Income", "Transport"};
        String[] wallet = {"Mandiri", "Cash"};
//
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, category );
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, wallet );
//
//
//
        spinner_cat.setAdapter(adapter);
        spinner_wall.setAdapter(adapter2);
//        amount.setText(item.getNominal());
//        desc.setText(item.getDeskripsi());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
//                database.transaksiDao().insertAll(spinner_cat.getSelectedItem().toString(), desc.getText().toString(),
//                        amount.getText().toString(), spinner_wall.getSelectedItem().toString());
                submitData();

                finish();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




//
    }

    public void submitData(){
        if (!validateForm()){
            return;
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getApplicationContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        DatabaseReference userTransactionsRef = transactionsRef.child(userId);
        String transactionId = userTransactionsRef.push().getKey();

        double nominal = Double.parseDouble(amount.getText().toString());
        String deskripsi = desc.getText().toString();
        Transaction baru = new Transaction(spinner_cat.getSelectedItem().toString(), deskripsi, nominal, spinner_wall.getSelectedItem().toString());

        if (transactionId != null) {
            userTransactionsRef.child(transactionId).setValue(baru)
                    .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Add data",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to Add data", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = this.getIntent();
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(amount.getText().toString())) {
            amount.setError("Required");
            result = false;
        } else {
            desc.setError(null);
        }
        if (TextUtils.isEmpty(desc.getText().toString())) {
            desc.setError("Required");
            result = false;
        } else {
            desc.setError(null);
        }
        return result;
    }



}
