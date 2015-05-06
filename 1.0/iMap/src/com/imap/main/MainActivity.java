package com.imap.main;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.imap.R;
import com.imap.database.HttpUtil;
import com.imap.database.Location;
import com.imap.database.User;
import com.imap.ui.LogInActivity;
import com.imap.ui.PlaceInfoActivity;
import com.imap.ui.SettingActivity;
import com.imap.ui.SplanshActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	MapView mMapView;
	BaiduMap mBaiduMap;
	Marker marker;
	List<Location> listLatLng = new ArrayList<Location>();
	Location location;
	ProgressDialog pd;
	User user;
	final int OK = 1;
	final String TAG_USERINFO = "userinfo";
	final String RES_ID = "id";
	final String RES_NAME = "username";
	final String RES_EMAIL = "email";
	final String RES_SEX = "sex";
	final String RES_MSG = "msg";
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			pd.dismiss();
			switch(msg.what){
			case OK:break;
			default:break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("user");
		saveUserInfo();
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		mMapView.showZoomControls(false);
		pd = new ProgressDialog(this);
		pd.setMessage("正在加载");
		pd.show();
		new Thread(){
			public void run(){
				initOverlay();
				mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

					@Override
					public boolean onMarkerClick(Marker marker) {
						// TODO Auto-generated method stub
						startDetailActivity(marker.getZIndex());
						return true;
					}
				});
				Message msg = new Message();
				msg.what = OK;
				handler.sendMessage(msg);
			}
		}.start();
		
	}
	
	public void saveUserInfo(){
		SharedPreferences.Editor sharedata = getSharedPreferences(TAG_USERINFO, MODE_PRIVATE).edit(); 
		sharedata.putInt(RES_ID, user.getId());
		sharedata.putString(RES_NAME, user.getName());
		sharedata.putString(RES_EMAIL, user.getEmail());
		sharedata.putString(RES_SEX, user.getSex());
		sharedata.putString(RES_MSG, user.getMsg());
		sharedata.commit();
	}

	public void initOverlay() {
		getListLatLng();
		for (Location lc : listLatLng) {
			OverlayOptions overlayOptions = new MarkerOptions()
					.position(lc.getLatLng()).icon(lc.getBitmap())
					.zIndex(lc.getId()).draggable(true);
			marker = (Marker) mBaiduMap.addOverlay(overlayOptions);
		}
	}

	public List<Location> getListLatLng() {
		listLatLng = new HttpUtil().getLocation();
//		location = new Location();
//		location.setLatLng(new LatLng(39.963175, 116.400244));
//		location.setId(1);
//		location.setIcon_id(R.drawable.icon_marka);
//		location.setBitmap(BitmapDescriptorFactory.fromResource(location
//				.getIcon_id()));
//		listLatLng.add(location);
		return listLatLng;
	}

	public void startDetailActivity(int id) {
		System.out.println(id);
		Intent intent = new Intent(MainActivity.this,
				PlaceInfoActivity.class);
		intent.putExtra("location", id);
		intent.putExtra("name", listLatLng.get(id).getName());
		intent.putExtra("comment", listLatLng.get(id).getComment());
		intent.putExtra("pic_url", listLatLng.get(id).getPic_url());
		startActivity(intent);
	}

	public void image1_onclick(View view) {
	//	ImageView imageView = (ImageView) findViewById(R.id.icon_01);
	//	System.out.println("I am in the function ONCLIICK");
		//imageView.setImageResource(R.drawable.ic_launcher);
	
		Intent intent=new
				 Intent(MainActivity.this,SettingActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("user", user);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public void image2_onclick(View view) {

	}

	public void image3_onclick(View view) {

	}

	/**
	 * �������Overlay
	 * 
	 * @param view
	 */
	public void clearOverlay(View view) {
		mBaiduMap.clear();
	}

	/**
	 * �������Overlay
	 * 
	 * @param view
	 */
	public void resetOverlay(View view) {
		clearOverlay(null);
		initOverlay();
	}

	@Override
	protected void onPause() {
		// MapView������������Activityͬ������activity����ʱ�����MapView.onPause()
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// MapView������������Activityͬ������activity�ָ�ʱ�����MapView.onResume()
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// MapView������������Activityͬ������activity���ʱ�����MapView.destroy()
		mMapView.onDestroy();
		super.onDestroy();
		// ���� bitmap ��Դ
		for (Location lc : listLatLng) {
			lc.getBitmap().recycle();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
