package adapter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Utils.MySQLHelper;
import gson_station.StationBean;
import skkk.admin.com.dakai_station.R;

/**
 * Created by admin on 2016/6/11.
 */
/*
* 
* 描    述：RecyclerAdapter
* 作    者：ksheng
* 时    间：
*/
public class StationRecyclerAdapter extends RecyclerView.Adapter<StationRecyclerAdapter.MyViewHolder>{

    private List<StationBean> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private MySQLHelper mySQLHelper;
    private String[] item;
    private SharedPreferences mPref;

    public StationRecyclerAdapter(SharedPreferences mPref,String[] item,MySQLHelper mySQLHelper, Context mContext, List<StationBean> mDatas) {
        this.mPref=mPref;
        this.item=item;
        this.mySQLHelper = mySQLHelper;
        this.mContext = mContext;
        this.mDatas = mDatas;
        inflater=LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_station_to_station,parent, false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //将始发站与终点站之间的多余显示去掉
        holder.tvStationTrainNumber.setText(mDatas.get(position).getTrainOpp());
        holder.tvStationArrivedTime.setText(mDatas.get(position).getArrived_time());
        holder.tvStationLeaveTime.setText(mDatas.get(position).getLeave_time());
        holder.tvStationStart.setText(mDatas.get(position).getStart_staion());
        holder.tvStationEnd.setText(mDatas.get(position).getEnd_station());

    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvStationTrainNumber,tvStationArrivedTime,
                tvStationLeaveTime,tvStationStart,tvStationEnd;
        LinearLayout llBg;
        private SQLiteDatabase db;
        private String clocktimeString;//提醒的时间String形式
        private Date clocktime;//发车时间的date形式
        private long diff;//间隔时间的时间戳
        private long resultTime;//闹钟提醒时间的时间戳


        public MyViewHolder(View view) {
            super(view);
            tvStationTrainNumber=(TextView) view.findViewById(R.id.tv_station_train_number);
            tvStationArrivedTime=(TextView) view.findViewById(R.id.tv_station_arrived_time);
            tvStationLeaveTime= (TextView) view.findViewById(R.id.tv_station_leave_time);
            tvStationStart=(TextView) view.findViewById(R.id.tv_station_start_name);
            tvStationEnd=(TextView) view.findViewById(R.id.tv_station_end_name);
            llBg= (LinearLayout) view.findViewById(R.id.ll_station_item_bg);
            CardView cvItemStation= (CardView) view.findViewById(R.id.cv_item_statin);
            cvItemStation.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            final AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
            builder.setTitle("");
            builder.setIcon(R.drawable.ic_menu_camera);
            builder.setTitle("内个，提前多久提醒您？");
            int defaultItem=mPref.getInt("default_item",0);
            builder.setSingleChoiceItems(item, defaultItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mPref.edit().putInt("default_item", which).commit();
                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db = mySQLHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();

                    //获得标准格式的时间
                    SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    clocktimeString = sDateFormat.format(new java.util.Date()) + " "
                            + tvStationLeaveTime.getText().toString().trim() + ":00";

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//小写的mm表示的是分钟
                    try {
                        clocktime = sdf.parse(clocktimeString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    switch (mPref.getInt("default_item", 0)) {
                        case 0:
                            diff = 24 * 60 * 60 * 1000;
                            break;
                        case 1:
                             diff = 6 * 60 * 60 * 1000;
                            break;
                        case 2:
                             diff=2*60*60*1000;
                            break;
                        case 3:
                             diff=1*60*60*1000;
                            break;
                        case 4:
                             diff=30*60*1000;
                            break;
                    }
                    resultTime = clocktime.getTime() - diff;

                    values.put("train_id", tvStationTrainNumber.getText().toString().trim());
                    values.put("start", tvStationStart.getText().toString().trim());
                    values.put("time",resultTime);
                    values.put("end", tvStationEnd.getText().toString().trim());
                    values.put("from_time", tvStationLeaveTime.getText().toString().trim());
                    values.put("to_time", tvStationArrivedTime.getText().toString().trim());
                    db.insert("Clock", null, values);
                    db.close();
                    dialog.dismiss();
                    Toast.makeText(mContext,
                            "列车"+tvStationTrainNumber.getText().toString().trim()+
                                    "已加入提醒列表"
                            , Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("取消", null);
            builder.show();

        }
    }
}
