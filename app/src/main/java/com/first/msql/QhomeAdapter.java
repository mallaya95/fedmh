package com.first.msql;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.first.msql.R.color.green;

public class QhomeAdapter extends RecyclerView.Adapter<QhomeAdapter.ViewHolder> {
    List<testset> data2;
    List<Qset>data1;
    Context context;
    String I;

    public QhomeAdapter(Context context,List<testset> data2) {
        this.context=context;
        this.data2=data2;
        Log.d("COUNT1" , "" + this.data2.size());
    }



    @NonNull
    @Override
    public QhomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.test,parent,false);
        ViewHolder pv=new ViewHolder(v);
        return pv;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

//        final testset item=data2.get(position);


//        for (int i=1;i<=data2.size();i++) {
//
//
//            if (data2.get(position).status.toString() == "yes") {

                holder.t1.setText(data2.get(position).testset);
                holder.t2.setText(data2.get(position).date);
                holder.t3.setText(data2.get(position).status);
                holder.t4.setText(data2.get(position).date1);
              //  holder.ans=item.status;

//        Log.d("status:",data2.get(position).status);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                if (holder.t3.getText().toString().equals("no") )
                {
                    Toast.makeText(activity,"sorry",Toast.LENGTH_SHORT).show();
                }
                else {
                    I = String.valueOf(data2.get(position).tid);
                    Fragment myFragment = new Questions(I, data2.get(position).testset);

                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, myFragment).addToBackStack(null).commit();
                }
            }


        });

    }

    @Override
    public int getItemCount() {
        return data2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3,t4;
        CardView cv;
        Button b1;
        //String ans="yes";
        public ViewHolder(View itemView) {
            super(itemView);

            t1=(TextView)itemView.findViewById(R.id.ttitle);
            t2=(TextView)itemView.findViewById(R.id.date);
            t4=(TextView)itemView.findViewById(R.id.date1);
            t3=(TextView)itemView.findViewById(R.id.status);
            cv=(CardView)itemView.findViewById(R.id.cvt);
            b1=(Button)itemView.findViewById(R.id.attend);
            b1.setTransformationMethod(null);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),t3.getText().toString(),Toast.LENGTH_LONG).show();
                }
            });

            for(int k = 0; k < data2.size();k++)
            {

                testset t = data2.get(k);
                if(t.getStatus().equals("no"))
                {
                    b1.setBackgroundColor(Color.RED);
                }
                else if (t.getStatus().equals("yes") )
                {
                    b1.setBackgroundColor(Color.GREEN);
                }
            }



//                if(t3.getText().toString()== "yes")
//                {
//                    b1.setBackgroundColor(Color.GREEN);
//                }
//                else if(t3.getText().toString()== "no")
//                {
//                    b1.setBackgroundColor(Color.RED);
//                }





        }
    }
}
