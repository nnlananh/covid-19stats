package com.example.covid19stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserReportFragment extends Fragment {

    private EditText edtTemperature, edtOtherDetails;
    private CheckBox chkCough, chkFever, chkHeadache, chkBreathing;
    private Button btnSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_report, container, false);

        // Initialize UI components
        edtTemperature = view.findViewById(R.id.edtTemperature);
        edtOtherDetails = view.findViewById(R.id.edtOtherDetails);
        chkCough = view.findViewById(R.id.chkCough);
        chkFever = view.findViewById(R.id.chkFever);
        chkHeadache = view.findViewById(R.id.chkHeadache);
        chkBreathing = view.findViewById(R.id.chkBreathing);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        // Handle submit button click
        btnSubmit.setOnClickListener(v -> {
            String temperatureStr = edtTemperature.getText().toString();
            double temperature = 0;
            try {
                temperature = Double.parseDouble(temperatureStr);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Please enter a valid temperature.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra nếu người dùng chọn Fever trong khi nhiệt độ dưới 37.5°C
            if (temperature < 37.5 && chkFever.isChecked()) {
                Toast.makeText(getContext(), "You cannot select 'Fever' when your temperature is below 37.5°C.", Toast.LENGTH_LONG).show();
                return;
            }

            // Xử lý dữ liệu báo cáo và tạo khuyến nghị
            String recommendation = getRecommendation(temperature);

            // Chuyển dữ liệu sang ReportResultFragment
            Bundle bundle = new Bundle();
            bundle.putString("report_data", recommendation);

            // Chuyển hướng sang ReportResultFragment
            ReportResultFragment reportFragment = new ReportResultFragment();
            reportFragment.setArguments(bundle);

            // Thực hiện Fragment transaction để chuyển sang ReportResultFragment
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, reportFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view; // This was missing, return the view after setting up
    }

    private String getRecommendation(double temperature) {
        StringBuilder recommendation = new StringBuilder();

        if (temperature > 38.0) {
            recommendation.append("High temperature detected. Please self-isolate and monitor your symptoms.\n");
        } else if (temperature > 37.5) {
            recommendation.append("Slightly elevated temperature. Consider monitoring your symptoms and stay cautious.\n");
        } else if (temperature >= 36.5 && temperature <= 37.5) {
            recommendation.append("Temperature is within normal range.\n");
        } else if (temperature < 36.5) {
            recommendation.append("Low temperature detected. This may indicate hypothermia. Seek medical attention if you feel unwell.\n");
        }

        if (chkCough.isChecked()) {
            recommendation.append("Cough detected. Consider contacting a healthcare provider.\n");
        }
        if (chkFever.isChecked()) {
            recommendation.append("Fever detected. Self-isolate and monitor your symptoms.\n");
        }
        if (chkHeadache.isChecked()) {
            recommendation.append("Headache detected. Rest and monitor your condition.\n");
        }
        if (chkBreathing.isChecked()) {
            recommendation.append("Difficulty breathing detected. Seek medical attention immediately.\n");
        }

        if (!chkCough.isChecked() && !chkFever.isChecked() && !chkHeadache.isChecked() && !chkBreathing.isChecked()) {
            recommendation.append("No severe symptoms reported. Continue to monitor your health.\n");
        }

        return recommendation.toString();
    }
}
