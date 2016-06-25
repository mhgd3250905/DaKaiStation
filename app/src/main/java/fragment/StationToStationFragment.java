package fragment;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Utils.MySQLHelper;
import activity.HomeActivity;
import adapter.StationRecyclerAdapter;
import gson_station.StationBean;
import gson_station.StationToStationData;
import skkk.admin.com.dakai_station.R;

/**
 * Created by admin on 2016/6/13.
 */
/*
* 
* 描    述：站到站查询结果列表
* 作    者：ksheng
* 时    间：6/13
*/
public class StationToStationFragment extends Fragment {

    private RecyclerView rvStationData;
    private StationRecyclerAdapter stationRecyclerAdapter;//站点对站点信息适配器
    private View view;//fragment界面

    private List<StationBean> data;
    private StationToStationData stationData;
    private HomeActivity homeActivity;
    private int totalcount;
    private String formTo;
    private TextView tvStationTotalcount;
    private MySQLHelper mySQLHelper;//数据库帮助
    private SQLiteDatabase db;
    private String clocktimeString;
    private List<StationBean> mData;
    private Calendar calendar;
    private int mYear;
    private int mMonth;
    private int mDay;
    private SharedPreferences mPref;
    private String result;
    private Gson gson;
    private Type type;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_station_to_station, container, false);

        mPref = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        mPref.edit().putBoolean("fragment_id",false).commit();

        result = mPref.getString("input_station", "");

        mySQLHelper = new MySQLHelper(getActivity(), "clock.db", null, 1);

        //获取RecyclerView实例

        rvStationData = (RecyclerView) view.findViewById(R.id.rv_station_data);

        getData();



        return view;
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==11111){
                //设置Adapter
                stationRecyclerAdapter = new StationRecyclerAdapter(getActivity(), data);

                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                //设置布局管理器
                rvStationData.setLayoutManager(layoutManager);
                //设置为垂直布局，这也是默认的
                layoutManager.setOrientation(OrientationHelper.VERTICAL);
                //设置Adapter
                rvStationData.setAdapter(stationRecyclerAdapter);

                homeActivity = (HomeActivity) getActivity();
                homeActivity.setCollapsingToolbarTitle(formTo);

                tvStationTotalcount = (TextView) view.findViewById(R.id.tv_station_total_count);
                tvStationTotalcount.setText("总计 " + totalcount + " 趟列车");
                calendar = Calendar.getInstance();
                stationRecyclerAdapter.setOnItemClickLitener(new StationRecyclerAdapter.OnItemClickLitener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        mYear = calendar.get(Calendar.YEAR);
                        mMonth = calendar.get(Calendar.MONTH);
                        mDay = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog mPicker = new DatePickerDialog(getActivity(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        mYear = year;
                                        mMonth = monthOfYear+1;
                                        mDay = dayOfMonth;
                                        //判断选择日期不可以晚于今天
                                        if(mYear>=calendar.get(Calendar.YEAR)){
                                            if(mMonth>=calendar.get(Calendar.MONTH)){
                                                if(mDay>calendar.get(Calendar.DAY_OF_MONTH)){
                                                    db = mySQLHelper.getWritableDatabase();
                                                    ContentValues values = new ContentValues();

                                                    //获得标准格式的时间
                                                    clocktimeString = mYear + "-" + mMonth + "-" + mDay;

                                                    values.put("train_id", mData.get(position).getTrainOpp());
                                                    values.put("start", mData.get(position).getStart_staion());
                                                    values.put("time", clocktimeString);
                                                    values.put("end", mData.get(position).getEnd_station());
                                                    values.put("from_time", mData.get(position).getLeave_time());
                                                    values.put("to_time", mData.get(position).getArrived_time());
                                                    db.insert("Clock", null, values);
                                                    if (db != null) {
                                                        db.close();
                                                    }
                                                    Toast.makeText(getActivity(),
                                                            "列车" + mData.get(position).getTrainOpp() +
                                                                    "已加入提醒列表"
                                                            , Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(getActivity(), "请选择不早于今日的提醒日期", Toast.LENGTH_SHORT).show();
                                                }
                                            }else {
                                                Toast.makeText(getActivity(), "请选择不早于今日的提醒日期", Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            Toast.makeText(getActivity(),"请选择不早于今日的提醒日期", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }, mYear, mMonth, mDay);
                        mPicker.show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                    }
                });
            }else if(msg.what==12345){
                Toast.makeText(getActivity(),"未查询到相关列车", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void getData() {

        ExecutorService singleThreadExector2= Executors.newSingleThreadExecutor();
        singleThreadExector2.execute(new Runnable() {
            @Override
            public void run() {
                gson = new Gson();
                type = new TypeToken<StationToStationData>() {
                }.getType();

                //获取网络解析出来的JSON数据
                stationData = gson.fromJson(result, type);
                int errorCode = stationData.getError_code();
                if (errorCode == 0) {
                    data = stationData.getResult().getData();
                    totalcount = stationData.getResult().getTotalcount();
                    formTo = stationData.getResult().getData().get(0).getStart_staion() + " - "
                            + stationData.getResult().getData().get(0).getEnd_station();
                    Message msg=new Message();
                    msg.what=11111;
                    handler.sendMessage(msg);
                }else {
                    Message msg2=new Message();
                    msg2.what=12345;
                    handler.sendMessage(msg2);
                }
            }
        });


    }

}
