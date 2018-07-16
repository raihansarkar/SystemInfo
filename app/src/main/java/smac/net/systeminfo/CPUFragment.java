package smac.net.systeminfo;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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
        clock_speed.setText(ReadCPUMhz());
        return view;
    }

    private String ReadCPUMhz()
    {
        ProcessBuilder cmd;
        String result="";
        int resultshow = 0;

        try{
            String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq"};
            cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[1024];
            while(in.read(re) != -1)
            {
                result = result + new String(re);

            }

            in.close();
        } catch(IOException ex){
            ex.printStackTrace();
        }
        Double value=Double.parseDouble(result);
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
