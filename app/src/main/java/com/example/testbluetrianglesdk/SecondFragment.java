package com.example.testbluetrianglesdk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.bluetriangle.analytics.Timer;

public class SecondFragment extends Fragment {
    private Timer timer;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
             timer = bundle.getParcelable("timer"); // Key
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();

        timer.interactive();
    }

    @Override
    public void onResume() {
        super.onResume();

        timer.end().submit();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }
}