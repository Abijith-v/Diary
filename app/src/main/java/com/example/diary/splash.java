package com.example.diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.concurrent.Executor;

public class splash extends AppCompatActivity {

    ImageView retryPIN;
    CircularProgressIndicator pb;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        sharedPreferences = this.getSharedPreferences("diaryPref", MODE_PRIVATE);
        retryPIN = findViewById(R.id.fingerprintTextInSplash);
        pb = findViewById(R.id.pbSplashScreen);

        Boolean fingerPrintON = sharedPreferences.getBoolean("fingerPrintActivated", false);

        if(fingerPrintON) {
            pb.setVisibility(View.INVISIBLE);
        }
        else {
            retryPIN.setVisibility(View.INVISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(splash.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }

        retryPIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fingerPrintAuth();
            }
        });
    }

    private void fingerPrintAuth() {


        BiometricManager biometricManager = BiometricManager.from(this);
//        switch (biometricManager.canAuthenticate()) {
//
//            case BiometricManager.BIOMETRIC_SUCCESS:
//                Toast.makeText(this, "Please retry later", Toast.LENGTH_SHORT).show();
//                break;
//
//            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
//                Toast.makeText(this, "Device doesnt have a fingerprint sensor", Toast.LENGTH_SHORT).show();
//                break;
//
//            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
//                Toast.makeText(this, "Device sensor is currently unavailable", Toast.LENGTH_SHORT).show();
//                break;
//
//            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
//                Toast.makeText(this, "No fingerprints saved in this device", Toast.LENGTH_SHORT).show();
//                break;
//        }

        Executor executor = ContextCompat.getMainExecutor(this);
        final BiometricPrompt biometricPrompt = new BiometricPrompt(splash.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

                Toast.makeText(splash.this, "Retry later", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                Intent intent = new Intent(splash.this, MainActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
//                Toast.makeText(splash.this, "failed to auth", Toast.LENGTH_SHORT).show();
            }
        });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Use fingerprint sensor to unlock")
                .setDescription("Use saved fingerprint")
                .setNegativeButtonText("cancel")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }
}