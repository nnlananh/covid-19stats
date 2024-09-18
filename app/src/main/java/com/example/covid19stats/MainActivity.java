package com.example.covid19stats;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private Button btnCases, btnDeaths, btnSummary, btnPrevention, btnSymptoms, btnTestingInfo, btnUserReport;
    private View buttonsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the layout containing the buttons
        buttonsLayout = findViewById(R.id.buttonsLayout);

        // Initialize buttons
        btnCases = findViewById(R.id.btnCases);
        btnDeaths = findViewById(R.id.btnDeaths);
        btnSummary = findViewById(R.id.btnSummary);
        btnPrevention = findViewById(R.id.btnPrevention);
        btnSymptoms = findViewById(R.id.btnSymptoms);
        btnTestingInfo = findViewById(R.id.btnTestingInfo);
        btnUserReport = findViewById(R.id.btnUserReport);

        // Initially, hide the buttons layout until the user logs in
        buttonsLayout.setVisibility(View.GONE);

        // Load LoginFragment as the first fragment
        loadFragment(new LoginFragment(), false);

        // Set button click listeners after login
        btnCases.setOnClickListener(v -> loadFragment(new CasesFragment(), true));
        btnDeaths.setOnClickListener(v -> loadFragment(new DeathsFragment(), true));
        btnSummary.setOnClickListener(v -> loadFragment(new SummaryFragment(), true));
        btnPrevention.setOnClickListener(v -> loadFragment(new PreventionFragment(), true));
        btnSymptoms.setOnClickListener(v -> loadFragment(new SymptomsFragment(), true));
        btnTestingInfo.setOnClickListener(v -> loadFragment(new TestingInfoFragment(), true));
        btnUserReport.setOnClickListener(v -> loadFragment(new UserReportFragment(), true));
    }

    // Method to load fragments
    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    // This method will be called after a successful login
    public void onLoginSuccess() {
        // Show the buttons layout after login
        buttonsLayout.setVisibility(View.VISIBLE);

        // Load the default fragment (e.g., CasesFragment) after login success
        loadFragment(new CasesFragment(), false);
    }
}
