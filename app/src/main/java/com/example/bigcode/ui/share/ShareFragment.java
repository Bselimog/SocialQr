package com.example.bigcode.ui.share;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.bigcode.Login;
import com.example.bigcode.R;
import com.google.firebase.auth.FirebaseAuth;

public class ShareFragment extends Fragment {

    View view;
    private FirebaseAuth mAuth;
    private ShareViewModel shareViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.signout_fragment, container, false);
        Button cikis = (Button) view.findViewById(R.id.nav_share);
        cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        mAuth.signOut();
                        Intent intent = new Intent(getActivity(), Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

            }
        });
        return root;


    }
}