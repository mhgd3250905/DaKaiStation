package ViewHolder;

import android.view.View;
import android.widget.TextView;

import skkk.admin.com.dakai_station.R;

/**
 * Created by admin on 2016/6/18.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：
*/
public class StationViewHolder extends MyBaseViewHolder {
    public TextView tvStationTrainNumber,tvStationArrivedTime,
            tvStationLeaveTime,tvStationStart,tvStationEnd;
    public StationViewHolder(View view) {
        super(view);
        tvStationTrainNumber=(TextView) view.findViewById(R.id.tv_station_train_number);
        tvStationArrivedTime=(TextView) view.findViewById(R.id.tv_station_arrived_time);
        tvStationLeaveTime= (TextView) view.findViewById(R.id.tv_station_leave_time);
        tvStationStart=(TextView) view.findViewById(R.id.tv_station_start_name);
        tvStationEnd=(TextView) view.findViewById(R.id.tv_station_end_name);
    }
}
