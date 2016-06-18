package adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import Utils.MySQLHelper;
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
    private String dateString;//标准格式时间
    private Date dateTime;
    private MySQLHelper mySQLHelper;

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



    public ClockRecyclerAdapter(MySQLHelper mySQLHelper, Context mContext, List<List<String>> mDatas) {
        this.mySQLHelper = mySQLHelper;
        this.mContext = mContext;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_clock, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        /*//解析时间
        long timeLong = Long.parseLong(mDatas.get(position).get(1));
        dateTime = new Date(timeLong);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");//小写的mm表示的是分钟
        dateString = sdf.format(dateTime);*/

        holder.tvClockTrainName.setText(mDatas.get(position).get(0));
        holder.tvClockTime.setText("提醒发车日期："+mDatas.get(position).get(1));
        holder.tvClockStart.setText(mDatas.get(position).get(2));
        holder.tvClockEnd.setText(mDatas.get(position).get(3));
        holder.tvClockStartTime.setText(mDatas.get(position).get(4));
        holder.tvClockEndTime.setText(mDatas.get(position).get(5));

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


    //删除数据
    public void removeItem(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
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
        TextView tvClockTrainName, tvClockTime, tvClockStart, tvClockEnd,
                tvClockStartTime, tvClockEndTime;
        CardView cvClock;

        public MyViewHolder(View view) {
            super(view);
            tvClockTrainName = (TextView) view.findViewById(R.id.tv_clock_train_name);
            tvClockTime = (TextView) view.findViewById(R.id.tv_clock_time);
            tvClockStart = (TextView) view.findViewById(R.id.tv_clock_start);
            tvClockEnd = (TextView) view.findViewById(R.id.tv_clock_end);
            tvClockStartTime = (TextView) view.findViewById(R.id.tv_clock_start_time);
            tvClockEndTime = (TextView) view.findViewById(R.id.tv_clock_end_time);
            cvClock = (CardView) view.findViewById(R.id.cv_clock);
        }
    }
}
