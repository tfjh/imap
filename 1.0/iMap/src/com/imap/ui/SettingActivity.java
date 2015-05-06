package com.imap.ui;

import java.io.File;
import java.io.FileInputStream;

import com.example.imap.R;
import com.imap.database.HttpUtil;
import com.imap.database.User;

import android.annotation.SuppressLint;
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

@SuppressLint("NewApi")
public class SettingActivity extends Activity {

	private static final String HEADPORTRAIT_FILE_NAME = "iMap/photo/headportrait.jpg";
	View view[] = new View[10];
	ImageView pic[] = new ImageView[10];
	ImageView headporteail;
	TextView titile_name;
	View aboutview;
	Button titlebarrightbt;
	LinearLayout gotouserinfo;
	
	User user = null;
	
	final String RES_NAME = "username";
	final String RES_PASS = "password";
	final String RES_EMAIL = "email";
	final String RES_SEX = "sex";
	final String RES_MSG = "msg";
	final String RES_ID = "id";
	final String TAG_USERINFO = "userinfo";
	
	ImageButton backbutton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settingpage);
		setupView();
		setListener();
		setText();
		try {
			setimage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("user");
	}

	public void setupView() {
		titile_name = (TextView) this.findViewById(R.id.myTitle);
		view[1] = this.findViewById(R.id.settingitem1);
		view[2] = this.findViewById(R.id.settingitem2);
		view[3] = this.findViewById(R.id.settingitem3);
		view[4] = this.findViewById(R.id.settingitem4);
		view[5] = this.findViewById(R.id.settingitem5);
		view[6] = this.findViewById(R.id.settingitem6);
		view[7] = this.findViewById(R.id.settingitem7);
		view[8] = this.findViewById(R.id.settingitem8);
		aboutview = this.findViewById(R.id.aboutApp);
		pic[1] = (ImageView) this.findViewById(R.id.settingpic1);
		pic[2] = (ImageView) this.findViewById(R.id.settingpic2);
		pic[3] = (ImageView) this.findViewById(R.id.settingpic3);
		pic[4] = (ImageView) this.findViewById(R.id.settingpic4);
		pic[5] = (ImageView) this.findViewById(R.id.settingpic5);
		pic[6] = (ImageView) this.findViewById(R.id.settingpic6);
		pic[7] = (ImageView) this.findViewById(R.id.settingpic7);
		pic[8] = (ImageView) this.findViewById(R.id.settingpic8);
		titlebarrightbt = (Button) this.findViewById(R.id.titlebarrightbt);
		headporteail = (ImageView) this.findViewById(R.id.headportrail);
		gotouserinfo = (LinearLayout) this.findViewById(R.id.gotouerinfo);
		backbutton=(ImageButton)this.findViewById(R.id.back_button);
	}

	public void setListener() {
		for (int i = 1; i <= 8; i++) {
			SettingListener listener = new SettingListener(pic[i], i,
					SettingActivity.this);
			view[i].setOnClickListener(listener);
			view[i].callOnClick();
			view[i].callOnClick();
		}
		gotouserinfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingActivity.this,
						UserInfoActivity.class);
				SettingActivity.this.startActivity(intent);
				
			}
		});
		aboutview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SettingActivity.this,
							AboutPageActivity.class);
				SettingActivity.this.startActivityForResult(intent, 0);
			}
		});
		backbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	public void setText() {
		titile_name.setText(R.string.setting);
	}

	public void setimage() throws Exception {

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
		input.close();
	}

	public void findHeadPortrail_fromdatabase() {
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		//upload the msg of the user to the server
		try {
			setimage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(){
			public void run() {
				SharedPreferences sp = getSharedPreferences(TAG_USERINFO, MODE_PRIVATE); 
				String name = sp.getString(RES_NAME, "");
				String password = sp.getString(RES_PASS, "");
				String sex = sp.getString(RES_SEX, "");
				String email = sp.getString(RES_EMAIL, "");
				String msg = sp.getString(RES_MSG, "");
				int id  = sp.getInt(RES_ID, 0);
				new HttpUtil().UserUpload(name, password, sex, email, msg, id);
			};
		}.start();
	}
	
}
