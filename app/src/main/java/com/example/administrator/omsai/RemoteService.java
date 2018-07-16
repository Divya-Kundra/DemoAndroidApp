package com.example.administrator.omsai;
;
import android.util.Base64;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import android.util.Base64;

import javax.net.ssl.HttpsURLConnection;

import static org.apache.http.protocol.HTTP.USER_AGENT;

public class RemoteService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;

    }


    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent("RestartSensor");
        sendBroadcast(broadcastIntent);

    }

    private final IRemote.Stub mBinder = new IRemote.Stub() {

        @Override
        public int createAcc(String uname, String pword,String age, String height, String id) throws RemoteException {
            int res= createAccnt(uname, pword,age,height,id);
            return res;
        }

        @Override
        public String accAuthen(String username, String password ) throws  RemoteException
        {
            String res=accountAuthen(username,password);
            return res ;}


        @Override
        public   String postDetails(String username, String password,String id, String age, String height) throws RemoteException
        {
            String res=postUserDetails(username,password,id,age,height);
            return res;

        }

        @Override
     public    String getDetails(String id, String authStatus, String username, String password) throws RemoteException
        {
            String res=getUserDetails(id,authStatus,username,password);
            return res;

        }

        @Override
        public  int updateDetails(String username, String password,String id, String age, String height) throws RemoteException
        {
            int res=updateUserDetails(username,password,id,age,height);
            return res;

        }


    };

    public int createAccnt(String uname, String pword, String age, String height, String id)  {
        int responseCode=0;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL url = new URL("https://mirror-android-test.herokuapp.com/users");
            HttpsURLConnection httpClient = (HttpsURLConnection) url.openConnection();
            httpClient.setRequestMethod("POST");
            httpClient.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpClient.setRequestProperty("Accept", "application/json;charset=urf-8");
            httpClient.setAllowUserInteraction(false);
            httpClient.setReadTimeout(10000);
            httpClient.setConnectTimeout(15000);

            JSONObject params = new JSONObject();

            params.put("username", uname);
            params.put("password", pword);
            params.put("age", age);
            params.put("height",height);
            params.put("id",id);


            String json=params.toString();
            httpClient.setRequestProperty("Content-length",json.getBytes().length +"" );


            httpClient.setDoInput(true);
            httpClient.setDoOutput(true);
            httpClient.setUseCaches(false);

            OutputStream outputStream = httpClient.getOutputStream();
            outputStream.write(json.getBytes("UTF-8"));


            httpClient.connect();
            responseCode = httpClient.getResponseCode();
            String str=httpClient.getResponseMessage();

            httpClient.disconnect();

            return responseCode;
        }catch (Exception e)
        {e.printStackTrace();
        }
        return responseCode;
    }

    public   String postUserDetails(String username, String password,String id, String age, String height)
    {
        String token = "";
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL url = new URL("https://mirror-android-test.herokuapp.com/users");
            HttpsURLConnection httpClient = (HttpsURLConnection) url.openConnection();
            httpClient.setRequestMethod("POST");
            httpClient.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpClient.setRequestProperty("Accept", "application/json;charset=urf-8");
            httpClient.setAllowUserInteraction(false);

            httpClient.setReadTimeout(10000);
            httpClient.setConnectTimeout(15000);
            JSONObject params = new JSONObject();

            params.put("username",username);
            params.put("password",password);
            params.put("age", age);
            params.put("height", height);

            String json = params.toString();
            httpClient.setRequestProperty("Content-length", json.getBytes().length + "");
            httpClient.setDoInput(true);
            httpClient.setDoOutput(true);
            httpClient.setUseCaches(false);


            OutputStream outputStream = httpClient.getOutputStream();
            outputStream.write(json.getBytes("UTF-8"));


            httpClient.connect();

            int responseCode = httpClient.getResponseCode();
            String t = String.valueOf(responseCode);
            Log.d("response of post", t);

            InputStream in = new BufferedInputStream(httpClient.getErrorStream());
            String urlResponse="";

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                urlResponse+=line;

            }
            httpClient.disconnect();

        }catch (Exception e) {
            e.printStackTrace();
        }
            return token;

    }



    public String accountAuthen(String username, String password ) throws  RemoteException
    {
        int status=0;
        String json_response = "";
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("https://mirror-android-test.herokuapp.com/auth");

            HttpsURLConnection httpClient = (HttpsURLConnection) url.openConnection();
            httpClient.setRequestMethod("POST");
            httpClient.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpClient.setRequestProperty("Accept", "application/json;charset=urf-8");
            httpClient.setAllowUserInteraction(false);
            httpClient.setReadTimeout(10000);
            httpClient.setConnectTimeout(15000);
            JSONObject params = new JSONObject();
            params.put("username", username);
            params.put("password", password);

        String  json=params.toString();
            httpClient.setRequestProperty("Content-length",json.getBytes().length +"" );


            httpClient.setDoInput(true);
            httpClient.setDoOutput(true);
            httpClient.setUseCaches(false);


            OutputStream outputStream = httpClient.getOutputStream();
            outputStream.write(json.getBytes("UTF-8"));


            httpClient.connect();

            status = httpClient.getResponseCode();


            if(status==200)
            {        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
            }
            else
            { Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();}

            outputStream.flush();
            outputStream.close();
            InputStreamReader in = new InputStreamReader(httpClient.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String text = "";
            while ((text = br.readLine()) != null) {
                json_response += text;
            }


            httpClient.disconnect();
            return json_response;
        }catch (Exception e)
        {e.printStackTrace();}
        return json_response;
    }





              public String getUserDetails(String id, String authStatus, String username, String password)
        {

            String json_response = null;
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);



               // String json="username":username&"p"

                URL url = new URL("https://mirror-android-test.herokuapp.com/users/"+id);
                HttpsURLConnection httpClient = (HttpsURLConnection) url.openConnection();
                httpClient.setRequestMethod("GET");
                httpClient.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                httpClient.setRequestProperty("Accept", "application/json;charset=urf-8");
                httpClient.setAllowUserInteraction(false);
                httpClient.setReadTimeout(10000);
                httpClient.setConnectTimeout(15000);




                httpClient.setDoInput(true);
                httpClient.setDoOutput(false);
                httpClient.setUseCaches(false);

                JSONObject params = new JSONObject();
                params.put("username", username);
                params.put("password", password);
                params.put("id",id);
                params.put("auth", authStatus);

                String json=params.toString();
                httpClient.setRequestProperty("CustomHeader",json);
