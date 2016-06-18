package Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by admin on 2016/6/17.
 */
/*
* 
* 描    述：火车提醒保存数据数据库
* 作    者：ksheng
* 时    间：6/17
*/
public class MySQLHelper extends SQLiteOpenHelper {
    public static final String CREATE_CLOCK="create table Clock(id integer primary key autoincrement," +
            "train_id text," +
            "time text," +
            "start text," +
            "end text," +
            "from_time text," +
            "to_time text," +
            "item_1 text," +
            "item_2 text)";

    public MySQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLOCK);
        Log.d("AAAAAAAAAAAAAAAAAAAAAA","数据库创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
