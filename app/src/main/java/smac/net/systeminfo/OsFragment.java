package smac.net.systeminfo;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * A simple {@link Fragment} subclass.
 */
public class OsFragment extends Fragment {

    View view;
    TextView android_version;
    TextView version_name;
    TextView api_level;
    TextView root_access;
    TextView build_id;
    TextView code_name;
    TextView fingerprint;

    private AdView mAdView;

    public OsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_os, container, false);
        android_version=view.findViewById(R.id.os_version_id);
        version_name=view.findViewById(R.id.os_version_name_id);
        api_level=view.findViewById(R.id.os_api_id);
        root_access=view.findViewById(R.id.os_root_id);
        build_id=view.findViewById(R.id.os_build_id);
        code_name=view.findViewById(R.id.os_code_name_id);
        fingerprint=view.findViewById(R.id.os_fingerprint_id);


        android_version.setText(String.valueOf(android.os.Build.VERSION.RELEASE));
        api_level.setText(String.valueOf(android.os.Build.VERSION.SDK_INT));
        build_id.setText(String.valueOf(Build.ID));
        code_name.setText(Build.VERSION.CODENAME);
        fingerprint.setText(Build.FINGERPRINT);
        getVersionNameMethod();
        if (isRooted()==true){
            root_access.setText("Rooted");
        }

        //==================...........Admob ............==================
        MobileAds.initialize(getActivity().getBaseContext(),"ca-app-pub-3940256099942544~3347511713");

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //============================Admob end====================================

        return view;
    }

    public String getSDKCodeName(){
        String codeName = "";
        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            codeName = fieldName;

        }
        return codeName;
    }


    public static boolean isRooted() {

        // get from build info
        String buildTags = android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }

        // check if /system/app/Superuser.apk is present
        try {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                return true;
            }
        } catch (Exception e1) {
            // ignore
        }

        // try executing commands
        return canExecuteCommand("/system/xbin/which su")
                || canExecuteCommand("/system/bin/which su") || canExecuteCommand("which su");
    }

    // executes a command on the system
    private static boolean canExecuteCommand(String command) {
        boolean executedSuccesfully;
        try {
            Runtime.getRuntime().exec(command);
            executedSuccesfully = true;
        } catch (Exception e) {
            executedSuccesfully = false;
        }

        return executedSuccesfully;
    }

    public void getVersionNameMethod(){
        int CVersion = android.os.Build.VERSION.SDK_INT;
        switch (CVersion){

            case 11 :
                version_name.setText("Honeycomb");
                break;

            case 12 :
                version_name.setText("Honeycomb");
                break;

            case 13 :
                version_name.setText("Honeycomb");
                break;

            case 14 :
                version_name.setText("Ice Cream Sandwich");
                break;

            case 15 :
                version_name.setText("Ice Cream Sandwich");
                break;

            case 16 :
                version_name.setText("Jelly Bean");
                break;

            case 17 :
                version_name.setText("Jelly Bean");
                break;

            case 18 :
                version_name.setText("Jelly Bean");
                break;

            case 19 :
                version_name.setText("KitKat");
                break;

            case 21 :
                version_name.setText("Lollipop");
                break;

            case 22 :
                version_name.setText("Lollipop");
                break;

            case 23 :
                version_name.setText("Marshmallow");
                break;

            case 24 :
                version_name.setText("Nougat");
                break;

            case 25 :
                version_name.setText("Nougat");
                break;

            default:
                version_name.setText("Version Name Not Found");
                version_name.setTextColor(Color.parseColor("RED"));
                break;
        }
    }

}
