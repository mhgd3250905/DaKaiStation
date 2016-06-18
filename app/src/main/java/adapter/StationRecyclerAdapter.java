package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gson_station.StationBean;
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
public class StationRecyclerAdapter extends RecyclerView.Adapter<StationRecyclerAdapter.MyViewHolder>{

    private List<StationBean> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public StationRecyclerAdapter(Context mContext, List<StationBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        inflater=LayoutInflater.from(mContext);
    }


    /*
* @desc 设置点击事件
* @时间 2016/6/18 15:53
*/
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_station_to_station,parent, false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //将始发站与终点站之间的多余显示去掉
        holder.tvStationTrainNumber.setText(mDatas.get(position).getTrainOpp());
        holder.tvStationArrivedTime.setText(mDatas.get(position).getArrived_time());
        holder.tvStationLeaveTime.setText(mDatas.get(position).getLeave_time());
        holder.tvStationStart.setText(mDatas.get(position).getStart_staion());
        holder.tvStationEnd.setText(mDatas.get(position).getEnd_station());
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvStationTrainNumber,tvStationArrivedTime,
                tvStationLeaveTime,tvStationStart,tvStationEnd;

        public MyViewHolder(View view) {
            super(view);
            tvStationTrainNumber=(TextView) view.findViewById(R.id.tv_station_train_number);
            tvStationArrivedTime=(TextView) view.findViewById(R.id.tv_station_arrived_time);
            tvStationLeaveTime= (TextView) view.findViewById(R.id.tv_station_leave_time);
            tvStationStart=(TextView) view.findViewById(R.id.tv_station_start_name);
            tvStationEnd=(TextView) view.findViewById(R.id.tv_station_end_name);
        }


    }
}
