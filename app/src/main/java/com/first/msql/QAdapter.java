package com.first.msql;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QAdapter extends RecyclerView.Adapter<QAdapter.ViewHolder>  {
    Context context;
    LayoutInflater inflter;

    String I;
    List<Qset> data2;
    ArrayList<String> ar = new ArrayList<String>();
    public static ArrayList<String> selectedAnswers;
    public QAdapter(FragmentActivity activity, List<Qset> data2)

    {
        this.context=activity;
        this.data2=data2;
//        for (int i = 0; i < data2.size(); i++) {
//            selectedAnswers.add("Not Attempted");
//        }
//        inflter = (LayoutInflater.from(activity));
    }



    @NonNull
    @Override
    public QAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.qset,parent,false);
        ViewHolder pv=new ViewHolder(v);
        return pv;
    }

    @Override
    public void onBindViewHolder(@NonNull QAdapter.ViewHolder holder, final int position) {

        final Qset item=data2.get(position);
        holder.t2.setText(item.questions);
        holder.qid = item.qid;
       // ar.add(item.questions);

    }


    @Override
    public int getItemCount() {
        return data2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView t2;
        String qid;
        CardView cardView;
        RadioGroup radioGroup;
        @SuppressLint("ResourceAsColor")
        public ViewHolder(final View itemView) {
            super(itemView);

            t2=(TextView)itemView.findViewById(R.id.qtitle);
            cardView=(CardView)itemView.findViewById(R.id.cvp1);
//            cardView.setCardBackgroundColor(green);
            radioGroup = (RadioGroup) itemView.findViewById(R.id.q_radio_group);

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    final RadioButton radioButton1 = (RadioButton) radioGroup.findViewById(R.id.yes_radio_btn);
                    final RadioButton radioButton2 = (RadioButton) radioGroup.findViewById(R.id.no_radio_btn);
                    if(radioButton1.isChecked())
                    {
                        for(int k = 0; k < data2.size();k++)
                        {
                            Qset t = data2.get(k);
                            if(t.qid.equals(qid))
                            {
                                t.status = 1;
                            }
                        }
                    }
                    else if(radioButton2.isChecked())
                    {
                        for(int k = 0; k < data2.size();k++)
                        {
                            Qset t = data2.get(k);
                            if(t.qid.equals(qid))
                            {
//                                Toast.makeText(itemView.getContext(),qid + "-" + radioButton2.getText().toString() , Toast.LENGTH_LONG).show();
                                t.status = 2;
                            }
                        }
//

                    }
                }
            });

        }
    }
}
