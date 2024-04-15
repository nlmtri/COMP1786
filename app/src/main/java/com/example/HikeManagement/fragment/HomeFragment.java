package com.example.HikeManagement.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HikeManagement.R;
import com.example.HikeManagement.adapter.HikerAdapter;
import com.example.HikeManagement.database.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton deleteAllButton;
    DatabaseHelper databaseHelper;
    ArrayList<String> hike_id, hike_name, hike_location, hike_date, hike_parking_available, hike_length, hike_weather_forecast, hike_time_estimated, hike_difficulty_level, hike_description;
    HikerAdapter hikerAdapter;
    ImageView emptyImageView;
    TextView noData;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        emptyImageView = view.findViewById(R.id.empty_imageView);
        noData = view.findViewById(R.id.no_data_textview);
        deleteAllButton = view.findViewById(R.id.deleteAllButton);
        deleteAllButton.setOnClickListener(v -> confirmDialogDeleteAll());

        databaseHelper = new DatabaseHelper(requireContext());

        hike_id = new ArrayList<>();
        hike_name = new ArrayList<>();
        hike_location = new ArrayList<>();
        hike_date = new ArrayList<>();
        hike_parking_available = new ArrayList<>();
        hike_length = new ArrayList<>();
        hike_weather_forecast = new ArrayList<>();
        hike_time_estimated = new ArrayList<>();
        hike_difficulty_level = new ArrayList<>();
        hike_description = new ArrayList<>();

        showData();

        hikerAdapter = new HikerAdapter(requireActivity(), requireContext(), hike_id, hike_name, hike_location, hike_date, hike_parking_available, hike_length, hike_weather_forecast, hike_time_estimated, hike_difficulty_level, hike_description);
        recyclerView.setAdapter(hikerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        return view;
    }

    public void showData() {
        Cursor cursor = databaseHelper.readAllHikeInformation();
        if (cursor.getCount() == 0) {
            emptyImageView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                hike_id.add(cursor.getString(0));
                hike_name.add(cursor.getString(1));
                hike_location.add(cursor.getString(2));
                hike_date.add(cursor.getString(3));
                hike_parking_available.add(cursor.getString(4));
                hike_length.add(cursor.getString(5));
                hike_weather_forecast.add(cursor.getString(6));
                hike_time_estimated.add(cursor.getString(7));
                hike_difficulty_level.add(cursor.getString(8));
                hike_description.add(cursor.getString(9));
            }
            emptyImageView.setVisibility(View.GONE);
            noData.setVisibility(View.GONE);
        }
    }

    public void confirmDialogDeleteAll(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all data?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
            databaseHelper.deleteAllHikeInformation();

            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, homeFragment);
            fragmentTransaction.commit();
        });

        builder.setNegativeButton("No", (dialogInterface, i) -> {});

        builder.create().show();
    }
}
