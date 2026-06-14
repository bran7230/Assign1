package com.example.assign1;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.assign1.databinding.ActivityMainBinding;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private final SearchFragment searchFragment = new SearchFragment();
    private final SavedFragment savedFragment = new SavedFragment();
    private final SettingsFragment settingsFragment = new SettingsFragment();
    private Fragment activeFragment = searchFragment;

    private ActivityMainBinding binding;
    private final Set<String> savedCities = new HashSet<>();
    private final Map<String, CityWeather> cityData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initCityData();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction().add(R.id.fragment_container, settingsFragment, "3").hide(settingsFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container, savedFragment, "2").hide(savedFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container, searchFragment, "1").commit();

        setBottomBarVisibility(true);

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            // Clear backstack when switching tabs
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            
            if (id == R.id.nav_search) {
                switchFragments(searchFragment);
                return true;
            } else if (id == R.id.nav_saved) {
                switchFragments(savedFragment);
                return true;
            } else if (id == R.id.nav_settings) {
                switchFragments(settingsFragment);
                return true;
            }
            return false;
        });

        getOnBackPressedDispatcher().addCallback(this, new androidx.activity.OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                    setBottomBarVisibility(true);
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                    setEnabled(true);
                }
            }
        });
    }

    private void initCityData() {
        cityData.put("London", new CityWeather("London", "City of London, Greater London", "Cloudy", 14, 57, 78, 18, 12, "2 Low"));
        cityData.put("Toronto", new CityWeather("Toronto", "Ontario, Canada", "Clear", 18, 64, 45, 12, 17, "4 Moderate"));
        cityData.put("Tokyo", new CityWeather("Tokyo", "Tokyo-to, Japan", "Rainy", 25, 77, 90, 22, 26, "1 Low"));
        cityData.put("Sydney", new CityWeather("Sydney", "New South Wales, Australia", "Sunny", 28, 82, 35, 15, 30, "9 Very High"));
        cityData.put("New York", new CityWeather("New York", "New York State, USA", "Partly Cloudy", 20, 68, 60, 20, 19, "6 High"));
    }

    public CityWeather getCityData(String cityName) {
        return cityData.get(cityName);
    }

    public void navigateToDetail(String cityName) {
        WeatherDetailFragment detailFragment = WeatherDetailFragment.newInstance(cityName);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
        setBottomBarVisibility(false);
    }

    public void setBottomBarVisibility(boolean visible) {
        if (binding != null) {
            binding.bottomNavigation.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    public void toggleCitySaved(String cityName) {
        if (savedCities.contains(cityName)) {
            savedCities.remove(cityName);
        } else {
            savedCities.add(cityName);
        }
    }

    public boolean isCitySaved(String cityName) {
        return savedCities.contains(cityName);
    }

    public Set<String> getSavedCities() {
        return savedCities;
    }

    private void switchFragments(Fragment targetFragment) {
        if (activeFragment != targetFragment) {
            getSupportFragmentManager().beginTransaction()
                    .hide(activeFragment)
                    .show(targetFragment)
                    .commit();
            activeFragment = targetFragment;
        }
        setBottomBarVisibility(true);
    }
}
