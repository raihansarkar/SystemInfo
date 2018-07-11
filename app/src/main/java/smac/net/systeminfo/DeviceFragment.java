package smac.net.systeminfo;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceFragment extends Fragment {

    View view;
    TextView model;
    TextView manufacture;
    TextView brand;
    TextView serial_id;
    TextView imei;
    TextView screen_size;
    TextView resolution;
    TextView screen_density;
    TextView refresh_rate;
    TextView front_camara;
    TextView back_camara;
    TextView flash;
    android.hardware.Camera camera;

    private AdView mAdView;

    public DeviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_device, container, false);
//----------device initialize-----------------------------------
        model = view.findViewById(R.id.device_model_id);
        manufacture = view.findViewById(R.id.device_manufacture_id);
        brand = view.findViewById(R.id.device_brand_id);
        serial_id = view.findViewById(R.id.device_serial_id);
        imei = view.findViewById(R.id.device_imei_id);
//----------screen initialize-----------------------------------
        screen_size = view.findViewById(R.id.screen_size_id);
        screen_density = view.findViewById(R.id.screen_density_id);
        resolution = view.findViewById(R.id.screen_resulation_id);
        refresh_rate = view.findViewById(R.id.screen_refresh_id);
//----------camara initialize-----------------------------------
        back_camara = view.findViewById(R.id.camara_back_id);
        front_camara = view.findViewById(R.id.camara_front_id);


//----------device setText-----------------------------------
        model.setText(android.os.Build.MODEL);
        manufacture.setText(android.os.Build.MANUFACTURER);
        brand.setText(android.os.Build.BRAND);
        serial_id.setText(android.os.Build.SERIAL);
        serial_id.setTextColor(Color.parseColor("BLUE"));
        imei.setText(getIMEI());
        imei.setTextColor(Color.parseColor("BLUE"));

//----------screen setText-----------------------------------
        screen_size.setText(String.format("%1$,.2f", getScreenSizeMethod()) + " inch");
        screen_density.setText(Integer.toString(getScreenDensityMethod()) + " dpi(xhdpi)");
        resolution.setText(getResolutionMethod() + " pixels");
        refresh_rate.setText(Float.toString(getRefreshRateMethod()) + " Hz");
//----------camera setText-----------------------------------

        try {
            back_camara.setText(Long.toString(getBackCameraResolutionInMp())+" MP");
            front_camara.setText(Long.toString(getFrontCameraResolutionInMp())+" MP");
        }catch (Exception e){
            back_camara.setText("Camara permission needed!");
            front_camara.setText("Camara permission needed!");
        }


        //==================...........Admob ............==================
        MobileAds.initialize(getActivity().getBaseContext(),"ca-app-pub-3940256099942544~3347511713");

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //============================Admob end====================================



        return view;
    }

    //IMEI===============================
    public String getIMEI() {
        TelephonyManager tm = (TelephonyManager) getActivity().getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(getActivity().getBaseContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "phone permission not active";
        }else {
            String IMEINumber = tm.getDeviceId();
            Log.w("imei", "getIMEI: ");
            return IMEINumber;
        }

    }
    //End IMEI===========================

    public double getScreenSizeMethod(){
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels/dm.xdpi,2);
        double y = Math.pow(dm.heightPixels/dm.ydpi,2);
        double screenInches = Math.sqrt(x+y);

        return screenInches;
    }
    public int getScreenDensityMethod(){
        DisplayMetrics dm = getActivity().getBaseContext().getResources().getDisplayMetrics();
        int densityDpi = dm.densityDpi;
        return densityDpi;
    }
    public String getResolutionMethod(){
        WindowManager wm = (WindowManager) getActivity().getBaseContext().getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getRealSize(size);
        String resolution = size.x + "x" + size.y;
        return resolution;
    }
    public float getRefreshRateMethod(){
        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        float refreshRating = display.getRefreshRate();
        return refreshRating;
    }
    public long getFrontCameraResolutionInMp()
    {
        Camera camera=Camera.open(1);    // For Back Camera
        android.hardware.Camera.Parameters params = camera.getParameters();
        List sizes = params.getSupportedPictureSizes();
        Camera.Size  result = null;

        ArrayList<Integer> arrayListForWidth = new ArrayList<Integer>();
        ArrayList<Integer> arrayListForHeight = new ArrayList<Integer>();

        for (int i=0;i<sizes.size();i++){
            result = (Camera.Size) sizes.get(i);
            arrayListForWidth.add(result.width);
            arrayListForHeight.add(result.height);
//            Log.w("camara", "Supported Size: " + result.width + "height : " + result.height);
//            System.out.println("BACK PictureSize Supported Size: " + result.width + "height : " + result.height);
        }
//            System.out.println("Back max W :"+ Collections.max(arrayListForWidth));              // Gives Maximum Width
//            System.out.println("Back max H :"+Collections.max(arrayListForHeight));                 // Gives Maximum Height

        long backMP = (((Collections.max(arrayListForWidth)) * (Collections.max(arrayListForHeight))) / 1024000 );

        camera.release();
        return backMP;
    }

    public long getBackCameraResolutionInMp(){

        Camera camera=Camera.open(0);    // For Back Camera
        android.hardware.Camera.Parameters params = camera.getParameters();
        List sizes = params.getSupportedPictureSizes();
        Camera.Size  result = null;

        ArrayList<Integer> arrayListForWidth = new ArrayList<Integer>();
        ArrayList<Integer> arrayListForHeight = new ArrayList<Integer>();

        for (int i=0;i<sizes.size();i++){
            result = (Camera.Size) sizes.get(i);
            arrayListForWidth.add(result.width);
            arrayListForHeight.add(result.height);
//            Log.w("camara", "Supported Size: " + result.width + "height : " + result.height);
//            System.out.println("BACK PictureSize Supported Size: " + result.width + "height : " + result.height);
        }
//            System.out.println("Back max W :"+ Collections.max(arrayListForWidth));              // Gives Maximum Width
//            System.out.println("Back max H :"+Collections.max(arrayListForHeight));                 // Gives Maximum Height

            long backMP = (((Collections.max(arrayListForWidth)) * (Collections.max(arrayListForHeight))) / 1024000 );

        camera.release();
        return backMP;
    }

}
