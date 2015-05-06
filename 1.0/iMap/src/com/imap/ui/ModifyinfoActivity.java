package com.imap.ui;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imap.R;

public class ModifyinfoActivity extends Activity implements OnClickListener {
	private static final String TAG_USERINFO = "userinfo";
	
	TextView titile_name;
	EditText modified_view;
	Button titlebarrightbt;
	Intent intent;
	final String RES_NAME = "username";
	final String RES_EMAIL = "email";
	final String RES_SEX = "sex";
	final String RES_MSG = "msg";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editinfopage);
		intent= this.getIntent(); 
		setupView();
		setText();
		setimage();
		setListener();
		 
	}
	public void setupView() {
		
		titile_name = (TextView) this.findViewById(R.id.myTitle);
		titlebarrightbt=(Button)this.findViewById(R.id.titlebarrightbt);
		modified_view=(EditText) this.findViewById(R.id.editText);
	}

	public void setText() {
		titile_name.setText(intent.getIntExtra("title", R.string.modifynickname));
		titlebarrightbt.setText(R.string.save);
		
		SharedPreferences sharedata = getSharedPreferences(TAG_USERINFO, MODE_PRIVATE);  
		
		if(intent.getIntExtra("title", R.string.modifynickname)==R.string.modifynickname){
			
			
			modified_view.setText(sharedata.getString(RES_NAME,"未设置"));
		}
		if(intent.getIntExtra("title", R.string.modifynickname)==R.string.modifyage){
			modified_view.setText(sharedata.getString(RES_MSG,"未设置"));
		}
		if(intent.getIntExtra("title", R.string.modifynickname)==R.string.modifyemail){
			modified_view.setText(sharedata.getString(RES_EMAIL,"未设置"));
		}
		
	}
	public void setimage(){
		
		titlebarrightbt.setBackgroundDrawable(null);
	}
	public void setListener(){
		titlebarrightbt.setOnClickListener(this);
		findViewById(R.id.back_button).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==titlebarrightbt){
			SharedPreferences.Editor sharedata = getSharedPreferences(TAG_USERINFO, MODE_PRIVATE).edit();  
			if(intent.getIntExtra("title", R.string.modifynickname)==R.string.modifynickname){
				sharedata.putString(RES_NAME,modified_view.getText()+""); 
			}
			if(intent.getIntExtra("title", R.string.modifynickname)==R.string.modifymsg){
				sharedata.putString(RES_MSG,modified_view.getText()+""); 
			}
			if(intent.getIntExtra("title", R.string.modifynickname)==R.string.modifyemail){
				sharedata.putString(RES_EMAIL,modified_view.getText()+""); 
			}
			 
			sharedata.commit();
//			Intent intent = new Intent(ModifyinfoActivity.this,
//					UserInfoActivity.class);
//			
//			startActivity(intent);
			finish();
		}
		if(v==findViewById(R.id.back_button)){
//			Intent intent = new Intent(ModifyinfoActivity.this,
//					UserInfoActivity.class);
//			
//			startActivity(intent);
			finish();
		}
	}
}
