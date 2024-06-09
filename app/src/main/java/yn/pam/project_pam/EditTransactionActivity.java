package yn.pam.project_pam;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditTransactionActivity extends AppCompatActivity {

    private Button continueButton;
    private EditText amountEditText;
    private EditText descriptionEditText;
    private ArrayList<CategoryModel> categoryList;
    private ArrayList<WalletModel> walletList;
    private RecyclerView categoryRecycleView;
    private RecyclerView walletRecycleView;
    private CategoryAdapter categoryAdapter;
    private WalletAdapter walletAdapter;
    private TextView categoryTextView;
    private RelativeLayout categoryDropdown;
    private TextView walletTextView;
    private RelativeLayout walletDropdown;
    private ImageView closeIcon;
    private Transaction item;
    private DatabaseReference mDatabase;
    private int logo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        categoryTextView = findViewById(R.id.tv_category_dropdown);
        walletTextView = findViewById(R.id.tv_wallet_dropdown);
        descriptionEditText = findViewById(R.id.et_description);
        amountEditText = findViewById(R.id.et_amount);

        Intent intent = getIntent();
        String jsonItem = intent.getStringExtra("itemData");
        if (jsonItem != null) {
            Gson gson = new Gson();
            item = gson.fromJson(jsonItem, Transaction.class);

            categoryTextView.setText(item.getCategory());
            amountEditText.setText(item.getAmount());
            descriptionEditText.setText(item.getDescription());
            walletTextView.setText(item.getWallet());
            logo = item.getLogo(); // default to existing logo
        }

        mDatabase = FirebaseDatabase.getInstance().getReference("transactions");

        categoryTextView = findViewById(R.id.tv_category_dropdown);
        categoryDropdown = findViewById(R.id.category_dropdown_container);
        categoryRecycleView = findViewById(R.id.rv_category);
        categoryAdapter = new CategoryAdapter(getApplicationContext(), categoryList, categoryTextView, categoryDropdown);
        categoryRecycleView.setLayoutManager(new LinearLayoutManager(this));
        categoryRecycleView.setAdapter(categoryAdapter);
        categoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CategoryLoader() {
                    @Override
                    protected void onPostExecute(ArrayList<CategoryModel> result) {
                        super.onPostExecute(result);
                        categoryList = result;
                        categoryAdapter = new CategoryAdapter(getApplicationContext(), categoryList, categoryTextView, categoryDropdown);
                        categoryRecycleView.setAdapter(categoryAdapter);
                        categoryDropdown.setVisibility(View.VISIBLE);
                    }
                }.execute();

            }
        });


        walletTextView = findViewById(R.id.tv_wallet_dropdown);
        walletDropdown = findViewById(R.id.wallet_dropdown_container);
        walletRecycleView = findViewById(R.id.rv_wallet);
        walletAdapter = new WalletAdapter(getApplicationContext(), walletList, walletTextView, walletDropdown);
        walletRecycleView.setLayoutManager(new LinearLayoutManager(this));
        walletRecycleView.setAdapter(walletAdapter);
        walletTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WalletLoader() {
                    @Override
                    protected void onPostExecute(ArrayList<WalletModel> result) {
                        super.onPostExecute(result);
                        walletList = result;
                        walletAdapter = new WalletAdapter(getApplicationContext(), walletList, walletTextView, walletDropdown);
                        walletRecycleView.setAdapter(walletAdapter);
                        walletDropdown.setVisibility(View.VISIBLE);
                    }
                }.execute();

            }
        });

        continueButton = findViewById(R.id.bt_continue);
        amountEditText = findViewById(R.id.et_amount);
        descriptionEditText = findViewById(R.id.et_description);
        closeIcon = findViewById(R.id.iv_close);

        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = item.getId();
                String category = categoryTextView.getText().toString().trim();
                String wallet = walletTextView.getText().toString().trim();
                String amount = amountEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();
                logo = item.getLogo();

                // Update logo only if category changes
                if (!category.equals(item.getCategory())) {
                    if (category.equals("Food")) {
                        logo = R.drawable.ic_food;
                    } else if (category.equals("Pet")) {
                        logo = R.drawable.ic_pet;
                    } else if (category.equals("Social")) {
                        logo = R.drawable.ic_social;
                    } else if (category.equals("Communication")) {
                        logo = R.drawable.ic_communication;
                    } else if (category.equals("Health")) {
                        logo = R.drawable.ic_health;
                    } else if (category.equals("Daily")) {
                        logo = R.drawable.ic_daily;
                    } else if (category.equals("Education")) {
                        logo = R.drawable.ic_education;
                    } else if (category.equals("Gift")) {
                        logo = R.drawable.ic_gift;
                    } else if (category.equals("Fashion")) {
                        logo = R.drawable.ic_fashion;
                    } else if (category.equals("Transport")) {
                        logo = R.drawable.ic_transport;
                    } else if (category.equals("Beauty")) {
                        logo = R.drawable.ic_beauty;
                    } else {
                        logo = R.drawable.ic_food;
                    }
                }

                item.setCategory(category);
                item.setWallet(wallet);
                item.setAmount(amount);
                item.setDescription(description);
                item.setLogo(logo);

                if (!category.isEmpty() && !wallet.isEmpty() && !amount.isEmpty() && !description.isEmpty()) {
                    updateTransaction(id, category, wallet, amount, description, logo);
                    Toast.makeText(EditTransactionActivity.this, "Transaction updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditTransactionActivity.this, DisplayTransactionActivity.class));
                    finish();
                } else {
                    Toast.makeText(EditTransactionActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getClassLoader();
    }


    private void updateTransaction(String id, String category, String wallet, String amount, String description, int logo) {
        Transaction transaction = new Transaction(id, category, wallet, amount, description, logo);
        Map<String, Object> transactionUpdates = new HashMap<>();
        transactionUpdates.put(String.valueOf(id), transaction.toMap());

        mDatabase.updateChildren(transactionUpdates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Transaction updated successfully.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Failed to update transaction: " + e.getMessage());
                    }
                });
    }


}