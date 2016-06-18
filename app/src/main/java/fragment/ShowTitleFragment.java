package fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import gson_train_number.AllData;
import gson_train_number.TrainInfo;
import skkk.admin.com.dakai_station.R;

/**
 * Created by admin on 2016/6/15.
 */
/*
* 
* 描    述：测试
* 作    者：ksheng
* 时    间：
*/
public class ShowTitleFragment extends Fragment {
    //显示主信息的所有TextView
    private TextView tvMainMileage;
    private TextView tvMainStart;
    private TextView tvMainEnd;
    private TextView tvMainStartTime;
    private TextView tvMainEndTime;
    SharedPreferences mPref;
    private AllData allData;//获取包含所有信息的类
    private String result;//从SP中获取包含的String
    private int errorCode;//错误编号 为0 则表示获取数据成功

    private TrainInfo main;//获取包含所有主信息的类
    private String mainName;//主信息中的车次
    private String mainStart;//主信息中的始发站
    private String mainEnd;//主信息中的终点站
    private String mainStartTime;//主信息中的始发时间
    private String mainEndTime;//主信息中的终点时间
    private String mainMileage;//主信息中的里程数

    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_title_show,container,false);
        init();
        return view;
    }

    /*
* @desc 初始化
* @时间 2016/6/11 21:05
*/
    private void init() {

        tvMainStart = (TextView) view.findViewById(R.id.tv_train_start);
        tvMainEnd = (TextView) view.findViewById(R.id.tv_train_end);
        tvMainStartTime = (TextView) view.findViewById(R.id.tv_train_start_time);
        tvMainEndTime = (TextView) view.findViewById(R.id.tv_train_end_time);
        tvMainMileage = (TextView) view.findViewById(R.id.tv_train_mileage);

        mPref = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<AllData>() {
        }.getType();
        //获取网络解析出来的JSON数据
        String result = mPref.getString("all_data", "");
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
            mainMileage = main.getMileage() + "km";
            fillData();
        }

    }
    private void fillData() {
        tvMainMileage.setText(mainMileage);
        tvMainStart.setText(mainStart);
        tvMainEnd.setText(mainEnd);
        tvMainStartTime.setText(mainStartTime);
        tvMainEndTime.setText(mainEndTime);
        getActivity().setTitle(mainName);
    }
}
