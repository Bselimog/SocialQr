package com.example.bigcode.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bigcode.Etkinlikler;
import com.example.bigcode.Kullanicilar;
import com.example.bigcode.ui.MyCustomDialog;
import com.example.bigcode.R;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment implements MyCustomDialog.OnInputSelected {
    TextView txt;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;
    TextView etkinlik,month2,year2,day,gun;
    private static final String TAG = "HomeFragment";
    @Override
    public void sendInput(String input) {
    Log.d(TAG,"sendInput: found incoming input: " + input);
    mInputDisplay.setText(input);
    }
    private HomeViewModel homeViewModel;
    CalendarView calendar;
    private Button mOpenDialog;
    public TextView mInputDisplay;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
       // txt = (TextView)view.findViewById(R.id.textView3);
        day = (TextView)view.findViewById(R.id.gun);
        gun = (TextView)view.findViewById(R.id.day);
        month2 = (TextView)view.findViewById(R.id.ay);
        year2 = (TextView)view.findViewById(R.id.yil);
        Button event_button = (Button)view.findViewById(R.id.kaydet);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        etkinlik = (TextView) view.findViewById(R.id.information);
        reference = database.getReference().child("Kullanicilar").child(user.getUid()).child("Etkinlikler");

        event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event=mInputDisplay.getText().toString();
               // String event_date=txt.getText().toString();
                String event_gun=day.getText().toString();
                String event_ay=month2.getText().toString();
                String event_yil=year2.getText().toString();
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Kullanicilar").child(user.getUid()).child("Etkinlikler");
                Map map = new HashMap();
                map.put("Etkinlik",event);
                map.put("Gun",event_gun);
                map.put("Ay",event_ay);
                map.put("Yil",event_yil);
                ref.setValue(map);
            }
        });
        mOpenDialog = view.findViewById(R.id.icon_plus);
        mInputDisplay = view.findViewById(R.id.textView2);
      mOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Log.d(TAG,"onClick:opening dialog");
                MyCustomDialog dialog = new MyCustomDialog();
                dialog.setTargetFragment(HomeFragment.this,1);
                dialog.show(getFragmentManager(),"MyCustomDialog");

            }
        });

     calendar = (CalendarView)view.findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){

            @Override
            public void onSelectedDayChange(CalendarView view,
                                            int year, int month, int dayOfMonth) {month += 1;
             // String date = dayOfMonth + " " + month + " " + year;
                String day1 = dayOfMonth + "";
                String month1 = month + "";
                String year1 = year + "";
                //txt.setText(date);
                day.setText(day1);
                month2.setText(month1);
                year2.setText(year1);
                if (dayOfMonth == 15 && month == 6 && year == 2020) {
                    bilgiGetir();
                }
               /* Toast.makeText(getActivity(),
                        dayOfMonth +"/"+month+"/"+ year,Toast.LENGTH_LONG).show();*/}
        });

        return view;
    }
    public void bilgiGetir()
    {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Etkinlikler e1 = dataSnapshot.getValue(Etkinlikler.class);
               // etkinlik.setText(e1.getEtkinlik());
                Toast.makeText(getActivity(),e1.getEtkinlik(),Toast.LENGTH_LONG).show();
               /*gun.setText(e1.getGun());
                gun.setText("");
                month2.setText(e1.getAy());
                year2.setText(e1.getYil());*/
               // Log.i("etkinlikler",e1.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
