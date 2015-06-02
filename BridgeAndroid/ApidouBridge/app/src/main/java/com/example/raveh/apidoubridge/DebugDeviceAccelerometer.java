package com.example.raveh.apidoubridge;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Parcel;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.message.Acceleration;

/**
 * Created by Raveh on 02/06/2015.
 */
public class DebugDeviceAccelerometer implements SensorEventListener
{
    private  static float GRAVITY_CONSTANT = 9.81f;

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private float gravity[] = new float[3];
    private ApidouListener _apidou;

    public DebugDeviceAccelerometer(ApidouListener apidou, SensorManager manager)
    {
        //senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senSensorManager = manager;
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        _apidou = apidou;
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            /*final float alpha = 0.8f;

            // Isolate the force of gravity with the low-pass filter.
            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

            float linear_acceleration[] = new float[3];

            // Remove the gravity contribution with the high-pass filter.
            linear_acceleration[0] = event.values[0] - gravity[0];
            linear_acceleration[1] = event.values[1] - gravity[1];
            linear_acceleration[2] = event.values[2] - gravity[2];

            if (linear_acceleration[0] > big[0])
                big[0] = linear_acceleration[0];
            if (linear_acceleration[1] > big[1])
                big[1] = linear_acceleration[1];
            if (linear_acceleration[2] > big[2])
                big[2] = linear_acceleration[2];*/

            _apidou.onAccelerationData(new DebugAcceleration(x / GRAVITY_CONSTANT, y / GRAVITY_CONSTANT, z / GRAVITY_CONSTANT));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    final class DebugAcceleration extends Acceleration {
        private final double x;
        private final double y;
        private final double z;

        DebugAcceleration(
                double x,
                double y,
                double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public double x() {
            return x;
        }

        @Override
        public double y() {
            return y;
        }

        @Override
        public double z() {
            return z;
        }

        @Override
        public String toString() {
            return "Acceleration{"
                    + "x=" + x
                    + ", y=" + y
                    + ", z=" + z
                    + "}";
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    };
}
