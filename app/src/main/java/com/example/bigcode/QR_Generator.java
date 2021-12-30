package com.example.bigcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;

import java.util.HashMap;
import java.util.Map;

public class QR_Generator extends AppCompatActivity {
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://bigcode-8d2ee.appspot.com");
    String TAG = "GenerateQrCode";
    int PICK_IMAGE_REQUEST = 111;
    EditText edttxt,eAd,eDate;
    Button uploadImg2,chooseQr;
    Uri filePath;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ImageView qrimg;
    String inputvalue,InputFirebase,EtknlkAd,EtknlkDate;
    Button start,saveBtn;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__generator);
        qrimg = (ImageView)findViewById(R.id.qrcode);
        edttxt = (EditText) findViewById(R.id.edittext);
        eAd = (EditText) findViewById(R.id.adEdtxt);
        eDate = (EditText) findViewById(R.id.etkinlik_dateedttxt);
        start = (Button) findViewById(R.id.createbtn);
        saveBtn = (Button) findViewById(R.id.save);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uploadImg2 = (Button)findViewById(R.id.uploadImg2);
        chooseQr = (Button)findViewById(R.id.chooseQR);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRCodeWriter writer = new QRCodeWriter();
                inputvalue = edttxt.getText().toString().trim();
                try {
                    BitMatrix bitMatrix = writer.encode(inputvalue, BarcodeFormat.QR_CODE, 512, 512);
                    int width = bitMatrix.getWidth();
                    int height = bitMatrix.getHeight();
                    Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                    for (int x = 0; x < width; x++) {
                        for (int y = 0; y < height; y++) {
                            bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                        }
                    }
                    ((ImageView) findViewById(R.id.qrcode)).setImageBitmap(bmp);
                    qrimg.setDrawingCacheEnabled(true);

                    Bitmap b = qrimg.getDrawingCache();
                    String title = "QR";
                    String description = "QR Deneme";
                    MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), b,title, description);

                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });

        chooseQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFirebase = edttxt.getText().toString();
                EtknlkAd = eAd.getText().toString();
                EtknlkDate = eDate.getText().toString();
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Kullanicilar").child(user.getUid()).child("Biletler");
                Map map = new HashMap();
                map.put("ReferansNumarasi",InputFirebase);
                map.put("EtkinlikAdi",EtknlkAd);
                map.put("EtkinlikTarihi",EtknlkDate);
                ref.setValue(map);

            }
        });
        uploadImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(filePath != null) {

                    StorageReference childRef = storageRef.child("QRCode").child("qr.jpg");

                    //uploading the image
                    UploadTask uploadTask = childRef.putFile(filePath);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "Select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);

                //Setting image to ImageView
                qrimg.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
