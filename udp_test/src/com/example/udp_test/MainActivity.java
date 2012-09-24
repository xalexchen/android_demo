package com.example.udp_test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.mybtn);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new TimeThread().start();
			}
		});
    }
    private class TimeThread extends Thread {
		
		private Object mPauseLock;
	    private boolean mPaused;
	    private boolean mFinished;
	    
	    private TimeThread() {
	        mPauseLock = new Object();
	        mPaused = false;
	        mFinished = false;
	        Log.v(TAG,"TimeThread create");
	    }
        @Override
    	public void run() {
    	        String messageStr="Android send udp data to port 8888";
    	        int server_port = 4001;
    	        DatagramSocket s = null;
    			try {
    				s = new DatagramSocket();
    			} catch (SocketException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    	        InetAddress local = null;
    			try {
    				local = InetAddress.getByName("192.168.1.195");
    			} catch (UnknownHostException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    	        int msg_length=messageStr.length();
    	        byte[] message = messageStr.getBytes();
    	        DatagramPacket p = new DatagramPacket(message, msg_length,local,server_port);
    	        try {
    				s.send(p);
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    	        try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        }
        private void onPause() {
            synchronized (mPauseLock) {
                mPaused = true;
                Log.v(TAG,"TimeThread onPause");
            }
        }
        private void onResume() {
            synchronized (mPauseLock) {
                mPaused = false;
                mPauseLock.notifyAll();
                Log.v(TAG,"TimeThread onResume");
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
