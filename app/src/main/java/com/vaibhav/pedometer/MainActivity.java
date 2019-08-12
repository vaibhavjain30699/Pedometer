package com.vaibhav.pedometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView textView;
    boolean running = false;
    float count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.steps);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor sensor1 = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if(sensor1==null)
            Toast.makeText(this, "DETECTOR NOT FOUND", Toast.LENGTH_SHORT).show();

        //count = sensor.getFifoReservedEventCount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        running  = true;
        if(sensor!=null){
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
        else
            Toast.makeText(this, "SENSOR NOT FOUND", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        count = (sensorEvent.values[0]);
        if(running){
            textView.setText(String.valueOf(sensorEvent.values[0]-count));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
