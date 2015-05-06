package com.imap.ui;

import com.example.imap.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * the welcome picture
 * 
 * @author LMW
 * 
 */
public class SplanshActivity extends Activity {

	private TextView versionNumber;
	private LinearLayout mLinearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);


		setContentView(R.layout.splash);
		mLinearLayout = (LinearLayout) this.findViewById(R.id.LinearLayout01);
		versionNumber = (TextView) this.findViewById(R.id.versionNumber);
		versionNumber.setText(getVersion());

		
		if (isNetWorkConnected()) {
			// splash welcome animation
			AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
			aa.setDuration(1500);
			mLinearLayout.setAnimation(aa);
			mLinearLayout.startAnimation(aa);
			
			new Handler().postDelayed(new LoadMainTabTask(), 2000);
		} else {
			showSetNetworkDialog();
		}

	}

	private class LoadMainTabTask implements Runnable {
		public void run() {
			Intent intent = new Intent(SplanshActivity.this,
					LogInActivity.class);
			startActivity(intent);
			finish();
		}
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

	private void showSetNetworkDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle(R.string.error);
		builder.setMessage(R.string.error_no_internet);
		builder.setPositiveButton(R.string.setting, new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent();
				
				intent.setClassName("com.android.settings",
						"com.android.settings.WirelessSettings");

				startActivity(intent);
				finish();
			}
		});
		builder.setNegativeButton(R.string.exit, new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		builder.create().show();

	}

	/**
	 * 判断网络是否连接
	 */
	
	private boolean isNetWorkConnected() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();

		// WifiManager wifimanager = (WifiManager)
		// getSystemService(WIFI_SERVICE);
		// wifimanager.isWifiEnabled();
		// wifimanager.getWifiState();

		return (info != null && info.isConnected());
	}
	
}
