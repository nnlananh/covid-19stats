package com.example.covid19stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ReportResultFragment extends Fragment {

    // TextView for displaying the report result
    private TextView tvReportResult;

    // Called to inflate the fragment's view
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout from XML (R.layout.fragment_report_result)
        View view = inflater.inflate(R.layout.fragment_report_result, container, false);

        // Initialize the TextView by finding it within the inflated layout
        tvReportResult = view.findViewById(R.id.tvReportResult);

        // Retrieve the Bundle containing arguments passed to the fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            // Get the "report_data" string from the Bundle
            String reportData = bundle.getString("report_data");

            // Set the retrieved data to the TextView to display it
            tvReportResult.setText(reportData);
        }

        // Return the inflated view to be displayed
        return view;
    }
}

