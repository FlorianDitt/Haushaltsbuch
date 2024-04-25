package com.example.haushaltsbuch;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;


public class AuthenticationActivity extends AppCompatActivity {
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    Button test;
    Activity a = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        test = findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonAuthenticate(view);
            }
        });

        Executor executor = ContextCompat.getMainExecutor(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    System.out.println("Error");
                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);

                    System.out.println("Success");
                    startActivity(new Intent(AuthenticationActivity.this,MainActivity.class));
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();

                    System.out.println("Failure");
                }
            });
        }

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Programmer World Authentication")
                .setNegativeButtonText("Cancel")
                .setConfirmationRequired(false)
                .build();
        BiometricManager biometricManager = BiometricManager.from(this);
        if (biometricManager.canAuthenticate() != BiometricManager.BIOMETRIC_SUCCESS) {

            System.out.println("Biometric Not Supported");
            return;
        }
        biometricPrompt.authenticate(promptInfo);
    }

    public void buttonAuthenticate(View view) {
        BiometricManager biometricManager = BiometricManager.from(this);
        if (biometricManager.canAuthenticate() != BiometricManager.BIOMETRIC_SUCCESS) {

            System.out.println("Biometric Not Supported");
            return;
        }
        biometricPrompt.authenticate(promptInfo);
    }
}