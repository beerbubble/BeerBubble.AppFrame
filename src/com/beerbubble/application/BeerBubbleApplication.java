package com.beerbubble.application;

import java.util.LinkedList;

import com.beerbubble.utils.ErrorHandler;
import com.beerbubble.utils.SharedPreferenceManager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class BeerBubbleApplication extends Application {

	private static BeerBubbleApplication beerBubbleApplicationInstance;
	private static ErrorHandler crashHandler = ErrorHandler.getInstance();;
	private static LinkedList<Activity> activityList = new LinkedList<Activity>();
	
	/** 全局的SharedPreferences */
    private SharedPreferenceManager prefsManager;


	public void onCreate() {
		super.onCreate();
		BeerBubbleApplication.beerBubbleApplicationInstance = this;
		BeerBubbleApplication.setCrashHandler(this);
		prefsManager = new SharedPreferenceManager(this);
	}

	public static BeerBubbleApplication getInstance() {
		return BeerBubbleApplication.beerBubbleApplicationInstance;
	}
	
    /** 全局的SharedPreference */
    public SharedPreferenceManager getPrefsManager()
    {
        return this.prefsManager;
    }
	
	public static void addToActivityList(final Activity act) {
		BeerBubbleApplication.activityList.add(act);
	}

	public static void delFromActivityList(final Activity act) {
		if (BeerBubbleApplication.activityList.indexOf(act) != -1) {
			BeerBubbleApplication.activityList.remove(act);
		}
	}

	public static void setCrashHandler(final Context c) {
		BeerBubbleApplication.crashHandler.setToErrorHandler(c);
	}

	public static void exitApp() {
		try {
			for (Activity activity : activityList) {
				if (activity != null) {
					activity.finish();
				}
			}

		} catch (final Exception e) {
		} finally {
			System.exit(0);
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}
}
