//package com.first.msql;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
//import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.io.IOException;
//
//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
//
// * to handle interaction events.
// * Use the {@link Myprofile#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class Myprofile extends Fragment {
//
//    ImageView imageView,m1,m2,m3,m4;
//    TextView t1,t2;
//    EditText ename,eno,eemail,eadress,elocation;
//    Button button;
//    String address;
//    RadioGroup ggbut1;
//    String s;
//    SharedPreferences sharedpreferences;
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
//    public Myprofile() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment Myprofile.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static Myprofile newInstance(String param1, String param2) {
//        Myprofile fragment = new Myprofile();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//        ((Homepage) getActivity())
//                .setActionBarTitle("Profile");
//        sharedpreferences = this.getActivity().getSharedPreferences("cuser", Context.MODE_PRIVATE);
//
//        String v = null ;
//        s=sharedpreferences.getString("useri",v);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//       View view= inflater.inflate(R.layout.fragment_myprofile, container, false);
//       imageView=(ImageView)view.findViewById(R.id.profilepic);
//        ename=(EditText)view.findViewById(R.id.profilename);
//        eno=(EditText)view.findViewById(R.id.profilemb);
//        eemail=(EditText)view.findViewById(R.id.emailp);
//        eadress=(EditText)view.findViewById(R.id.eaddress);
//        elocation=(EditText)view.findViewById(R.id.eloc);
//        button=(Button)view.findViewById(R.id.regsave) ;
//        ggbut1=(RadioGroup)view.findViewById(R.id.gender_radio_groupp);
//
//        m4=(ImageView)view.findViewById(R.id.editprofile);
//        t2=(TextView)view.findViewById(R.id.udateprofilepictext);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(),"soory some issues found",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//       return view;
//    }
//
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        }
////        else {
////            throw new RuntimeException(context.toString()
////                    + " must implement OnFragmentInteractionListener");
////        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//
//}
