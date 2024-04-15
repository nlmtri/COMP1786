package com.example.HikeManagement.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.HikeManagement.database.DatabaseHelper;
import com.example.HikeManagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Locale;

public class UpdateActivity extends AppCompatActivity {

    EditText nameInputUpdate, locationInputUpdate, lengthInputUpdate, descriptionInputUpdate;
    RadioGroup parkingRadioGroup;
    Spinner hikeWeatherForecastSpinner, hikeDifficultyLevelSpinner;
    RadioButton radioButtonYes, radioButtonNo;
    Button updateButton, deleteButton, estimatedTimeUpdate, dateUpdate;
    FloatingActionButton backButton;
    String id, name, location, date, parkingAvailable, length, weatherForecast, estimatedTime, difficultyLevel, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        TextView nameHikeLabel = findViewById(R.id.nameHikeUpdate);
        TextView locationHikeLabel = findViewById(R.id.nameLocationUpdate);
        TextView dateHikeLabel = findViewById(R.id.dateHikeUpdate);
        TextView parkingAvailableHikeLabel = findViewById(R.id.parkingAvailableHikeUpdate);
        TextView lengthHikeLabel = findViewById(R.id.lengthHikeUpdate);
        TextView difficultyLevelHikeLabel = findViewById(R.id.levelHikeUpdate);
        TextView weatherForecastLabel = findViewById(R.id.weatherForecastHikeUpdate);
        TextView estimatedTimeLabel = findViewById(R.id.estimatedTimeHikeUpdate);

        String redAsterisk = " *";

        SpannableString nameHikeSpan = new SpannableString(nameHikeLabel.getText() + redAsterisk);
        SpannableString locationHikeSpan = new SpannableString(locationHikeLabel.getText() + redAsterisk);
        SpannableString dateHikeSpan = new SpannableString(dateHikeLabel.getText() + redAsterisk);
        SpannableString parkingAvailableHikeSpan = new SpannableString(parkingAvailableHikeLabel.getText() + redAsterisk);
        SpannableString lengthHikeSpan = new SpannableString(lengthHikeLabel.getText() + redAsterisk);
        SpannableString difficultyLevelHikeSpan = new SpannableString(difficultyLevelHikeLabel.getText() + redAsterisk);
        SpannableString weatherForecastSpan = new SpannableString(weatherForecastLabel.getText() + redAsterisk);
        SpannableString estimatedTimeSpan = new SpannableString(estimatedTimeLabel.getText() + redAsterisk);

