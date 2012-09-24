package com.example.udp_receive;

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
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new TimeThread().start();
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
    		while(!mFinished) {

    			String text;
    			int server_port = 9999;
    			byte[] message = new byte[1500];
    			DatagramPacket p = new DatagramPacket(message, message.length);
    			DatagramSocket s = null;
				try {
					s = new DatagramSocket(server_port);
				} catch (SocketException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
    			try {
    				Log.v("alex","wait for udp data");
					s.receive(p);
					Log.v("alex","get udp data");
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
    			text = new String(message, 0, p.getLength());
    			Log.d("Udp tutorial","message:" + text);
    			s.close();
    			synchronized (mPauseLock) {
    				while (mPaused) {
    					try {
    						mPauseLock.wait();
    					} catch (InterruptedException e) {
    					}
    				}
    			}
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
