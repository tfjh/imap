package com.imap.ui;

import com.example.imap.R;
import com.imap.database.HttpUtil;
import com.imap.database.User;
import com.imap.database.Util;
import com.imap.main.MainActivity;
import com.imap.main.RegisterActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends Activity implements OnClickListener {
	protected static final int NEED_CAPTCHA = 10;
	protected static final int NOT_NEED_CAPTCHA = 11;
	protected static final int GET_CAPTCHA_ERROR = 12;
	protected static final int LOGIN_SUCCESS = 13;
	protected static final int LOGIN_FAIL = 14;
	private EditText mNameEditText;
	private EditText mPwdEditText;
	private LinearLayout mCaptchaLinearLayout;
	private TextView mEditTextCaptchaValue;
	private ImageView mImageViewCaptcha;
	private Button btnLogin, btnExit;
	private CheckBox checkBox;
	boolean hasChecked = false;
//	private Util util = new Util();
	ProgressDialog pd;
	User user;
	String result = null;
	
	final String RES_NAME = "username";
	final String LOG_PASS = "log_password";
	final String RES_PASS = "password";
	final String CHECK_KEY = "isChecked";
	final String TAG_USERINFO = "userinfo";
	
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			pd.dismiss();
			switch (msg.what) {

			case NEED_CAPTCHA:
				mCaptchaLinearLayout.setVisibility(View.VISIBLE);
				Bitmap bitmap = (Bitmap) msg.obj;
				mImageViewCaptcha.setImageBitmap(bitmap);
				break;
			case NOT_NEED_CAPTCHA:
				break;
			case GET_CAPTCHA_ERROR:
				Toast.makeText(getApplicationContext(), "验证码获取错误", 1)
						.show();
				break;
			case LOGIN_SUCCESS:
				// login success,go to the map page
				Intent intent = new Intent(LogInActivity.this,
						MainActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("user", user);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
				break;
			case LOGIN_FAIL:
				Toast.makeText(getApplicationContext(), "Username or Password is wrong!\nIf you have no account, register firstly please", 1).show();
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		setupView();
		setLinstener();
	}

	
	private void setupView() {
		//set text
		SharedPreferences remdname = getSharedPreferences(TAG_USERINFO,MODE_PRIVATE);
		String username = remdname.getString(RES_NAME, "");
		String password = remdname.getString(LOG_PASS, "");
		boolean isChecked = remdname.getBoolean(CHECK_KEY, false);
		hasChecked = isChecked;
		
		
		mNameEditText = (EditText) this.findViewById(R.id.EditTextEmail);
		mPwdEditText = (EditText) this.findViewById(R.id.EditTextPassword);
		mNameEditText.setText(username);
		mPwdEditText.setText(password);
		
		mCaptchaLinearLayout = (LinearLayout) this
				.findViewById(R.id.ll_captcha);
		mEditTextCaptchaValue = (TextView) this
				.findViewById(R.id.EditTextCaptchaValue);
		mImageViewCaptcha = (ImageView) this
				.findViewById(R.id.ImageViewCaptcha);
		btnExit = (Button) this.findViewById(R.id.btnExit);
		btnLogin = (Button) this.findViewById(R.id.btnLogin);
		checkBox = (CheckBox) this.findViewById(R.id.login_check);
		checkBox.setChecked(isChecked);
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			   
			   @Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					hasChecked = true;
				}
				if (!isChecked) {
					hasChecked = false;
				}
			}
		});
		getCaptcha();

	}

	private void getCaptcha() {
		// �ж��Ƿ���Ҫ������֤��
		pd = new ProgressDialog(this);
		pd.setMessage("正在登陆");
		pd.show();
		new Thread() {
			@Override
			public void run() {

				try {
//					result = util.isNeedCaptcha();
					result = null;
					if (result != null) {
						// �д�ʵ��
						// ������֤���ȡ���Ӧ��ͼƬ
						// String imagepath =
						// getResources().getString(R.string.captchaurl)+result+"&amp;size=s";
						// Bitmap bitmap = NetUtil.getImage(imagepath);
						Message msg = new Message();
						msg.what = NEED_CAPTCHA;
						// msg.obj = bitmap;
						handler.sendMessage(msg);
					} else {
						Message msg = new Message();
						msg.what = NOT_NEED_CAPTCHA;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {

					e.printStackTrace();
					Message msg = new Message();
					msg.what = GET_CAPTCHA_ERROR;
					handler.sendMessage(msg);
				}

			}

		}.start();
	}

	// 3.���õ���¼�
	private void setLinstener() {
		btnExit.setOnClickListener(this);
		btnLogin.setOnClickListener(this);
		mImageViewCaptcha.setOnClickListener(this);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLogin:
			final String name = mNameEditText.getText().toString();
			final String pwd = mPwdEditText.getText().toString();
			
			//store the username and the password
			if (hasChecked) {
				SharedPreferences remdname = getSharedPreferences(TAG_USERINFO,MODE_PRIVATE);
				SharedPreferences.Editor edit = remdname.edit();
				edit.putString(RES_NAME, mNameEditText.getText().toString());
				edit.putString(LOG_PASS, mPwdEditText.getText().toString());
				edit.putBoolean(CHECK_KEY, true);
				edit.commit();
			} else {
				SharedPreferences remdname = getSharedPreferences(TAG_USERINFO,MODE_PRIVATE);
				SharedPreferences.Editor edit = remdname.edit();
				edit.putString(RES_NAME, "");
				edit.putString(LOG_PASS, "");
				edit.putBoolean(CHECK_KEY, false);
				edit.commit();
			}
		
		
			if ("".equals(name) || "".equals(pwd)) {
				Toast.makeText(this, "Please input your name and password", 1).show();
				return;
			} else {
				if (result != null) {
					final String captchavalue = mEditTextCaptchaValue.getText()
							.toString();
					if ("".equals(captchavalue)) {
						Toast.makeText(this, "Please input the captcha", 1).show();
						return;
					} else {
						login(name, pwd, captchavalue);
					}

				} else {
					login(name, pwd, "");
				}

			}
			break;
		case R.id.btnExit:
			Intent intent = new Intent(this,RegisterActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.ImageViewCaptcha:
			getCaptcha();
			break;

		}

	}

	private void login(final String name, final String pwd,
			final String captchavalue) {
		// ��½�Ĳ���
		pd.setMessage("Login....");
		pd.show();
		new Thread() {

			@Override
			public void run() {
				try {
					user = new HttpUtil().UserLogin(name, pwd);
					if (user!=null) {
						SharedPreferences remdname = getSharedPreferences(TAG_USERINFO,MODE_PRIVATE);
						SharedPreferences.Editor edit = remdname.edit();
						edit.putString(RES_PASS, mPwdEditText.getText().toString());
						edit.commit();
						Message msg = new Message();
						msg.what = LOGIN_SUCCESS;
						handler.sendMessage(msg);
					} else {
						Message msg = new Message();
						msg.what = LOGIN_FAIL;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
					Message msg = new Message();
					msg.what = LOGIN_FAIL;
					handler.sendMessage(msg);
				}

			}
		}.start();
	}
	
	

}
