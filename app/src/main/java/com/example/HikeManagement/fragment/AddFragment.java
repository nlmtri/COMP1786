package com.example.HikeManagement.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import com.example.HikeManagement.R;
import com.example.HikeManagement.activities.ConfirmationActivity;
import com.example.HikeManagement.databinding.FragmentAddBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddFragment extends Fragment {
    private FragmentAddBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        TextView[] labels = {
                view.findViewById(R.id.nameHike),
                view.findViewById(R.id.nameLocation),
                view.findViewById(R.id.dateHike),
                view.findViewById(R.id.parkingAvailableHike),
                view.findViewById(R.id.lengthHike),
                view.findViewById(R.id.levelHike),
                view.findViewById(R.id.weatherHike),
                view.findViewById(R.id.estimatedTimeHike)
        };

        String redAsterisk = " *";
        int redColor = Color.RED;

        for (TextView label : labels) {
            SpannableString span = new SpannableString(label.getText() + redAsterisk);
            span.setSpan(new ForegroundColorSpan(redColor), span.length() - 1, span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            label.setText(span);
        }

        EditText hike_NameEditText = view.findViewById(R.id.hike_name_text);
        EditText hike_LocationEditText = view.findViewById(R.id.hike_location_text);
        Button dateButton = view.findViewById(R.id.hike_date_button);
        RadioGroup radioGroupParking = view.findViewById(R.id.radioGroupParking);
        EditText hike_LengthEditText = view.findViewById(R.id.hike_length_text);
        Spinner spinnerDifficulty = view.findViewById(R.id.spinnerDifficulty);
        Spinner spinnerWeatherForecast = view.findViewById(R.id.hike_weather_spinner);
        Button timeButton = view.findViewById(R.id.hike_estimated_time_button);
        EditText hike_DescriptionEditText = view.findViewById(R.id.hike_description_text);

        List<String> weatherForecast = new ArrayList<>();

        // Set up the Difficulty Level Spinner
        List<String> difficultyLevels = new ArrayList<>();

        // Create a List of difficulty level
        difficultyLevels.add("Easy");
        difficultyLevels.add("Normal");
        difficultyLevels.add("Difficult");

        // Create a adapter to connect to the Spinner
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, difficultyLevels);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        spinnerDifficulty.setAdapter(difficultyAdapter);

        // Create a List of weather
        weatherForecast.add("Sunny");
        weatherForecast.add("Cloudy");
        weatherForecast.add("Rainy");
        weatherForecast.add("Snowy");

        // Create a adapter to connect to the Spinner
        ArrayAdapter<String> weatherAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, weatherForecast);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        spinnerWeatherForecast.setAdapter(weatherAdapter);

        // Setup Difficulty Level Spinner and Weather Spinner

        dateButton.setOnClickListener(view1 -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (datePicker, year1, month1, dayOfMonth) -> {
                String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                dateButton.setText(selectedDate);
            }, year, month, day);

            datePickerDialog.show();
        });

        timeButton.setOnClickListener(view12 -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), (timePicker, hourOfDay, minute1) -> {
                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute1);
                timeButton.setText(selectedTime);
            }, hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));

            timePickerDialog.show();
        });

        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(view13 -> {
            String hike_Name = hike_NameEditText.getText().toString();
            String hike_Location = hike_LocationEditText.getText().toString();
            String hike_Description = hike_DescriptionEditText.getText().toString();

            String hikeParking = "";
            int checkedId = radioGroupParking.getCheckedRadioButtonId();
            if (checkedId == R.id.radioButtonYes) {
                hikeParking = "Yes";
            } else if (checkedId == R.id.radioButtonNo) {
                hikeParking = "No";
            }

            String hike_Length = hike_LengthEditText.getText().toString();

            final String[] hike_Weather = {spinnerWeatherForecast.getSelectedItem().toString()};

            String hike_Date = dateButton.getText().toString();
            String hike_Time = timeButton.getText().toString();

            final String[] hike_Difficulty = {spinnerDifficulty.getSelectedItem().toString()};

            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Confirm Information!");
            builder.setMessage("Are you sure you want to add this information?");
            String finalHike_Parking = hikeParking;
            builder.setPositiveButton("Yes", (dialog, which) -> {
                boolean isNameValid = CheckNameValid(hike_Name);
                boolean isLocationValid = CheckLocationValid(hike_Location);
                boolean isLengthValid = CheckLengthValid(hike_Length);
                boolean isParkingValid = finalHike_Parking.equals("Yes") || finalHike_Parking.equals("No");

                if (
                        hike_Name.isEmpty() ||
                                hike_Location.isEmpty() ||
                                hike_Date.isEmpty() ||
                                hike_Length.isEmpty() ||
                                hike_Time.isEmpty() ||
                                !isParkingValid
                ){
                    showValidationError("All fields marked with * are required!");
                }
                else if (!isNameValid) {
                    showValidationError("Invalid name. Please enter again!");
                } else if (!isLocationValid) {
                    showValidationError("Invalid location. Please enter again!");
                } else if (!isLengthValid) {
                    showValidationError("Invalid length. Please enter again!");
                } else {
                    Intent intent = new Intent(getActivity(), ConfirmationActivity.class);
                    intent.putExtra("Hike_Name", hike_Name);
                    intent.putExtra("Hike_Location", hike_Location);
                    intent.putExtra("Hike_Date", hike_Date);
                    intent.putExtra("Hike_Parking", finalHike_Parking);
                    intent.putExtra("Hike_Length", hike_Length);
                    intent.putExtra("Hike_Weather", hike_Weather[0]);
                    intent.putExtra("Hike_Time", hike_Time);
                    intent.putExtra("Hike_Difficulty", hike_Difficulty[0]);
                    intent.putExtra("Hike_Description", hike_Description);

                    startActivity(intent);
                }
            });
            builder.setNegativeButton("No", (dialog, which) -> {});
            builder.show();
        });

        return view;
    }

    private void showValidationError(String errorMessage){
        new AlertDialog.Builder(getActivity()).setTitle("Missing Information").setMessage(errorMessage).setPositiveButton("OK", null).show();
    }

    private boolean CheckNameValid(String name){
        return name.matches("[a-zA-Z ]+");
    }

    // Validation method for location of the hike
    private boolean CheckLocationValid(String name){
        return name.matches("[a-zA-Z ]+");
    }

    // Validation method for location of the hike
    private boolean CheckLengthValid(String length) {
        return length.matches("\\d+\\s?(m|km|M|KM)");
    }
}
