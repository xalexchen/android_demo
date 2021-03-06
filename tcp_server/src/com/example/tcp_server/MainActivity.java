package com.example.tcp_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    		
    			
    			try {  
    	            Boolean endFlag = false;  
    	            ServerSocket ss = new ServerSocket(12345);  
    	            while (!endFlag) {  
    	                // 等待客户端连接  
    	                Socket s = ss.accept();  
    	                BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));  
    	                //注意第二个参数据为true将会自动flush，否则需要需要手动操作output.flush()  
    	                PrintWriter output = new PrintWriter(s.getOutputStream(),true);  
    	                String message = input.readLine();  
    	                Log.d("Tcp Demo", "message from Client:"+message);  
    	                output.println("message received!");  
    	                //output.flush();  
    	                if("shutDown".equals(message)){  
    	                    endFlag=true;  
    	                }  
    	                s.close();  
    	            }  
    	            ss.close();  
    	   
    	        } catch (UnknownHostException e) {  
    	            e.printStackTrace();  
    	        } catch (IOException e) {  
    	            e.printStackTrace();  
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
