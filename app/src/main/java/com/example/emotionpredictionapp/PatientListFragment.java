package com.example.emotionpredictionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PatientListFragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.patient_list_fragment, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        String[] patientListItems = {"abc","def","ghi"};

        ArrayAdapter adapter = new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_list_item_1,patientListItems);
        ListView listView = view.findViewById(R.id.patientListView);
        listView.setAdapter(adapter);
    }
}
