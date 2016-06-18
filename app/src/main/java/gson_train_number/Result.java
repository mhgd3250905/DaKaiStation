package gson_train_number;

import java.util.List;

/**
 * Created by admin on 2016/6/11.
 */
/*
* 
* 描    述：gson result
* 作    者：ksheng
* 时    间：
*/
public class Result {

    public TrainInfo train_info;
    public List<Station> station_list;

    public List<Station> getStation_list() {
        return station_list;
    }

    public void setStation_list(List<Station> station_list) {
        this.station_list = station_list;
    }

    public TrainInfo getTrain_info() {
        return train_info;
    }

    public void setTrain_info(TrainInfo train_info) {
        this.train_info = train_info;
    }
}
