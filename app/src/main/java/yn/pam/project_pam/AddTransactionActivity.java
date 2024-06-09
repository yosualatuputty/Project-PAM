package yn.pam.project_pam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddTransactionActivity extends AppCompatActivity {

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
    private DatabaseReference mDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        mDatabase = FirebaseDatabase.getInstance().getReference("transactions");

        categoryTextView = findViewById(R.id.tv_category_dropdown);
        categoryDropdown = findViewById(R.id.category_dropdown_container);
        categoryRecycleView = findViewById(R.id.rv_category);
        walletTextView = findViewById(R.id.tv_wallet_dropdown);
        walletDropdown = findViewById(R.id.wallet_dropdown_container);
        walletRecycleView = findViewById(R.id.rv_wallet);
        continueButton = findViewById(R.id.bt_continue);
        amountEditText = findViewById(R.id.et_amount);
        descriptionEditText = findViewById(R.id.et_description);
        closeIcon = findViewById(R.id.iv_close);

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

        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTransactionActivity.this, DisplayTransactionActivity.class);
                startActivity(intent);
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = categoryTextView.getText().toString().trim();
                int logo = 0;
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
                String id = mDatabase.push().getKey();
                String wallet = walletTextView.getText().toString().trim();
                String amount = amountEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();

                Toast.makeText(AddTransactionActivity.this, "Transaction added", Toast.LENGTH_SHORT).show();

                if (!category.isEmpty() && !wallet.isEmpty() && !amount.isEmpty() && !description.isEmpty()) {
                    addTransaction(id, category, wallet, amount, description, logo);
                    Toast.makeText(AddTransactionActivity.this, "Transaction added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddTransactionActivity.this, DisplayTransactionActivity.class));
                    finish();
                } else {
                    Toast.makeText(AddTransactionActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });
        getClassLoader();
    }

    private void addTransaction(String id, String category, String wallet, String amount, String description, int logo) {
        Transaction transaction = new Transaction(id, category, wallet, amount, description, logo);
        mDatabase.child(id).setValue(transaction); // Menggunakan kunci yang sama untuk menyimpan data
    }
}