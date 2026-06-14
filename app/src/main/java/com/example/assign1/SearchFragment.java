package com.example.assign1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.assign1.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        
        setupCard(binding.cardLondon, "London");
        setupCard(binding.cardToronto, "Toronto");
        setupCard(binding.cardTokyo, "Tokyo");
        setupCard(binding.cardSydney, "Sydney");
        setupCard(binding.cardNewYork, "New York");

        return binding.getRoot();
    }

    private void setupCard(View card, String cityName) {
        card.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToDetail(cityName);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
