package com.first.msql;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QlistAdapter extends BaseAdapter {
    Context context;
    List<Qset> data1;
    LayoutInflater inflter;
    public static ArrayList<String> selectedAnswers;


    public QlistAdapter(FragmentActivity activity, List<Qset> data1) {
        this.context=activity;
        this.data1=data1;
        selectedAnswers = new ArrayList<>();
        for (int i = 0; i < data1.size(); i++) {
            selectedAnswers.add("Not Attempted");
        }
        inflter = (LayoutInflater.from(activity));

    }

    @Override
    public int getCount() {
        return data1.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

            view = inflter.inflate(R.layout.qset, null);

            TextView question = (TextView) view.findViewById(R.id.qtitle);
            RadioButton yes = (RadioButton) view.findViewById(R.id.yes_radio_btn);
            RadioButton no = (RadioButton) view.findViewById(R.id.no_radio_btn);


            yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked)
                        // Integer.parseInt(data1.get(i).qid);
                        selectedAnswers.set(i, "Yes");
                }
            });

            no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked)
//                    Integer.parseInt(data1.get(i).qid);
                        selectedAnswers.set(i, "No");

                }
            });
// set the value in TextView

            question.setText(data1.get(i).questions);

            return view;

    }

}
