package com.example.ninaadpai.androidsessionactivation;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothClass;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String[] arr = {"gif","emo","a-emo","sti","a-sti"};
    final static String SERVER_URL = "https://sand-cxp.emogi.com/v1/sessions";
    List<String> ad_fmts = new ArrayList<>();
    EditText ageEditText;
    RadioButton femaleRadioBtn, maleRadioBtn;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(String s : arr) {
            ad_fmts.add(s);
        }
        final GPSTracker gpsTracker = new GPSTracker(this);
        ageEditText = (EditText)findViewById(R.id.ageEditText);
        femaleRadioBtn = (RadioButton) findViewById(R.id.femaleRadioBtn);
        maleRadioBtn = (RadioButton) findViewById(R.id.maleRadioBtn);
        findViewById(R.id.connectButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "CLICKED", Toast.LENGTH_SHORT).show();
                JSONObject postData = new JSONObject();
                try {
                    postData.put("app_id","1FUUBK");
                    postData.put("dev_id","AA7E87A1-8A50-426B-8361-6F74977E8FD0");
                    postData.put("dev_id_type","IDFA");
                    postData.put("ad_fmts", ad_fmts);
                    JSONObject geoData = new JSONObject();
                    geoData.put("lat",gpsTracker.latitude);
                    geoData.put("lng",gpsTracker.longitude);
                    postData.put("geo",geoData);
                    final JSONObject userData = new JSONObject();
                    if(!TextUtils.isEmpty(ageEditText.getText()))
                        userData.put("age",ageEditText.getText());
                    if(femaleRadioBtn.isChecked())
                        userData.put("gender","f");
                    else if(femaleRadioBtn.isChecked())
                        userData.put("gender","m");
                    postData.put("user",userData);
                    JSONObject devData = new JSONObject();
                    if (DeviceUtil.isTablet(MainActivity.this))
                        devData.put("dev_type","tablet");
                     else
                        devData.put("dev_type","phone");
                    devData.put("bat", DeviceUtil.batLevel(MainActivity.this));
                    devData.put("conn",DeviceUtil.getNetworkType(MainActivity.this));
                    devData.put("os",System.getProperty("os.version"));
                    devData.put("os_ver",android.os.Build.VERSION.INCREMENTAL);
                    devData.put("scr_w",DeviceUtil.getDeviceWidth(MainActivity.this));
                    devData.put("scr_h",DeviceUtil.getDeviceHeight(MainActivity.this));
                    float ppi = Float.parseFloat(String.valueOf(DeviceUtil.getPpi()));
                    float screenSize = Float.parseFloat(DeviceUtil.getDeviceInch(MainActivity.this));
                    devData.put("ppi",ppi/screenSize);
                    postData.put("dev",devData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("RES",String.valueOf(postData));
                new SendDeviceDetails().execute(SERVER_URL, String.valueOf(postData));
            }
        });
    }
}
