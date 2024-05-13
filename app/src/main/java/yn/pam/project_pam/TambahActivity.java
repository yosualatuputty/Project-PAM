package yn.pam.project_pam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import yn.pam.project_pam.database.AppDatabase;

public class TambahActivity extends AppCompatActivity {
    ImageView closeButton;
    Button continueButton;
    private AppDatabase database;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);

        database = AppDatabase.getInstance(getApplicationContext());

        closeButton = findViewById(R.id.closeButton_add);
        continueButton = findViewById(R.id.bt_add);
//        String jsonItem = getIntent().getStringExtra("itemData");

        Spinner spinner_cat = findViewById(R.id.sp_category);
        Spinner spinner_wall = findViewById(R.id.sp_wallet);
        EditText amount = findViewById(R.id.et_amount);
        EditText desc = findViewById(R.id.et_desc);

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
                database.transaksiDao().insertAll(spinner_cat.getSelectedItem().toString(), desc.getText().toString(),
                        amount.getText().toString(), spinner_wall.getSelectedItem().toString());
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

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = this.getIntent();
    }


}
