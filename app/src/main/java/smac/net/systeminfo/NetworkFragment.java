package smac.net.systeminfo;


import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import static android.content.Context.WIFI_SERVICE;
import static android.graphics.Color.GREEN;


/**
 * A simple {@link Fragment} subclass.
 */
public class NetworkFragment extends Fragment {

    View view;
    TextView data_connection;
    TextView data_type;
    TextView link_speed;
    TextView wifi_name;
    TextView frequency;
    TextView ip_address;
    TextView bssid;
    TextView mac_address;
    TextView download_speed;
    TextView upload_speed;

    LinearLayout layout_wifi_name;
    LinearLayout layout_link_speed;
    LinearLayout layout_frequency;
    LinearLayout layout_bssid;
    LinearLayout layout_mac;

    String IPaddress;
    Boolean IPValue;
    private InetAddress thisIp;
    private String thisIpAddress;
    //download and upload speed-------
    private Handler mHandler = new Handler();
    private long mStartRX = 0;
    private long mStartTX = 0;

    public NetworkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_network, container, false);
        data_connection=view.findViewById(R.id.network_data_connection_id);
        data_type=view.findViewById(R.id.network_data_type_id);
        link_speed=view.findViewById(R.id.network_link_speed_id);
        wifi_name=view.findViewById(R.id.network_wifi_name_id);
        frequency=view.findViewById(R.id.network_frequency_id);
        ip_address=view.findViewById(R.id.network_ip_address_id);
        bssid=view.findViewById(R.id.network_bssid_id);
        mac_address=view.findViewById(R.id.network_mac_id);
        download_speed=view.findViewById(R.id.network_download_speed_id);
        upload_speed=view.findViewById(R.id.network_upload_speed_id);
        //layout===============================
        layout_wifi_name=view.findViewById(R.id.network_layout_wifi_name_id);
        layout_link_speed=view.findViewById(R.id.network_layout_link_speed_id);
        layout_frequency=view.findViewById(R.id.network_layout_frequency_id);
        layout_bssid=view.findViewById(R.id.network_layout_bssid_id);
        layout_mac=view.findViewById(R.id.network_layout_mac_id);




