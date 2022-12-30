package com.example.emotionpredictionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class PatientGraphs extends AppCompatActivity {

    GraphView graph;
    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_graphs);

        int x1,y;



        graph = findViewById(R.id.emotionGraph);
        series = new LineGraphSeries<DataPoint>();
        graph.clearAnimation();

        y=1;
        String[] emotions = {"Happy", "Sad", "Nuetral","Angry", "Disgust","Fear"};
        for(int i=0; i<60; i++){

            series.appendData(new DataPoint(i,y),true, 500);
            y+=1;

            if(y>6)
                y=1;

        }
        graph.removeAllSeries();
//        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
//        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);


//        graph.getGridLabelRenderer().setLabelFormatter();
        graph.addSeries(series);

        String[] arraySpinner = new String[] {
                "Azhar Durrani", "Ali Hyder", "Farhan", "Haseeb", "Fahad",
        };
        Spinner s = (Spinner) findViewById(R.id.dateSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

    }
}