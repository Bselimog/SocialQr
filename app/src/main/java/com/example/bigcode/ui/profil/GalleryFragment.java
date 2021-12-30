package com.example.bigcode.ui.profil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.bigcode.Kullanicilar;
import com.example.bigcode.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class GalleryFragment extends Fragment {


    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://bigcode-8d2ee.appspot.com");
    ImageView imgView;
    private GalleryViewModel galleryViewModel;
    Button chooseImg, uploadImg;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    EditText adSoyad,dog_tar1,email1,telefon1,sifre1;
    CircleImageView profile_image;
    Button guncelle;
    String imageUri;
    View view;
    int PICK_IMAGE_REQUEST = 111;
    Uri filePath;
    ProgressDialog pd;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        view = inflater.inflate(R.layout.profil_fragment, container, false);
        chooseImg = (Button)view.findViewById(R.id.chooseImg);
        uploadImg = (Button)view.findViewById(R.id.uploadImg);
        imgView = (ImageView)view.findViewById(R.id.imgView);
        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading....");
        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });
        uploadImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(filePath != null) {
                    pd.show();

                    StorageReference childRef = storageRef.child("profile_image.jpg");

                    //uploading the image
                    UploadTask uploadTask = childRef.putFile(filePath);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Toast.makeText(getActivity(), "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(getActivity(), "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(), "Select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tanimla();
        bilgileriGetir();
        return view;
    }

    public void tanimla(){
        adSoyad = (EditText)view.findViewById(R.id.fullad);
        dog_tar1 = (EditText)view.findViewById(R.id.tarih);
        email1 = (EditText)view.findViewById(R.id.eposta);
        telefon1 = (EditText)view.findViewById(R.id.tel);
        sifre1 = (EditText)view.findViewById(R.id.sifre1);
        guncelle = (Button)view.findViewById(R.id.guncelle);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Kullanicilar").child(user.getUid()).child("kisiselBilgiler");
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* ActivityCompat.requestPermissions(GalleryFragment.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE},1);*/
                galeriAc();
            }
        });


    }
    public void bilgileriGetir(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Kullanicilar kl = dataSnapshot.getValue(Kullanicilar.class);
                adSoyad.setText(kl.getIsim()+" "+kl.getSoyisim());
                dog_tar1.setText(kl.getDogumtarihi());
                email1.setText(kl.getMail());
                telefon1.setText(kl.getTelefon());
                sifre1.setText(kl.getSifre());
                imageUri = kl.getResim();
                if (!kl.getResim().equals("null")){
                    Picasso.get().load(kl.getResim()).into(profile_image);
                }

               /* String adi = dataSnapshot.child("isim").getValue().toString();
                String soyadi = dataSnapshot.child("soyisim").getValue().toString();
                String mail = dataSnapshot.child("mail").getValue().toString();
                String dog_tar = dataSnapshot.child("dogumtarihi").getValue().toString();*/

              //Log.i("bilgiler",kl.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void galeriAc() {
        /*Intent intentGaleri = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intentGaleri,5);*/
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 5);
    }
  /*  public void onActitivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null  )
        { filePath = data.getData();
          StorageReference ref = storageReference.child("kullaniciResimleri").child(String.valueOf(imageUri));
          ref.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
              @Override
              public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(getContext(),"Resim Güncellendi...",Toast.LENGTH_LONG).show();
                    reference = database.getReference().child("Kullanicilar").child(auth.getUid());
                    Map map = new HashMap();
                    map.put("resim",task.getResult().getStorage().getDownloadUrl().toString());
                    reference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                       /* if (task.isSuccessful())
                        {
                            ChangeFragment fragment = new ChangeFragment(getContext());
                        }
                        }
                    });

                }else
                {
                    Toast.makeText(getContext(),"Resim Güncellenirken Hata Oluştu...",Toast.LENGTH_LONG).show();
                }
              }
          });
          //Log.i("resim",""+filePath);
        }
    }*/


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode ==Activity.RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                //Setting image to ImageView
                profile_image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}