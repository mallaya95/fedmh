package com.first.msql;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Homepage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView im,im2;
    String p;
    TextView t1;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        im=(ImageView)findViewById(R.id.imageuserpic);
        im2=(ImageView)findViewById(R.id.imaged);
        t1=(TextView)findViewById(R.id.usern);

        sharedpreferences = getSharedPreferences("cuser", Context.MODE_PRIVATE);
        String v = null ;
        p=sharedpreferences.getString("useri",v);
 //       t1.setText(p);

//        Toast.makeText(getApplicationContext(),"user now:"+p,Toast.LENGTH_SHORT).show();

//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.icon)
//                        .setContentTitle("Notifications Example")
//                        .setContentText("This is a test notification");
//
//        Intent notificationIntent = new Intent(this, Homepage.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(contentIntent);

        // Add as notification
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());


        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int permissionCheck1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE);

                if (permissionCheck1 == PackageManager.PERMISSION_GRANTED) {
                    startGallery1();
                } else {
//                    ActivityCompat.requestPermissions(getApplication(),
//                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                            2000);
                }

            }
        });





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void startGallery1() {

        Intent cameraIntent1 = new Intent(Intent.ACTION_GET_CONTENT);
        cameraIntent1.setType("image/*");
        if (cameraIntent1.resolveActivity(getApplication().getPackageManager()) != null) {
            startActivityForResult(cameraIntent1, 3000);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == RESULT_OK) {
            if(requestCode == 3000){
                Uri returnUri = data.getData();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                RoundedBitmapDrawable roundedBitmapDrawable1= RoundedBitmapDrawableFactory.create(getResources(),bitmapImage);
                roundedBitmapDrawable1.setCircular(true);
                im.setImageDrawable(roundedBitmapDrawable1);
            }
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent i=new Intent(getApplicationContext(),Homepage.class);
            startActivity(i);
        } else if (id == R.id.nav_Profile) {
//            Fragment f11=Myprofile.newInstance ("","");
//            setme(f11);


        }
        else if (id == R.id.nav_Que) {
            Fragment f11=Qhome.newInstance ("","");
            //f11.execute();
            setme(f11);


        } else if (id == R.id.nav_not) {


            Fragment f11=Notifications.newInstance ("","");
            setme(f11);

        }
        else if (id == R.id.report) {
            Fragment f11=Report.newInstance ("","");
            setme(f11);

        }else if (id == R.id.faq) {


        }  else if (id == R.id.logot) {

            updateuser bg=new updateuser(p);
            bg.execute();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void setme(Fragment f) {
        FragmentManager fm =getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.frame,f);
        ft.commit();

    }
    public static class updateuser extends AsyncTask{
        String uc;

        public updateuser(String p) {
            this.uc=p;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String result1 = "";

            String conne = "http://192.168.22.89/main/mobdata/updateuseractive.php";

            try {
                URL url = new URL(conne);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream outputStream = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data =  URLEncoder.encode("uid", "UTF-8") + "=" + URLEncoder.encode(uc, "UTF-8");
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
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