        int redColor = Color.RED;
        nameHikeSpan.setSpan(new ForegroundColorSpan(redColor), nameHikeSpan.length() - 1, nameHikeSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        locationHikeSpan.setSpan(new ForegroundColorSpan(redColor), locationHikeSpan.length() - 1, locationHikeSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        dateHikeSpan.setSpan(new ForegroundColorSpan(redColor), dateHikeSpan.length() - 1, dateHikeSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        parkingAvailableHikeSpan.setSpan(new ForegroundColorSpan(redColor), parkingAvailableHikeSpan.length() - 1, parkingAvailableHikeSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lengthHikeSpan.setSpan(new ForegroundColorSpan(redColor), lengthHikeSpan.length() - 1, lengthHikeSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        difficultyLevelHikeSpan.setSpan(new ForegroundColorSpan(redColor), difficultyLevelHikeSpan.length() - 1, difficultyLevelHikeSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        weatherForecastSpan.setSpan(new ForegroundColorSpan(redColor), weatherForecastSpan.length() - 1, weatherForecastSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        estimatedTimeSpan.setSpan(new ForegroundColorSpan(redColor), estimatedTimeSpan.length() - 1, estimatedTimeSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        nameHikeLabel.setText(nameHikeSpan);
        locationHikeLabel.setText(locationHikeSpan);
        dateHikeLabel.setText(dateHikeSpan);
        parkingAvailableHikeLabel.setText(parkingAvailableHikeSpan);
        lengthHikeLabel.setText(lengthHikeSpan);
        difficultyLevelHikeLabel.setText(difficultyLevelHikeSpan);
        weatherForecastLabel.setText(weatherForecastSpan);
        estimatedTimeLabel.setText(estimatedTimeSpan);

        nameInputUpdate = findViewById(R.id.hike_name_text_update);
        locationInputUpdate = findViewById(R.id.hike_location_text_update);
        lengthInputUpdate = findViewById(R.id.hike_length_text_update);
        descriptionInputUpdate = findViewById(R.id.hike_description_text_update);

        estimatedTimeUpdate = findViewById(R.id.hike_estimated_time_update_button);
        dateUpdate = findViewById(R.id.hike_date_update_button);

        hikeWeatherForecastSpinner = findViewById(R.id.hike_weather_forecast_spinner);
        hikeDifficultyLevelSpinner = findViewById(R.id.hike_difficulty_level_spinner);

        parkingRadioGroup = findViewById(R.id.parking_available_radio_group);
        radioButtonYes = findViewById(R.id.radio_yes);
        radioButtonNo = findViewById(R.id.radio_no);

        deleteButton = findViewById(R.id.deleteButton);
        updateButton = findViewById(R.id.updateButton);
        backButton = findViewById(R.id.return_button);

        getHikerInformation();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(UpdateActivity.this);

                name = nameInputUpdate.getText().toString().trim();
                location = locationInputUpdate.getText().toString().trim();
                date = dateUpdate.getText().toString().trim();
                length = lengthInputUpdate.getText().toString().trim();
                estimatedTime = estimatedTimeUpdate.getText().toString().trim();
                description = descriptionInputUpdate.getText().toString().trim();

                int selectedId = parkingRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);
                parkingAvailable = selectedRadioButton.getText().toString();

                weatherForecast = hikeWeatherForecastSpinner.getSelectedItem().toString();
                difficultyLevel = hikeDifficultyLevelSpinner.getSelectedItem().toString();

                if (validateData()) {
                    databaseHelper = new DatabaseHelper(UpdateActivity.this);
                    databaseHelper.updateHikeInformation(
                            id,
                            name,
                            location,
                            date,
                            parkingAvailable,
                            length,
                            weatherForecast,
                            estimatedTime,
                            difficultyLevel,
                            description
                    );

                    Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                    intent.putExtra("fragmentToLoad", "home_fragment");
                    startActivity(intent);
                    finish();
                }
            }
        });

        dateUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        dateUpdate.setText(selectedDate);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });
        estimatedTimeUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(UpdateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        estimatedTimeUpdate.setText(selectedTime);
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(UpdateActivity.this));

                timePickerDialog.show();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToHomeFragment();
            }
        });
    }

    private boolean validateData() {
        if (name.isEmpty() || location.isEmpty() || date.isEmpty() || length.isEmpty() || estimatedTime.isEmpty()) {
            Toast.makeText(UpdateActivity.this, "Please enter all required (*) fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isNameValid(name)) {
            Toast.makeText(UpdateActivity.this, "Invalid name, Please enter again!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isLocationValid(location)) {
            Toast.makeText(UpdateActivity.this, "Invalid location, Please enter again!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isLengthValid(length)) {
            Toast.makeText(UpdateActivity.this, "Invalid length, Please enter again! (m or km)", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isNameValid(String name) {
        return name.matches("[a-zA-Z ]+");
    }

    private boolean isLocationValid(String location) {
        return location.matches("[a-zA-Z ]+");
    }

    private boolean isLengthValid(String length) {
        return length.matches("\\d+\\s?(m|km)");
    }

    public void getHikerInformation(){
        if (
                getIntent().hasExtra("id") &&
                        getIntent().hasExtra("name") &&
                        getIntent().hasExtra("location") &&
                        getIntent().hasExtra("date") &&
                        getIntent().hasExtra("parkingAvailable") &&
                        getIntent().hasExtra("length") &&
                        getIntent().hasExtra("weatherForecast") &&
                        getIntent().hasExtra("estimatedTime") &&
                        getIntent().hasExtra("difficultyLevel") &&
                        getIntent().hasExtra("description")
        ){
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            location = getIntent().getStringExtra("location");
            date = getIntent().getStringExtra("date");
            parkingAvailable = getIntent().getStringExtra("parkingAvailable");
            length = getIntent().getStringExtra("length");
            String weatherForecast = getIntent().getStringExtra("weatherForecast");
            estimatedTime = getIntent().getStringExtra("estimatedTime");
            String difficultyLevel = getIntent().getStringExtra("difficultyLevel");
            description = getIntent().getStringExtra("description");

            setupSpinner(hikeWeatherForecastSpinner, R.array.weather_forecast_array, weatherForecast);
            setupSpinner(hikeDifficultyLevelSpinner, R.array.difficulty_level_array, difficultyLevel);

            nameInputUpdate.setText(name);
            locationInputUpdate.setText(location);
            dateUpdate.setText(date);
            lengthInputUpdate.setText(length);
            estimatedTimeUpdate.setText(estimatedTime);
            descriptionInputUpdate.setText(description);

            if (parkingAvailable.equals("Yes")) {
                radioButtonYes.setChecked(true);
            } else if (parkingAvailable.equals("No")) {
                radioButtonNo.setChecked(true);
            }
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    public void setupSpinner(Spinner spinner, int arrayResource, String selectedValue) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayResource, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        int position = adapter.getPosition(selectedValue);
        spinner.setSelection(position);
    }
    public void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper databaseHelper = new DatabaseHelper(UpdateActivity.this);
                databaseHelper.deleteOneHikeInformation(id);
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                intent.putExtra("fragmentToLoad", "home_fragment");
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    public void returnToHomeFragment(){
        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
        intent.putExtra("fragmentToLoad", "home_fragment");
        startActivity(intent);
        finish();
    }
}
