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

    private TextView tvReportResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_result, container, false);

        tvReportResult = view.findViewById(R.id.tvReportResult);

        // Lấy dữ liệu từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String reportData = bundle.getString("report_data");
            tvReportResult.setText(reportData);
        }

        return view;
    }
}
