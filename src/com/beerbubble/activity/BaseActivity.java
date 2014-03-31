package com.beerbubble.activity;

import com.beerbubble.application.BeerBubbleApplication;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
        BeerBubbleApplication.addToActivityList(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		BeerBubbleApplication.delFromActivityList(this);
        super.onDestroy();
	}
}
