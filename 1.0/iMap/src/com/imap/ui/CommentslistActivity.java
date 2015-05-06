package com.imap.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.imap.R;
import com.imap.database.Comment;
import com.imap.database.HttpUtil;
import com.imap.database.LoadImageAsynTask;
import com.imap.database.Util;
import com.imap.main.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CommentslistActivity extends Activity implements OnClickListener {
	// Util util = new Util();
	ListView commentslist;
	LinearLayout mLoading;
	ImageButton mImageBack;
	private List<Map<String, Object>> Infolist;
	TextView title;
	String title_name;
	String iconurl;
	Button titlebarrightbt;
	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commentlist);
		Bundle extras = getIntent().getExtras();
		id = extras.getInt("id");
		setupView();
		setListener();
		fillData();
	}

	/**
	 * 获取各种控件对象
	 */
	public void setupView() {
		title = (TextView) this.findViewById(R.id.myTitle);
		// 加载进度条
		mLoading = (LinearLayout) this.findViewById(R.id.loading);
		mImageBack = (ImageButton) this.findViewById(R.id.back_button);
		commentslist = (ListView) this.findViewById(R.id.baselist);
		titlebarrightbt = (Button) this.findViewById(R.id.titlebarrightbt);

	}

	/**
	 * 设置监听器
	 */
	public void setListener() {
		mImageBack.setOnClickListener(this);
		titlebarrightbt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CommentslistActivity.this,
						SettingActivity.class);
				CommentslistActivity.this.startActivityForResult(intent, 0);

			}
		});
	}

	/**
	 * 填充数据
	 */
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

				title.setText("评论");// title_name

				SimpleAdapter adapter = new SimpleAdapter(
						CommentslistActivity.this, Infolist,
						R.layout.comments_item, new String[] { "user_name",
								"title", "comment" }, new int[] {
								R.id.user_name, R.id.comment_title,
								R.id.comment });
				commentslist.setAdapter(adapter);

				LoadImageAsynTask task = new LoadImageAsynTask(
						new LoadImageAsynTask.LoadImageAsynTaskCallback() {

							public void beforeLoadImage() {
								//
								// place_icon.setImageResource(R.drawable.logo);

							}

							public void afterLoadImage(Bitmap bitmap) {
								if (bitmap != null) {
									// place_icon.setImageBitmap(bitmap);
								} else {
									// place_icon.setImageResource(R.drawable.logo);
								}

							}
						});
				task.execute(iconurl);

			}

			// doInBackground

			@Override
			protected Void doInBackground(Void... params) {
				// 从数据库读取数据
				try {
					// title_name = util.getPlaceName()+ " 评论";

					Infolist = getCommentsData();
					// ....��ȡͼƬ
					// iconurl = util.getPlaceIconURL();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		}.execute();

	}

	public List<Map<String, Object>> getCommentsData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		ArrayList<Comment> arrayList = (ArrayList<Comment>) new HttpUtil()
				.getLocComment(id);
		int i = 0;
		while (i < arrayList.size()) {
			Comment comment = arrayList.get(i);
			map.put("title", null);
			map.put("user_name", comment.getUser_name());
			map.put("comment", comment.getContent());
			list.add(map);

			map = new HashMap<String, Object>();
			i++;

		}

		return list;

	}

	/**
	 * 设置点击监听器 1.back_button 返回地图界面
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_button:
			// Intent intent = new Intent(CommentslistActivity.this,
			// PlaceInfoActivity.class);
			// startActivity(intent);
			finish();
			break;

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
