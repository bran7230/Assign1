package com.example.assign1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.assign1.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends AppCompatActivity {

    private static final long SPLASH_DELAY = 2000L;
    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
        
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, SPLASH_DELAY);
    }
}
