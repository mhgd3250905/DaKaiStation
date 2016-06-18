package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gson_train_number.Station;
import skkk.admin.com.dakai_station.R;

/**
 * Created by admin on 2016/6/11.
 */
/*
* 
* 描    述：RecyclerAdapter
* 作    者：ksheng
* 时    间：
*/
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>{

    private List<Station> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public MyRecyclerAdapter(Context mContext, List<Station> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        inflater=LayoutInflater.from(mContext);
    }

    /*
    * @desc 界面view设置
    * @时间 2016/6/14 23:46
    */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_show_station,parent, false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }


    /*
    * @desc 数据绑定
    * @时间 2016/6/14 23:46
    */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //将始发站与终点站之间的多余显示去掉
        if (position==0){
            holder.tvArrivedTime.setText("始发站");
        }else{
            holder.tvArrivedTime.setText("抵达时间： "+mDatas.get(position).getArrived_time());
        }
        if (position==(mDatas.size()-1)){
            holder.tvLeaveTime.setText("终点站");
        }else{
            holder.tvLeaveTime.setText("离开时间： "+mDatas.get(position).getLeave_time());
        }
        if(position==0|position==(mDatas.size()-1)){
            holder.tvItemStay.setText("");
        }else{
            holder.tvItemStay.setText("停留时间： "+mDatas.get(position).getStay()+"min");
        }
        holder.tvItemName.setText(mDatas.get(position).getStation_name());

    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvArrivedTime,tvLeaveTime,tvItemName,tvItemStay;

        public MyViewHolder(View view) {
            super(view);
            tvArrivedTime=(TextView) view.findViewById(R.id.tv_arrived_time);
            tvLeaveTime=(TextView) view.findViewById(R.id.tv_leave_time);
            tvItemName=(TextView) view.findViewById(R.id.tv_name_item);
            tvItemStay=(TextView) view.findViewById(R.id.tv_stay_item);
        }

    }
}
