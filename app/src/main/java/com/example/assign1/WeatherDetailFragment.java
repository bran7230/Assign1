package com.example.assign1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.assign1.databinding.FragmentWeatherDetailBinding;

public class WeatherDetailFragment extends Fragment {
    private FragmentWeatherDetailBinding binding;
    private static final String ARG_CITY_NAME = "city_name";

    public static WeatherDetailFragment newInstance(String cityName) {
        WeatherDetailFragment fragment = new WeatherDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY_NAME, cityName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWeatherDetailBinding.inflate(inflater, container, false);

        String cityName = getArguments() != null ? getArguments().getString(ARG_CITY_NAME) : "London";
        
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            CityWeather weather = activity.getCityData(cityName);
            if (weather != null) {
                displayWeather(weather, activity);
            }
            
            updateBookmarkIcon(activity, cityName);
            
            binding.btnBookmark.setOnClickListener(v -> {
                activity.toggleCitySaved(cityName);
                updateBookmarkIcon(activity, cityName);
                String message = activity.isCitySaved(cityName) ? "City bookmarked!" : "Bookmark removed.";
                android.widget.Toast.makeText(getContext(), message, android.widget.Toast.LENGTH_SHORT).show();
            });
        }

        binding.btnBack.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).setBottomBarVisibility(true);
            }
            getParentFragmentManager().popBackStack();
        });

        return binding.getRoot();
    }

    private void updateBookmarkIcon(MainActivity activity, String cityName) {
        boolean isSaved = activity.isCitySaved(cityName);
        binding.btnBookmark.setImageResource(isSaved ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
    }

    private void displayWeather(CityWeather weather, MainActivity activity) {
        binding.tvCityName.setText(weather.cityName);
        binding.tvCitySubtitle.setText(weather.subtitle);
        binding.tvCondition.setText(weather.condition);
        binding.tvTempC.setText(String.valueOf(weather.tempC));
        binding.tvTempF.setText(weather.tempF + "°F");
        binding.tvHumidity.setText(String.valueOf(weather.humidity));
        binding.tvWind.setText(String.valueOf(weather.windKph));
        binding.tvFeelsLike.setText(String.valueOf(weather.feelsLikeC));
        
        String[] uvParts = weather.uvIndex.split(" ", 2);
        binding.tvUvIndex.setText(uvParts[0]);
        if (uvParts.length > 1) {
            binding.tvUvLabel.setText(uvParts[1]);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
