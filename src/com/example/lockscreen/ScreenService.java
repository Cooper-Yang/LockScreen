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
       public void onReceive(Context context, Intent intent) {  /////////ע��4�����յ��㲥ʱ���õĺ���
           //Toast.makeText(context, "myReceiver receive", Toast.LENGTH_SHORT).show();  
       	//KeyguardManager km = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
       	//KeyguardLock kk = km.newKeyguardLock("");
       	//kk.disableKeyguard();
       	String s=intent.getAction();                       
       	if(s.equalsIgnoreCase(Intent.ACTION_SCREEN_OFF))   //////ע��5�������Ϩ����Ļ
       	{
       		
       		Intent myintent=new Intent(ScreenService.this,KeyLockScreenActivity.class); ////////ע��6��׼�����������������ͼ
       	    myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);                    ////ע��7������һ��flag
       	    //myintent.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
       	    try                                                     ////////ע��8���ȴ�1�룬������Ļ��û������ǰ����������Ӱ������
       	    {    
                 Thread.sleep(1000);  
            } catch (InterruptedException e) {  
                //e.printStackTrace();  
            }
       	    startActivity(myintent);                         /////ע��9��������������
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
	    	
	    	IntentFilter filter = new IntentFilter();       ////////ע��1����̬ע��Ϩ����Ļ�㲥�Ľ�������Ϩ�������������㲥���ܾ�̬ע�ᣬֻ���ö�̬  
	        filter.addAction(Intent.ACTION_SCREEN_OFF); 
	        //filter.addAction(Intent.ACTION_SCREEN_ON); 
	        filter.setPriority(Integer.MAX_VALUE);          ////////ע��2���Լ�������㲥�Ľ������ȼ�������
	        registerReceiver(myReceiver, filter);           ////// ע��3��ע��㲥������
	        
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
