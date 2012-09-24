package com.example.shelltest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
public class MainActivity extends Activity {
	private final static String[] ARGS = {"sh","data/test","20120808.080808"};
	private final static String TAG = "MainActivity";
	Button mButton;
	TextView myTextView;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        TextView myTextView = new TextView(this);
        setContentView(myTextView);
        myTextView.setText(getResult());

    }
    public String getResult(){
    	 ShellExecute cmdexe = new ShellExecute ( );
    	 String result="";
    	 try {
			result = cmdexe.execute(ARGS, "/");
			Log.v(TAG, "" + result);
		} catch (IOException e) {
			Log.e(TAG, "IOException");
			e.printStackTrace();
		}
		return result;
    }
    private class ShellExecute {
    	/*
    	 * args[0] : shell 命令  如"ls" 或"ls -1";
    	 * args[1] : 命令执行路径  如"/" ;
    	 */
    	public String execute ( String [] cmmand,String directory)
    	throws IOException {
    	String result = "" ;
    	try {
    	ProcessBuilder builder = new ProcessBuilder(cmmand);
    	
    	if ( directory != null )
    	builder.directory ( new File ( directory ) ) ;
    	builder.redirectErrorStream (true) ;
    	Process process = builder.start ( ) ;
    	
    	//得到命令执行后的结果
    	InputStream is = process.getInputStream ( ) ;
    	byte[] buffer = new byte[1024] ;
    	while ( is.read(buffer) != -1 ) {
    	result = result + new String (buffer) ;
    	}
    	is.close ( ) ;
    	} catch ( Exception e ) {
    		e.printStackTrace ( ) ;
    	}
    	return result ;
    	}
    }
}
