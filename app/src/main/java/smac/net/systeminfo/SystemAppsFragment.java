package smac.net.systeminfo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SystemAppsFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    public SystemAppsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_system_apps, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.sys_recycler_view);

        // Passing the column number 1 to show online one column in each row.
        recyclerViewLayoutManager = new GridLayoutManager(getActivity().getBaseContext(), 1);

        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        adapter = new SystemAppsAdapter(getActivity().getApplicationContext(), new SystemApkInfoExtractor(getActivity().getApplicationContext()).GetAllInstalledApkInfo());

        recyclerView.setAdapter(adapter);

        return view;
    }

}
