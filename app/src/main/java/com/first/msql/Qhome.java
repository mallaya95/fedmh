package com.first.msql;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link Qhome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Qhome extends Fragment implements OnFragmentInteractionListener {
    RecyclerView rv;
    List<testset>data2;

    int s1[];
    String[] names = new String[10];
    int logFlag = 0;
    String s;
    SharedPreferences sharedpreferences;
    List<storeid>getqset;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;

    private OnFragmentInteractionListener mListener;

    public Qhome() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Qhome.
     */
    // TODO: Rename and change types and number of parameters
    public static Qhome newInstance(String param1, String param2) {
        Qhome fragment = new Qhome();
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
                .setActionBarTitle("Test");
        sharedpreferences = this.getActivity().getSharedPreferences("cuser", Context.MODE_PRIVATE);

        String v = null ;
        s=sharedpreferences.getString("useri",v);
       firstexecute();
        //execute();
    }

    private void firstexecute() {

        class quserdata extends AsyncTask{
            @SuppressLint("WrongThread")
            @Override
            protected Object doInBackground(Object[] objects) {
                String result2="";

                String conne="http://192.168.22.89/main/mobdata/userquestioncheck.php";
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

                        result2+=line;

                    }

                    reader.close();
                    ips.close();
                    result2.trim();

                    if(result2.length() > 2) {
                        logFlag = 1;
                        getqset=new ArrayList<>();
                        JSONArray arr = new JSONArray(result2);

                        for (int i = 0; i<arr.length(); i++)
                        {
                            JSONObject obj = arr.getJSONObject(i);
                            storeid st=new storeid();
                            st.qid=obj.getInt("qid");
                            st.ustatus=obj.getString("ust");
                            getqset.add(st);
                            backqset bg=new backqset(String.valueOf(st.qid),st.ustatus);
                            bg.execute();

                        }


                    }


                    http.disconnect();
                    return result2;



                }


                catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("q vlaues\n",result2);
                return result2;
            }

            protected void onPreExecute() {

            }

            protected void onPostExecute(String result) {
                Log.d("POST METHOD",result);

            }

        }
        quserdata qd=new quserdata();
        qd.execute();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_qhome, container, false);

        rv=(RecyclerView)view.findViewById(R.id.rvqh);
        rv.setHasFixedSize(true);
        data2=new ArrayList<>();



        LinearLayoutManager lm1=new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(lm1);

        return view;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public class storeid
    {

        public int qid;

        public  String ustatus;

        public void setUstatus(String ustatus) {
            this.ustatus = ustatus;
        }

        public String getUstatus() {
            return ustatus;
        }

        public void setQid(int qid) {
            this.qid = qid;
        }

        public int getQid() {
            return qid;
        }
    }


        class backqset extends AsyncTask {
        String c,c1;



            public backqset(String valueOf, String ustatus) {
                this.c=valueOf;
                this.c1=ustatus;
            }


            @Override
            protected Object doInBackground(Object[] objects) {

                String result="";

                String conne="http://192.168.22.89/main/mobdata/qusethome.php";
                try {
                    URL url = new URL(conne);
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();

                    http.setRequestMethod("POST");
                    http.setDoInput(true);
                    http.setDoOutput(true);

                OutputStream outputStream=http.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                int n=getqset.size();
//                Log.d("size of the array", String.valueOf(n));//2

                    String data= URLEncoder.encode("qid","UTF-8")+"="+URLEncoder.encode(c,"UTF-8");
                writer.write(data);


//
//
                writer.flush();
                writer.close();
                outputStream.close();

                    InputStream ips=http.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
                    String line="";
                    while ((line=reader.readLine())!=null){
//                    Log.d("get vlaues\n",result);

                        result+=line;

                    }

                    reader.close();
                    ips.close();
                    result.trim();

                    if(result.length() > 2) {
                        logFlag = 1;

                        JSONArray arr = new JSONArray(result);

                        for (int i = 0; i<arr.length(); i++)
                        {
                            JSONObject obj = arr.getJSONObject(i);
                            testset p=new testset();
                            p.tid=obj.getInt("id");
                            p.testset=obj.getString("qname");
                            p.date=obj.getString("fromdate");
                            p.date1=obj.getString("todate");
                            p.status=c1;
                            Log.d("result",obj.getString("qname"));
                            data2.add(p);

                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                QhomeAdapter q1=new QhomeAdapter(view.getContext(),data2);
                                rv.setAdapter(q1);
                            }
                        });

                        Log.d("COUNT" , "" + data2.size());


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

}
