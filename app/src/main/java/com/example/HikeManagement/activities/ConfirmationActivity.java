package com.example.HikeManagement.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.HikeManagement.R;
import com.example.HikeManagement.database.DatabaseHelper;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Intent intent = getIntent();
        String hike_Name = intent.getStringExtra("Hike_Name");
        String hike_Location = intent.getStringExtra("Hike_Location");
        String hike_Date = intent.getStringExtra("Hike_Date");
        String hike_Parking = intent.getStringExtra("Hike_Parking");
        String hike_Length = intent.getStringExtra("Hike_Length");
        String hike_Weather = intent.getStringExtra("Hike_Weather");
        String hike_Time = intent.getStringExtra("Hike_Time");
        String hike_Difficulty = intent.getStringExtra("Hike_Difficulty");
        String hike_Description = intent.getStringExtra("Hike_Description");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please review your information!");
        builder.setMessage("Name: " + hike_Name +
                "\nLocation: " + hike_Location +
                "\nDate: " + hike_Date +
                "\nParking Available: " + hike_Parking +
                "\nLength: " + hike_Length +
                "\nWeather: " + hike_Weather +
                "\nTime Estimated: " + hike_Time +
                "\nDifficulty Level: " + hike_Difficulty +
                "\nDescription: " + hike_Description);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper dbHelper = new DatabaseHelper(ConfirmationActivity.this);
                dbHelper.addNewHike(
                        hike_Name,
                        hike_Location,
                        hike_Date,
                        hike_Parking,
                        hike_Length,
                        hike_Weather,
                        hike_Time,
                        hike_Difficulty,
                        hike_Description
                );

                Intent intent = new Intent(ConfirmationActivity.this, MainActivity.class);
                intent.putExtra("fragmentToLoad", "home_fragment");
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        builder.show();
    }

    private void clearInputFields() {
        EditText nameText = findViewById(R.id.hike_name_text);
        EditText locationText = findViewById(R.id.hike_location_text);
        EditText dateText = findViewById(R.id.hike_date_text);

        RadioGroup radioGroupParking = findViewById(R.id.radioGroupParking);
        radioGroupParking.clearCheck();

        EditText lengthText = findViewById(R.id.hike_length_text);
        EditText descriptionText = findViewById(R.id.hike_description_text);

        nameText.setText("");
        locationText.setText("");
        dateText.setText("");
        lengthText.setText("");
        descriptionText.setText("");
    }

}
