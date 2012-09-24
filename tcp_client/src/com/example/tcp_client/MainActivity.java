package com.example.tcp_client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
	private static final String TAG = "TCP-Client";
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
    		
        		while (true)
        		{
				try {
					// try to connect server
					Log.d(TAG, "connecting to server");
					Socket s = new Socket("192.168.1.195", 12345);
					Log.d(TAG, "connected");
					// outgoing stream redirect to socket
					OutputStream out = s.getOutputStream();
					// 注意第二个参数据为true将会自动flush，否则需要需要手动操作out.flush()
					PrintWriter output = new PrintWriter(out, true);
					output.println("Hello server , i am TC1");
					BufferedReader input = new BufferedReader(
							new InputStreamReader(s.getInputStream()));
//					BufferedInputStream is = new BufferedInputStream (s.getInputStream());
					// read line(s)
//					String message = input.readLine();
					byte[] mData = new byte[1];
					while (!s.isClosed())
					{
//						input.read(mData);
//						input.read(mData);
//						is.read(mData);
						String message = input.readLine();
						if (message != null)
						Log.d(TAG, "message From Server:" + message);
						if (mData[0] == 0x32)
						{
							Log.d(TAG, "close");
							s.close();
						}
					}
					Log.d(TAG, "disconnect to server");
					s.close();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
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
