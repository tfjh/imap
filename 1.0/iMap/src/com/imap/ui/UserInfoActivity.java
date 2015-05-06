package com.imap.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.example.imap.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserInfoActivity extends Activity implements OnClickListener {
	private static final String HEADPORTRAIT_FILE_NAME = "iMap/photo/headportrait.jpg";
	private static final String TAG_USERINFO = "userinfo";
	TextView titile_name;

	TextView msg;
	TextView username;
	TextView gender;
	TextView email;
	LinearLayout item1, item2, item3, item4, item5;
	Button titlebarrightbt;
	ImageView headporteail;
	SharedPreferences sharedata; 
	ImageButton backbutton;
	
	final String RES_NAME = "username";
	final String RES_EMAIL = "email";
	final String RES_SEX = "sex";
	final String RES_MSG = "msg";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfopage);
//		System.out.print("~~~~~");
		setupView();
		setText();
		 setListener();
		try {
			setImage();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setupView() {
		titlebarrightbt = (Button) this.findViewById(R.id.titlebarrightbt);

		titile_name = (TextView) this.findViewById(R.id.myTitle);
		headporteail = (ImageView) this.findViewById(R.id.headportrail);
		backbutton=(ImageButton)this.findViewById(R.id.back_button);
		username = (TextView) this.findViewById(R.id.username);
		msg = (TextView) this.findViewById(R.id.msg);
		gender = (TextView) this.findViewById(R.id.gender);
		email = (TextView) this.findViewById(R.id.email);
		item1 = (LinearLayout) this.findViewById(R.id.item1);
		item2 = (LinearLayout) this.findViewById(R.id.item2);
		item3 = (LinearLayout) this.findViewById(R.id.item3);
		item5 = (LinearLayout) this.findViewById(R.id.item5);
		item4 = (LinearLayout) this.findViewById(R.id.item4);
	}

	public void setText() {
		sharedata = getSharedPreferences(TAG_USERINFO, MODE_PRIVATE); 
		titile_name.setText(R.string.userinfo);
//		titlebarrightbt.setText(R.string.save);
		username.setText(sharedata.getString(RES_NAME,""));
		msg.setText(sharedata.getString(RES_MSG,"未设置"));
		gender.setText(sharedata.getString(RES_SEX,"secret"));
		email.setText(sharedata.getString(RES_EMAIL,"未设置"));
		
	}
	public void setListener(){
		item1.setOnClickListener(this);
		item2.setOnClickListener(this);
		item3.setOnClickListener(this);
		item4.setOnClickListener(this);
		item5.setOnClickListener(this);
		backbutton.setOnClickListener(this);
	}
	public void findHeadPortrail_fromdatabase() {

	}

	public void setImage() throws FileNotFoundException {
		
		titlebarrightbt.setBackgroundDrawable(null);
		
		findHeadPortrail_fromdatabase();
		File temp = new File(Environment.getExternalStorageDirectory(),
				HEADPORTRAIT_FILE_NAME);
		// 使用默认图片
		if (temp.exists() == false)
			return;

		FileInputStream input = new FileInputStream(temp);
		Bitmap imageBitmap = BitmapFactory.decodeStream(input);
		headporteail.setImageBitmap(imageBitmap);
	}

	@Override
	public void onClick(View v) {
		if(v==backbutton){
			finish();
		}
		
		// TODO Auto-generated method stub
		if (v == item1) {
			Intent intent = new Intent(UserInfoActivity.this,
					SetHeadPortraitActivity.class);
			startActivity(intent);
			
		}
		if (v == item2) {
			Intent intent = new Intent(UserInfoActivity.this,
					ModifyinfoActivity.class);
			intent.putExtra("title", R.string.modifynickname);
			startActivity(intent);
			
		}
		if (v == item3) {
			Intent intent = new Intent(UserInfoActivity.this,
					ModifyinfoActivity.class);
			intent.putExtra("title", R.string.modifyemail);
			startActivity(intent);
			
		}
		if (v == item5) {
			Intent intent = new Intent(UserInfoActivity.this,
					ModifyinfoActivity.class);
			intent.putExtra("title", R.string.modifymsg);
			startActivity(intent);
			
		}
		if (v == item4) {
			SharedPreferences.Editor sharedata2 = getSharedPreferences(TAG_USERINFO, MODE_PRIVATE).edit();  
			
			if((gender.getText()+"").equals("secret")){
				gender.setText(R.string.man);
				sharedata2.putString("gender",gender.getText()+"");  
				sharedata2.commit();
				return;
			}
				
			if((gender.getText()+"").equals("male")){
				gender.setText(R.string.woman);
				sharedata2.putString("gender",gender.getText()+"");  
				sharedata2.commit();
				return;
			}
			if((gender.getText()+"").equals("female")){
				gender.setText(R.string.secret);
				sharedata2.putString("gender",gender.getText()+"");  
				sharedata2.commit();
				return;
			}
			
		}
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		SharedPreferences.Editor sharedata = getSharedPreferences(TAG_USERINFO, MODE_PRIVATE).edit();
		sharedata.putString(RES_SEX, gender.getText().toString());
		sharedata.commit();
		
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		setText();
		try {
			setImage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
