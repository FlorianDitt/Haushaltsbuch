package com.example.haushaltsbuch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AuthenticationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
        if (!fingerprintManager.isHardwareDetected()) {
            Toast.makeText(AuthenticationActivity.this, "Keinen Fingerabdruck verfügbar", Toast.LENGTH_SHORT).show();
        } else if (!fingerprintManager.hasEnrolledFingerprints()) {
            Toast.makeText(AuthenticationActivity.this, "Das Gerät besitzt keinen Fingerabdruck", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AuthenticationActivity.this, "Fingerabdruck funktioniert 1", Toast.LENGTH_SHORT).show();
            CheckFingerprint(this);
        }
    }
    public void CheckFingerprint(Activity a){
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
        FingerprintManager.AuthenticationCallback authenticationCallback = new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                // Fingerabdruck erfolgreich erkannt
                // Öffne die gewünschte Activity oder führe die gewünschte Aktion aus
                Toast.makeText(a, "Fingerabdruck funktioniert 2", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AuthenticationActivity.this, MainActivity.class));
            }
            @Override
            public void onAuthenticationFailed() {
                // Fingerabdruck nicht erkannt
                Toast.makeText(a, "Fingerabdruck nicht erkannt", Toast.LENGTH_SHORT).show();
            }
        };
        // Starte die Authentifizierung
        fingerprintManager.authenticate(null, null, 0, authenticationCallback, null);

    }
}