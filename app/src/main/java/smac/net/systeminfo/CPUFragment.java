package smac.net.systeminfo;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;


/**
 * A simple {@link Fragment} subclass.
 */
public class CPUFragment extends Fragment {

    View view;
    TextView cpu_model;
    TextView clock_speed;
    TextView hardware;
    TextView cpu_board;
    TextView cores;
    TextView bootloader;
    TextView java_heap;
    TextView cpu_load;
    TextView cpu_governor;
    TextView kernel_version;
    TextView kernel_architectute;
    private AdView mAdView;

    

    public CPUFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cpu, container, false);
        cpu_model=view.findViewById(R.id.cpu_model_id);
        clock_speed=view.findViewById(R.id.cpu_clock_speed_id);
        hardware=view.findViewById(R.id.cpu_hardware_id);
        cpu_board=view.findViewById(R.id.cpu_board_id);
        cores=view.findViewById(R.id.cpu_cores_id);
        bootloader=view.findViewById(R.id.cpu_bootloader_id);
        java_heap=view.findViewById(R.id.cpu_java_heap_id);
        cpu_load=view.findViewById(R.id.cpu_load_id);
        cpu_governor=view.findViewById(R.id.cpu_governor_id);
        kernel_version=view.findViewById(R.id.cpu_kernel_version_id);
        kernel_architectute=view.findViewById(R.id.cpu_kernel_architectute_id);


        kernel_version.setText(System.getProperty( "os.name")+" "+System.getProperty("os.version"));
        kernel_architectute.setText(System.getProperty("os.arch"));
        cores.setText(Runtime.getRuntime().availableProcessors()+"");
        hardware.setText(Build.HARDWARE);
        cpu_board.setText(Build.BOARD);
        bootloader.setText(Build.BOOTLOADER);
        cpu_governor.setText(getGovernor());
        //cpu_model.setText(Integer.toString(getMaxCPUFreqMHz()));
        java_heap.setText(Formatter.formatFileSize(getActivity().getBaseContext(), Runtime.getRuntime().maxMemory()));
        try {
            clock_speed.setText(ReadCPUMhz2());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //==================...........Admob ............==================
        MobileAds.initialize(getActivity().getBaseContext(),"ca-app-pub-3940256099942544~3347511713");

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //============================Admob end====================================

        return view;
    }

    public String ReadCPUMhz2() throws IOException
    {
        String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};

        ProcessBuilder cmd;
        cmd = new ProcessBuilder(args);
        Process process = null;
        process = cmd.start();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        StringBuilder log=new StringBuilder();

        String line;
        Log.d("cpu","aha");
        try {
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line + "\n");
            }
        }catch (Exception e){
            // no action
        }

        Log.d("cpu",log.toString());

        double value=Double.parseDouble(log.toString());
        value=value/1000;
        String st=Double.toString(value)+" MHz";
        if (value>1000){
            value=value/1000;
            st=Double.toString(value)+" GHz";
        }
        return st;
    }

    public String getGovernor() {
        StringBuffer sb = new StringBuffer();

        //String file = "/proc/cpuinfo";  // Gets most cpu info (but not the governor)
        String file = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";  // Gets governor

        if (new File(file).exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(new File(file)));
                String aLine;
                while ((aLine = br.readLine()) != null)
                    sb.append(aLine + "\n");

                if (br != null)
                    br.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

}
