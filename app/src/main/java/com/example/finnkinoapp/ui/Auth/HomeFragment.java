package com.example.finnkinoapp.ui.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finnkinoapp.R;
import com.example.finnkinoapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements  View.OnClickListener {

    private FragmentHomeBinding binding;
    private TextView register;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // register button
        register = binding.registerUser;
        register.setOnClickListener(this);

        // final TextView textView = binding.textHome;
        // homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    // clicking register text opens new activity for register view
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerUser:
                startActivity(new Intent(getActivity().getBaseContext(), RegisterActivity.class));
                break;
        }
    }
}