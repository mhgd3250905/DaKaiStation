package gson_station;

import java.util.List;

/**
 * Created by admin on 2016/6/13.
 */
/*
* 
* 描    述：result类
* 作    者：ksheng
* 时    间：
*/
public class Result {
    public  int totalcount;

    public List<StationBean> data;


    public List<StationBean> getData() {
        return data;
    }

    public void setData(List<StationBean> data) {
        this.data = data;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }
}
