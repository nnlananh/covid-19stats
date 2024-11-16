package com.example.covid19stats;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnCases, btnDeaths, btnSummary, btnPrevention, btnSymptoms, btnTestingInfo, btnUserReport;
    private View buttonsLayout;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Sử dụng đúng bố cục chính

        // Khởi tạo ApiService
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Khởi tạo các View
        buttonsLayout = findViewById(R.id.buttonsLayout);
        btnCases = findViewById(R.id.btnCases);
        btnDeaths = findViewById(R.id.btnDeaths);
        btnSummary = findViewById(R.id.btnSummary);
        btnPrevention = findViewById(R.id.btnPrevention);
        btnSymptoms = findViewById(R.id.btnSymptoms);
        btnTestingInfo = findViewById(R.id.btnTestingInfo);
        btnUserReport = findViewById(R.id.btnUserReport);

        // Ẩn layout chứa các button cho đến khi người dùng đăng nhập thành công
        buttonsLayout.setVisibility(View.GONE);

        // Load LoginFragment là fragment đầu tiên
        loadFragment(new LoginFragment(), false);

        // Đặt sự kiện click cho các button sau khi đăng nhập
        btnCases.setOnClickListener(v -> loadFragment(new CasesFragment(), true));
        btnSummary.setOnClickListener(v -> loadFragment(new SummaryFragment(), true));
        btnPrevention.setOnClickListener(v -> loadFragment(new PreventionFragment(), true));
        btnSymptoms.setOnClickListener(v -> loadFragment(new SymptomsFragment(), true));
        btnTestingInfo.setOnClickListener(v -> loadFragment(new TestingInfoFragment(), true));
        btnUserReport.setOnClickListener(v -> loadFragment(new UserReportFragment(), true));

        // Thực hiện đăng nhập (hoặc đăng ký) khi người dùng cung cấp thông tin
        performLogin("user@example.com", "password123"); // Thay thế bằng dữ liệu thực tế
    }

    // Phương thức để thực hiện đăng nhập hoặc đăng ký
    private void performLogin(String email, String password) {
        apiService.loginUser(email, password).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus().equals("success")) {
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        onLoginSuccess(); // Hiển thị các nút sau khi đăng nhập thành công
                    } else {
                        Toast.makeText(MainActivity.this, "Đăng nhập thất bại: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Phản hồi không thành công!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Yêu cầu thất bại: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Phương thức để load các fragment
    void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    // Phương thức này sẽ được gọi sau khi đăng nhập thành công
    public void onLoginSuccess() {
        buttonsLayout.setVisibility(View.VISIBLE); // Hiển thị layout các button sau khi đăng nhập
        loadFragment(new CasesFragment(), false); // Load fragment mặc định sau khi đăng nhập thành công
    }
}
