package com.xgb.test.animations.demo;



import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.xgb.test.webivew.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

public class AnimationDemosActivity extends Activity implements OnClickListener
{

	private ImageView webView;
	private Bitmap bitmap;
	private Button fadeInButton, fadeOutButton, slideInButton, slideOutButton, scaleInButton, scaleOutButton, rotateInButton, rotateOutButton, scaleRotateInButton, scaleRotateOutButton,
			slideFadeInButton, slideFadeOutButton;
	private AnimationController animationController;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		webView = (ImageView) findViewById(R.id.webview);
//		webView.setBackgroundColor(Color.BLUE);

		fadeInButton = (Button) findViewById(R.id.fadeInButton);
		fadeOutButton = (Button) findViewById(R.id.fadeOutButton);
		slideInButton = (Button) findViewById(R.id.slideInButton);
		slideOutButton = (Button) findViewById(R.id.slideOutButton);

		scaleInButton = (Button) findViewById(R.id.scaleInButton);
		scaleOutButton = (Button) findViewById(R.id.scaleOutButton);
		rotateInButton = (Button) findViewById(R.id.rotateInButton);
		rotateOutButton = (Button) findViewById(R.id.rotateOutButton);

		scaleRotateInButton = (Button) findViewById(R.id.scaleRotateInButton);
		scaleRotateOutButton = (Button) findViewById(R.id.scaleRotateOutButton);
		slideFadeInButton = (Button) findViewById(R.id.slideFadeInButton);
		slideFadeOutButton = (Button) findViewById(R.id.slideFadeOutButton);

		fadeInButton.setOnClickListener(this);
		fadeOutButton.setOnClickListener(this);
		slideInButton.setOnClickListener(this);
		slideOutButton.setOnClickListener(this);

		scaleInButton.setOnClickListener(this);
		scaleOutButton.setOnClickListener(this);
		rotateInButton.setOnClickListener(this);
		rotateOutButton.setOnClickListener(this);

		scaleRotateInButton.setOnClickListener(this);
		scaleRotateOutButton.setOnClickListener(this);
		slideFadeInButton.setOnClickListener(this);
		slideFadeOutButton.setOnClickListener(this);

		animationController = new AnimationController();
//		new SendThread().start();
	}
    private class SendThread extends Thread {
        @Override
    	public void run() {
        	while (true)
        	{
        		bitmap = getLoacalBitmap("/mnt/sdcard/tcis-test/floorpic/floor1.jpeg");
				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
        		try {
					Thread.sleep(2100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		Message msg2 = new Message();
				msg2.what = 2;
				mHandler.sendMessage(msg2);
        		try {
					Thread.sleep(2100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage (Message msg) {
            super.handleMessage(msg);
            ImageView view = webView;
            view.setImageBitmap(bitmap);
			switch (msg.what) {
			case 1:
				animationController.rotateIn(view, 2000, 0);
				break;
			case 2:
				animationController.rotateOut(view, 2000, 0);
				break;
			}
        }
    };
	@Override
	public void onClick(View v)
	{
		long durationMillis = 2000, delayMillis = 0;
		ImageView view = webView;

		if (v == fadeInButton)
		{
			animationController.fadeIn(view, durationMillis, delayMillis);
		}
		else if (v == fadeOutButton)
		{
			animationController.fadeOut(view, durationMillis, delayMillis);
		}
		else if (v == slideInButton)
		{
			animationController.slideIn(view, durationMillis, delayMillis);
		}
		else if (v == slideOutButton)
		{
			animationController.slideOut(view, durationMillis, delayMillis);
		}
		else if (v == scaleInButton)
		{
			animationController.scaleIn(view, durationMillis, delayMillis);

		}
		else if (v == scaleOutButton)
		{
			animationController.scaleOut(view, durationMillis, delayMillis);
		}
		else if (v == rotateInButton)
		{
			Bitmap bitmap = getLoacalBitmap("/mnt/sdcard/tcis-test/floorpic/floor1.jpeg");
			view.setImageBitmap(bitmap);
			animationController.rotateIn(view, durationMillis, delayMillis);
		}
		else if (v == rotateOutButton)
		{
			Bitmap bitmap2 = getLoacalBitmap("/mnt/sdcard/tcis-test/floorpic/floor3.jpeg");
			view.setImageBitmap(bitmap2);
			animationController.rotateOut(view, durationMillis, delayMillis);
		}
		else if (v == scaleRotateInButton)
		{
			animationController.scaleRotateIn(view, durationMillis, delayMillis);

		}
		else if (v == scaleRotateOutButton)
		{
			animationController.scaleRotateOut(view, durationMillis, delayMillis);
		}
		else if (v == slideFadeInButton)
		{
			animationController.slideFadeIn(view, durationMillis, delayMillis);
		}
		else if (v == slideFadeOutButton)
		{
			animationController.slideFadeOut(view, durationMillis, delayMillis);
		}

	}
	public static Bitmap getLoacalBitmap(String url) {
		try {
		FileInputStream fis = new FileInputStream(url);
		return BitmapFactory.decodeStream(fis);

		} catch (FileNotFoundException e) {
		e.printStackTrace();
		return null;
		}
	} 
}