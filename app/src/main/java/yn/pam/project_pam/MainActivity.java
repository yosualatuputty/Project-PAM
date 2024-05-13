package yn.pam.project_pam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yn.pam.project_pam.database.AppDatabase;
import yn.pam.project_pam.database.entity.Transaksi;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Adapter adapter;

    private Button foodButton, allButton, transButton, petButton, dailyButton, btTambah;
    List<Transaksi> items = new ArrayList<Transaksi>();

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = AppDatabase.getInstance(this);
        items.clear();
        items.addAll(database.transaksiDao().getAll());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new TransactionFragment())
                .commit();

//        RecyclerView recyclerView = findViewById(R.id.rv_transactionList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new Adapter(this, items);
//        recyclerView.setAdapter(adapter);

//        items.add(new Item("Food", "Bakso", "-Rp 25.000", "Cash"));

//        fetchData("");

        foodButton = findViewById(R.id.bt_Food);
        foodButton.setOnClickListener(this);
        allButton = findViewById(R.id.bt_All);
        allButton.setOnClickListener(this);
        transButton = findViewById(R.id.bt_Transport);
        transButton.setOnClickListener(this);
        petButton = findViewById(R.id.bt_Pet);
        petButton.setOnClickListener(this);
        dailyButton = findViewById(R.id.bt_Daily);
        dailyButton.setOnClickListener(this);
        btTambah = findViewById(R.id.bt_tambah);
        btTambah.setOnClickListener(this);





    }

    @Override
    public void onStart() {
        super.onStart();
//        items.clear();
//        items.addAll(database.transaksiDao().getAll());
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bt_All) {
            fetchData("");
        } else if (v.getId() == R.id.bt_Food) {
            fetchData("Food");
        } else if (v.getId() == R.id.bt_Transport) {
            fetchData("Transport");
        } else if (v.getId() == R.id.bt_Pet) {
            fetchData("Pet");
        } else if (v.getId() == R.id.bt_Daily) {
            fetchData("Daily");
        } else if (v.getId() == R.id.bt_tambah) {
            startActivity(new Intent(MainActivity.this, TambahActivity.class));
        }
    }


    private void fetchData(String kategori) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Item>> call = apiService.getItems(kategori);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    List<Item> items = response.body();
                    if (items != null) {
//                        adapter.setTaskList(items);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

                Log.d("JSON_RESPONSE", "Failure");
                t.printStackTrace();
            }
        });


    }


}

//KODE LAMA

//        items.add(new Item("Food", "Bakso", "-Rp 25.000", "Cash", R.drawable.food));
//        items.add(new Item("Food", "Fried Chicken", "-Rp 27.000", "Cash", R.drawable.food));
//        items.add(new Item("Income", "From mother", "+Rp 100.000", "Mandiri", R.drawable.parrents));
//        items.add(new Item("Social", "Hangout with friends", "-Rp 35.000", "Cash", R.drawable.social));
//        items.add(new Item("Transport", "Go-Jek", "-Rp 15.000", "Cash", R.drawable.transport));
//        items.add(new Item("Health", "Fever Medicine", "-Rp 16.000", "Cash", R.drawable.health));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new Adapter(this, items));