package cn.anycall.testlist;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import android.app.ListActivity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.anycall.testlist.AsyncImageLoader.ImageCallback;

public class TestListViewActivity extends ListActivity {

	private List<Map<String, Object>> mData = new ArrayList<Map<String, Object>>(); 
	public final class ViewHolder {
		public ImageView img;
		public TextView title;
		public TextView info;
	}
     
	public List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
	public int a = 0;
	public Button button;
	public LinearLayout layloading;
	public MyHandler myHandler;
	public  MyAdapter adapter ; 
	private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
	
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		myHandler = new MyHandler();
		
		
		ListView listView = getListView();// �õ�ListView
		listView.setDividerHeight(0);
		LinearLayout listFooter = (LinearLayout) LayoutInflater.from(this)
				.inflate(R.layout.main_foot, null);
		listView.addFooterView(listFooter);// ���FooterView
		button = (Button) findViewById(R.id.more);
		layloading = (LinearLayout) findViewById(R.id.loading);
		button.setVisibility(View.VISIBLE);
		layloading.setVisibility(View.GONE);
		adapter =  new MyAdapter(this); 
		setListAdapter(adapter);
		button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//��һ���̸߳��º�̨���
				MyThread m = new MyThread();
				new Thread(m).start();
				
			}
		});
		for(int i=0;i<20;i++){
			a++;
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("img", R.drawable.icon); 
			 map.put("title", "G"+a);  
			 map.put("info", "google"+a);  
			 mData.add(map); 
		}
	}
	public class MyAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		
		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}
		public int getCount() {
			return mData.size();
		}
		public Object getItem(int arg0) {
			return null;
		}
		public long getItemId(int arg0) {
			return 0;
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.main_list, null);
				
				holder.img = (ImageView)convertView.findViewById(R.id.img); 
				holder.title = (TextView) convertView
						.findViewById(R.id.listtitle);
				holder.info = (TextView) convertView
						.findViewById(R.id.listtext);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			
			
			//holder.img.setBackgroundResource((Integer)mData.get(position).get("img")); 
			holder.title.setText((String) mData.get(position).get("title"));
			holder.info.setText((String) mData.get(position).get("info"));
			

			//�첽����ͼƬ
			Drawable cachedImage = asyncImageLoader.loadDrawable("http://www.1860.cn/tuangou/groupon/img/logo_v4.png",holder.img, new ImageCallback(){
                
                public void imageLoaded(Drawable imageDrawable,ImageView imageView, String imageUrl) {
                    imageView.setImageDrawable(imageDrawable);
                }
            });
			System.out.println(cachedImage);
			if (cachedImage == null) {
				holder.img.setImageResource(R.drawable.icon);
			} else {
				holder.img.setImageDrawable(cachedImage);
			}
			
			
			
			
			
			return convertView;

		}

	}
	
	
	
	//���º�̨���
	class MyThread implements Runnable {
		public void run() {

			
			String msglist = "1";
			Message msg = new Message();
			Bundle b = new Bundle();// ������
			b.putString("rmsg", msglist);
			msg.setData(b);
			TestListViewActivity.this.myHandler.sendMessage(msg); // ��Handler������Ϣ,����UI
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			 msglist = "2";
			 msg = new Message();
			 b = new Bundle();// ������
			b.putString("rmsg", msglist);
			msg.setData(b);
			TestListViewActivity.this.myHandler.sendMessage(msg); // ��Handler������Ϣ,����UI
			

		}
	}
	
	
	
	class MyHandler extends Handler {
		public MyHandler() {
		}

		public MyHandler(Looper L) {
			super(L);
		}

		// ���������д�˷���,�������
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			// �˴����Ը���UI
			Bundle b = msg.getData();
			String rmsg = b.getString("rmsg");
			if ("1".equals(rmsg)) {
				// do nothing
				button.setVisibility(View.GONE);
				layloading.setVisibility(View.VISIBLE);
			
			}else if ("2".equals(rmsg)) { 
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(int i=0;i<20;i++){
					a++;
					Map<String, Object> map = new HashMap<String, Object>(); 
					map.put("img", R.drawable.icon); 
					 map.put("title", "G"+a);  
					 map.put("info", "google"+a);  
					 mData.add(map); 
				}
				button.setVisibility(View.VISIBLE);
				layloading.setVisibility(View.GONE);
				adapter.notifyDataSetChanged();
			}

		}
	}

}