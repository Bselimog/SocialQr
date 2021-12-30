package com.example.bigcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class Login extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    Button b1,b2;
    FirebaseDatabase mDatabase;
    DatabaseReference reference;
    EditText gmail, pass;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        final EditText mail = (EditText) findViewById(R.id.mail);
        final EditText sifre= (EditText) findViewById(R.id.sifre_edit);
        //auth =FirebaseAuth.getInstance();

        b1=(Button) findViewById(R.id.giris);
        gmail = (EditText) findViewById (R.id.mail);
        pass = (EditText) findViewById (R.id.sifre_edit);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   String gmail = mail.getText().toString();
                   mail.setText("");
                   String pass = sifre.getText().toString();
                   sifre.setText("");
                   if (!gmail.equals("")&& !pass.equals("") )
                   {
                       loginUser(gmail,pass);
                   }
            }
        });


        b2=(Button) findViewById(R.id.register);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kadi=gmail.getText().toString();
                String password=pass.getText().toString();
                if(!kadi.equals("")&& !password.equals("")) {
                    kayitol(kadi, password);
                    Intent kayit = new Intent(getApplicationContext(), Register.class);
//                kayit.putExtra("KullaniciId", auth.getCurrentUser().getUid());
                    startActivity(kayit);
                }
            }
        });
    }

    private void loginUser (String gmail,String password){
      auth.signInWithEmailAndPassword(gmail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful())
              {
                  progressDialog = new ProgressDialog(Login.this);
                  progressDialog.setMessage("Yükleniyor...");
                  progressDialog.setCancelable(false);
                  progressDialog.show();
                  Intent giris = new Intent(getApplicationContext(),MainActivity.class);
                  // giris.putExtra("KullaniciId", auth.getCurrentUser().getUid());
                  startActivity(giris);
              }
              else
              {
                  Toast.makeText(getApplicationContext(),"Kullanıcı kaydı bulunamadı!",Toast.LENGTH_LONG).show();
              }
          }
      });
    }
    private void kayitol(String email, String pass_ )
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

    }

}
