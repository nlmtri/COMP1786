package com.example.HikeManagement.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HikeManagement.R;
import com.example.HikeManagement.adapter.HikerAdapter;
import com.example.HikeManagement.database.DatabaseHelper;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private ArrayList<String> hike_id, hike_name, hike_location, hike_date, hike_parking_available, hike_length, hike_weather_forecast, hike_time_estimated, hike_difficulty_level, hike_description;
    private DatabaseHelper databaseHelper;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        EditText searchEditText = view.findViewById(R.id.searchEditText);
        Button searchButton = view.findViewById(R.id.searchButton);
        RecyclerView searchResultsRecyclerView = view.findViewById(R.id.searchResultsRecyclerView);
        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseHelper = new DatabaseHelper(getContext());

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

        searchButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString().trim();
            if (!query.isEmpty()) {
                hike_id.clear();
                hike_name.clear();
                hike_location.clear();
                hike_date.clear();
                hike_parking_available.clear();
                hike_length.clear();
                hike_weather_forecast.clear();
                hike_time_estimated.clear();
                hike_difficulty_level.clear();
                hike_description.clear();

                Cursor cursor = databaseHelper.searchHikesByName(query);
                if (cursor.getCount() == 0) {
                    Toast.makeText(requireContext(), "Not found!", Toast.LENGTH_SHORT).show();
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

                    HikerAdapter adapter = new HikerAdapter(requireActivity(), requireContext(), hike_id, hike_name, hike_location, hike_date, hike_parking_available, hike_length, hike_weather_forecast, hike_time_estimated, hike_difficulty_level, hike_description);
                    searchResultsRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(getContext(), "Please type something to search!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