//        NetwordDetect();
//        wifiInformation();

        //download and upload speed
        mStartRX = TrafficStats.getTotalRxBytes();
        mStartTX = TrafficStats.getTotalTxBytes();


        if (mStartRX == TrafficStats.UNSUPPORTED
                || mStartTX == TrafficStats.UNSUPPORTED) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity().getBaseContext());
            alert.setTitle("Uh Oh!");
            alert.setMessage("Your device does not support traffic stat monitoring.");
            alert.show();

        } else {
            mHandler.postDelayed(mRunnable, 1000);

        }



        return view;
    }
    private void wifiInformation(){
        WifiManager wifiManager = (WifiManager)getActivity().getApplicationContext().getSystemService(WIFI_SERVICE);
        if(wifiManager.isWifiEnabled()){
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (String.valueOf(wifiInfo.getSupplicantState()).equals("COMPLETED")){
                bssid.setText(wifiInfo.getBSSID());
                mac_address.setText(wifiInfo.getMacAddress());
                mac_address.setTextColor(Color.parseColor("BLUE"));
                link_speed.setText(String.valueOf(wifiInfo.getLinkSpeed())+" Mbps");
                wifi_name.setText(wifiInfo.getSSID());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    frequency.setText(String.valueOf(wifiInfo.getFrequency())+" MHz");
                }else {
                    frequency.setText("VERSION HAVE TO UPPER THEN LOLIPOP");
                }

            }
            layout_wifi_name.setVisibility(View.VISIBLE);
            layout_link_speed.setVisibility(View.VISIBLE);
            layout_frequency.setVisibility(View.VISIBLE);
            layout_bssid.setVisibility(View.VISIBLE);
            layout_mac.setVisibility(View.VISIBLE);

        }else {

            // wifiManager.setWifiEnabled(true);
            layout_wifi_name.setVisibility(View.GONE);
            layout_link_speed.setVisibility(View.GONE);
            layout_frequency.setVisibility(View.GONE);
            layout_bssid.setVisibility(View.GONE);
            layout_mac.setVisibility(View.GONE);
        }
    }


    //Check the internet connection.
    private void NetwordDetect() {

        boolean WIFI = false;

        boolean MOBILE = false;

        ConnectivityManager CM = (ConnectivityManager) getActivity().getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfo = CM.getAllNetworkInfo();

        for (NetworkInfo netInfo : networkInfo) {

            if (netInfo.getTypeName().equalsIgnoreCase("WIFI")){
                if (netInfo.isConnected()){
                    WIFI = true;
                    data_type.setText("Wifi");
                    data_type.setTextColor(Color.parseColor("GREEN"));
                    data_connection.setText("Data Connected");
                    data_connection.setTextColor(Color.parseColor("GREEN"));
                    //here wifi name will be set
                }

            }




            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE")){
                if (netInfo.isConnected()){
                    MOBILE = true;
                    data_type.setText("Mobile");
                    data_type.setTextColor(Color.parseColor("GREEN"));
                    data_connection.setText("Data Connected");
                    data_connection.setTextColor(Color.parseColor("GREEN"));
                    //here wifi name will be set
                }else {

                    data_type.setText("Internet Disconnected!");
                    data_type.setTextColor(Color.parseColor("RED"));
                    data_connection.setText("Data Disconnected!");
                    data_connection.setTextColor(Color.parseColor("RED"));
                }
            }

        }

        if(WIFI == true)

        {
            IPaddress = GetDeviceipWiFiData();
            ip_address.setText(IPaddress);
            ip_address.setTextColor(Color.parseColor("BLUE"));


        }

        if(MOBILE == true)
        {

            IPaddress = GetDeviceipMobileData();
            ip_address.setText(IPaddress);
            ip_address.setTextColor(Color.parseColor("BLUE"));

        }

    }


    public String GetDeviceipMobileData(){
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements();) {
                NetworkInterface networkinterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkinterface.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
            Log.w("Current IP", ex.toString());
        }
        return null;
    }

    public String GetDeviceipWiFiData()
    {

        WifiManager wm = (WifiManager) getActivity().getApplicationContext().getSystemService(WIFI_SERVICE);
          @SuppressWarnings("deprecation")
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        return ip;

    }

    //download and upload speed method in per second------------------------------------------
    private final Runnable mRunnable = new Runnable() {
        public void run() {
            //this is others network related methods are define in this class that are call in run method
            NetwordDetect();
            wifiInformation();



//            TextView download = (TextView) view.findViewById(R.id.network_download_speed_id);
//            TextView upload = (TextView) view.findViewById(R.id.network_upload_speed_id);
            // long rxBytes = TrafficStats.getTotalRxBytes() - mStartRX;
            // RX.setText(Long.toString(rxBytes));

            long rxBytes = TrafficStats.getTotalRxBytes() - mStartRX;
            Log.w("speed", "speed: "+TrafficStats.getTotalRxBytes()+" - "+mStartRX+" = "+rxBytes);
            download_speed.setText(Long.toString(rxBytes) + " bytes");
            if (rxBytes >= 1024) {
                // KB or more
                long rxKb = rxBytes / 1024;
                download_speed.setText(Long.toString(rxKb) + " KBs");
                if (rxKb >= 1024) {
                    // MB or more
                    long rxMB = rxKb / 1024;
                    download_speed.setText(Long.toString(rxMB) + " MBs");
                    if (rxMB >= 1024) {
                        // GB or more
                        long rxGB = rxMB / 1024;
                        download_speed.setText(Long.toString(rxGB));
                    }// rxMB>1024
                }// rxKb > 1024
            }// rxBytes>=1024

            // long txBytes = TrafficStats.getTotalTxBytes() - mStartTX;
            // TX.setText(Long.toString(txBytes));
            long txBytes = TrafficStats.getTotalTxBytes() - mStartTX;

            upload_speed.setText(Long.toString(txBytes) + " bytes");
            if (txBytes >= 1024) {
                // KB or more
                long txKb = txBytes / 1024;
                upload_speed.setText(Long.toString(txKb) + " KBs");
                if (txKb >= 1024) {
                    // MB or more
                    long txMB = txKb / 1024;
                    upload_speed.setText(Long.toString(txMB) + " MBs");
                    if (txMB >= 1024) {
                        // GB or more
                        long txGB = txMB / 1024;
                        upload_speed.setText(Long.toString(txGB));
                    }// rxMB>1024
                }// rxKb > 1024
            }// rxBytes>=1024

            mHandler.postDelayed(mRunnable, 1000);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable, 1000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
    }


}
