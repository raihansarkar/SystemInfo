package smac.net.systeminfo;


import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;

import static android.content.Context.ACTIVITY_SERVICE;
import static java.util.jar.Pack200.Packer.ERROR;


/**
 * A simple {@link Fragment} subclass.
 */
public class MemoryFragment extends Fragment {

    View view;
    TextView totalRam;
    TextView freeRam;
    TextView totalInternal;
    TextView freeInternal;
    TextView totalExternal;
    TextView freeExternal;

    public MemoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_memory, container, false);

        totalRam=view.findViewById(R.id.ram_total_space_id);
        freeRam=view.findViewById(R.id.ram_avialable_space_id);
        totalInternal=view.findViewById(R.id.internal_total_space_id);
        freeInternal=view.findViewById(R.id.internal_avialable_space_id);
        totalExternal=view.findViewById(R.id.external_total_space_id);
        freeExternal=view.findViewById(R.id.external_avialable_space_id);

        totalRam.setText(getTotalRamSize()+"");
        freeRam.setText(getFreeRamSize()+"");
        totalInternal.setText(getTotalInternalMemorySize()+"");
        freeInternal.setText(getFreeInternalMemorySize()+"");
        totalExternal.setText(getTotalExternalMemorySize()+"");
        freeExternal.setText(getAvailableExternalMemorySize()+"");

        return view;
    }

    private String getTotalRamSize() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager)getActivity().getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        double availableMegs = mi.totalMem / 1048576L;
        if(availableMegs>1000){
            double totalGB=availableMegs/1000;
            return String.format("%1$,.1f", totalGB)+" GB";
        }else{
            double totalGB=availableMegs;
            return String.format("%1$,.0f", totalGB)+" MB";
        }
    }
    private String getFreeRamSize() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager)getActivity().getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        double availableMegs = mi.availMem / 1048576L;
        if(availableMegs>1000){
            double totalGB=availableMegs/1000;
            return String.format("%1$,.1f", totalGB)+" GB";
        }else{
            double totalGB=availableMegs;
            return String.format("%1$,.0f", totalGB)+" MB";
        }
    }
    public String getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        String format =  Formatter.formatFileSize(getActivity().getBaseContext(), totalBlocks * blockSize);

        return format;
    }

    public String getFreeInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        String format =  Formatter.formatFileSize(getActivity().getBaseContext(), availableBlocks * blockSize);

        return format;
    }
    public boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageDirectory().equals(Environment.MEDIA_UNMOUNTED);

    }
    public  String getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            String format =  Formatter.formatFileSize(getActivity().getBaseContext(), totalBlocks * blockSize);
            return format;
        } else {
            String noMemory="No Memory";
            return noMemory;
        }
    }
    public  String getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            String format =  Formatter.formatFileSize(getActivity().getBaseContext(), availableBlocks * blockSize);
            return format;
        } else {
            String noMemory="No Memory";
            return noMemory;
        }
    }


}
