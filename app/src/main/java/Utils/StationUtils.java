package Utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/6/13.
 */
/*
* 
* 描    述：获取有关所有火车站的信息的工具类
* 作    者：ksheng
* 时    间：6/13
*/
public class StationUtils {
    private static final String PATH =
            "data/data/skkk.admin.com.dakai_station/files/station.db";
    //注意该路径必须是这个目录data/data目录的文件，否则数据路访问不到

    /*
    * @desc 获取所有的火车站点
    * @时间 2016/6/13 18:32
    */
    public List<String> getAllStation(){
        ArrayList<String> listStation = new ArrayList<>();
        SQLiteDatabase db= SQLiteDatabase.openDatabase(PATH,
                null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select name from city", null);
        while (cursor.moveToNext()){
            listStation.add(cursor.getString(0));
        }
        db.close();
        cursor.close();
        return listStation;
    }
}
