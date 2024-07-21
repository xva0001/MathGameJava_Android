package com.example.itp4501_gameingx;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.itp4501_gameingx.data_model.PlayRecord;
import com.example.itp4501_gameingx.data_model.Ranking;

import java.util.ArrayList;

public class RecordAdapter extends BaseAdapter {

    private final Context context;
    ArrayList<PlayRecord> list;
    public RecordAdapter(Context context, ArrayList<PlayRecord> list){
        this.context = context;
        this.list = list;
        if (list == null){
            Log.d("TAG list : ", "LeaderboardAdapter: " +"null");
            //setList();
        }
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public PlayRecord getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecordViewHolder viewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.leaderboard_item,parent,false);
            viewHolder = new RecordViewHolder();
            viewHolder.Name = convertView.findViewById(R.id.leader_item_name_tv);
            viewHolder.Correct = convertView.findViewById(R.id.leader_item_correct_tv);
            viewHolder.Time = convertView.findViewById(R.id.leader_item_time_tv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (RecordViewHolder) convertView.getTag();
        }

        PlayRecord temp = list.get(position);
        if (temp!=null){
            LinearLayout linearLayout = convertView.findViewById(R.id.lnlLeaderItem);
            TextView tvName = convertView.findViewById(R.id.leader_item_name_tv);
            tvName.setText(""+temp.getDateTime());
            TextView tvCorrect = convertView.findViewById(R.id.leader_item_correct_tv);
            tvCorrect.setText(""+temp.correctCount);
            TextView tvTime = convertView.findViewById(R.id.leader_item_time_tv);
            tvTime.setText(""+temp.getDuration() + "");

            if (position%2 == 0){
                linearLayout.setBackground(ResourcesCompat.getDrawable(convertView.getResources(),R.color.tranBlue,null));
            }


        }
        return convertView;
    }
}
class RecordViewHolder{
    TextView Name;
    TextView Correct;
    TextView Time;
}