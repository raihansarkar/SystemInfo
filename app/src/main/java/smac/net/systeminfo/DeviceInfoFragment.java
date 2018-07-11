package smac.net.systeminfo;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceInfoFragment extends Fragment {

    View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public DeviceInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_device_info, container, false);

        tabLayout=view.findViewById(R.id.device_info_tab_layout_id);
        viewPager=view.findViewById(R.id.device_info_viewpager_id);

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getActivity().getSupportFragmentManager());
        //Adding fragment=============================
        viewPageAdapter.addFregment(new DeviceFragment(), "DEVICE");
        viewPageAdapter.addFregment(new MemoryFragment(), "MEMORY");
        viewPageAdapter.addFregment(new BatteryFragment(), "BATTERY");
        viewPageAdapter.addFregment(new NetworkFragment(), "NETWORK");

        //adapter setup
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
