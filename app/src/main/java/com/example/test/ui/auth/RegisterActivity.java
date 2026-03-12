package com.example.test.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;
import com.example.test.data.network.ApiClient;
import com.example.test.data.network.ApiService;
import com.example.test.data.network.dto.RegisterRequest;
import com.example.test.data.network.dto.TokenResponse;
import com.example.test.data.session.SessionManager;
import com.example.test.ui.main.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final int MIN_PASSWORD_LENGTH = 8;

    private ApiService apiService;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sessionManager = new SessionManager(this);
        apiService = ApiClient.create(sessionManager);

        TextInputEditText etFullName = findViewById(R.id.etFullName);
        TextInputEditText etEmail = findViewById(R.id.etEmail);
        TextInputEditText etPassword = findViewById(R.id.etPassword);
        MaterialButton btnSignUp = findViewById(R.id.btnSignUp);
        TextView tvLogin = findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(v -> finish());

        btnSignUp.setOnClickListener(v -> {
            String fullName = etFullName.getText() == null ? "" : etFullName.getText().toString().trim();
            String email = etEmail.getText() == null ? "" : etEmail.getText().toString().trim();
            String password = etPassword.getText() == null ? "" : etPassword.getText().toString().trim();

            if (fullName.isEmpty()) {
                etFullName.setError(getString(R.string.required_field));
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError(getString(R.string.invalid_email));
                return;
            }
            if (password.length() < MIN_PASSWORD_LENGTH) {
                etPassword.setError(getString(R.string.invalid_password));
                return;
            }

            btnSignUp.setEnabled(false);
            apiService.register(new RegisterRequest(fullName, email, password)).enqueue(new Callback<TokenResponse>() {
                @Override
                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                    btnSignUp.setEnabled(true);
                    if (response.isSuccessful() && response.body() != null && response.body().resolvedToken() != null) {
                        sessionManager.saveToken(response.body().resolvedToken());
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finishAffinity();
                    } else {
                        Toast.makeText(RegisterActivity.this, getString(R.string.register_failed), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TokenResponse> call, Throwable t) {
                    btnSignUp.setEnabled(true);
                    Toast.makeText(RegisterActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
