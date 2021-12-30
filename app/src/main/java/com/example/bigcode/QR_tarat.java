package com.example.bigcode;



import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class QR_tarat extends AppCompatActivity {
    private Button button;
    private TextView text_qr_code_sonuc,txt_sonuc,txt_code_kind,txt_qr_code_kind_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_tarat);
        final Activity activity = this;
        button = (Button) findViewById(R.id.button);
        txt_sonuc = (TextView) findViewById(R.id.txt_sonuc);
        text_qr_code_sonuc = (TextView) findViewById(R.id.qr_code_sonucu);
        txt_code_kind = (TextView) findViewById(R.id.txt_code_kind);
        txt_qr_code_kind_result = (TextView) findViewById(R.id.txt_qr_code_kind_result);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bu activity içinde çalıştırıyoruz.
                IntentIntegrator integrator = new IntentIntegrator(activity);
                //Kütüphanede bir kaç kod tipi var biz hepsini tarayacak şekilde çalıştırdık.
                //integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                //şeklindede sadece qr code taratabiliriz.
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                //Kamera açıldığında aşağıda yazı gösterecek
                integrator.setPrompt("Scan");
                //telefonun kendi kamerasını kullandırıcaz
                integrator.setCameraId(0);
                //okuduğunda 'beep' sesi çıkarır
                integrator.setBeepEnabled(true);
                //okunan barkodun image dosyasını kaydediyor
                integrator.setBarcodeImageEnabled(false);
                //scan başlatılıyor
                integrator.initiateScan();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Kütüphane okuduktan sonra bu metodla bize result döndürüyor.
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                text_qr_code_sonuc.setText("Kod Sonucu:");
                txt_sonuc.setText("Qr Code Bulunamadı.");
                txt_code_kind.setText("Kod Türü:");
                txt_qr_code_kind_result.setText("Bulunamadı.");
            } else {
                Log.d("MainActivity", "Scanned");
                text_qr_code_sonuc.setText("Kod Sonucu:");
                txt_sonuc.setText(result.getContents());
                txt_code_kind.setText("Kod Türü:");
                txt_qr_code_kind_result.setText(result.getFormatName());
            }
        }
    }

}

