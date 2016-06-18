package fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import Utils.StationUtils;
import activity.HomeActivity;
import skkk.admin.com.dakai_station.R;

/**
 * Created by admin on 2016/6/11.
 */
/*
* 
* 描    述：点击查询界面
* 作    者：ksheng
* 时    间：
*/
public class SelectFragment extends Fragment {
    StationUtils utils = new StationUtils();
    private Button btnQuery;
    private String startStation;
    private String endStation;
    private AutoCompleteTextView actvStartStation;
    private AutoCompleteTextView actvEndStation;
    private EditText etSelect;
    private ImageView ivExchangeStation;
    private String trainId;
    private HomeActivity homeActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select, container, false);

        etSelect = (EditText) view.findViewById(R.id.et_select);
        actvStartStation = (AutoCompleteTextView) view.findViewById(R.id.actv_start_station);
        actvEndStation = (AutoCompleteTextView) view.findViewById(R.id.actv_end_station);
        ivExchangeStation = (ImageView) view.findViewById(R.id.iv_exchange_station);
        ivExchangeStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStation = actvStartStation.getText().toString().trim();
                endStation = actvEndStation.getText().toString().trim();
                if (!TextUtils.isEmpty(startStation) && !TextUtils.isEmpty(endStation)) {
                    actvStartStation.setText(endStation);
                    actvEndStation.setText(startStation);
                    btnQuery.setFocusable(true);
                    btnQuery.setFocusableInTouchMode(true);
                    btnQuery.requestFocus();
                    btnQuery.requestFocusFromTouch();
                } else {
                    Toast.makeText(getActivity(), "请输入正确的车站", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnQuery = (Button) view.findViewById(R.id.btn_query);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStation = actvStartStation.getText().toString().trim();
                endStation = actvEndStation.getText().toString().trim();
                trainId = etSelect.getText().toString().trim();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (!TextUtils.isEmpty(trainId) && TextUtils.
                        isEmpty(startStation) && TextUtils.isEmpty(endStation)) {

                    ShowFragment mShowFragment = new ShowFragment();
                    getFragmentManager().beginTransaction().
                            replace(R.id.home_fragment, mShowFragment).commit();
                } else if (TextUtils.isEmpty(trainId) && !TextUtils.
                        isEmpty(startStation) && !TextUtils.isEmpty(endStation)) {

                    StationToStationFragment mStationToStationFragment =
                            new StationToStationFragment();
                    transaction.replace(R.id.home_fragment, mStationToStationFragment).commit();
                }else{
                    Toast.makeText(getActivity(), "请输入需要查询的内容", Toast.LENGTH_SHORT).show();
                }
            }
        });

        List<String> listStation = utils.getAllStation();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, listStation);
        actvStartStation.setAdapter(adapter);
        actvEndStation.setAdapter(adapter);

        homeActivity = (HomeActivity) getActivity();
        homeActivity.setCollapsingToolbarTitle("开火车");

        return view;
    }


}
