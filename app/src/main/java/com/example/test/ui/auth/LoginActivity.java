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
import com.example.test.data.network.dto.AuthRequest;
import com.example.test.data.network.dto.TokenResponse;
import com.example.test.data.session.SessionManager;
import com.example.test.ui.main.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private MaterialButton btnLogin;

    private ApiService apiService;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        apiService = ApiClient.create(sessionManager);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        TextView tvForgot = findViewById(R.id.tvForgotPassword);
        TextView tvRegister = findViewById(R.id.tvRegister);

        tvForgot.setOnClickListener(v -> startActivity(new Intent(this, ForgotPasswordActivity.class)));
        tvRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));

        btnLogin.setOnClickListener(v -> login());
    }

    private void login() {
        String email = etEmail.getText() == null ? "" : etEmail.getText().toString().trim();
        String password = etPassword.getText() == null ? "" : etPassword.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.invalid_email));
            return;
        }

        if (password.length() < 6) {
            etPassword.setError(getString(R.string.invalid_password));
            return;
        }

        btnLogin.setEnabled(false);
        apiService.login(new AuthRequest(email, password)).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                btnLogin.setEnabled(true);
                if (response.isSuccessful() && response.body() != null && response.body().resolvedToken() != null) {
                    sessionManager.saveToken(response.body().resolvedToken());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finishAffinity();
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                btnLogin.setEnabled(true);
                Toast.makeText(LoginActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
