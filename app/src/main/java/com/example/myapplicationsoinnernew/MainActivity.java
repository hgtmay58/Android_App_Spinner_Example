package com.example.myapplicationsoinnernew;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
Spinner sp1,sp2,sp3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp1=(Spinner)findViewById(R.id.spinner);
        sp2=(Spinner)findViewById(R.id.spinner2);
        sp3=(Spinner)findViewById(R.id.spinner3);
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //
        //
           sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               }

               @Override
               public void onNothingSelected(AdapterView<?> parent) {

               }
           });
        //
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 String s=sp1.getSelectedItem().toString();
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MainActivity.this,"一定要選",Toast.LENGTH_LONG).show();
            }
        });
       //
        String[] yitems = new String[100];
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
        String nyear= dateFormat.format(new Date());
        int j,i=0;
        for (j=Integer.valueOf(nyear);j>(Integer.valueOf(nyear)-100);j--)
        {
            yitems[i]=String.valueOf(j);
            i++;
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, yitems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter);

        //
        final String w="https://tcgbusfs.blob.core.windows.net/blobyoubike/YouBikeTP.json";
        final okhttp_util okhttpw=new okhttp_util();
        Thread t=new Thread(){
            public void run()
            {
                final String res=okhttpw.urlget(w);
                Log.d(" TAG ", res);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject j1= null;
                        try {
                            j1 = new JSONObject(res);
                            JSONObject j2=j1.getJSONObject("retVal");
                            JSONObject j3=j2.getJSONObject("0001");
                            String sna=j3.getString("sna");
                            String tot=j3.getString("tot");
                            String area=j3.getString("sarea");
                            String itemans=sna+",tot="+tot+",area="+area;
                            JSONObject j32=j2.getJSONObject("0002");
                            String sna2=j32.getString("sna");
                            String tot2=j32.getString("tot");
                            String area2=j32.getString("sarea");
                            String itemans2=sna2+",tot="+tot2+",area="+area2;
                           //
                            String[] bitems = new String[2];
                            bitems[0]=itemans;
                            bitems[1]=itemans2;
                            ArrayAdapter<String> adapter =
                                    new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item, bitems);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp1.setAdapter(adapter);
                            //
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        t.start();


       //

    }


}