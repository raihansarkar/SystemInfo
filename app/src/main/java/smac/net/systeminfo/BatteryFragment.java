package smac.net.systeminfo;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class BatteryFragment extends Fragment {

    View view;
    TextView batteryPctTv;
    TextView healthTv;
    TextView chargingStatusTv;
    TextView pluggedTv;
    TextView capacityTv;
    TextView voltageTv;
    TextView temperatureTv;
    TextView technologyTv;
    private BroadcastReceiver batteryInfoReceiver;

    public BatteryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_battery, container, false);
        batteryPctTv=view.findViewById(R.id.battery_level_id);
        healthTv=view.findViewById(R.id.battery_health_id);
        chargingStatusTv=view.findViewById(R.id.battery_charging_id);
        pluggedTv=view.findViewById(R.id.battery_plugged_id);
        capacityTv=view.findViewById(R.id.battery_capacity_id);
        voltageTv=view.findViewById(R.id.battery_voltage_id);
        temperatureTv=view.findViewById(R.id.battery_temperature_id);
        technologyTv=view.findViewById(R.id.battery_technology_id);




        batteryInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateBatteryData(intent);
            }
        };


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);

        getActivity().registerReceiver(batteryInfoReceiver, intentFilter);

        return view;
    }


//    private void loadBatterySection() {
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
//        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
//        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
//
//        getActivity().registerReceiver(batteryInfoReceiver, intentFilter);
//    }


    private void updateBatteryData(Intent intent) {
        boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);

        if (present) {
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            int healthLbl = -1;

            switch (health) {
                case BatteryManager.BATTERY_HEALTH_COLD:
                    healthLbl = R.string.battery_health_cold;
                    break;

                case BatteryManager.BATTERY_HEALTH_DEAD:
                    healthLbl = R.string.battery_health_dead;
                    break;

                case BatteryManager.BATTERY_HEALTH_GOOD:
                    healthLbl = R.string.battery_health_good;
                    break;

                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    healthLbl = R.string.battery_health_over_voltage;
                    break;

                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    healthLbl = R.string.battery_health_overheat;
                    break;

                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    healthLbl = R.string.battery_health_unspecified_failure;
                    break;

                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                default:
                    break;
            }

            if (healthLbl != -1) {
                // display battery health ...
                healthTv.setText(""+getString(healthLbl));
            }

            // Calculate Battery Pourcentage ...
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            if (level != -1 && scale != -1) {
                int batteryPct = (int) ((level / (float) scale) * 100f);
                batteryPctTv.setText(batteryPct+" %");
            }

            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            int pluggedLbl = R.string.battery_plugged_none;

            switch (plugged) {
                case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                    pluggedLbl = R.string.battery_plugged_wireless;
                    break;

                case BatteryManager.BATTERY_PLUGGED_USB:
                    pluggedLbl = R.string.battery_plugged_usb;
                    break;

                case BatteryManager.BATTERY_PLUGGED_AC:
                    pluggedLbl = R.string.battery_plugged_ac;
                    break;

                default:
                    pluggedLbl = R.string.battery_plugged_none;
                    break;
            }

            // display plugged status ...
            pluggedTv.setText(""+getString(pluggedLbl));

            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            int statusLbl = R.string.battery_status_discharging;

            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    statusLbl = R.string.battery_status_charging;
                    break;

                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    statusLbl = R.string.battery_status_discharging;
                    break;

                case BatteryManager.BATTERY_STATUS_FULL:
                    statusLbl = R.string.battery_status_full;
                    break;

                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    statusLbl = -1;
                    break;

                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                default:
                    statusLbl = R.string.battery_status_discharging;
                    break;
            }

            if (statusLbl != -1) {
                chargingStatusTv.setText(""+getString(statusLbl));
            }

            if (intent.getExtras() != null) {
                String technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);

                if (!"".equals(technology)) {
                    technologyTv.setText(""+technology);
                }
            }

            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);

            if (temperature > 0) {
                float temp = ((float) temperature) / 10f;
                temperatureTv.setText(temp+" °C");
            }

            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

            if (voltage > 0) {
                voltageTv.setText(voltage+" mV");
            }

            double capacity = getBatteryCapacity(getActivity().getBaseContext());

            if (capacity > 0) {
                capacityTv.setText(capacity+" mAh");
            }

        } else {
            Toast.makeText(getActivity().getBaseContext(), "No Battery present", Toast.LENGTH_SHORT).show();
        }

    }

    public double getBatteryCapacity(Context context) {
        Object mPowerProfile;
        double batteryCapacity = 0;
        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try {
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class)
                    .newInstance(context);

            batteryCapacity = (double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getBatteryCapacity")
                    .invoke(mPowerProfile);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return batteryCapacity;

    }

}
