package gson_train_number;

/**
 * Created by admin on 2016/6/11.
 */
/*
* 
* 描    述：当前车次信息
* 作    者：ksheng
* 时    间：
*/
public class TrainInfo {
    public  String name;
    public  String start;
    public  String end;
    public  String starttime;
    public  String endtime;
    public  String mileage;

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }
}
