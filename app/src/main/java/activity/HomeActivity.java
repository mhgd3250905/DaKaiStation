package activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.Timer;
import java.util.TimerTask;

import fragment.ClockFragment;
import fragment.SelectFragment;
import fragment.SettingFragment;
import service.ClockService;
import skkk.admin.com.dakai_station.R;

/*
*
* @作者 admin
* @时间 2016/6/11 11:57
*
* 主页面
*/
public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @ViewInject(R.id.tv_title)
    TextView tvTitle;
    @ViewInject(R.id.iv_title)
    ImageView ivTitle;

    private static String testUrl = "{\"resultcode\":\"200\",\"reason\":\"Successed!\",\"result\":{\"train_info\":{\"name\":\"G678\\/G675\",\"start\":\"武汉\",\"end\":\"上海虹桥\",\"starttime\":\"13:35\",\"endtime\":\"18:26\",\"mileage\":\"807km\"},\"station_list\":[{\"train_id\":\"1\",\"station_name\":\"武汉\",\"arrived_time\":\"-\",\"leave_time\":\"13:35\",\"mileage\":\"-\",\"fsoftSeat\":\"-\",\"ssoftSeat\":\"-\",\"hardSead\":\"-\",\"softSeat\":\"-\",\"hardSleep\":\"-\",\"softSleep\":\"-\",\"wuzuo\":\"-\",\"swz\":\"-\",\"tdz\":\"-\",\"gjrw\":\"-\",\"stay\":\"-\"},{\"train_id\":\"2\",\"station_name\":\"麻城北\",\"arrived_time\":\"14:18\",\"leave_time\":\"14:20\",\"mileage\":\"107km\",\"fsoftSeat\":\"37.5\",\"ssoftSeat\":\"31.5\",\"hardSead\":\"-\",\"softSeat\":\"-\",\"hardSleep\":\"-\",\"softSleep\":\"-\",\"wuzuo\":\"31.5\",\"swz\":\"96.0\",\"tdz\":\"-\",\"gjrw\":\"-\",\"stay\":\"2\"},{\"train_id\":\"3\",\"station_name\":\"合肥南\",\"arrived_time\":\"15:40\",\"leave_time\":\"15:47\",\"mileage\":\"355km\",\"fsoftSeat\":\"125.5\",\"ssoftSeat\":\"104.5\",\"hardSead\":\"-\",\"softSeat\":\"-\",\"hardSleep\":\"-\",\"softSleep\":\"-\",\"wuzuo\":\"104.5\",\"swz\":\"314.0\",\"tdz\":\"-\",\"gjrw\":\"-\",\"stay\":\"7\"},{\"train_id\":\"4\",\"station_name\":\"南京南\",\"arrived_time\":\"16:43\",\"leave_time\":\"16:45\",\"mileage\":\"512km\",\"fsoftSeat\":\"197.5\",\"ssoftSeat\":\"165.5\",\"hardSead\":\"-\",\"softSeat\":\"-\",\"hardSleep\":\"-\",\"softSleep\":\"-\",\"wuzuo\":\"165.5\",\"swz\":\"494.0\",\"tdz\":\"-\",\"gjrw\":\"-\",\"stay\":\"2\"},{\"train_id\":\"5\",\"station_name\":\"镇江南\",\"arrived_time\":\"17:06\",\"leave_time\":\"17:12\",\"mileage\":\"577km\",\"fsoftSeat\":\"248.0\",\"ssoftSeat\":\"195.5\",\"hardSead\":\"-\",\"softSeat\":\"-\",\"hardSleep\":\"-\",\"softSleep\":\"-\",\"wuzuo\":\"195.5\",\"swz\":\"588.0\",\"tdz\":\"-\",\"gjrw\":\"-\",\"stay\":\"6\"},{\"train_id\":\"6\",\"station_name\":\"丹阳北\",\"arrived_time\":\"17:22\",\"leave_time\":\"17:24\",\"mileage\":\"609km\",\"fsoftSeat\":\"272.5\",\"ssoftSeat\":\"210.0\",\"hardSead\":\"-\",\"softSeat\":\"-\",\"hardSleep\":\"-\",\"softSleep\":\"-\",\"wuzuo\":\"210.0\",\"swz\":\"634.5\",\"tdz\":\"-\",\"gjrw\":\"-\",\"stay\":\"2\"},{\"train_id\":\"7\",\"station_name\":\"无锡东\",\"arrived_time\":\"17:48\",\"leave_time\":\"17:50\",\"mileage\":\"699km\",\"fsoftSeat\":\"342.0\",\"ssoftSeat\":\"251.5\",\"hardSead\":\"-\",\"softSeat\":\"-\",\"hardSleep\":\"-\",\"softSleep\":\"-\",\"wuzuo\":\"251.5\",\"swz\":\"765.0\",\"tdz\":\"-\",\"gjrw\":\"-\",\"stay\":\"2\"},{\"train_id\":\"8\",\"station_name\":\"苏州北\",\"arrived_time\":\"18:00\",\"leave_time\":\"18:02\",\"mileage\":\"726km\",\"fsoftSeat\":\"363.0\",\"ssoftSeat\":\"264.0\",\"hardSead\":\"-\",\"softSeat\":\"-\",\"hardSleep\":\"-\",\"softSleep\":\"-\",\"wuzuo\":\"264.0\",\"swz\":\"804.5\",\"tdz\":\"-\",\"gjrw\":\"-\",\"stay\":\"2\"},{\"train_id\":\"9\",\"station_name\":\"上海虹桥\",\"arrived_time\":\"18:26\",\"leave_time\":\"18:26\",\"mileage\":\"807km\",\"fsoftSeat\":\"425.5\",\"ssoftSeat\":\"301.0\",\"hardSead\":\"-\",\"softSeat\":\"-\",\"hardSleep\":\"-\",\"softSleep\":\"-\",\"wuzuo\":\"301.0\",\"swz\":\"921.5\",\"tdz\":\"-\",\"gjrw\":\"-\",\"stay\":\"-\"}]},\"error_code\":0}";
    private SharedPreferences mPref;//万能的SP
    private SelectFragment mSelectFragment;//显示搜索内容的fragemnt
    private CollapsingToolbarLayout collapsingToolbar;

    private NotificationManager manager;
    private Notification notification;
    private NotificationCompat.Builder builder;
    private ClockFragment mClockFragment;
    private SettingFragment mSettingFragment;


    /*
    * @desc 初始化
    * @时间 2016/6/11 13:12
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPref = getSharedPreferences("config", MODE_PRIVATE);
        initUI();
        initData();
        setDefaultFragemnt();
        boolean needClock = mPref.getBoolean("need_clock", true);
        if (needClock){
            startService(new Intent(this, ClockService.class));
        }
    }





    /*
    * @desc 初始化数据
    * @时间 2016/6/11 13:12
    */
    private void initData() {
        mPref.edit().putString("all_data", testUrl).commit();
    }

    /*
    * @desc UI初始化
    * @时间 2016/6/11 13:04
    */
    private void initUI() {
        setContentView(R.layout.activity_home);
        ViewUtils.inject(this);
        //初始化toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //初始化悬浮按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectFragment = new SelectFragment();
                getFragmentManager().beginTransaction().replace(R.id.home_fragment,
                        mSelectFragment).commit();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //初始化侧滑菜单
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //使用CollapsingToolbarLayout后，title需要设置到CollapsingToolbarLayout上
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //通过CollapsingToolbarLayout修改字体颜色
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩之后的标题位置
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);//设置收缩之后的字体颜色
        collapsingToolbar.setExpandedTitleGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);//设置未收缩时候的标题位置
        collapsingToolbar.setTitle("开火车");


    }

    public void setCollapsingToolbarTitle(String s) {
        collapsingToolbar.setTitle(s);
    }

    private void setDefaultFragemnt() {
        SelectFragment mSelectFragment = new SelectFragment();
        getFragmentManager().beginTransaction().add(R.id.home_fragment, mSelectFragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(mPref.getBoolean("fragment_id",false)){
                exitBy2Click(); //调用双击退出函数
            }else{

                //大开搜索页面
                mSelectFragment = new SelectFragment();
                getFragmentManager().beginTransaction().replace(R.id.home_fragment,
                        mSelectFragment).commit();
            }
        }
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    /*
    * @desc 设置toolbar上 menu点击事件
    * @时间 2016/6/19 18:46
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            builder = new NotificationCompat.Builder(this);
            notification = builder
                    .setContentTitle("这是通知标题")
                    .setContentText("这是通知内容")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .build();
            manager.notify(1, notification);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    * @desc 设置菜单页面点击事件
    * @时间 2016/6/19 18:46
    */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            //大开搜索页面
            mSelectFragment = new SelectFragment();
            getFragmentManager().beginTransaction().replace(R.id.home_fragment,
                    mSelectFragment).commit();

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            //大开提醒页面
            mClockFragment = new ClockFragment();
            getFragmentManager().beginTransaction().replace(R.id.home_fragment,
                    mClockFragment).commit();

        } else if (id == R.id.nav_slideshow) {


        } else if (id == R.id.nav_manage) {
            //大开设置页面
            mSettingFragment = new SettingFragment();
            getFragmentManager().beginTransaction().replace(R.id.home_fragment,
                    mSettingFragment).commit();

        } else if (id == R.id.nav_share) {

            stopService(new Intent(this, ClockService.class));

        } else if (id == R.id.nav_send) {

            startService(new Intent(this, ClockService.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setTitle(String s) {
        tvTitle.setText(s);
    }
    public void setTitleImage(int image){
        ivTitle.setImageResource(image);
    }

}
