package fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
    private SharedPreferences mPref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select, container, false);

        mPref = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        mPref.edit().putBoolean("fragment_id",true).commit();

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
                RequestQueue queue= Volley.newRequestQueue(getActivity());
                String url=null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (!TextUtils.isEmpty(trainId) && TextUtils.
                        isEmpty(startStation) && TextUtils.isEmpty(endStation)) {
                    //执行查找车次信息
                    url="http://apis.juhe.cn/train/s?name="+trainId+"&key=47efc9fc09ca5666d6bfc4a81b580e89";
                    StringRequest request=new StringRequest(url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            mPref.edit().putString("all_data",s).commit();
                            //设置fragment跳转

                            ShowFragment mShowFragment = new ShowFragment();
                            getFragmentManager().beginTransaction().
                                    replace(R.id.home_fragment, mShowFragment).commit();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(getActivity(), "查询出错", Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(request);


                } else if (TextUtils.isEmpty(trainId) && !TextUtils.
                        isEmpty(startStation) && !TextUtils.isEmpty(endStation)) {
                    //执行查找车次信息
                    url="http://apis.juhe.cn/train/s2s?start="+startStation+"&end="+
                            endStation+"&traintype=g&key=47efc9fc09ca5666d6bfc4a81b580e89";
                    StringRequest request=new StringRequest(url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            mPref.edit().putString("input_station",s).commit();
                            //设置fragment跳转
                            StationToStationFragment mStationToStationFragment =
                                    new StationToStationFragment();
                            getFragmentManager().beginTransaction().replace(R.id.home_fragment,
                                    mStationToStationFragment).commit();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(getActivity(), "查询出错", Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(request);




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
