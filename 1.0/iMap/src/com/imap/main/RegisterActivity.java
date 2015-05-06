package com.imap.main;

import com.example.imap.R;
import com.imap.database.HttpUtil;
import com.imap.database.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	EditText username_view, psw1_view, psw2_view, email_view, msg_view;
	Spinner spinner;
	Button register_button;
	String username,psw1,psw2,email,msg,sex;
	String[] list = {"Male", "Female", "Secret ^_^"};
	int id;
	ProgressDialog pd;
	final int OK = 1;
	
	final String RES_NAME = "username";
	final String RES_PASS = "password";
	final String LOG_PASS = "log_password";
	final String CHECK_KEY = "isChecked";
	final String TAG_USERINFO = "userinfo";
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			pd.dismiss();
			if(msg.what==OK){
				SharedPreferences remdname = getSharedPreferences(TAG_USERINFO,Context.MODE_PRIVATE);
				SharedPreferences.Editor edit = remdname.edit();
				edit.putString(RES_NAME, "");
				edit.putString(LOG_PASS, "");
				edit.putString(RES_PASS, psw1);
				edit.putBoolean(CHECK_KEY, false);
				edit.commit();
				startOther();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		setupView();
	}
	
	public void setupView(){
		username_view = (EditText) findViewById(R.id.username_view);
		psw1_view = (EditText) findViewById(R.id.psw1_view);
		psw2_view = (EditText) findViewById(R.id.psw2_view);
		email_view = (EditText) findViewById(R.id.email_view);
		msg_view  = (EditText) findViewById(R.id.msg_view);
		spinner = (Spinner) findViewById(R.id.spinner);
		register_button = (Button) findViewById(R.id.register_button);
		pd = new ProgressDialog(this);
		ArrayAdapter aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
		spinner.setAdapter(aa);
		spinner.setOnItemSelectedListener(new SpinnerListener());
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	public void clickButton(View view){
		if((username=username_view.getText().toString()).equals("")){
			Toast.makeText(this, "Please input name", 1).show();
			return;
		}
		if((psw1=psw1_view.getText().toString()).equals("")||(psw2=psw2_view.getText().toString()).equals("")){
			Toast.makeText(this, "Please input password", 1).show();
			return;
		}
		if(!(psw1=psw1_view.getText().toString()).equals(psw2=psw2_view.getText().toString())){
			Toast.makeText(this, "Your password is in mess!", 1).show();
			return;
		}
		email = email_view.getText().toString();
		msg = msg_view.getText().toString();
		new Thread(){
			public void run(){
				id = new HttpUtil().UserRegister(username, psw1, sex, email, msg);
				Message message = new Message();
				message.what=OK;
				handler.sendMessage(message);
			}
		}.start();
		
		
	}
	
	public void startOther(){
		if(id==-1){
			Toast.makeText(this, "Username has been used!", 1).show();
			return;
		}
		Toast.makeText(this, "Completed!\nEnjoy yourself!", 1).show();
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setMsg(msg);
		user.setName(username);
		user.setSex(sex);
		Intent intent = new Intent(this, MainActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("user", user);
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
	}
	
	class SpinnerListener implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			if(position==0){
				sex = "male";
			}else if(position==1){
				sex="female";
			}else{
				sex="secret";
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			sex="secret";
		}
		
	}
	
}
