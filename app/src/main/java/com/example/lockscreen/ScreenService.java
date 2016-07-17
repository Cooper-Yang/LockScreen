package com.example.lockscreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;


public class ScreenService extends Service{
	public class Mybind extends Binder {
		public Service getMybind()
		{
			return ScreenService.this;
		}
	}
	
	private BroadcastReceiver myReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {  /////////注释4：接收到广播时调用的函数

			String s=intent.getAction();
			//如果屏幕熄灭,启动KeyScreenActivity
			if(s.equalsIgnoreCase(Intent.ACTION_SCREEN_OFF)){
				Intent myintent=new Intent(ScreenService.this,KeyLockScreenActivity.class);
				//设置Flag，防止Activity重复
				myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(myintent);
			}
		}
	};

	public void onCreate(){
		super.onCreate();
		//动态注册熄灭屏幕广播的接收器，熄屏和亮屏两个广播不能静态注册，只能用动态
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		//自己对这个广播的接收优先级设成最高
		filter.setPriority(Integer.MAX_VALUE);
		registerReceiver(myReceiver, filter);
	}
	    
	protected void onHandleIntent(Intent intent) {
		// Normally we would do some work here, like download a file.
		// For our sample, we just sleep for 5 seconds.
	}
	public IBinder onBind(Intent intent){
		return new Mybind();
	}

	public void set_layout_click(){

	}
}
