package com.example.assign1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.assign1.databinding.FragmentSavedBinding;
import com.google.android.material.card.MaterialCardView;
import java.util.Set;

public class SavedFragment extends Fragment {
    private FragmentSavedBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSavedBinding.inflate(inflater, container, false);
        updateSavedList();
        return binding.getRoot();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            updateSavedList();
        }
    }

    private void updateSavedList() {
        if (getActivity() instanceof MainActivity && binding != null) {
            MainActivity activity = (MainActivity) getActivity();
            Set<String> saved = activity.getSavedCities();

            // Clear previous dynamic views
            binding.savedListContainer.removeAllViews();

            if (saved.isEmpty()) {
                binding.emptyStateContainer.setVisibility(View.VISIBLE);
            } else {
                binding.emptyStateContainer.setVisibility(View.GONE);
                for (String cityName : saved) {
                    addCityCard(cityName);
                }
            }
        }
    }

    private void addCityCard(String cityName) {
        MaterialCardView card = new MaterialCardView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen.item_spacing));
        card.setLayoutParams(params);
        card.setRadius(getResources().getDimension(R.dimen.card_radius));
        card.setCardElevation(0);
        card.setStrokeWidth(0);
        card.setCardBackgroundColor(getResources().getColor(R.color.white, null));

        TextView textView = new TextView(getContext());
        textView.setText(cityName);
        textView.setPadding(48, 48, 48, 48);
        textView.setTextSize(18);
        textView.setTextColor(getResources().getColor(R.color.text_slate, null));
        textView.setTypeface(null, android.graphics.Typeface.BOLD);

        card.addView(textView);
        card.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToDetail(cityName);
            }
        });

        binding.savedListContainer.addView(card);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
