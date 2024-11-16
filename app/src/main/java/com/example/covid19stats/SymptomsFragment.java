package com.example.covid19stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SymptomsFragment extends Fragment {

    // This method is called to inflate the layout for the fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // 'fragment_symptoms' is the XML layout resource for this fragment
        // 'container' is the parent view group that the fragment's UI should be attached to
        // 'false' means the inflated view will not be automatically added to the container
        return inflater.inflate(R.layout.fragment_symptoms, container, false);
    }
}
