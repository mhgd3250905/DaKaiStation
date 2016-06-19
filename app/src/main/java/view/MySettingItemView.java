package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import skkk.admin.com.dakai_station.R;

/**
 * Created by admin on 2016/6/19.
 */
/*
* 
* 描    述：自定义控件用勾选设置
* 作    者：ksheng
* 时    间：6/19
*/
public class MySettingItemView extends RelativeLayout {
    private TextView tvTitle;
    private TextView tvDesc;
    private CheckBox cbStatus;
    private final String NAMESPACE="http://schemas.android.com/apk/res-auto";
    private String mTitle,mDescOn,mDescOff;


    public MySettingItemView(Context context) {
        super(context);
        initView();
    }

    public MySettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //根据属性名称获取属性的值
        mTitle=attrs.getAttributeValue(NAMESPACE,"titleText");
        mDescOn=attrs.getAttributeValue(NAMESPACE,"desc_on");
        mDescOff=attrs.getAttributeValue(NAMESPACE,"desc_off");
        initView();
    }

    public MySettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /*
    * @desc 初始化布局
    * @时间 2016/6/19 18:00
    */
    private void initView(){
        //将定义好的布局文件设置给当前的SettingItemView
        View.inflate(getContext(), R.layout.view_setting_item, this);
        tvTitle= (TextView) findViewById(R.id.tv_title);
        tvDesc= (TextView) findViewById(R.id.tv_desc);
        cbStatus= (CheckBox) findViewById(R.id.cb_status);
    }
    //设置标题内容
    public void setTitle(String title){
        tvTitle.setText(title);
    }
    //设置描述内容
    public void setDesc(String desc){
        tvDesc.setText(desc);
    }

    //返回勾选状态
    public boolean isChecked(){
        return cbStatus.isChecked();
    }

    //设置勾选状态
    public void setChecked(boolean checked){
        cbStatus.setChecked(checked);
        //根据选择的状态更新文本描述
        if(checked){
            setDesc(mDescOn);
        }else{
            setDesc(mDescOff);
        }
    }




}
