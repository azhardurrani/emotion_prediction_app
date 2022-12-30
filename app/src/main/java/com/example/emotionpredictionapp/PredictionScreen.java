package com.example.emotionpredictionapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PredictionScreen extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_screen);

        postUrl = ApiHandler.baseURL+"/predict";


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PackageManager.PERMISSION_GRANTED);


        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                threadLoop();
            }
        });

        threadExecutor = Executors.newFixedThreadPool(10);
        status = findViewById(R.id.status);

        graph = findViewById(R.id.audioGraph);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);


    }



    private String postUrl;

    TextView status;

    AudioRecord audioRecord;
//    AudioTrack audioTrack;

    int bufferSize;
    short shortAudioData[];

    int gain;
    boolean isActive = false;


    private Thread thread;

    int iterationPerSecond =25;
    double audioDuration = 1;

    ExecutorService threadExecutor;
    GraphView graph;




    private void threadLoop(){

//        int recordSampleRate = (AudioTrack.getNativeOutputSampleRate(AudioManager.STREAM_MUSIC));

        int recordSampleRate = 44100;
        bufferSize = AudioRecord.getMinBufferSize(recordSampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);

        bufferSize*= iterationPerSecond*audioDuration;

        System.out.println("Sample-Rate: "+recordSampleRate);


//        int recordSampleRate = 1000;
//        bufferSize = 700;


        System.out.println("Buffer-Size: "+bufferSize);

        shortAudioData = new short[bufferSize];

        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                recordSampleRate,
                AudioFormat.CHANNEL_IN_STEREO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize);

//        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
//                recordSampleRate,
//                AudioFormat.CHANNEL_IN_STEREO,
//                AudioFormat.ENCODING_PCM_16BIT,
//                bufferSize,
//                AudioTrack.MODE_STREAM);

//        audioTrack.setPlaybackRate(recordSampleRate);

        audioRecord.startRecording();
//        audioTrack.play();

        int x=0;

        while(isActive){
            audioRecord.read(shortAudioData, 0, shortAudioData.length);

//            audioTrack.write(shortAudioData, 0, shortAudioData.length);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDateTime date = LocalDateTime.now();
                long seconds = Duration.between(date.withSecond(0).withMinute(0).withHour(0), date).getSeconds();
                System.out.println("TIME : "+seconds);
            }

            predictRequest(shortAudioData);

            double x1,y;
            x1= -10.0;


            series = new LineGraphSeries<DataPoint>();
            graph.clearAnimation();

            for(int i=0; i<bufferSize; i++){

                x1+=3;
                if(shortAudioData[i]>90 || shortAudioData[i]<-90) {
                    y = Math.sin(shortAudioData[i]);
                }else {
                    y = 0;

                }
                series.appendData(new DataPoint(x1,y),true, 500);
            }
            graph.removeAllSeries();
            graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
            graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

            graph.addSeries(series);
        }
        System.out.println("loop ended");
    }


    public void startRecord(View view){


        if(!isActive){

            isActive = true;
            ((TextView) view).setText("Stop");

            status.setText("...");

            threadExecutor.execute(thread);

        }else{
            isActive = false;
//            audioRecord.stop();

            ((TextView) view).setText("Predict Emotions");


            status.setText("Click on Button to Predict Emotions");



        }


    }



    public void predictRequest(short[] audioData){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {


            JSONArray jsonArray = new JSONArray(shortAudioData);
            postData.put("audio",jsonArray);



        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("VOLLEY", e.getMessage());
            Toast.makeText(this, "Error in extracting raw audio frequency", Toast.LENGTH_SHORT).show();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(isActive){
                        status.setText(response.getString("emotion"));
                    }

                } catch (JSONException e) {
                    status.setText("Error : "+e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
                status.setText("Connection Error: \n"+ error.getMessage());
                System.out.println("VOLLEY"+ error.getMessage());
            }
        });

        requestQueue.add(jsonObjectRequest);

    }



}
