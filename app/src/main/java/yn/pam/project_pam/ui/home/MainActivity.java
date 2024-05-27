package yn.pam.project_pam.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import yn.pam.project_pam.R;
import yn.pam.project_pam.ui.auth.Auth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnKeluar = findViewById(R.id.btnKeluar);

        mAuth = FirebaseAuth.getInstance();
        btnKeluar.setOnClickListener(this::onClick);

        Button statNavButton = findViewById(R.id.bt_statNav);
        statNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnalyticsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void logOut(){
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, Auth.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//makesure user cant go back
        startActivity(intent);
        finish();
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnKeluar:
                logOut();
                break;
        }
    }
}