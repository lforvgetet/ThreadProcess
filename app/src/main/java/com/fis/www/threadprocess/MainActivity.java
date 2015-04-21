package com.fis.www.threadprocess;
//Handler 若選到Utitly 會產生錯誤
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Date;



public class MainActivity extends ActionBarActivity {

    TextView tvMessage; //視狀況決定命名位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMessage= (TextView)findViewById(R.id.tvMessage);

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
}
