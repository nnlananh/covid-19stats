package com.example.covid19stats;

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

public class LoginFragment extends Fragment {

    private EditText edtUsername, edtPassword;
    private Button btnLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize UI components
        edtUsername = view.findViewById(R.id.edtUsername);
        edtPassword = view.findViewById(R.id.edtPassword);
        btnLogin = view.findViewById(R.id.btnLogin);

        // Handle login button click
        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();

            // Simple validation for login
            if (username.equals("admin") && password.equals("1234")) {
                // Call method in MainActivity to switch to the main content
                ((MainActivity) getActivity()).onLoginSuccess();
            } else {
                Toast.makeText(getContext(), "Invalid username or password.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
