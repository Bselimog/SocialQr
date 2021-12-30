package com.example.bigcode.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bigcode.R;
import android.app.Fragment;
import android.app.FragmentContainer;
import android.app.FragmentManager;
import android.widget.Toast;

import com.example.bigcode.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MyCustomDialog extends DialogFragment {
    private static final String TAG = "MyCustomDialog";

    CalendarView calendar;
    private EditText mInput,RefID;
    FirebaseAuth auth;
    FirebaseUser user;
    private TextView mActionOk, mActionCancel;
    public interface OnInputSelected{
        void sendInput(String input);
    }
    public OnInputSelected mOnInputSelected;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dialog_my_custom, container, false);
        mActionOk = view.findViewById(R.id.action_ok);
        mActionCancel = view.findViewById(R.id.action_cancel);
        mInput = view.findViewById(R.id.input);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: closing dialog");
                getDialog().dismiss();
            }
        });
        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: capturing dialog");
                String input = mInput.getText().toString();
                if (!input.equals("")){
                  /*  HomeFragment fragment = (HomeFragment) getActivity().getSupportFragmentManager().findFragmentByTag("HomeFragment");
                    fragment.mInputDisplay.setText(input);*/
                    mOnInputSelected.sendInput(input);
                }

                getDialog().dismiss();
            }
        });

        return view;

    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try {
            mOnInputSelected = (OnInputSelected) getTargetFragment();
        }catch (ClassCastException e){
            Log.e(TAG,"onAttach: ClassCastException : "+ e.getMessage());
        }
    }

}
