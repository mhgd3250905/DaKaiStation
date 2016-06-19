package service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Utils.MySQLHelper;
import skkk.admin.com.dakai_station.R;

public class ClockService extends Service {
    private long diff_24 = 36 * 60 * 60 * 1000;
    private long diff_6 = 18 * 60 * 60 * 1000;
    private long diff_2 = 14 * 60 * 60 * 1000;
    private MySQLHelper mySQLHelper;
    SQLiteDatabase db;
    private Cursor cursor;
    private long clockDate_24_long;
    private long clockDate_6_long;
    private long clockDate_2_long;
    private ArrayList<List<String>> dataList;
    private NotificationManager manager;
    private NotificationCompat.Builder builder;
    private Notification notification;
    private Date timeDate;
    private String aaa;
    private long nowDate;
    private ArrayList<String> data;


    @Override
    public IBinder onBind(Intent intent) {
      return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("AAAAAAAAA", "onCreate");
        Toast.makeText(ClockService.this, "aaa", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        nowDate = System.currentTimeMillis();
        mySQLHelper = new MySQLHelper(this,"clock.db",null,1);
        db = mySQLHelper.getReadableDatabase();
        cursor = db.rawQuery("select train_id,time,start,end,from_time,to_time,item_1,item_2 from Clock", null);
        dataList = new ArrayList<>();
        while (cursor.moveToNext()){

            data = new ArrayList<>();
            data.add(cursor.getString(0));//train_id
            data.add(cursor.getString(2));//start
            data.add(cursor.getString(3));//end
            data.add(cursor.getString(4));//start_time
            data.add(cursor.getString(5));//end_time
            data.add(String.valueOf(cursor.getPosition()));

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
            try {
                aaa = cursor.getString(1)+" "+cursor.getString(4)+":00";
                timeDate = sdf.parse(aaa);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            clockDate_24_long=timeDate.getTime()-diff_24;
            clockDate_6_long=timeDate.getTime()-diff_6;
            clockDate_2_long=timeDate.getTime()-diff_2;

            notificationDeal(clockDate_24_long,24);
            notificationDeal(clockDate_6_long,6);
            notificationDeal(clockDate_2_long,2);

            dataList.add(data);
        }
        if(cursor!=null) {
            cursor.close();
        }
        if(db!=null){
            db.close();
        }

        Log.d("AAAAAAAAA", "Start");
        return super.onStartCommand(intent, flags, startId);

    }

    private void notificationDeal(long clockTime,int hours) {
        if (clockTime<nowDate){
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            builder = new NotificationCompat.Builder(this);
            notification = builder
                    .setContentTitle("提醒")
                    .setContentText("从 "+data.get(1)+" 开往 "+data.get(2)+" 的列车 "+data.get(0)+" 还有"+hours+"小时就要出发了")
                    .setTicker("提前"+hours+"小时火车提醒")
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .build();
            manager.notify(Integer.valueOf(data.get(5)), notification);


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("AAAAAAAAA", "Stop");
    }


}
