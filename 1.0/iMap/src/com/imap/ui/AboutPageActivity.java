package com.imap.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.example.imap.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutPageActivity extends Activity implements OnClickListener {
	private TextView versionNumber;
	private LinearLayout mLinearLayout;
	TextView titile_name;
	Button titlebarrightbt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.aboutpage);
		mLinearLayout = (LinearLayout) this.findViewById(R.id.LinearLayout01);
		versionNumber = (TextView) this.findViewById(R.id.versionNumber);
		versionNumber.setText(getVersion());
		titile_name = (TextView) this.findViewById(R.id.myTitle);
		titile_name.setText(R.string.about);
		setListener();
		setimage();
	}

	private String getVersion() {
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			return "Version " + info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "Version";
		}
	}

	public void setListener() {
		findViewById(R.id.back_button).setOnClickListener(this);
		titlebarrightbt = (Button) findViewById(R.id.titlebarrightbt);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == findViewById(R.id.back_button)) {

			finish();
		}
	}

	public void setimage() {

		titlebarrightbt.setBackgroundDrawable(null);

	}

}
