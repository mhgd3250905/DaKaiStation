package fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Utils.MySQLHelper;
import activity.HomeActivity;
import adapter.ClockRecyclerAdapter;
import skkk.admin.com.dakai_station.R;

/**
 * Created by admin on 2016/6/16.
 */
/*
* 
* 描    述：用于防止设置好的闹钟
* 作    者：ksheng
* 时    间：6/16
*/
public class ClockFragment extends Fragment{

    private View view;
    private HomeActivity mParentActivity;//父Activity
    private RecyclerView rvClock;
    private MySQLHelper mySQLHelper;
    private ClockRecyclerAdapter mClockRecyclerAdapter;
    private List<String> dataFromSQL;
    private List<List<String>> mData;
    private SQLiteDatabase db;
    private Cursor cursor;
    private Date dateTime;
    private String dateString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_clock,container,false);
        mParentActivity = (HomeActivity) getActivity();
        mParentActivity.setCollapsingToolbarTitle("火车提醒");

        //获取RecyclerView实例
        rvClock = (RecyclerView) view.findViewById(R.id.rv_clock);
        mySQLHelper = new MySQLHelper(getActivity(),"clock.db",null,1);
        mData = getDataFromSQL();
        //设置Adapter
        //获取需要的SQLhelper
        mySQLHelper = new MySQLHelper(getActivity(),"clock.db",null,1);
        mClockRecyclerAdapter = new ClockRecyclerAdapter(mySQLHelper,getActivity(), mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        rvClock.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        rvClock.setAdapter(mClockRecyclerAdapter);

        mClockRecyclerAdapter.setOnItemClickLitener(new ClockRecyclerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, final int position) {
                switch (view.getId()) {
                    case R.id.cv_clock:
                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                        builder.setTitle("提示");
                        builder.setMessage("您将删除列车 " + mData.get(position).get(0)+ " 的提醒！");

                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SQLiteDatabase db = mySQLHelper.getWritableDatabase();
                                String SQLTrain_id =  mData.get(position).get(0);
                                String SQLTime = mData.get(position).get(1);
                                Log.d("CCCCCCCCCCCCCCCC", SQLTime + "   " + SQLTrain_id);
                                db.delete("Clock", "train_id=? and time=?",
                                        new String[]{SQLTrain_id, SQLTime});
                                if (db!=null){
                                    db.close();
                                }
                                mClockRecyclerAdapter.removeItem(position);
                            }

                        });
                        builder.setNegativeButton("取消",null);
                        builder.show();
                        break;
                }
            }
        });
        return view;
    }

    /*
    * @desc 返回一个adapter需要的 List<List<String>>
    * @时间 2016/6/17 23:29
    */
    public List<List<String>> getDataFromSQL() {
        ArrayList<List<String>> dataList = new ArrayList<>();
        db = mySQLHelper.getReadableDatabase();
        cursor = db.rawQuery("select train_id,time,start,end,from_time,to_time,item_1,item_2 from Clock", null);
        while (cursor.moveToNext()){
            ArrayList<String> data = new ArrayList<>();
            data.add(cursor.getString(0));//train_id
            data.add(cursor.getString(1));//time
            data.add(cursor.getString(2));//start
            data.add(cursor.getString(3));//end
            data.add(cursor.getString(4));//start_time
            data.add(cursor.getString(4));//end_time
            dataList.add(data);
        }
        cursor.close();
        db.close();
        return dataList;
    }
    public void RefleshAdapter(){
        mClockRecyclerAdapter.notifyDataSetChanged();
    }
}
