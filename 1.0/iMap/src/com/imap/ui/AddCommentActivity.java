package com.imap.ui;

import com.example.imap.R;
import com.imap.database.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddCommentActivity extends Activity implements OnClickListener{
	EditText editText;
	Button btnsave;
	ImageButton btnBack;
	TextView myTitle;
	
	private int locId;
	private int userId;
	private String username;
	private String content;
	final String TAG_USERINFO = "userinfo";
	final String RES_ID = "id";
	final String RES_NAME = "username";
	final String INFO = "添加评论";
	final String SAVE = "保存";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editinfopage);
		System.out.println("I am already in the class AddCommentActivity!");
		Bundle extras = getIntent().getExtras();
		locId = extras.getInt("id");
		SharedPreferences sp = getSharedPreferences(TAG_USERINFO, MODE_PRIVATE);
		userId = sp.getInt(RES_ID, -1);
		username = sp.getString(RES_NAME, "");
		setupView();
		setListener();
	}
	
	public void setupView(){
		editText = (EditText) findViewById(R.id.editText);
		btnsave = (Button) findViewById(R.id.titlebarrightbt);
		btnBack = (ImageButton) findViewById(R.id.back_button);
		myTitle = (TextView) findViewById(R.id.myTitle);
		editText.setSingleLine(false);
		myTitle.setText(INFO);
		btnsave.setBackgroundDrawable(null);
		btnsave.setText(SAVE);
	}
	
	public void setListener(){
		btnBack.setOnClickListener(this);
		btnsave.setOnClickListener(this);
	}
	
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.back_button:{
			finish();
			break;
			}
		case R.id.titlebarrightbt:{
			content = editText.getText().toString();
			new Thread(){
				public void run(){
					System.out.println(username);
					System.out.println(content);
					System.out.println(locId);
					System.out.println(userId);
					new HttpUtil().addComment(username, content, locId, userId);
				}
			}.start();
			
			finish();
			
			break;
			}
		}
	}
	
}
