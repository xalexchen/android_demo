package com.example.saveparameter;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private TextView view2;
	private Spinner spinner2;
	private ArrayAdapter adapter2;
	private final static String TAG = "MainActivity";
	private final static String mSaveTag = "Parameter";
	private final static String mSaveUserNameTag = "username";
	private final static String mSaveNameTag = "name";
	private final static String mSaveServerIPTag = "server";
	private final static String mSaveScreenSizeTag = "size";
	private final static String mSaveLocationTag = "location";
	private final static String mSaveOrientationTag = "orientation";
	
	
	private String Username;
	private String Name;
	private String ServerIP;
	private String ScreenSize;
	private String Location;
	private String Orientation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String[] m={"Horizontal","Vertical"};
		DisplayMetrics dm = new DisplayMetrics();   
        getWindowManager().getDefaultDisplay().getMetrics(dm); 
        LoadParameter();
		spinner2 = (Spinner) findViewById(R.id.spinner1);
		view2 = (TextView) findViewById(R.id.TextView_Orientation);
		TextView view3 = (TextView) findViewById(R.id.EditText_Resolution);
		
		view3.setText(""+dm.widthPixels+"*"+""+dm.heightPixels);
		adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adapter2);
		spinner2.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
		spinner2.setVisibility(View.VISIBLE);
	}
    @Override
	public void onDestroy()
    {
    	super.onDestroy();
		
    	//Save latest parameter
		Editor sharedata = getSharedPreferences(mSaveTag, 0).edit();
		
		EditText view = (EditText) findViewById(R.id.EditText_Username);
		sharedata.putString(mSaveUserNameTag, view.getText().toString());
		
		EditText view2 = (EditText) findViewById(R.id.EditText_Name);
		sharedata.putString(mSaveNameTag, view2.getText().toString());
		
		EditText view3 = (EditText) findViewById(R.id.EditText_Server);
		sharedata.putString(mSaveServerIPTag, view3.getText().toString());
	
		EditText view4 = (EditText) findViewById(R.id.EditText_Screen);
		sharedata.putString(mSaveScreenSizeTag, view4.getText().toString());
	
		EditText view5 = (EditText) findViewById(R.id.EditText_Location);
		sharedata.putString(mSaveLocationTag, view5.getText().toString());
		
		sharedata.commit();
		Log.v(TAG,"onDestroy = ");
    }
	private void LoadParameter()
	{
		SharedPreferences sharedata = getSharedPreferences(mSaveTag,0);
		Username = sharedata.getString(mSaveUserNameTag, null);
		Name = sharedata.getString(mSaveNameTag, null);
		ServerIP = sharedata.getString(mSaveServerIPTag, null);
		ScreenSize = sharedata.getString(mSaveScreenSizeTag, null);
		Location = sharedata.getString(mSaveLocationTag, null);
		Orientation = sharedata.getString(mSaveOrientationTag, null);
		if (Username != null)
		{
			EditText view = (EditText) findViewById(R.id.EditText_Username);
			view.setText(Username);
		}
		if (Name != null)
		{
			EditText view = (EditText) findViewById(R.id.EditText_Name);
			view.setText(Name);
		}
		if (ServerIP != null)
		{
			EditText view = (EditText) findViewById(R.id.EditText_Server);
			view.setText(ServerIP);
		}
		if (ScreenSize != null)
		{
			EditText view = (EditText) findViewById(R.id.EditText_Screen);
			view.setText(ScreenSize);
		}
		if (Location != null)
		{
			EditText view = (EditText) findViewById(R.id.EditText_Location);
			view.setText(Location);
		}
		Log.v(TAG,"Username = "+Username);
	}
	
	class SpinnerXMLSelectedListener implements OnItemSelectedListener{
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (adapter2.getItem(arg2).equals("Horizontal"))
			{
				if(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
			      {
			       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			      }
			}
			else if (adapter2.getItem(arg2).equals("Vertical"))
			{
				 if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
				     setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				 }

			}
			view2.setText("Orientation: "+adapter2.getItem(arg2));
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
		
	}
}
