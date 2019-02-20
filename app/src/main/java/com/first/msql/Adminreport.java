package com.first.msql;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Adminreport.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Adminreport#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Adminreport extends Fragment {
    List<allreportdata> data1;
    Button b1,b2;
    String s1;
    String[] questions;
    int logFlag = 0;
    RecyclerView rv;
    SharedPreferences sharedpreferences;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Adminreport() {
        // Required empty public constructor
    }




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Adminreport.
     */
    // TODO: Rename and change types and number of parameters
    public static Adminreport newInstance(String param1, String param2) {
        Adminreport fragment = new Adminreport();
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
        ((Adminhomepage) getActivity())
                .setActionBarTitle("Report");
        sharedpreferences = this.getActivity().getSharedPreferences("cuser", Context.MODE_PRIVATE);

        String v = null ;
        s1=sharedpreferences.getString("useri",v);
        execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_adminreport, container, false);
        rv=(RecyclerView)view.findViewById(R.id.arvr);
        rv.setHasFixedSize(true);
        LinearLayoutManager lm1=new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(lm1);


        return view;
    }
    public  class Allreportadapter extends RecyclerView.Adapter<Allreportadapter.Viewholder>{
        List<allreportdata>data1;
        Context c;

        public Allreportadapter(Context context, List<allreportdata> data1) {
            this.c=context;
            this.data1=data1;
        }

        @NonNull
        @Override
        public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.adreport,parent,false);
            Viewholder pv=new Viewholder(v);

            return pv;
        }

        @Override
        public void onBindViewHolder(@NonNull Viewholder holder, int position) {
            holder.t1.setText(data1.get(position).name);
            holder.t2.setText(data1.get(position).report);

        }

        @Override
        public int getItemCount() {
            return data1.size();
        }

        public class Viewholder extends RecyclerView.ViewHolder {
            TextView t1,t2;
            public Viewholder(View itemView) {
                super(itemView);
                t1=(TextView)itemView.findViewById(R.id.arname);
                t2=(TextView)itemView.findViewById(R.id.ardata);

            }
        }
    }
    public  class allreportdata{
        String name;
        String report;

        public void setName(String name) {
            this.name = name;
        }

        public void setReport(String report) {
            this.report = report;
        }

        public String getName() {
            return name;
        }

        public String getReport() {
            return report;
        }
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
    private void execute() {

        class backadminReport extends AsyncTask {

            @Override
            protected Object doInBackground(Object[] objects) {

                String result = "";

                String conne = "http://192.168.22.89/main/mobdata/reportdata.php";

                try {
                    URL url = new URL(conne);
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();

                    http.setRequestMethod("POST");
                    http.setDoInput(true);
                    http.setDoOutput(true);


                    InputStream ips = http.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
//                        Log.d("get vlaues\n", result);

                        result += line;

                    }

                    reader.close();
                    ips.close();
                    result.trim();

                    if (result.length() > 2) {
                        logFlag = 1;
                        JSONArray arr = new JSONArray(result);
                        data1 = new ArrayList<>();
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = arr.getJSONObject(i);
                            allreportdata p = new allreportdata();
                            p.name=obj.getString("name");
                            p.report = obj.getString("report");

                            Log.d("datain name",obj.getString("name"));
                            Log.d("datain report",obj.getString("report"));
                            data1.add(p);

                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Allreportadapter ar=new Allreportadapter(getContext(),data1);
                                rv.setAdapter(ar);

                            }
                        });
                    }

                    http.disconnect();
                    return result;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                Log.d("last vlaues\n", result);
                return result;
            }

        }
        backadminReport bg=new backadminReport();
        bg.execute();
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
