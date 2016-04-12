package com.example.lockscreen;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.view.WindowManager;


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
       public void onReceive(Context context, Intent intent) {  
           //Toast.makeText(context, "myReceiver receive", Toast.LENGTH_SHORT).show();  
       	//KeyguardManager km = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
       	//KeyguardLock kk = km.newKeyguardLock("");
       	//kk.disableKeyguard();
       	
       	
       	Intent myintent=new Intent(ScreenService.this,KeyLockScreenActivity.class);
       	myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       	myintent.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
       	 try {    
                Thread.sleep(1000);  
            } catch (InterruptedException e) {  
                //e.printStackTrace();  
            }
       	startActivity(myintent);
       }  
 
   };
	    public void onCreate(){
	    	super.onCreate();
	    	
	    	IntentFilter filter = new IntentFilter();  
	        filter.addAction(Intent.ACTION_SCREEN_OFF); 
	        //filter.addAction(Intent.ACTION_SCREEN_OFF); 
	        filter.setPriority(Integer.MAX_VALUE);  
	        registerReceiver(myReceiver, filter);
	        
	        //LockLisnter receiver = new LockLisnter();
	    	//////
	    	
	        
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
