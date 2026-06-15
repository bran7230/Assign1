package com.example.assign1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.assign1.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        binding.tvAppVersion.setText("Version: " + BuildConfig.VERSION_NAME);

        binding.btnFeedback.setOnClickListener(v -> sendFeedback());
        binding.btnGitHub.setOnClickListener(v -> viewGitHub());
        binding.btnShare.setOnClickListener(v -> shareApp());

        return binding.getRoot();
    }

    private void sendFeedback() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:200608789@student.georgianc.on.ca"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for WeatherNow App");
        
        try {
            startActivity(Intent.createChooser(intent, "Send Email"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewGitHub() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/bran7230/Assign1"));
        
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "No browser app available.", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Check out WeatherNow, the best weather app! https://weathernow.com");
        
        try {
            startActivity(Intent.createChooser(intent, "Share via"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "No app available to share.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
