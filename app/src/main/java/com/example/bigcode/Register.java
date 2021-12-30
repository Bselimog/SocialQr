package com.example.bigcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register extends AppCompatActivity {
    EditText gmail, pass,name,surname,birthday,telefon,resim;
    Button kayit;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        kayit =(Button) findViewById(R.id.register_kaydet);

        gmail = (EditText) findViewById (R.id.mail_editText);
        pass = (EditText) findViewById (R.id.sifre_register_editText);
        name = (EditText) findViewById (R.id.isim_editText);
        surname = (EditText) findViewById (R.id.soyad_editText);
        birthday = (EditText) findViewById (R.id.dog_tar_editText);
        telefon = (EditText) findViewById (R.id.register_telefon);

         //final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

       kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kadi=gmail.getText().toString();
                String password=pass.getText().toString();
                String isim = name.getText().toString();
                String soyisim = surname.getText().toString();
                String dog_tar = birthday.getText().toString();
                String tel = telefon.getText().toString();
                String res = "null";
                if(!kadi.equals("")&& !password.equals("")){

                   // kayitol (kadi,password);

                  // ref.child(auth.getCurrentUser().getUid()).child("child1").setValue("deneme");

                    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Kullanicilar").child(user.getUid()).child("kisiselBilgiler");
                    Map map = new HashMap();
                    map.put("mail",kadi);
                    map.put("sifre",password);
                    map.put("isim",isim);
                    map.put("soyisim",soyisim);
                    map.put("dogumtarihi",dog_tar);
                    map.put("telefon",tel);
                    map.put("resim",res);
                    ref.setValue(map);
                    //ref.child("child1").setValue("user");
                    Toast.makeText(getApplicationContext(),"Başarılı",Toast.LENGTH_LONG).show();
                    gmail.setText("");
                    pass.setText("");
                   // CleanEditText();
                    Intent kayitOl = new Intent(getApplicationContext(),Login.class);
                    //kayitOl.putExtra("aktifUserUid", auth.getCurrentUser().getUid());
                    startActivity(kayitOl);
                }

            }
        });
        //final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


/*
        kayit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here

                // Write a message to the database


              //  myRef.child("birinci").setValue("Hello, World!");

                ref.child("child1").setValue("deneme");
                ref.push().setValue("test1");
                Toast.makeText(getApplicationContext(),"Başarılı",Toast.LENGTH_LONG).show();
            }
        });

 */

    }

/*private void kayitol(String email, String pass_ )
    {

       // Log.i("test",""+auth);
       auth.createUserWithEmailAndPassword(email, pass_).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Başarılı",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Hata var",Toast.LENGTH_LONG).show();
                }
            }
        });

    }*/
    }


