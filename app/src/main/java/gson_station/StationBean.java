package gson_station;

/**
 * Created by admin on 2016/6/13.
 */
/*
* 
* 描    述：站点信息 这里是站对站
* 作    者：ksheng
* 时    间：
*/
public class StationBean {
    public  String trainOpp;
    public  String train_typename;
    public  String start_staion;
    public  String end_station;
    public  String leave_time;
    public  String arrived_time;
    public  String mileage;

    public String getArrived_time() {
        return arrived_time;
    }

    public void setArrived_time(String arrived_time) {
        this.arrived_time = arrived_time;
    }

    public String getEnd_station() {
        return end_station;
    }

    public void setEnd_station(String end_station) {
        this.end_station = end_station;
    }

    public String getLeave_time() {
        return leave_time;
    }

    public void setLeave_time(String leave_time) {
        this.leave_time = leave_time;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getStart_staion() {
        return start_staion;
    }

    public void setStart_staion(String start_staion) {
        this.start_staion = start_staion;
    }

    public String getTrain_typename() {
        return train_typename;
    }

    public void setTrain_typename(String train_typename) {
        this.train_typename = train_typename;
    }

    public String getTrainOpp() {
        return trainOpp;
    }

    public void setTrainOpp(String trainOpp) {
        this.trainOpp = trainOpp;
    }
}
