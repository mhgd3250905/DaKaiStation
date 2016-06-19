package fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import service.ClockService;
import skkk.admin.com.dakai_station.R;
import view.MySettingItemView;


public class SettingFragment extends Fragment {
    MySettingItemView mySettingItemView;
    SharedPreferences mPref;
    private boolean needClock;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        mPref=getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        mPref.edit().putBoolean("fragment_id", false).commit();

        mySettingItemView= (MySettingItemView) view.findViewById(R.id.msiv_setting_clock);
        mySettingItemView.setTitle("是否开启火车提醒");

        needClock = mPref.getBoolean("need_clock",true);
        if (needClock){
            mySettingItemView.setChecked(true);
        }else{
            mySettingItemView.setChecked(false);
        }

        mySettingItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mySettingItemView.isChecked()) {
                    mySettingItemView.setChecked(false);
                    mPref.edit().putBoolean("need_clock",false).commit();

                    getActivity().stopService(new Intent(getActivity(), ClockService.class));

                } else {
                    mySettingItemView.setChecked(true);
                    mPref.edit().putBoolean("need_clock",true).commit();
                    Toast.makeText(getActivity(), "提醒服务已开启", Toast.LENGTH_SHORT).show();
                    getActivity().startService(new Intent(getActivity(), ClockService.class));
                }
            }
        });

        return view;
    }
}
