package com.first.msql;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Report.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Report#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Report extends Fragment {
    int logFlag = 0;
    String s;
    RecyclerView rv;
    TextView dtext;
    List<Reportdata>data;
    SharedPreferences sharedpreferences;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Report() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Report.
     */
    // TODO: Rename and change types and number of parameters
    public static Report newInstance(String param1, String param2) {
        Report fragment = new Report();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ((Homepage) getActivity())
                .setActionBarTitle("Report");
        sharedpreferences = this.getActivity().getSharedPreferences("cuser", Context.MODE_PRIVATE);

        String v = null ;
        s=sharedpreferences.getString("useri",v);
        execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_report, container, false);
       rv=(RecyclerView)view.findViewById(R.id.rvr);
        rv.setHasFixedSize(true);
        LinearLayoutManager lm1=new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(lm1);

        dtext=(TextView)view.findViewById(R.id.rdata1);









        return view;
    }
    public class Reportdata {
        public String name;
        public String rd;

        public void setRd(String rd) {
            this.rd = rd;
        }

        public String getRd() {
            return rd;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder>
    {
        List<Reportdata>d;
        Context c;
        public ReportAdapter(Context context, List<Reportdata> data) {
            this.d=data;
            this.c=context;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.report,parent,false);
            ViewHolder pv=new ViewHolder(v);
            return pv;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


            holder.t2.setText(d.get(position).rd);
            holder.a=d.get(position).name;

        }

        @Override
        public int getItemCount() {
            return d.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView t1,t2;
            String a;
            public ViewHolder(View itemView) {
                super(itemView);
                t1=(TextView)itemView.findViewById(R.id.rname);
                t2=(TextView)itemView.findViewById(R.id.rdata);


            }
        }
    }


    public void execute()
    {

    class backreport extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            String result="";

            String conne="http://192.168.22.89/main/mobdata/reportcheck.php";
            try {
                URL url = new URL(conne);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();

                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream outputStream=http.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data2= URLEncoder.encode("uid","UTF-8")+"="+URLEncoder.encode(s,"UTF-8");
                writer.write(data2);

                writer.flush();
                writer.close();
                outputStream.close();

                InputStream ips=http.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
                String line="";
                while ((line=reader.readLine())!=null){

                    result+=line;

                }

                reader.close();
                ips.close();
                result.trim();

                if(result.length() > 2) {
                    logFlag = 1;

                    JSONArray arr = new JSONArray(result);
                    data = new ArrayList<>();

                    for (int i = 0; i<arr.length(); i++)
                    {
                        JSONObject obj = arr.getJSONObject(i);
                        Reportdata p=new Reportdata();
                        p.rd=obj.getString("report");
                        p.name=obj.getString("rid");
                        data.add(p);



                    }
                    getActivity().runOnUiThread(new Runnable() {
                        //@Override
                        @SuppressLint("WrongConstant")
                        public void run()
                        {
                            ReportAdapter ra = new ReportAdapter(getContext(), data);
                            rv.setAdapter(ra);

                        }
                    });




                }
                else
                {
                    getActivity().runOnUiThread(new Runnable() {
                        //@Override
                        @SuppressLint("WrongConstant")
                        public void run()
                        {
                            dtext.setText("Sorry you Report is being prepared !! ");
                            dtext.setTextColor(Color.RED);

                        }
                    });
                }

                http.disconnect();
                return result;



            }


            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("last vlaues\n",result);
            return result;
        }

        protected void onPreExecute() {

        }

        protected void onPostExecute(String result) {
            Log.d("POST METHOD",result);

        }

    }
        backreport qd=new backreport();
        qd.execute();




     }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
//        else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
