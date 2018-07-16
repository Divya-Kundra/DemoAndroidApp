package com.example.administrator.omsai;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText username,password,userage,userheight,userid;
    String user,pass,age,height,id;
    Button logIn,createNew;
    protected IRemote calService = null;
    private RemoteService mremote;
    Intent mintent;

    Context ctx;
    public Context getCtx() {
        return ctx;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onStart()

    {
        super.onStart();
        Toast.makeText(getApplicationContext(),    "Login or fill form to Create Account", Toast.LENGTH_LONG).show();
        username= (EditText)findViewById(R.id.e1);
        password= (EditText) findViewById(R.id.e2);
        userage=(EditText)findViewById(R.id.e12);
        userheight=(EditText)findViewById(R.id.e13);
        userid=(EditText)findViewById(R.id.e11) ;
        logIn= findViewById(R.id.b1);
        createNew= findViewById(R.id.b2);

        if ((calService == null)) {

            mintent=new Intent("remoteService");
            mintent.setPackage("com.example.administrator.omsai");
            getApplicationContext().bindService(mintent,connection,Context.BIND_AUTO_CREATE);
        }
    }



    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }



    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            calService = IRemote.Stub.asInterface(service);
            Toast.makeText(getApplicationContext(),    "Service Connected", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            calService = null;
            Toast.makeText(getApplicationContext(), "Service Disconnected", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onDestroy() {
        stopService(mintent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();

    }

    String status;
    public void logIn(View v)
    {
        try {
            status = calService.accAuthen(username.getText().toString(), password.getText().toString());

        }catch (RemoteException e)
        {e.printStackTrace();}
    }

    public void createLogin(View v) throws Exception
    {  int i;
        user=username.getText().toString();
        pass=password.getText().toString();
        age=userage.getText().toString();
        height=userage.getText().toString();
        id=userid.getText().toString();
      i= calService.createAcc(user,pass,age,height,id);
        if(i == 200 || i==201)

            Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_SHORT).show();

        else
        {  Log.d(" Respose ", String.valueOf(i));

            Toast.makeText(getApplicationContext(), "Account can't be created", Toast.LENGTH_SHORT).show();

        }



    }




public String getInfo(View v)
{

    String response="";
    id=userid.getText().toString();
    user=username.getText().toString();
    pass=password.getText().toString()   ;

    try
    {response=calService.getDetails(id,status,user,pass);

        return  response;}

    catch (Exception e){e.printStackTrace();}
return  response;

}

public int updtDetails(View v)
{
    int response= 0;
    id=userid.getText().toString();
    user=username.getText().toString();
    pass=password.getText().toString()   ;

    try
    {//response=calService.getDetails(username,password,id,age,height);

        return  response;}

    catch (Exception e){e.printStackTrace();}
    return  response;

}
}
