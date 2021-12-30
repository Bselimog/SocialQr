package com.example.bigcode.ui.tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.bigcode.QR_Generator;
import com.example.bigcode.QR_tarat;
import com.example.bigcode.R;

public class ToolsFragment extends Fragment {
    Activity context;
    private ToolsViewModel toolsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View view = inflater.inflate(R.layout.biletolustur_fragment, container, false);

        Button QR_tarat = view.findViewById(R.id.qr_tarat);
        QR_tarat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentQR = new Intent(getActivity(), QR_tarat.class);
                startActivity(intentQR);
            }
        });
        Button QR_generator = view.findViewById(R.id.referans_tara);
        QR_generator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent QR_generatorr = new Intent(getActivity(), QR_Generator.class);
                startActivity(QR_generatorr);
            }
        });
        return view;
    }
}