//
//            OutputStream outputStream = httpClient.getOutputStream();
  //             outputStream.write(json.getBytes("UTF-8"));
                httpClient.connect();
                int   responseCode = httpClient.getResponseCode();
                String t=String.valueOf(responseCode);
                Log.d("response of get",t);

                if(Integer.parseInt(t)>400) {
                    InputStreamReader in = new InputStreamReader(httpClient.getInputStream());
                    BufferedReader br = new BufferedReader(in);
                    String text = "";
                    while ((text = br.readLine()) != null) {
                        json_response += text;
                    }
                    Log.e("get response", json_response);
                }
                return json_response;


            }catch (Exception e)
            {
                e.printStackTrace();

            }
            return json_response;

        }




    int updateUserDetails(String username, String password,String id, String age, String height)
    {
                int res=0;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL url = new URL("https://mirror-android-test.herokuapp.com/users/:" + id);
            HttpsURLConnection httpClient = (HttpsURLConnection) url.openConnection();
            httpClient.setRequestMethod("PATCH");
            httpClient.setAllowUserInteraction(false);
            httpClient.setReadTimeout(10000);
            httpClient.setConnectTimeout(15000);


            JSONObject params = new JSONObject();
            params.put("username", username);
            params.put("password", password);
            params.put("age", age);
            params.put("height",height);
            params.put("id",id);

            String json=params.toString();
            httpClient.setRequestProperty("Content-length",json.getBytes().length +"" );


            httpClient.setDoInput(true);
            httpClient.setDoOutput(true);
            httpClient.setUseCaches(false);

            OutputStream outputStream = httpClient.getOutputStream();
            outputStream.write(json.getBytes("UTF-8"));

            httpClient.connect();
            int responseCode;
            responseCode = httpClient.getResponseCode();
            String str=httpClient.getResponseMessage();
            Log.e("resp of creatg accnt",str);


        }catch (Exception e)
        {
            e.printStackTrace();

        }

        return res;
    }

}