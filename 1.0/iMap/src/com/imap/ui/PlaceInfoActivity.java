package com.imap.ui;

import java.util.List;
import java.util.Map;

import com.example.imap.R;
import com.imap.database.LoadImageAsynTask;
import com.imap.database.Util;
import com.imap.main.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * used to show a  in detail and show comments
 * 
 * @author LMW
 * 
 */
public class PlaceInfoActivity extends Activity implements OnClickListener {
//	Util util = new Util();
	private TextView place_name;
	private TextView place_location;
	private TextView place_info;
	private TextView title_name;
	private TextView commentinfo;
	private TextView gotoAddComment;
	private ImageView place_icon;
	private int id;
	//private ListView commentslist;
	//private List<Map<String, Object>> Infolist;
	LinearLayout mLoading;
	LinearLayout placeinfotextlayout;
	ImageButton mImageBack;
	String name;
	String location;
	String content;
	String iconurl;
	String titlename;
	String commentsaboutifo;
	String gotoAdd;
	
	Button titlebarrightbt;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.placeinfo);
		
		Bundle extras = getIntent().getExtras();
		id = extras.getInt("id");
		name = extras.getString("name");
		content = extras.getString("comment");
		iconurl = extras.getString("pic_url");
		commentsaboutifo="点此查看评论";
		gotoAdd = "点击添加评论";
		setupView();
		setListener();
		fillData();
	}

	public void setupView() {
		title_name= (TextView) this.findViewById(R.id.myTitle);
		place_name = (TextView) this.findViewById(R.id.txtPlaceName);
		commentinfo=(TextView) this.findViewById(R.id.gotocomments);
		gotoAddComment=(TextView) this.findViewById(R.id.gotoaddcomments);
		place_location = (TextView) this.findViewById(R.id.txtAddress);
		place_info = (TextView) this.findViewById(R.id.txtPlaceDescription);
		place_icon = (ImageView) this.findViewById(R.id.imgPlace);
		mLoading = (LinearLayout) this.findViewById(R.id.loading);// ���Ƽ��ؽ����
		mImageBack = (ImageButton) this.findViewById(R.id.back_button);
		
		titlebarrightbt=(Button)this.findViewById(R.id.titlebarrightbt);
		//commentslist = (ListView) this.findViewById(R.id.subjectlist);
		
		//placeinfotextlayout=(LinearLayout) this.findViewById(R.id.placeinfotextlayout);
		

	}

	public void setListener() {
		mImageBack.setOnClickListener(this);
		commentinfo.setOnClickListener(this);
		gotoAddComment.setOnClickListener(this);
		titlebarrightbt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(PlaceInfoActivity.this, SettingActivity.class);
				PlaceInfoActivity.this.startActivityForResult(intent, 0);
				
			}
		});
	}

	public void fillData() {
		new AsyncTask<Void, Void, Void>() {

			/*
			 * 
			 * 在另一个线程里加载数据，并且显示进度条
			 */
			@Override
			protected void onPreExecute() {
				showLoading();
				super.onPreExecute();
			}

			@Override
			protected void onPostExecute(Void result) {
				hideLoading();
				super.onPostExecute(result);
				place_info.setText(content);
				place_location.setText(location);
				place_name.setText(name);
				title_name.setText(titlename);
				commentinfo.setText(commentsaboutifo);
				gotoAddComment.setText(gotoAdd);
				/*
		        SimpleAdapter adapter = new SimpleAdapter(PlaceInfoActivity.this,Infolist,R.layout.comments_item,
	                    new String[]{"user_name","title","comment"},
	                    new int[]{R.id.user_name,R.id.comment_title,R.id.comment});
	            commentslist.setAdapter(adapter);
	*/
				
				
				LoadImageAsynTask task = new LoadImageAsynTask(
						new LoadImageAsynTask.LoadImageAsynTaskCallback() {

							public void beforeLoadImage() {
								// Ĭ��û�е�ͼͼƬ���õ�ͼƬ
								place_icon.setImageResource(R.drawable.logo);

							}

							public void afterLoadImage(Bitmap bitmap) {
								if (bitmap != null) {
									place_icon.setImageBitmap(bitmap);
								} else {
									place_icon
											.setImageResource(R.drawable.logo);
								}

							}
						});
				task.execute(iconurl);

			}

			// doInBackground 
			
			@Override
			protected Void doInBackground(Void... params) {
				//从数据库读取数据
//				try {
//					commentsaboutifo="点此查看评论";
//					titlename=util.getPlaceName();
//					name = util.getPlaceName();
//					location = util.getPlaceAdress();
//					content = util.getPlaceDescription();
//					//Infolist=util.getCommentsData();
//					// ....��ȡͼƬ
//					iconurl = util.getPlaceIconURL();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
				return null;
			}
		}.execute();

	}
	/**
	 * 设置点击监听器
	 * 1.back_button 返回地图界面
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_button:
		{
//			Intent intent = new Intent(PlaceInfoActivity.this,
//					MainActivity.class);
//			startActivity(intent);
			finish();
			break;}
		case R.id.gotocomments: 
		{
			Intent intent = new Intent(PlaceInfoActivity.this,
					CommentslistActivity.class);
			intent.putExtra("id", id);
			startActivity(intent);
			// finish();
			break;
		}
			
		case R.id.gotoaddcomments:
			{
				System.out.println("click button to gotoaddcomment");
				Intent intent = new Intent(PlaceInfoActivity.this,
						AddCommentActivity.class);
				intent.putExtra("id", id);
				startActivity(intent);
//				finish();
				break;
			}
			
		}

	}

	public void showLoading() {
		mLoading.setVisibility(View.VISIBLE);
		AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
		aa.setDuration(1000);
		ScaleAnimation sa = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f);
		sa.setDuration(1000);
		AnimationSet set = new AnimationSet(false);
		set.addAnimation(sa);
		set.addAnimation(aa);
		mLoading.setAnimation(set);
		mLoading.startAnimation(set);
	}

	public void hideLoading() {
		AlphaAnimation aa = new AlphaAnimation(1.0f, 0.0f);
		aa.setDuration(1000);
		ScaleAnimation sa = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f);
		sa.setDuration(1000);
		AnimationSet set = new AnimationSet(false);
		set.addAnimation(sa);
		set.addAnimation(aa);
		mLoading.setAnimation(set);
		mLoading.startAnimation(set);
		mLoading.setVisibility(View.INVISIBLE);
	}
	
}
