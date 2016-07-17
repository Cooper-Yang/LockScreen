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
	public class Mybind extends Binder
	 {
	  public Service getMybind()
	  {
	   return ScreenService.this;
	  }
	 }
	
	private BroadcastReceiver myReceiver = new BroadcastReceiver() {  
		  
       @Override  
       public void onReceive(Context context, Intent intent) {  /////////注释4：接收到广播时调用的函数
           //Toast.makeText(context, "myReceiver receive", Toast.LENGTH_SHORT).show();  
       	//KeyguardManager km = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
       	//KeyguardLock kk = km.newKeyguardLock("");
       	//kk.disableKeyguard();
       	String s=intent.getAction();                       
       	if(s.equalsIgnoreCase(Intent.ACTION_SCREEN_OFF))   //////注释5：如果是熄灭屏幕
       	{
       		
       		Intent myintent=new Intent(ScreenService.this,KeyLockScreenActivity.class); ////////注释6：准备启动锁屏界面的意图
       	    myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);                    ////注释7：设置一下flag
       	    //myintent.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
       	    try                                                     ////////注释8：等待1秒，避免屏幕还没彻底灭前就启动界面影响美观
       	    {    
                 Thread.sleep(1000);  
            } catch (InterruptedException e) {  
                //e.printStackTrace();  
            }
       	    startActivity(myintent);                         /////注释9：启动锁屏界面
       		//wm.addView(g,wmParams);
       } 
       	/*
       	else if(s.equalsIgnoreCase(Intent.ACTION_SCREEN_ON))
       {
    	   Intent myintent=new Intent(ScreenService.this,KeyLockScreenActivity.class);
      	    myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      	    myintent.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
       }*/
   }
       	
       
 
   };
	    public void onCreate(){
	    	super.onCreate();
	    	
	    	IntentFilter filter = new IntentFilter();       ////////注释1：动态注册熄灭屏幕广播的接收器，熄屏和亮屏两个广播不能静态注册，只能用动态  
	        filter.addAction(Intent.ACTION_SCREEN_OFF); 
	        //filter.addAction(Intent.ACTION_SCREEN_ON); 
	        filter.setPriority(Integer.MAX_VALUE);          ////////注释2：自己对这个广播的接收优先级设成最高
	        registerReceiver(myReceiver, filter);           ////// 注释3：注册广播接收器
	        
	        //LockLisnter receiver = new LockLisnter();
	    	//////        
	        //wm.addView(g,wmParams);
	        
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
