package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import Utils.StreamUtils;
import skkk.admin.com.dakai_station.R;

/**
 * Created by admin on 2016/6/11.
 */
/*
*
* 描    述：闪屏页面
* 作    者：ksheng
* 时    间：12:03
* 版    权：
*/

public class SplashActivity extends AppCompatActivity{

    private String mUrl;//获取数据URL
    private SharedPreferences mPref;//SP
    private String result;//获取之数据
    private long start;//加载数据开始时间
    private long end;//加载数据结束时间
    private long duration;//加载数据持续时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setContentView(R.layout.layout_splash);
        copyDB("station.db");
        selectData();
    }




    /*
    * @desc 在闪屏页面查找数据完毕之后跳转
    * @时间 2016/6/11 12:15
    */
    private void selectData() {

        //获取数据开始时间
        start = System.currentTimeMillis();
        mUrl="http://apis.juhe.cn/train/s?name=g4&key=47efc9fc09ca5666d6bfc4a81b580e89";

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //77ea626ec1eb28e6a026f511f0830c4a
                    URL url = new URL(mUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");//设置请求方法
                    conn.setConnectTimeout(5000);//设置连接超时
                    conn.setReadTimeout(5000);//设置响应超时，连接上了但是服务器迟迟不给响应
                    conn.connect();//连接服务器
                    int responseCode = conn.getResponseCode();//获取响应码
                    if (responseCode == 200) {
                        InputStream inputStream = conn.getInputStream();
                        result = StreamUtils.readFormStream(inputStream);
                        Log.d("AAAAAAAAAAAAAAAAAAAAAAA", result);
                        //将获取到的数据保存在SP中
                        mPref = getSharedPreferences("config", MODE_PRIVATE);
                        mPref.edit().putString("all_data",result).commit();

                    }
                } catch (MalformedURLException e) {
                    //URL错误异常
                    e.printStackTrace();
                } catch (IOException e) {
                    //网络错误异常
                    e.printStackTrace();
                }
            }
        }).start();

        //获取数据结束时间
        end = System.currentTimeMillis();
        //获取数据加载时间，如果大于2秒就直接跳转，否则将等待至两秒之后再跳转
        duration = end - start;

        if (duration<2000){
            SystemClock.sleep(2000 - duration);
        }
        startActivity(new Intent(this,HomeActivity.class));
    }

    /*
    * @desc 拷贝数据库
    * @时间 2016/6/11 12:14
    */
    private void copyDB(String dbName) {

        File destFile = new File(getFilesDir(), dbName);//要拷贝的目标地址

        if (destFile.exists()) {
            Toast.makeText(SplashActivity.this, "数据库已经存在", Toast.LENGTH_SHORT).show();
            return;
        }
        FileOutputStream out = null;
        InputStream in = null;
        try {
            in = getAssets().open(dbName);
            out = new FileOutputStream(destFile);

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
