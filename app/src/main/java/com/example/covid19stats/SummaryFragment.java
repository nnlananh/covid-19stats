package com.example.covid19stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.covid19stats.model.CovidSummary;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SummaryFragment extends Fragment {

    private TextView casesTextView, deathsTextView, recoveredTextView;
    private Spinner countrySpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        // Initialize TextViews
        casesTextView = view.findViewById(R.id.casesTextView);
        deathsTextView = view.findViewById(R.id.deathsTextView);
        recoveredTextView = view.findViewById(R.id.recoveredTextView);

        countrySpinner = view.findViewById(R.id.countrySpinner);


        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedCountry = (String) parent.getItemAtPosition(position);
                fetchCovidData(selectedCountry);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        return view;
    }

    private void fetchCovidData(String country) {
        // Create an instance of Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://disease.sh/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CovidApiService apiService = retrofit.create(CovidApiService.class);
        String countryPath;
        switch (country) {
            case "USA":
                countryPath = "usa";
                break;
            case "UK":
                countryPath = "uk";
                break;
            case "Vietnam":
            default:
                countryPath = "vietnam";
                break;
        }

        // Call API
        Call<CovidSummary> call = apiService.getCovidSummary(countryPath);
        call.enqueue(new Callback<CovidSummary>() {
            @Override
            public void onResponse(Call<CovidSummary> call, Response<CovidSummary> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CovidSummary summary = response.body();
                    casesTextView.setText("Số ca nhiễm: " + summary.getCases());
                    deathsTextView.setText("Số ca tử vong: " + summary.getDeaths());
                    recoveredTextView.setText("Số ca hồi phục: " + summary.getRecovered());
                }
            }

            @Override
            public void onFailure(Call<CovidSummary> call, Throwable t) {
                // Handle error
                casesTextView.setText("Không thể tải dữ liệu");
            }
        });
    }
}
