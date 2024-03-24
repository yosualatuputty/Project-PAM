package yn.pam.project_pam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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