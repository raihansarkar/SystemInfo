package smac.net.systeminfo;


import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


/**
 * A simple {@link Fragment} subclass.
 */
public class SensorFragment extends Fragment {

    View view;
    TextView accelerometer;
    TextView compass;
    TextView proximity;
    TextView gyroscope;
    TextView light_sensor;
    TextView barometer;
    TextView relative_humudity;
    TextView heart_rate_monitor;
    TextView temperature_sensor;
    TextView fingerprint_sensor;

    private SensorManager sensorManager;

    private AdView mAdView;


    public SensorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sensor, container, false);
        accelerometer=view.findViewById(R.id.sensor_accelerometer_id);
        compass=view.findViewById(R.id.sensor_compass_id);
        proximity=view.findViewById(R.id.sensor_proximity_id);
        gyroscope=view.findViewById(R.id.sensor_gyroscope_id);
        light_sensor=view.findViewById(R.id.sensor_ambient_light_id);
        barometer=view.findViewById(R.id.sensor_barometer_id);
        relative_humudity=view.findViewById(R.id.sensor_humidity_id);
        heart_rate_monitor=view.findViewById(R.id.sensor_heart_rate_id);
        temperature_sensor=view.findViewById(R.id.sensor_temperature_id);
        fingerprint_sensor=view.findViewById(R.id.sensor_fingerprint_id);


        isAccelerometerAvailable();

        //==================...........Admob ............==================
        MobileAds.initialize(getActivity().getBaseContext(),"ca-app-pub-3940256099942544~3347511713");

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //============================Admob end====================================

        return view;
    }

    public void isAccelerometerAvailable(){

        sensorManager = (SensorManager) getActivity().getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        //------------------accelerometer---------------------------------------
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer
            accelerometer.setText("Yes");
            accelerometer.setTextColor(Color.parseColor("GREEN"));
        } else {
            // fai! we dont have an accelerometer!
            accelerometer.setText("No");
            accelerometer.setTextColor(Color.parseColor("RED"));
        }
        //------------------PROXIMITY---------------------------------------
        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            // success! we have an accelerometer
            compass.setText("Yes");
            compass.setTextColor(Color.parseColor("GREEN"));
        } else {
            // fai! we dont have an accelerometer!
            compass.setText("No");
            compass.setTextColor(Color.parseColor("RED"));
        }
        //------------------PROXIMITY---------------------------------------
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            // success! we have an accelerometer
            proximity.setText("Yes");
            proximity.setTextColor(Color.parseColor("GREEN"));
        } else {
            // fai! we dont have an accelerometer!
            proximity.setText("No");
            proximity.setTextColor(Color.parseColor("RED"));
        }
        //------------------GYROSCOPE---------------------------------------
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            // success! we have an accelerometer
            gyroscope.setText("Yes");
            gyroscope.setTextColor(Color.parseColor("GREEN"));
        } else {
            // fai! we dont have an accelerometer!
            gyroscope.setText("No");
            gyroscope.setTextColor(Color.parseColor("RED"));
        }
        //------------------PRESSURE/BAROMETER---------------------------------------
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
            // success! we have an accelerometer
            barometer.setText("Yes");
            barometer.setTextColor(Color.parseColor("GREEN"));
        } else {
            // fai! we dont have an accelerometer!
            barometer.setText("No");
            barometer.setTextColor(Color.parseColor("RED"));
        }
        //------------------LIGHT---------------------------------------
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            // success! we have an accelerometer
            light_sensor.setText("Yes");
            light_sensor.setTextColor(Color.parseColor("GREEN"));
        } else {
            // fai! we dont have an accelerometer!
            light_sensor.setText("No");
            light_sensor.setTextColor(Color.parseColor("RED"));
        }
        //------------------RELATIVE_HUMIDITY---------------------------------------
        if (sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null) {
            // success! we have an accelerometer
            relative_humudity.setText("Yes");
            relative_humudity.setTextColor(Color.parseColor("GREEN"));
        } else {
            // fai! we dont have an accelerometer!
            relative_humudity.setText("No");
            relative_humudity.setTextColor(Color.parseColor("RED"));
        }
        //------------------HEART_RATE---------------------------------------
        if (sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null) {
            // success! we have an accelerometer
            heart_rate_monitor.setText("Yes");
            heart_rate_monitor.setTextColor(Color.parseColor("GREEN"));
        } else {
            // fai! we dont have an accelerometer!
            heart_rate_monitor.setText("No");
            heart_rate_monitor.setTextColor(Color.parseColor("RED"));
        }
        //------------------AMBIENT_TEMPERATURE---------------------------------------
        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            // success! we have an accelerometer
            temperature_sensor.setText("Yes");
            temperature_sensor.setTextColor(Color.parseColor("GREEN"));
        } else {
            // fai! we dont have an accelerometer!
            temperature_sensor.setText("No");
            temperature_sensor.setTextColor(Color.parseColor("RED"));
        }
        //------------------FINGERPRINT---------------------------------------
        //Fingerprint API only available on from Android 6.0 (M)
        FingerprintManager fingerprintManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            fingerprintManager = (FingerprintManager) getActivity().getApplicationContext().getSystemService(Context.FINGERPRINT_SERVICE);

            if (!fingerprintManager.isHardwareDetected()) {
                // Device doesn't support fingerprint authentication
                fingerprint_sensor.setText("No");
                fingerprint_sensor.setTextColor(Color.parseColor("RED"));
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                // User hasn't enrolled any fingerprints to authenticate with
                fingerprint_sensor.setText("User hasn't enrolled any fingerprints");
                fingerprint_sensor.setTextColor(Color.parseColor("RED"));
            } else {
                // Everything is ready for fingerprint authentication
                fingerprint_sensor.setText("Yes");
                fingerprint_sensor.setTextColor(Color.parseColor("GREEN"));
            }
        }


    }

}
