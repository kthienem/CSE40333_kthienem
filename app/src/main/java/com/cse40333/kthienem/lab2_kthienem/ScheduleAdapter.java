package com.cse40333.kthienem.lab2_kthienem;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kris on 2/8/2017.
 */

public class ScheduleAdapter extends ArrayAdapter<String[]> {

    ScheduleAdapter (Context context, ArrayList<String[]> schedule) {
        super(context, R.layout.schedule_item, schedule);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        LayoutInflater scheduleInflater = LayoutInflater.from(getContext());
        View scheduleView = scheduleInflater.inflate(R.layout.schedule_item, parent, false);

        String[] matchItem = getItem(position);
        TextView teamName = (TextView) scheduleView.findViewById(R.id.teamName);
        teamName.setText(matchItem[0]);

        TextView gameDate = (TextView) scheduleView.findViewById(R.id.gameDate);
        gameDate.setText(matchItem[1]);

        ImageView teamLogo = (ImageView) scheduleView.findViewById(R.id.teamLogo);
        String mDrawableName = matchItem[2];
        int resID = getContext().getResources().getIdentifier(mDrawableName, "drawable", getContext().getPackageName());
        teamLogo.setImageResource(resID);

        return scheduleView;
    }
}
