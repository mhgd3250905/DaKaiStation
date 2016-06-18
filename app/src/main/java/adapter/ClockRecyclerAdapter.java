package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import skkk.admin.com.dakai_station.R;

/**
 * Created by admin on 2016/6/17.
 */
/*
* 
* 描    述：提醒页面的adapter
* 作    者：ksheng
* 时    间：6/17
*/
public class ClockRecyclerAdapter extends RecyclerView.Adapter<ClockRecyclerAdapter.MyViewHolder> {
    private List<List<String>> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public ClockRecyclerAdapter(Context mContext, List<List<String>> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        inflater=LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_clock,parent, false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvClockTrainName.setText(mDatas.get(position).get(0));
        holder.tvClockTime.setText(mDatas.get(position).get(1));
        holder.tvClockStart.setText(mDatas.get(position).get(2));
        holder.tvClockEnd.setText(mDatas.get(position).get(3));
        holder.tvClockStartTime.setText(mDatas.get(position).get(4));
        holder.tvClockEndTime.setText(mDatas.get(position).get(5));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

/*
* @desc 本适配器需要的viewholder
* @时间 2016/6/17 23:30
*/
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvClockTrainName,tvClockTime,tvClockStart,tvClockEnd,
                tvClockStartTime,tvClockEndTime;

        public MyViewHolder(View view) {
            super(view);
            tvClockTrainName= (TextView) view.findViewById(R.id.tv_clock_train_name);
            tvClockTime= (TextView) view.findViewById(R.id.tv_clock_time);
            tvClockStart= (TextView) view.findViewById(R.id.tv_clock_start);
            tvClockEnd= (TextView) view.findViewById(R.id.tv_clock_end);
            tvClockStartTime= (TextView) view.findViewById(R.id.tv_clock_start_time);
            tvClockEndTime= (TextView) view.findViewById(R.id.tv_clock_end_time);
        }

    }
}
