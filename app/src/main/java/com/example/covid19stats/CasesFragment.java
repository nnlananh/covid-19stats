package com.example.covid19stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import com.example.covid19stats.model.CovidSummary;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CasesFragment extends Fragment {

    private BarChart casesChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cases, container, false);
        casesChart = view.findViewById(R.id.casesChart);

        fetchCovidData();

        return view;
    }

    private void fetchCovidData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://disease.sh/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CovidApiService apiService = retrofit.create(CovidApiService.class);
        Call<CovidSummary> call = apiService.getCovidSummary("vietnam");
        call.enqueue(new Callback<CovidSummary>() {
            @Override
            public void onResponse(Call<CovidSummary> call, Response<CovidSummary> response) {
                if (response.isSuccessful() && response.body() != null) {

                    CovidSummary summary = response.body();
                    int cases = summary.getCases();
                    int deaths = summary.getDeaths();
                    int recovered = summary.getRecovered();

                    ArrayList<BarEntry> entries = new ArrayList<>();
                    entries.add(new BarEntry(0, cases));
                    entries.add(new BarEntry(1, deaths));
                    entries.add(new BarEntry(2, recovered));
                    BarDataSet dataSet = new BarDataSet(entries, "COVID-19 Cases in Vietnam");
                    dataSet.setColor(ContextCompat.getColor(getContext(), R.color.purple_500));
                    BarData barData = new BarData(dataSet);
                    barData.setBarWidth(0.9f);

                    casesChart.setData(barData);
                    XAxis xAxis = casesChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setGranularity(1f);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            if (value == 0) {
                                return "Ca nhiễm";
                            } else if (value == 1) {
                                return "Tử vong";
                            } else if (value == 2) {
                                return "Hồi phục";
                            } else {
                                return "";
                            }
                        }
                    });

                    casesChart.setFitBars(true);
                    casesChart.invalidate();
                }
            }

            @Override
            public void onFailure(Call<CovidSummary> call, Throwable t) {
                Toast.makeText(getContext(), "Không thể tải dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
