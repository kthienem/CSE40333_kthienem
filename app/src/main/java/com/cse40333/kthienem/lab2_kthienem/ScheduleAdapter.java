package com.cse40333.kthienem.lab2_kthienem;

import android.content.Context;
import android.support.annotation.NonNull;
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

public class ScheduleAdapter extends ArrayAdapter<Game> {

    ScheduleAdapter (Context context, ArrayList<Game> schedule) {
        super(context, R.layout.schedule_item, schedule);
    }

    @Override
    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater scheduleInflater = LayoutInflater.from(getContext());
        View scheduleView = scheduleInflater.inflate(R.layout.schedule_item, parent, false);

        Game matchItem = getItem(position);
        TextView teamName = (TextView) scheduleView.findViewById(R.id.teamName);

        TextView gameDate = (TextView) scheduleView.findViewById(R.id.gameDate);
        gameDate.setText(matchItem.getDate());

        ImageView teamLogo = (ImageView) scheduleView.findViewById(R.id.teamLogo);
        String mDrawableName = "leprechaun";

        if (matchItem.getType() == Game.gameType.HOME) {
            teamName.setText(matchItem.getVisitor().getTeamName());
            mDrawableName = matchItem.getVisitor().getTeamLogo();
        } else if (matchItem.getType() == Game.gameType.AWAY) {
            teamName.setText(matchItem.getHome().getTeamName());
            mDrawableName = matchItem.getHome().getTeamLogo();
        }

        int resID = getContext().getResources().getIdentifier(mDrawableName, "drawable", getContext().getPackageName());
        teamLogo.setImageResource(resID);

        return scheduleView;
    }
}
