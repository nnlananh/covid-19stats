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

    // Declare a TextView to display the report result
    private TextView tvReportResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment and return the view
        View view = inflater.inflate(R.layout.fragment_report_result, container, false);

        // Initialize the TextView for displaying the report result
        tvReportResult = view.findViewById(R.id.tvReportResult);

        // Retrieve the data passed from the UserReportFragment via Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            // Extract the "report_data" string from the Bundle
            String reportData = bundle.getString("report_data");
            // Set the extracted data to the TextView to display the report
            tvReportResult.setText(reportData);
        }

        // Return the view to be displayed
        return view;
    }
}
