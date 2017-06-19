package com.example.pckosek.a006_asynctask;

/* ------------------------*/
/*    FILE VERSION 2.0     */
/* ------------------------*/

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mtextView;
    private Button mButton_01;
    private ViewGroup mtransitionsContainer;

    private int mSomeInt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtextView = (TextView) findViewById(R.id.tv_01);
        mButton_01 = (Button) findViewById(R.id.button1);
        mtransitionsContainer = (ViewGroup) findViewById(R.id.transitions_container);

        mButton_01.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        MyTask myTask = new MyTask();
        myTask.execute(mSomeInt);
    }

    // A Generic function to read a stream from the assets folder

    public String getStringAsset(String filename) {
        AssetManager assetManager = getAssets();
        ByteArrayOutputStream result = new ByteArrayOutputStream();

        try {
            InputStream ins = assetManager.open(filename);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = ins.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private class MyTask extends AsyncTask<Integer, Void, Integer> {
        // THIS CLASS IS CALLED WITH an Integer input
        //      <Integer,...,...>
        // which means it is called by saying myTask.execute(0);
        //
        //  it also does nothing in onProgressUpdate
        //      <...,Void,...>
        //
        //  BUT - doInBackground still returns an Integer - WHICH is passed to onPostExecute
        //      <...,...,Integer>


        @Override
        protected Integer doInBackground(Integer... params) {
            // Params is a list
            int i = params[0];
            for (int j=0; j<100; j++) {
                i += j;
            }
            return i;

        }

        @Override
        protected void onPostExecute(Integer i) {
            super.onPostExecute(i);
            mSomeInt = i;
            mtextView.setText(i+"");
        }
    }
}
