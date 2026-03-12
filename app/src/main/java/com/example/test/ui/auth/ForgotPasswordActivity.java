package com.example.test.ui.auth;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;
import com.example.test.data.network.ApiClient;
import com.example.test.data.network.ApiService;
import com.example.test.data.session.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        TextInputEditText etForgotEmail = findViewById(R.id.etForgotEmail);
        MaterialButton btnContinue = findViewById(R.id.btnContinue);

        ApiService apiService = ApiClient.create(new SessionManager(this));

        btnContinue.setOnClickListener(v -> {
            String email = etForgotEmail.getText() == null ? "" : etForgotEmail.getText().toString().trim();
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etForgotEmail.setError(getString(R.string.invalid_email));
                return;
            }

            btnContinue.setEnabled(false);
            Map<String, String> body = new HashMap<>();
            body.put("email", email);

            apiService.recover(body).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    btnContinue.setEnabled(true);
                    if (response.isSuccessful()) {
                        Toast.makeText(ForgotPasswordActivity.this, getString(R.string.recover_sent), Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, getString(R.string.recover_failed), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    btnContinue.setEnabled(true);
                    Toast.makeText(ForgotPasswordActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
