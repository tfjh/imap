package com.imap.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class SettingListener implements OnClickListener {
	private static final String TAG_SETTING = "setting";
	public static int TURN_OFF = 1;
	public static int TURN_ON = 2;
	boolean setting = false;
	private View view;
	int symbol;
	String mark;
	Context context;
	SharedPreferences sharedata;

	public SettingListener(View arg0, int symbol, Context context) {
		super();
		view = arg0;
		this.symbol = symbol;
		this.context = context;
		mark = "settting" + symbol;
		sharedata = context.getSharedPreferences(TAG_SETTING,
				Activity.MODE_PRIVATE);
		setting = sharedata.getBoolean(mark, false);
	}

	@Override
	public void onClick(View arg0) {

		
		if (setting == false) {
			((ImageView) view).getDrawable().setLevel(TURN_ON);
		} else
			((ImageView) view).getDrawable().setLevel(TURN_OFF);
		setting = !setting;

		SharedPreferences.Editor sharedata2 = context.getSharedPreferences(
				TAG_SETTING, Activity.MODE_PRIVATE).edit();

		sharedata2.putBoolean(mark, setting);
		sharedata2.commit();
		return;

	}

}
