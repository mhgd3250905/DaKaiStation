package fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import activity.HomeActivity;
import adapter.MyRecyclerAdapter;
import gson_train_number.AllData;
import gson_train_number.Station;
import gson_train_number.TrainInfo;
import skkk.admin.com.dakai_station.R;

/**
 * Created by admin on 2016/6/11.
 */
/*
* 
* 描    述：查询界面
* 作    者：ksheng
* 时    间：
*/
public class ShowFragment extends Fragment {
    SharedPreferences mPref;
    private AllData allData;//获取包含所有信息的类
    private String result;//从SP中获取包含的String
    private int errorCode;//错误编号 为0 则表示获取数据成功
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter myRecyclerAdapter;

    private TrainInfo main;//获取包含所有主信息的类
    private String mainName;//主信息中的车次
    private String mainStart;//主信息中的始发站
    private String mainEnd;//主信息中的终点站
    private String mainStartTime;//主信息中的始发时间
    private String mainEndTime;//主信息中的终点时间
    private View view;

    //显示主信息的所有TextView
    private TextView tvMainName;
    private TextView tvMainStart;
    private TextView tvMainEnd;
    private TextView tvMainStartTime;
    private TextView tvMainEndTime;
    private TextView tvMainMileage;

    //包含RecycleView所有数据的数组
    private List<Station> stationList;
    private String mainMileage;
    private HomeActivity parentActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_show, container, false);

        mPref = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        mPref.edit().putBoolean("fragment_id",false).commit();

        init();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_show_station);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        //设置Adapter


        myRecyclerAdapter= new MyRecyclerAdapter(getActivity(),stationList);

        mRecyclerView.setAdapter(myRecyclerAdapter);
        //设置分隔线
        //mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        //设置增加或删除条目的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }



/*
* @desc 初始化
* @时间 2016/6/11 21:05
*/
    private void init() {

        tvMainName = (TextView) view.findViewById(R.id.tv_train_name);
        tvMainStart = (TextView) view.findViewById(R.id.tv_train_start);
        tvMainEnd = (TextView) view.findViewById(R.id.tv_train_end);
        tvMainStartTime = (TextView) view.findViewById(R.id.tv_train_start_time);
        tvMainEndTime = (TextView) view.findViewById(R.id.tv_train_end_time);
        tvMainMileage= (TextView) view.findViewById(R.id.tv_train_mileage);

        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<AllData>() {
        }.getType();
        //获取网络解析出来的JSON数据
        result = mPref.getString("all_data", "");
        Log.d("AAAAAAAAAAAA", result);
        allData = gson.fromJson(result, type);
        errorCode = allData.getError_code();

        if (errorCode == 0) {
            main = allData.getResult().getTrain_info();
            mainName = main.getName();
            mainStart = main.getStart();
            mainEnd = main.getEnd();
            mainStartTime = main.getStarttime();
            mainEndTime = main.getEndtime();
            mainMileage = main.getMileage();
            fillData();
            //将SP中获取的数据转存的需要的数组mDatas中
            stationList = allData.getResult().getStation_list();

        }

    }

    private void fillData() {
        tvMainStart.setText(mainStart);
        tvMainEnd.setText(mainEnd);
        tvMainStartTime.setText(mainStartTime);
        tvMainEndTime.setText(mainEndTime);
        tvMainMileage.setText("里程： " + mainMileage);
        parentActivity = (HomeActivity) getActivity();
        parentActivity.setCollapsingToolbarTitle(mainName);
    }
}
