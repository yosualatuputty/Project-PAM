package yn.pam.project_pam.activity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import androidx.room.Room;

import com.google.gson.Gson;

import java.util.ArrayList;

import yn.pam.project_pam.CategoryLoader;
import yn.pam.project_pam.R;
import yn.pam.project_pam.Transaction;
import yn.pam.project_pam.TransactionDatabase;
import yn.pam.project_pam.adapter.CategoryAdapter;
import yn.pam.project_pam.adapter.WalletAdapter;
import yn.pam.project_pam.model.CategoryModel;
import yn.pam.project_pam.model.WalletModel;

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

        }

        TransactionDatabase transactionDatabase = Room.databaseBuilder(getApplicationContext(),
                TransactionDatabase.class, "transaction-db").build();

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


        walletList = generateSampleData2();
        walletTextView = findViewById(R.id.tv_wallet_dropdown);
        walletDropdown = findViewById(R.id.wallet_dropdown_container);
        walletRecycleView = findViewById(R.id.rv_wallet);
        walletAdapter = new WalletAdapter(getApplicationContext(), walletList, walletTextView, walletDropdown);
        walletRecycleView.setLayoutManager(new LinearLayoutManager(this));
        walletRecycleView.setAdapter(walletAdapter);
        walletTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walletDropdown.setVisibility(View.VISIBLE);
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
                String category = categoryTextView.getText().toString().trim();
                String wallet = walletTextView.getText().toString().trim();
                String amount = amountEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();

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

                Toast.makeText(EditTransactionActivity.this, "Button Continue diklik", Toast.LENGTH_SHORT).show();

                item.setCategory(category);
                item.setWallet(wallet);
                item.setAmount(amount);
                item.setDescription(description);

                int finalLogo = logo;
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        Log.d("EditTransaction", "doInBackground: Updating transaction in database");
                        transactionDatabase.transactionDao().update(item);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        Log.d("EditTransaction", "onPostExecute: Transaction updated, finishing activity");
                        // Kembalikan ke activity sebelumnya setelah memperbarui transaksi
                        finish();
                    }
                }.execute();

            }
        });
        getClassLoader();
    }


    private ArrayList<WalletModel> generateSampleData2() {
        ArrayList<WalletModel> walletList = new ArrayList<>();
        WalletModel.initWallet();
        for (WalletModel wallet : WalletModel.getWalletArrayList()) {
            walletList.add(new WalletModel(wallet.getId(), wallet.getName()));
        }
        return walletList;
    }

}