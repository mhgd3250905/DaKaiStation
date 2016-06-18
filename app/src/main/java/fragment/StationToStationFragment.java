package fragment;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import java.util.Calendar;
import java.util.List;

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
    private String result = "{\"resultcode\":\"200\",\"reason\":\"Successed!\",\"result\":{\"data\":[{\"trainOpp\":\"G104\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"07:10\",\"arrived_time\":\"07:33\",\"mileage\":\"81\"},{\"trainOpp\":\"G108\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"07:30\",\"arrived_time\":\"07:54\",\"mileage\":\"81\"},{\"trainOpp\":\"G110\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"08:05\",\"arrived_time\":\"08:28\",\"mileage\":\"81\"},{\"trainOpp\":\"G114\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"09:10\",\"arrived_time\":\"09:33\",\"mileage\":\"81\"},{\"trainOpp\":\"G118\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"10:05\",\"arrived_time\":\"10:28\",\"mileage\":\"81\"},{\"trainOpp\":\"G1204/G1201\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"09:39\",\"arrived_time\":\"10:02\",\"mileage\":\"81\"},{\"trainOpp\":\"G1214/G1211\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"10:22\",\"arrived_time\":\"10:55\",\"mileage\":\"81\"},{\"trainOpp\":\"G122\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"10:46\",\"arrived_time\":\"11:09\",\"mileage\":\"81\"},{\"trainOpp\":\"G1228/G1225\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"11:43\",\"arrived_time\":\"12:06\",\"mileage\":\"81\"},{\"trainOpp\":\"G1252/G1253\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"11:20\",\"arrived_time\":\"11:43\",\"mileage\":\"81\"},{\"trainOpp\":\"G126\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"11:10\",\"arrived_time\":\"11:33\",\"mileage\":\"81\"},{\"trainOpp\":\"G132\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"12:18\",\"arrived_time\":\"12:41\",\"mileage\":\"81\"},{\"trainOpp\":\"G138\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"13:29\",\"arrived_time\":\"13:52\",\"mileage\":\"81\"},{\"trainOpp\":\"G140\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"13:41\",\"arrived_time\":\"14:04\",\"mileage\":\"81\"},{\"trainOpp\":\"G142\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"14:21\",\"arrived_time\":\"14:44\",\"mileage\":\"50\"},{\"trainOpp\":\"G146\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"15:05\",\"arrived_time\":\"15:28\",\"mileage\":\"81\"},{\"trainOpp\":\"G148\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"15:19\",\"arrived_time\":\"15:42\",\"mileage\":\"81\"},{\"trainOpp\":\"G150\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"16:05\",\"arrived_time\":\"16:28\",\"mileage\":\"81\"},{\"trainOpp\":\"G154\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"17:14\",\"arrived_time\":\"17:37\",\"mileage\":\"81\"},{\"trainOpp\":\"G178\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"12:35\",\"arrived_time\":\"12:58\",\"mileage\":\"81\"},{\"trainOpp\":\"G214\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"14:54\",\"arrived_time\":\"15:17\",\"mileage\":\"81\"},{\"trainOpp\":\"G216\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"16:38\",\"arrived_time\":\"17:01\",\"mileage\":\"81\"},{\"trainOpp\":\"G220\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"10:33\",\"arrived_time\":\"11:02\",\"mileage\":\"81\"},{\"trainOpp\":\"G226/G227\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"14:05\",\"arrived_time\":\"14:28\",\"mileage\":\"81\"},{\"trainOpp\":\"G230/G231\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"09:15\",\"arrived_time\":\"09:38\",\"mileage\":\"81\"},{\"trainOpp\":\"G576/G577\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"08:35\",\"arrived_time\":\"08:58\",\"mileage\":\"81\"},{\"trainOpp\":\"G598/G599\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"07:15\",\"arrived_time\":\"07:38\",\"mileage\":\"81\"},{\"trainOpp\":\"G676/G677\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"08:40\",\"arrived_time\":\"09:03\",\"mileage\":\"81\"},{\"trainOpp\":\"G7002\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"07:00\",\"arrived_time\":\"07:25\",\"mileage\":\"84\"},{\"trainOpp\":\"G7004\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"08:00\",\"arrived_time\":\"08:25\",\"mileage\":\"84\"},{\"trainOpp\":\"G7006\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"09:00\",\"arrived_time\":\"09:25\",\"mileage\":\"84\"},{\"trainOpp\":\"G7008\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"10:00\",\"arrived_time\":\"10:25\",\"mileage\":\"84\"},{\"trainOpp\":\"G7010\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"11:00\",\"arrived_time\":\"11:25\",\"mileage\":\"84\"},{\"trainOpp\":\"G7012\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"12:00\",\"arrived_time\":\"12:25\",\"mileage\":\"84\"},{\"trainOpp\":\"G7014\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"13:00\",\"arrived_time\":\"13:25\",\"mileage\":\"84\"},{\"trainOpp\":\"G7016\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"14:00\",\"arrived_time\":\"14:25\",\"mileage\":\"84\"},{\"trainOpp\":\"G7018\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"15:00\",\"arrived_time\":\"15:25\",\"mileage\":\"84\"},{\"trainOpp\":\"G7020\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"16:00\",\"arrived_time\":\"16:25\",\"mileage\":\"84\"},{\"trainOpp\":\"G7022\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"17:00\",\"arrived_time\":\"17:25\",\"mileage\":\"84\"},{\"trainOpp\":\"G7024\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"18:00\",\"arrived_time\":\"18:25\",\"mileage\":\"84\"},{\"trainOpp\":\"G7026\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"19:00\",\"arrived_time\":\"19:25\",\"mileage\":\"84\"},{\"trainOpp\":\"G7028\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"20:00\",\"arrived_time\":\"20:25\",\"mileage\":\"84\"},{\"trainOpp\":\"G7030\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"21:00\",\"arrived_time\":\"21:32\",\"mileage\":\"84\"},{\"trainOpp\":\"G7034\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"06:18\",\"arrived_time\":\"06:50\",\"mileage\":\"84\"},{\"trainOpp\":\"G7036\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"07:23\",\"arrived_time\":\"07:59\",\"mileage\":\"84\"},{\"trainOpp\":\"G7038\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"07:45\",\"arrived_time\":\"08:17\",\"mileage\":\"84\"},{\"trainOpp\":\"G7040\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"08:22\",\"arrived_time\":\"08:54\",\"mileage\":\"84\"},{\"trainOpp\":\"G7042\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"08:32\",\"arrived_time\":\"09:11\",\"mileage\":\"84\"},{\"trainOpp\":\"G7044\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"09:12\",\"arrived_time\":\"09:44\",\"mileage\":\"84\"},{\"trainOpp\":\"G7046\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州园区\",\"leave_time\":\"09:50\",\"arrived_time\":\"10:18\",\"mileage\":\"74\"},{\"trainOpp\":\"G7048\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"10:26\",\"arrived_time\":\"10:58\",\"mileage\":\"84\"},{\"trainOpp\":\"G7050\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"10:46\",\"arrived_time\":\"11:18\",\"mileage\":\"84\"},{\"trainOpp\":\"G7052\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"11:33\",\"arrived_time\":\"12:05\",\"mileage\":\"84\"},{\"trainOpp\":\"G7054\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州园区\",\"leave_time\":\"12:19\",\"arrived_time\":\"12:43\",\"mileage\":\"74\"},{\"trainOpp\":\"G7056\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"12:41\",\"arrived_time\":\"13:13\",\"mileage\":\"84\"},{\"trainOpp\":\"G7058\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"13:33\",\"arrived_time\":\"14:10\",\"mileage\":\"84\"},{\"trainOpp\":\"G7060\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州园区\",\"leave_time\":\"14:32\",\"arrived_time\":\"15:03\",\"mileage\":\"74\"},{\"trainOpp\":\"G7062\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"15:38\",\"arrived_time\":\"16:11\",\"mileage\":\"84\"},{\"trainOpp\":\"G7064\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州园区\",\"leave_time\":\"15:47\",\"arrived_time\":\"16:18\",\"mileage\":\"74\"},{\"trainOpp\":\"G7066\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州园区\",\"leave_time\":\"16:20\",\"arrived_time\":\"16:51\",\"mileage\":\"74\"},{\"trainOpp\":\"G7068\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"16:41\",\"arrived_time\":\"17:19\",\"mileage\":\"84\"},{\"trainOpp\":\"G7070\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"17:09\",\"arrived_time\":\"17:42\",\"mileage\":\"84\"},{\"trainOpp\":\"G7072\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州园区\",\"leave_time\":\"17:28\",\"arrived_time\":\"18:02\",\"mileage\":\"74\"},{\"trainOpp\":\"G7074\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"18:11\",\"arrived_time\":\"18:51\",\"mileage\":\"84\"},{\"trainOpp\":\"G7076\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州园区\",\"leave_time\":\"18:40\",\"arrived_time\":\"19:11\",\"mileage\":\"74\"},{\"trainOpp\":\"G7078\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州园区\",\"leave_time\":\"19:12\",\"arrived_time\":\"19:45\",\"mileage\":\"74\"},{\"trainOpp\":\"G7080\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"19:32\",\"arrived_time\":\"20:04\",\"mileage\":\"84\"},{\"trainOpp\":\"G7082\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州园区\",\"leave_time\":\"19:47\",\"arrived_time\":\"20:18\",\"mileage\":\"74\"},{\"trainOpp\":\"G7084\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"20:26\",\"arrived_time\":\"20:59\",\"mileage\":\"84\"},{\"trainOpp\":\"G7086\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"20:53\",\"arrived_time\":\"21:25\",\"mileage\":\"84\"},{\"trainOpp\":\"G7092\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"21:45\",\"arrived_time\":\"22:17\",\"mileage\":\"84\"},{\"trainOpp\":\"G7122\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州\",\"leave_time\":\"07:15\",\"arrived_time\":\"07:47\",\"mileage\":\"84\"},{\"trainOpp\":\"G7124\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州园区\",\"leave_time\":\"08:54\",\"arrived_time\":\"09:18\",\"mileage\":\"74\"},{\"trainOpp\":\"G7126\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州\",\"leave_time\":\"10:11\",\"arrived_time\":\"10:43\",\"mileage\":\"84\"},{\"trainOpp\":\"G7128\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州\",\"leave_time\":\"11:05\",\"arrived_time\":\"11:37\",\"mileage\":\"84\"},{\"trainOpp\":\"G7130\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州\",\"leave_time\":\"11:39\",\"arrived_time\":\"12:11\",\"mileage\":\"84\"},{\"trainOpp\":\"G7132\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州\",\"leave_time\":\"12:48\",\"arrived_time\":\"13:20\",\"mileage\":\"84\"},{\"trainOpp\":\"G7134\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州\",\"leave_time\":\"13:12\",\"arrived_time\":\"13:43\",\"mileage\":\"84\"},{\"trainOpp\":\"G7136\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州\",\"leave_time\":\"14:07\",\"arrived_time\":\"14:39\",\"mileage\":\"84\"},{\"trainOpp\":\"G7138\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州\",\"leave_time\":\"15:05\",\"arrived_time\":\"15:37\",\"mileage\":\"84\"},{\"trainOpp\":\"G7140\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州\",\"leave_time\":\"16:14\",\"arrived_time\":\"16:46\",\"mileage\":\"84\"},{\"trainOpp\":\"G7142\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州\",\"leave_time\":\"17:18\",\"arrived_time\":\"17:50\",\"mileage\":\"84\"},{\"trainOpp\":\"G7144\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州\",\"leave_time\":\"18:09\",\"arrived_time\":\"18:41\",\"mileage\":\"84\"},{\"trainOpp\":\"G7146\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州\",\"leave_time\":\"19:03\",\"arrived_time\":\"19:35\",\"mileage\":\"84\"},{\"trainOpp\":\"G7148\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州\",\"leave_time\":\"20:36\",\"arrived_time\":\"21:08\",\"mileage\":\"84\"},{\"trainOpp\":\"G7150\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州\",\"leave_time\":\"21:10\",\"arrived_time\":\"21:42\",\"mileage\":\"84\"},{\"trainOpp\":\"G7186/G7187\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"07:54\",\"arrived_time\":\"08:17\",\"mileage\":\"81\"},{\"trainOpp\":\"G7198\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"19:40\",\"arrived_time\":\"20:10\",\"mileage\":\"81\"},{\"trainOpp\":\"G7202\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"08:08\",\"arrived_time\":\"08:46\",\"mileage\":\"84\"},{\"trainOpp\":\"G7204\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"09:21\",\"arrived_time\":\"09:53\",\"mileage\":\"84\"},{\"trainOpp\":\"G7206\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"12:53\",\"arrived_time\":\"13:30\",\"mileage\":\"84\"},{\"trainOpp\":\"G7208\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"14:19\",\"arrived_time\":\"14:51\",\"mileage\":\"84\"},{\"trainOpp\":\"G7210\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"18:54\",\"arrived_time\":\"19:29\",\"mileage\":\"84\"},{\"trainOpp\":\"G7214\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州园区\",\"leave_time\":\"08:27\",\"arrived_time\":\"08:58\",\"mileage\":\"74\"},{\"trainOpp\":\"G7216\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"17:23\",\"arrived_time\":\"17:55\",\"mileage\":\"84\"},{\"trainOpp\":\"G7218\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"21:50\",\"arrived_time\":\"22:22\",\"mileage\":\"84\"},{\"trainOpp\":\"G7220\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"22:20\",\"arrived_time\":\"22:52\",\"mileage\":\"84\"},{\"trainOpp\":\"G7232/G7233\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"09:35\",\"arrived_time\":\"10:07\",\"mileage\":\"84\"},{\"trainOpp\":\"G7236/G7237\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"10:40\",\"arrived_time\":\"11:05\",\"mileage\":\"84\"},{\"trainOpp\":\"G7240/G7241\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"11:18\",\"arrived_time\":\"11:44\",\"mileage\":\"84\"},{\"trainOpp\":\"G7244/G7245\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"12:11\",\"arrived_time\":\"12:36\",\"mileage\":\"84\"},{\"trainOpp\":\"G7248/G7249\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"13:26\",\"arrived_time\":\"13:51\",\"mileage\":\"84\"},{\"trainOpp\":\"G7252/G7253\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"15:30\",\"arrived_time\":\"16:02\",\"mileage\":\"84\"},{\"trainOpp\":\"G7256/G7257\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州\",\"leave_time\":\"17:42\",\"arrived_time\":\"18:14\",\"mileage\":\"84\"},{\"trainOpp\":\"G7292\",\"train_typename\":\"高铁\",\"start_staion\":\"上海\",\"end_station\":\"苏州北\",\"leave_time\":\"11:55\",\"arrived_time\":\"12:23\",\"mileage\":\"88\"},{\"trainOpp\":\"G7382\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州园区\",\"leave_time\":\"11:28\",\"arrived_time\":\"11:52\",\"mileage\":\"74\"},{\"trainOpp\":\"G7388\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州\",\"leave_time\":\"21:36\",\"arrived_time\":\"22:08\",\"mileage\":\"84\"},{\"trainOpp\":\"G7582\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州\",\"leave_time\":\"19:25\",\"arrived_time\":\"19:57\",\"mileage\":\"84\"},{\"trainOpp\":\"G7584\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州\",\"leave_time\":\"14:27\",\"arrived_time\":\"14:59\",\"mileage\":\"84\"},{\"trainOpp\":\"G7586\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州\",\"leave_time\":\"11:54\",\"arrived_time\":\"12:30\",\"mileage\":\"84\"},{\"trainOpp\":\"G7590\",\"train_typename\":\"高铁\",\"start_staion\":\"上海虹桥\",\"end_station\":\"苏州北\",\"leave_time\":\"19:04\",\"arrived_time\":\"19:34\",\"mileage\":\"81\"}],\"totalcount\":111},\"error_code\":0}";
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



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_station_to_station, container, false);
        //获取RecyclerView实例
        rvStationData = (RecyclerView) view.findViewById(R.id.rv_station_data);
        mData = getData();
        mySQLHelper = new MySQLHelper(getActivity(), "clock.db", null, 1);
        //设置Adapter
        stationRecyclerAdapter = new StationRecyclerAdapter(getActivity(), mData);

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

                                /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//小写的mm表示的是分钟
                                try {
                                    clocktime = sdf.parse(clocktimeString);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                diff = 24 * 60 * 60 * 1000;
                                resultTime = clocktime.getTime() - diff;*/

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
        return view;
    }


    public List<StationBean> getData() {
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<StationToStationData>() {
        }.getType();
        //获取网络解析出来的JSON数据

        stationData = gson.fromJson(result, type);

        int errorCode = stationData.getError_code();
        if (errorCode == 0) {
            data = stationData.getResult().getData();
            totalcount = stationData.getResult().getTotalcount();
            formTo = stationData.getResult().getData().get(0).getStart_staion() + " - "
                    + stationData.getResult().getData().get(0).getEnd_station();
        }

        return data;
    }

}
