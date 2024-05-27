package yn.pam.project_pam.ui.auth;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import yn.pam.project_pam.R;
import yn.pam.project_pam.ui.home.MainActivity;

public class Auth extends AppCompatActivity {
    private EditText etEmail, etPass;
    private Button btnMasuk, btnDaftar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_pass);
        btnMasuk = findViewById(R.id.btn_masuk);
        btnDaftar = findViewById(R.id.btn_daftar);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");

        btnMasuk.setOnClickListener(this::onClick);
        btnDaftar.setOnClickListener(this::onClick);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(Auth.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_masuk:
                login(etEmail.getText().toString(), etPass.getText().toString());
                break;
            case R.id.btn_daftar:
                signUp(etEmail.getText().toString(), etPass.getText().toString());
                break;
        }
    }

    public void signUp(String email, String password) {
        if (!validateForm()) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                saveUserToDatabase(user);
                                updateUI(user);
                                Toast.makeText(Auth.this, user.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Auth.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void saveUserToDatabase(FirebaseUser user) {
        String userId = user.getUid();
        String userEmail = user.getEmail();

        User userData = new User(userId, userEmail);
        usersRef.child(userId).setValue(userData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User data saved successfully.");
                        } else {
                            Log.w(TAG, "Failed to save user data.", task.getException());
                        }
                    }
                });
    }

    public void login(String email, String password) {
        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Auth.this, user.toString(), Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Auth.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Required");
            result = false;
        } else {
            etEmail.setError(null);
        }
        if (TextUtils.isEmpty(etPass.getText().toString())) {
            etPass.setError("Required");
            result = false;
        } else {
            etPass.setError(null);
        }
        return result;
    }

    public void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(Auth.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(Auth.this, "Log In First", Toast.LENGTH_SHORT).show();
        }
    }

    static class User {
        public String id;
        public String name;

        public User(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
