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

import com.example.itp4501_gameingx.data_model.Ranking;

import java.util.ArrayList;

public class LeaderboardAdapter extends BaseAdapter {

    private final Context context;
    ArrayList<Ranking> list;
    public  LeaderboardAdapter(Context context, ArrayList<Ranking> list){
        this.context = context;
        this.list = list;
        if (list == null){
            Log.d("TAG list : ", "LeaderboardAdapter: " +list.toString());
            setList();
        }
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Ranking getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void setList(){
        FetchingData fData = new FetchingData("https://ranking-mobileasignment-wlicpnigvf.cn-hongkong.fcapp.run");
        try{
            fData.sendRequest();

            list = fData.resultToList();
        }catch (Exception e){

            return;
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            // If there's no reusable view, it inflates a new one from the layout
            // file leaderboard_item.xml.

            //It creates a ViewHolder object to store references to
            // the views within the inflated layout (Name, Correct, Time)
            convertView = LayoutInflater.from(context).inflate(R.layout.leaderboard_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.Name = convertView.findViewById(R.id.leader_item_name_tv);
            viewHolder.Correct = convertView.findViewById(R.id.leader_item_correct_tv);
            viewHolder.Time = convertView.findViewById(R.id.leader_item_time_tv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Ranking temp = list.get(position);
        if (temp!=null){
            LinearLayout linearLayout = convertView.findViewById(R.id.lnlLeaderItem);
            TextView tvName = convertView.findViewById(R.id.leader_item_name_tv);
            tvName.setText(""+temp.Name);
            TextView tvCorrect = convertView.findViewById(R.id.leader_item_correct_tv);
            tvCorrect.setText(""+temp.Correct);
            TextView tvTime = convertView.findViewById(R.id.leader_item_time_tv);
            tvTime.setText(""+temp.Time + " s");


            // Checks if the current position is even.
            //It sets the background of the LinearLayout to
            // a color resource (tranBlue) for even-positioned items,
            if (position%2 == 0){
                linearLayout.setBackground(ResourcesCompat.getDrawable(convertView.getResources(),R.color.tranBlue,null));
            }


        }



        return convertView;
    }
}
class ViewHolder{
    TextView Name;
    TextView Correct;
    TextView Time;
}