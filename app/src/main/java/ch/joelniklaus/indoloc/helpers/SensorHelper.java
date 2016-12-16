package ch.joelniklaus.indoloc.helpers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import ch.joelniklaus.indoloc.models.SensorData;

/**
 * Created by joelniklaus on 13.11.16.
 */

public class SensorHelper {

    private Context context;

    private SensorManager sensorManager;
    private Sensor ambientTemperatureSensor, lightSensor, pressureSensor, relativeHumiditySensor, magnetometer, accelerometer;
    private double ambientTemperature, light, pressure, relativeHumidity;


    public SensorHelper(Context context) {
        this.context = context;
    }

    public void setUpSensors() {
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);

/*
        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            ambientTemperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            alert("Success: ambient temperature");
        }
        else {
            alert("Failure: ambient temperature");
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null){
            relativeHumiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            alert("Success: relative humidity");
        }
        else {
            alert("Failure: reltive humidity");
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            alert("Success: light");
        } else {
            alert("Failure: light");
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
            pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
            alert("Success: pressure");
        } else {
            alert("Failure: pressure");
        }
        */

        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            //alert("Success: magnetometer");
        } else {
            alert("Failure: No magnetometer available");
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            //alert("Success: accelerometer");
        } else {
            alert("Failure: No accelerometer available");
        }
    }

    public void registerListeners() {
        //sensorManager.registerListener(this, ambientTemperatureSensor, SensorManager.SENSOR_DELAY_UI);
        //sensorManager.registerListener(this, relativeHumiditySensor, SensorManager.SENSOR_DELAY_UI);
        //sensorManager.registerListener((SensorEventListener) context, lightSensor, SensorManager.SENSOR_DELAY_UI);
        //sensorManager.registerListener((SensorEventListener) context, pressureSensor, SensorManager.SENSOR_DELAY_UI);

        sensorManager.registerListener((SensorEventListener) context, magnetometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener((SensorEventListener) context, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    public void unRegisterListeners() {
        sensorManager.unregisterListener((SensorEventListener) context);
    }

    public SensorData readSensorData(SensorEvent event) {
                /*
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            //take the values
            ambientTemperature = event.values[0];
        }

         if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            //take the values
            relativeHumidity = event.values[0];
        }


        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            //take the values
            light = event.values[0];

        }

        if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            //take the values
            pressure = event.values[0];

        }
        */
        float[] magnetic = new float[3], gravity = new float[3], magneticFingerprint = new float[3];
        final float alpha = (float) 0.8;


        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //take the values
            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];
        }

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            //take the values
            magnetic[0] = event.values[0];
            magnetic[1] = event.values[1];
            magnetic[2] = event.values[2];

            float[] R = new float[9];
            float[] I = new float[9];
            SensorManager.getRotationMatrix(R, I, gravity, magnetic);
            float[] A_D = event.values.clone();
            float[] A_W = new float[3];
            A_W[0] = R[0] * A_D[0] + R[1] * A_D[1] + R[2] * A_D[2];
            A_W[1] = R[3] * A_D[0] + R[4] * A_D[1] + R[5] * A_D[2];
            A_W[2] = R[6] * A_D[0] + R[7] * A_D[1] + R[8] * A_D[2];

            magneticFingerprint[0] = A_W[0]; // x-value: should always be 0
            magneticFingerprint[1] = A_W[1]; // y-value
            magneticFingerprint[2] = A_W[2]; // z-value

            //Log.d("Field","\nX :"+A_W[0]+"\nY :"+A_W[1]+"\nZ :"+A_W[2]);
        }

        return new SensorData(magneticFingerprint[1], magneticFingerprint[1]);
    }

    public void alert(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
