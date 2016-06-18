package gson_station;

/**
 * Created by admin on 2016/6/13.
 */
/*
* 
* 描    述：站对站所有信息
* 作    者：ksheng
* 时    间：
*/
public class StationToStationData {
    public String resultcode;
    public String reason;
    public Result result;
    public int error_code;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }
}
