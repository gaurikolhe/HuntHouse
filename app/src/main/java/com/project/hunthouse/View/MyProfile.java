package com.project.hunthouse.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.project.hunthouse.R;

//import com.venyou.venyou.R;

public class MyProfile extends AppCompatActivity {

    private Uri file;
    int REQUEST_CODE;
    private TextView name, email;
    private ImageView image;
    private Uri imageUri;
    private String uid,uname,uemail,url;
    Button takeImage;
    Map<String,Object> pic = new HashMap<>();
    DatabaseReference mRef;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    String currPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = (TextView) findViewById(R.id.profile_name);
        email = (TextView) findViewById(R.id.profile_email);
        image = (ImageView) findViewById(R.id.profile_image);
        takeImage = (Button) findViewById(R.id.take_image);

        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        uid = (String) getIntent().getExtras().get("id");
        url = (String) getIntent().getExtras().get("url");
        uname = (String) getIntent().getExtras().get("name");
        uemail = (String) getIntent().getExtras().get("email");
        name.setText("Name: "+uname);
        email.setText("Email: "+uemail);
        mRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid).getRef();
        Picasso.with(getApplicationContext())
                .load(url)
                .into(image);
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

    }


    public void chooseImage(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);


//            String currPath = null;
//            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//            String imageFileName = "JPEG_" + timeStamp + "_";
//            try{
//                File storageDir = Environment.getExternalStorageDirectory();
//                File image = File.createTempFile(
//                        imageFileName,  /* prefix */
//                        ".jpg",         /* suffix */
//                        storageDir      /* directory */
//                );
//                currPath = image.getAbsolutePath();
//            }
//            catch (IOException e){
//
//            }
            /*
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

            String imageEncoded = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);

            String path = Environment.getExternalStorageDirectory().toString()+
                    System.currentTimeMillis() + ".jpg";
            File destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");

            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageUri = Uri.fromFile(new File(destination.getAbsolutePath())); */
        }

        if (data != null){
            imageUri = data.getData();
            image.setImageURI(null);
            image.setImageURI(imageUri);
        }
    }

    public void updateProfile(View view){

            FirebaseStorage storage = FirebaseStorage.getInstance("gs://househunt-e992c.appspot.com");

            // Create a storage reference from our app
            StorageReference storageRef = storage.getReference();
            if(imageUri != null){
                Log.d("MyProfile:","imageUri is not null");
                Uri file = imageUri;
                StorageReference riversRef = storageRef.child("users/"+file.getLastPathSegment());
                UploadTask uploadTask = riversRef.putFile(file);

                // Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(getApplicationContext(),"failed : "+exception, Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        pic.put("pic", downloadUrl.toString());
                        mRef.updateChildren(pic);

                    }
                });
            }

            Intent intent = new Intent(MyProfile.this,MainActivity.class);
            Bundle userData = new Bundle();
            userData.putString("name", uname);
            userData.putString("email", uemail);
            userData.putString("id", uid);
            intent.putExtras(userData);
            startActivity(intent);

    }
}
