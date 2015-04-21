package com.fis.www.threadprocess;
//Handler 若選到Utitly 會產生錯誤
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;



public class MainActivity extends ActionBarActivity {

    TextView tvMessage; //視狀況決定命名位置
    TextView tvMessage2;
    Button StartButton;
    Button NotifyButton; //通知畫面
    Button NotifyBack; //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMessage= (TextView)findViewById(R.id.tvMessage);
        tvMessage2=(TextView)findViewById(R.id.tvMessage2);
        StartButton =(Button)findViewById(R.id.StartButton);
        StartButton.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View v){
                new PostTask().execute("http://video.test.com");  //輸入下載的URL
            }

        });
        //通知訊息
        NotifyButton = (Button)findViewById(R.id.NotifyButton);
        NotifyButton.setOnClickListener(new View.OnClickListener(){
            @Override
                public void onClick(View v){
                final int notifyId=1;
                final NotificationManager manager= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                final Notification notification = new Notification.Builder(getApplicationContext()).setSmallIcon(R.drawable.ic_action_copy).setContentTitle("FIS TEST").setContentText("Test Abby.").build();
                manager.notify(notifyId,notification);
            }
        });

        NotifyBack = (Button)findViewById(R.id.NotifyBack);
        NotifyBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final int notifyId=1;
                final int requestCode=notifyId;
                final Intent intent = getIntent();//不同程式之間會進行程式的切換
                //alt + enter =>import 元件
                final int flags = PendingIntent.FLAG_CANCEL_CURRENT;
                final PendingIntent  pendingIntent = PendingIntent.getActivity(getApplicationContext(),requestCode,intent, flags);

                final NotificationManager manager= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                final Notification notification = new Notification.Builder(getApplicationContext()).setSmallIcon(R.drawable.ic_action_copy).setContentTitle("FIS TEST").setContentText("Test back.").setContentIntent(pendingIntent).build();
                manager.notify(notifyId,notification);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    //button Click
    public void StartUpdate(View view){
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            //客製Thread
            public void run() {
                do {
                    handler.post(new Runnable(){
                        @Override
                           public void run(){
                            tvMessage.setText(new Date().toString()); //抓系統時間顯示在tvMessage
                        }
                    });
                    try {
                        Thread.sleep(1000); //以1000為單位
                    }
                    catch(Exception ex){
                        ex.printStackTrace() ;
                    }
                }while(true);
            }
        });
        thread.start();
    }
    //進度條
    private class PostTask  extends AsyncTask<String, Integer, String>{
        protected String doInBackground(String... params)
        {

            String url= params[0];
            for(int i = 0 ; i<=100; i+=5)
            {
                try
                {
                    Thread.sleep(500);
                }
                catch(InterruptedException ex)
                {
                    ex.printStackTrace();
                }
                publishProgress(i);
            }
            return "finished";
        }
        protected void onPreExecute()
        {
         super.onPreExecute();
            tvMessage2.setText("Download....");
        }
        protected void onProgressUpdate(Integer... values)
        {
        super.onProgressUpdate(values);
           tvMessage2.setText("Downloading..." +values[0] + "%");

        }
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            tvMessage2.setText("Download finished");
        }
    }

}
