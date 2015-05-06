package com.imap.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class StartSettingPageListener implements OnClickListener {

	Context context;
	public StartSettingPageListener(Context context){
		this.context=context;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(context,SettingActivity.class);
		
		
		
	}
	

}
