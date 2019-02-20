package com.first.msql;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

 * to handle interaction events.
 * Use the {@link Questions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Questions extends Fragment {

    int a;
    RecyclerView rv;
    List<Qset> data1;
    Button b1,b2;
    int logFlag = 0;
    String s,s1;
    public static ArrayList<String> selectedAnswers;
    String[] questionsList;
    SharedPreferences sharedpreferences;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Questions() {
        // Required empty public constructor
    }


    @SuppressLint("ValidFragment")
    public Questions(String s,String p) {
        this.mParam1=s;
        this.mParam2=p;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Questions.
     */
    // TODO: Rename and change types and number of parameters
    public static Questions newInstance(String param1, String param2) {
        Questions fragment = new Questions();
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
                .setActionBarTitle(mParam2);
        sharedpreferences = this.getActivity().getSharedPreferences("cuser", Context.MODE_PRIVATE);

        String v = null ;
        s1=sharedpreferences.getString("useri",v);
        execute();
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_questions, container, false);
        final TextView t1=(TextView)view.findViewById(R.id.tx);

        rv=(RecyclerView)view.findViewById(R.id.rvq);
        rv.setHasFixedSize(true);
        initializeData() ;

        LinearLayoutManager lm1=new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(lm1);

        b1=(Button)view.findViewById(R.id.back);
        b1.setTransformationMethod(null);
        b2=(Button)view.findViewById(R.id.confirm1);
        b2.setTransformationMethod(null);



        final AppCompatActivity activity = (AppCompatActivity)view.getContext();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment myFragment = new Qhome();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, myFragment).addToBackStack(null).commit();

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                for(int k = 0; k < data1.size();k++)
                {
                    Qset t = data1.get(k);
                    if(t.status== 1)
                    {
//                        Toast.makeText(getContext(),t.qid + "-" + "yes" , Toast.LENGTH_LONG).show();
                                        insertans as=new insertans(t.qid,"yes");
                                        as.execute();

                    }
                    else if(t.status== 2) {
//                        Toast.makeText(getContext(),t.qid + "-" + "no" , Toast.LENGTH_LONG).show();
                        insertans as=new insertans(t.qid,"no");
                        as.execute();

                    }
                }


                updateQset bg=new updateQset();
                bg.execute();

                Fragment myFragment = new Qhome();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, myFragment).addToBackStack(null).commit();

            }
        });


        return view;
    }


    private void initializeData() {

//        data1=new ArrayList<>();
//        data1.add(new Qset("1",1.Do you have headache ?"));
//        data1.add(new Qset("1","2.Do you have fever ?"));
//        data1.add(new Qset("1","3.Do you have painkiller ?"));
//        data1.add(new Qset("1","4.Do you have bodypain ?"));
//        data1.add(new Qset("1","5.Do you have skin diseases ?"));
//        data1.add(new Qset("1","6.Was you affected by chicken box ?"));
    }




    private void execute() {

        class backquestions extends AsyncTask {

            @Override
            protected Object doInBackground(Object[] objects) {

                String result = "";

                String conne = "http://192.168.22.89/main/mobdata/questions.php";

                try {
                    URL url = new URL(conne);
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();

                    http.setRequestMethod("POST");
                    http.setDoInput(true);
                    http.setDoOutput(true);
                    OutputStream outputStream=http.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data= URLEncoder.encode("qsetid","UTF-8")+"="+URLEncoder.encode(mParam1,"UTF-8");
                writer.write(data);

                writer.flush();
                writer.close();
                outputStream.close();


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
                            Qset p = new Qset();
                            p.qid=obj.getString("id");
                            p.questions = obj.getString("quest");
                            p.status=-1;
//                            Log.d("result", obj.getString("quest"));
                            data1.add(p);

                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final QAdapter pr1=new QAdapter(getActivity(), data1);
                                rv.setAdapter(pr1);

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
        backquestions bg=new backquestions();
        bg.execute();
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

    class updateQset extends AsyncTask {


        @Override
        protected Object doInBackground(Object[] objects) {
            String result1 = "";

            String conne = "http://192.168.22.89/main/mobdata/updateqset.php";

            try {
                URL url = new URL(conne);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();

                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream outputStream = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode("no", "UTF-8") + "&&"
                        + URLEncoder.encode("testid", "UTF-8") + "=" + URLEncoder.encode(mParam1, "UTF-8") + "&&"
                        + URLEncoder.encode("uid", "UTF-8") + "=" + URLEncoder.encode(s1, "UTF-8");
                writer.write(data);

                writer.flush();
                writer.close();
                outputStream.close();


                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String line = "";
                while ((line = reader.readLine()) != null) {
//                            Log.d("get vlaues\n", result1);

                    result1 += line;

                }

                reader.close();
                ips.close();
                result1.trim();
//                        if(result1.length() > 2)
//                        {
//
//                            Toast.makeText(getActivity(),"Sucess:",Toast.LENGTH_LONG).show();
//                        }
//                        else
//                        {
//                            Toast.makeText(getActivity(),"somenting went wrong",Toast.LENGTH_LONG).show();
//                        }


                http.disconnect();
                return result1;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

//                    Log.d("last vlaues\n", result1);
            return result1;
        }
    }


    class insertans extends AsyncTask
    {

        String a,b;

        public insertans(String qid, String yes) {
            this.a=qid;
            this.b=yes;
        }

        @Override
                protected Object doInBackground(Object[] objects) {
                    String result1 = "";

                    String conne = "http://192.168.22.89/main/mobdata/answerinsert.php";

                    try {
                        URL url = new URL(conne);
                        HttpURLConnection http = (HttpURLConnection) url.openConnection();

                        http.setRequestMethod("POST");
                        http.setDoInput(true);
                        http.setDoOutput(true);
                        OutputStream outputStream=http.getOutputStream();
                        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                        String data= URLEncoder.encode("uid","UTF-8")+"="+URLEncoder.encode(s1,"UTF-8")+"&&"
                                +URLEncoder.encode("qid","UTF-8")+"="+URLEncoder.encode(a,"UTF-8")+"&&"
                                +URLEncoder.encode("ans","UTF-8")+"="+URLEncoder.encode(b,"UTF-8");
                        writer.write(data);

                        writer.flush();
                        writer.close();
                        outputStream.close();


                        InputStream ips = http.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                        String line = "";
                        while ((line = reader.readLine()) != null) {
//                            Log.d("get vlaues\n", result1);

                            result1 += line;

                        }

                        reader.close();
                        ips.close();
                        result1.trim();



                        http.disconnect();
                        return result1;


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

//                    Log.d("last vlaues\n", result1);
                    return result1;
                }
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
