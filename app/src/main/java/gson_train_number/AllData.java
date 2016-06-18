package gson_train_number;

/**
 * Created by admin on 2016/6/11.
 */
/*
* 
* 描    述：gson 所有数据
* 作    者：ksheng
* 时    间：6/11
*/
public class AllData {
    public  String resultcode;
    public  String reason;
    public Result result;
    public  int error_code;

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
