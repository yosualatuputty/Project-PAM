package yn.pam.project_pam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
<<<<<<< Updated upstream
=======
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yn.pam.project_pam.analytics.AnalyticsActivity;
import yn.pam.project_pam.database.AppDatabase;
import yn.pam.project_pam.database.entity.Transaksi;
>>>>>>> Stashed changes

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< Updated upstream
=======
//        database = AppDatabase.getInstance(this);
//        items.clear();
//        items.addAll(database.transaksiDao().getAll());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new TransactionFragment())
                .commit();

//        RecyclerView recyclerView = findViewById(R.id.rv_transactionList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new Adapter(this, items);
//        recyclerView.setAdapter(adapter);

//        items.add(new Item("Food", "Bakso", "-Rp 25.000", "Cash"));

//        fetchData("");

//        foodButton = findViewById(R.id.bt_Food);
//        foodButton.setOnClickListener(this);
//        allButton = findViewById(R.id.bt_All);
//        allButton.setOnClickListener(this);
//        transButton = findViewById(R.id.bt_Transport);
//        transButton.setOnClickListener(this);
//        petButton = findViewById(R.id.bt_Pet);
//        petButton.setOnClickListener(this);
//        dailyButton = findViewById(R.id.bt_Daily);
//        dailyButton.setOnClickListener(this);
        btTambah = findViewById(R.id.bt_tambah);
//        btTambah.setOnClickListener(this);

        btTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });

        Button statNavButton = findViewById(R.id.bt_statNav);
        statNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnalyticsActivity.class);
                startActivity(intent);
            }
        });



>>>>>>> Stashed changes

        RecyclerView recyclerView = findViewById(R.id.rv_transactionList);

        List<Item> items = new ArrayList<Item>();
        items.add(new Item("Food", "Bakso", "-Rp 25.000", "Cash", R.drawable.food));
        items.add(new Item("Food", "Fried Chicken", "-Rp 27.000", "Cash", R.drawable.food));
        items.add(new Item("Income", "From mother", "+Rp 100.000", "Mandiri", R.drawable.parrents));
        items.add(new Item("Social", "Hangout with friends", "-Rp 35.000", "Cash", R.drawable.social));
        items.add(new Item("Transport", "Go-Jek", "-Rp 15.000", "Cash", R.drawable.transport));
        items.add(new Item("Health", "Fever Medicine", "-Rp 16.000", "Cash", R.drawable.health));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Adapter(this, items));
    }
}