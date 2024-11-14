package com.example.covid19stats;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private Button btnRegister;

    private ApiService apiService;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Khởi tạo UI components
        edtUsername = view.findViewById(R.id.edtUsername);
        edtPassword = view.findViewById(R.id.edtPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnRegister = view.findViewById(R.id.btnRegister);


        // Khởi tạo ApiService
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Xử lý sự kiện click của nút đăng nhập
        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();

            // Kiểm tra đơn giản xem các trường không được để trống
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gửi yêu cầu đăng nhập qua API
            performLogin(username, password);
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Fragment mới (ví dụ: RegisterFragment)
                RegisterFragment registerFragment = new RegisterFragment();

                // Thay thế Fragment hiện tại bằng Fragment mới
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, registerFragment) // Thay R.id.fragment_container bằng ID của container Fragment trong layout của Activity
                        .addToBackStack(null) // Thêm vào back stack để có thể quay lại Fragment trước đó
                        .commit();
            }
        });




        return view;
    }

    // Phương thức để gửi yêu cầu đăng nhập
    private void performLogin(String username, String password) {
        apiService.loginUser(username, password).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus().equals("success")) {
                        // Đăng nhập thành công
                        Toast.makeText(getContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        ((MainActivity) getActivity()).onLoginSuccess(); // Gọi phương thức trong MainActivity để hiển thị các nút
                    } else {
                        // Thông báo lỗi từ server
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Đăng nhập thất bại! Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                // Xử lý khi yêu cầu không thành công
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
