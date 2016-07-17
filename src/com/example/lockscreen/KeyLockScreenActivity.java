package com.example.lockscreen;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class KeyLockScreenActivity extends Activity{   
	
	String passwordhash;     
    String input;            
	GridView g;              
	public WindowManager wm;  
	WindowManager.LayoutParams wmParams; 
	boolean locked;           
    public LinearLayout linearLayout;          ///新增
    public TextView textView;                  ///新增
	
	private BroadcastReceiver myReceiver = new BroadcastReceiver()
	{  
		  
	   @Override  
	   public void onReceive(Context context, Intent intent)
	   {  
	       	String s=intent.getAction();
	       	if(s.equalsIgnoreCase(Intent.ACTION_SCREEN_ON))
	       	{
	       		input="";
	       		
                if(locked==false)
                {
      		    
	       		    wm.addView(linearLayout, wmParams);
	       		    locked=true;
                }else{
                	;
                }
	       		
	        } 
	   }
	};
	
	
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
            // TODO Auto-generated method stub
            super.onWindowFocusChanged(hasFocus);
            
            /////////        
    }
	protected void onCreate(Bundle savedInstanceState) {     
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);           
		
		IntentFilter filter = new IntentFilter();                       
        filter.addAction(Intent.ACTION_SCREEN_ON); 
        filter.setPriority(Integer.MAX_VALUE);  
        registerReceiver(myReceiver, filter);
		
        final LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        linearLayout = (LinearLayout)inflater.inflate(R.layout.back,null,false).findViewById(R.id.lockscreen);
       
    
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);  
		setContentView(R.layout.activity_keylockscreen);                  
		passwordhash=getPasswordhash();                               
		locked=false;                                                  
        
		
		wm=(WindowManager)getApplicationContext().getSystemService("window");  
        wmParams = new WindowManager.LayoutParams();   

        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR ; 
        wmParams.format = PixelFormat.OPAQUE;
        wmParams.flags=WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD                
                //| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN            ////更改：取消全屏，显示状态栏
                | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR;
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        wmParams.width=metric.widthPixels;
        wmParams.height=metric.heightPixels;
        wmParams.format=PixelFormat.RGBA_8888;                   
        
        
        
        textView=(TextView)linearLayout.findViewById(R.id.textView);            ///以下是新增    
        ImageView one=(ImageView)linearLayout.findViewById(R.id.one);
        one.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_1(v);
        		        }
        		    });
        ImageView two=(ImageView)linearLayout.findViewById(R.id.two);
        two.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_2(v);
        		        }
        		    });
        ImageView three=(ImageView)linearLayout.findViewById(R.id.three);
        three.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_3(v);
        		        }
        		    });
        ImageView four=(ImageView)linearLayout.findViewById(R.id.four);
        four.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_4(v);
        		        }
        		    });
        ImageView five=(ImageView)linearLayout.findViewById(R.id.five);
        five.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_5(v);
        		        }
        		    });
        ImageView six=(ImageView)linearLayout.findViewById(R.id.six);
        six.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_6(v);
        		        }
        		    });
        ImageView seven=(ImageView)linearLayout.findViewById(R.id.seven);
        seven.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_7(v);
        		        }
        		    });
        ImageView eight=(ImageView)linearLayout.findViewById(R.id.eight);
        eight.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_8(v);
        		        }
        		    });
        ImageView nine=(ImageView)linearLayout.findViewById(R.id.nine);
        nine.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_9(v);
        		        }
        		    });
        ImageView zero=(ImageView)linearLayout.findViewById(R.id.zero);
        zero.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_1(v);
        		        }
        		    });
        ImageView back=(ImageView)linearLayout.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_del(v);
        		        }
        		    });

              
        
        sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));  ///这句原来就有
        ///////////
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {      
	    switch (keyCode) {
	        case KeyEvent.KEYCODE_BACK:
	        case KeyEvent.KEYCODE_MENU:
	            return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	public String getPasswordhash(){                 
		SharedPreferences preferences=getSharedPreferences("user",Context.MODE_PRIVATE);
		String hashValue=preferences.getString("hashValue", hash("123456"));
		return hashValue;
	}
	public String hash(String s)                     
	{
		int h=Integer.parseInt(s);
		h=h*13+34;
		String r=""+h;
		return r;
	}
	public void input(String _input){                   ////更改：只在输入长度小于6的情况下输入
		if(input.length()<6){                        
		    input=input+_input;
		}else {                                         
			;
		}
		
	}
	public void clearInput(){                     
		input="";
	}
	public boolean isMatch(){                     
		
		if(hash(input).equals(passwordhash)){
			return true;
		}else{
			return false;
		}
	}

	
	
	public void onClick_1(View v){               ///更改：全都添加了show（），用于在textView上显示文本
		input("1");
		show();
		tryDeblock();
	}
	public void onClick_2(View v){
		input("2");
		show();
		tryDeblock();
	}
	public void onClick_3(View v){
		input("3");
		show();
		tryDeblock();
	}
	public void onClick_4(View v){
		input("4");
		show();
		tryDeblock();
	}
	public void onClick_5(View v){
		input("5");
		show();
		tryDeblock();
	}
	public void onClick_6(View v){
		input("6");
		show();
		tryDeblock();
	}
	public void onClick_7(View v){
		input("7");
		show();
		tryDeblock();
	}
	public void onClick_8(View v){
		input("8");
		show();
		tryDeblock();
	}
	public void onClick_9(View v){
		input("9");
		show();
		tryDeblock();
	}
	public void onClick_0(View v){
		input("0");
		show();
		tryDeblock();
	}
	public void onClick_del(View v){
		show();
		clearInput();
	}
	public void show()
	{
		String star="";
		for(int i=0;i<input.length();i++)
		{
			star=star+"*";
		}
		textView.setText(star);
		
	}
	public void tryDeblock()                 ////更改：新增了输入长度的判断
	{
		if(input.length()<6)
		{
			;
		}else if(input.length()==6){           ////只在长度是6的情况下判断密码是否正确
			if(isMatch()){                      
			    wm.removeView(linearLayout);               
			    this.finish();                   
		    }else {
			    input="";
			    textView.setText("密码错误");
		    }
		}
	}

}
