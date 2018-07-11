package smac.net.systeminfo;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppsInfoFragment extends Fragment {

    View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public AppsInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_apps_info, container, false);

        tabLayout=view.findViewById(R.id.apps_info_tab_layout_id);
        viewPager=view.findViewById(R.id.apps_info_viewpager_id);

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getActivity().getSupportFragmentManager());
        //Adding fragment=============================
        viewPageAdapter.addFregment(new InstalledAppsFragment(), "INSTALLED APPS");
        viewPageAdapter.addFregment(new SystemAppsFragment(), "SYSTEM APPS");

        //adapter setup
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
