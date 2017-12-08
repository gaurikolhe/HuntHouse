package com.project.hunthouse.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.hunthouse.R;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class OwnerSingleRoom extends AppCompatActivity implements View.OnClickListener{

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;

    EditText startDate,endDate,startPrice,description;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Button cancel,save;
    ImageButton camera,video;
    EditText location;
    CheckBox wifi,parking,university,grocery,hospital,restaurant,airport,gasStation,childCare;
    Intent intent;
    int flag=-1;

   // HouseData houseData = new HouseData();
    HashMap newHouse = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_single_room);

        myCalendar = Calendar.getInstance();

        startDate = (EditText) findViewById(R.id.startDateET);
        endDate   = (EditText) findViewById(R.id.endDateET);
        startPrice= (EditText) findViewById(R.id.rentStartET);
        description = (EditText) findViewById(R.id.descriptionET);
        cancel    = (Button) findViewById(R.id.cancelBtn);
        save    = (Button) findViewById(R.id.saveBtn);
        location = (EditText) findViewById(R.id.osrLocationSpinner);

        wifi = (CheckBox) findViewById(R.id.wifiChkBox);
        parking = (CheckBox) findViewById(R.id.parkingChkBox);
        university = (CheckBox) findViewById(R.id.universityChkBox);
        grocery = (CheckBox) findViewById(R.id.groceryChkBox);
        hospital = (CheckBox) findViewById(R.id.hospitalChkBox);
        restaurant = (CheckBox) findViewById(R.id.restaurantChkBox);
        airport = (CheckBox) findViewById(R.id.airportChkBox);
        gasStation = (CheckBox) findViewById(R.id.gasStationChkBox);
        childCare = (CheckBox) findViewById(R.id.childCareChkBox);

        camera = (ImageButton) findViewById(R.id.osrCameraImgBtn);
        video = (ImageButton) findViewById(R.id.osrVideoImgBtn);


        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        cancel.setOnClickListener(this);
        save.setOnClickListener(this);
        camera.setOnClickListener(this);
        video.setOnClickListener(this);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }

        };
    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if(flag==0)
            startDate.setText(sdf.format(myCalendar.getTime()));
        else if(flag==1)
            endDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id)
        {
            case R.id.startDateET:
                flag=0;
                new DatePickerDialog(OwnerSingleRoom.this,date,myCalendar.get(Calendar.YEAR)
                        ,myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.endDateET:
                flag=1;
                new DatePickerDialog(OwnerSingleRoom.this,date,myCalendar.get(Calendar.YEAR)
                        ,myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.cancelBtn:
                intent = new Intent(OwnerSingleRoom.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.osrCameraImgBtn:
                Toast.makeText(OwnerSingleRoom.this,"Write code for camera logic", Toast.LENGTH_LONG).show();

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                    File photoFile = null;
                    try
                    {
                        photoFile = createImageFile();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                    if(photoFile!=null)
                    {
                        Uri photoURI = FileProvider.getUriForFile(this,
                                "com.example.android.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }

                break;
            case R.id.osrVideoImgBtn:
                Toast.makeText(OwnerSingleRoom.this,"Write code for video logic", Toast.LENGTH_LONG).show();
                break;
            case R.id.saveBtn:

                newHouse.put("image",R.drawable.house);
                newHouse.put("location",location.getText().toString());
                newHouse.put("bedRooms","0");
                newHouse.put("price",startPrice.getText().toString());
                newHouse.put("startDate",startDate.getText().toString());
                newHouse.put("endDate",endDate.getText().toString());
                newHouse.put("description",description.getText().toString());

                newHouse.put("wifi",wifi.isSelected());
                newHouse.put("parking",parking.isSelected());
                newHouse.put("university",university.isSelected());
                newHouse.put("grocery",grocery.isSelected());
                newHouse.put("hospital",hospital.isSelected());
                newHouse.put("restaurant",restaurant.isSelected());
                newHouse.put("airport",airport.isSelected());
                newHouse.put("gasStation",gasStation.isSelected());
                newHouse.put("childCare",childCare.isSelected());

        //        houseData.addItem(newHouse);
                /*Boolean result = houseData.addItem(newHouse);

                if(result)
                {*/
                    Toast.makeText(OwnerSingleRoom.this,"Record added successfully!!!", Toast.LENGTH_LONG).show();
                    intent = new Intent(OwnerSingleRoom.this,MainActivity.class);
                    startActivity(intent);
                /*}
                else
                {
                    Toast.makeText(OwnerSingleRoom.this,"Error Occured while inserting",Toast.LENGTH_LONG).show();
                } */
                break;
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "HouseHunt_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);//getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
