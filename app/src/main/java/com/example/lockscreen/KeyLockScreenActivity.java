package com.example.lockscreen;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class KeyLockScreenActivity extends Activity{   
	
	String passwordhash;     
    String input = "";
	public WindowManager wm;  
	WindowManager.LayoutParams wmParams; 
	boolean locked;           
    public FrameLayout frameLayout;          ///新增
    public TextView textView;                  ///新增

	//get height of status bar
	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	//设置按钮按下和弹起的事件
	private static final View.OnTouchListener onTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				v.setBackgroundColor(Color.parseColor("#fffbff00"));
			}else if(event.getAction() == MotionEvent.ACTION_UP){
				v.setBackground(v.getBackground());
				v.setBackgroundColor(Color.parseColor("#00fbff00"));
			}
			return false;
		}
	};
	//监听屏幕点亮事件，当屏幕亮起时将输入重置
	private BroadcastReceiver myReceiver = new BroadcastReceiver()
	{
	   @Override
	   public void onReceive(Context context, Intent intent)
	   {
	       	String s=intent.getAction();
	       	if(s.equalsIgnoreCase(Intent.ACTION_SCREEN_ON))
	       	{
	       		input="";
				textView.setText("请输入6位密码");
				wm.updateViewLayout(frameLayout, wmParams);
	        } 
	   }
	};

	
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
            // TODO Auto-generated method stub
            super.onWindowFocusChanged(hasFocus);
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {     
		super.onCreate(savedInstanceState);

		//过滤屏幕点亮事件
		IntentFilter filter = new IntentFilter();                       
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.setPriority(Integer.MAX_VALUE);  
        registerReceiver(myReceiver, filter);

        //对锁屏界面进行布局，动态加载xml资源文件
		final LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
		frameLayout = (FrameLayout) inflater.inflate(R.layout.back,null,false).findViewById(R.id.lock_screen);
		frameLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
				| View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
		passwordhash=getPasswordhash();
		locked=false;
		//初始化WindowMangager
		wm=(WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE);
		//设置锁屏Window的参数
        wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        wmParams.format = PixelFormat.TRANSPARENT;
        wmParams.flags=WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN            ////更改：取消全屏，显示状态栏
				| WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR;

		//获取不包含NavigationBar和StatusBar的屏幕真实宽高
		int rawWidth = 0, rawHeight = 0;
		final DisplayMetrics metrics = new DisplayMetrics();
		Display display = getWindowManager().getDefaultDisplay();
		Method mGetRawH = null, mGetRawW = null;
		try {
			// For JellyBean 4.2 (API 17) and onward
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
				display.getRealMetrics(metrics);

				rawWidth = metrics.widthPixels;
				rawHeight = metrics.heightPixels;
			} else {
				mGetRawH = Display.class.getMethod("getRawHeight");
				mGetRawW = Display.class.getMethod("getRawWidth");

				try {
					rawWidth = (Integer) mGetRawW.invoke(display);
					rawHeight = (Integer) mGetRawH.invoke(display);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		} catch (NoSuchMethodException e3) {
			e3.printStackTrace();
		}
        wmParams.width=rawWidth;
        wmParams.height=rawHeight;
		//获取文本框，对全局参数textView进行初始化
        textView=(TextView) frameLayout.findViewById(R.id.textView);
		//设置顶部状态栏
		ImageView imageView = (ImageView)frameLayout.findViewById(R.id.background);
		LinearLayout linearLayout = (LinearLayout)frameLayout.findViewById(R.id.layout_numpad);
		TextClock textClock = (TextClock)frameLayout.findViewById(R.id.clock);
		imageView.setPadding(0,getStatusBarHeight()*2,0,0);
		linearLayout.setPadding(0,getStatusBarHeight()*2,0,0);
		//textClock.setTextSize(getStatusBarHeight());
		//对每个按钮设置监听事件
		ImageView one=(ImageView) frameLayout.findViewById(R.id.one);
        one.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_1(v);
        		        }
        		    });
		one.setOnTouchListener(onTouchListener);
        ImageView two=(ImageView) frameLayout.findViewById(R.id.two);
        two.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_2(v);
        		        }
        		    });
		two.setOnTouchListener(onTouchListener);
        ImageView three=(ImageView) frameLayout.findViewById(R.id.three);
        three.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_3(v);
        		        }
        		    });
		three.setOnTouchListener(onTouchListener);
        ImageView four=(ImageView) frameLayout.findViewById(R.id.four);
        four.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_4(v);
        		        }
        		    });
		four.setOnTouchListener(onTouchListener);
        ImageView five=(ImageView) frameLayout.findViewById(R.id.five);
        five.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_5(v);
        		        }
        		    });
		five.setOnTouchListener(onTouchListener);
        ImageView six=(ImageView) frameLayout.findViewById(R.id.six);
        six.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_6(v);
        		        }
        		    });
		six.setOnTouchListener(onTouchListener);
        ImageView seven=(ImageView) frameLayout.findViewById(R.id.seven);
        seven.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_7(v);
        		        }
        		    });
		seven.setOnTouchListener(onTouchListener);
        ImageView eight=(ImageView) frameLayout.findViewById(R.id.eight);
        eight.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_8(v);
        		        }
        		    });
		eight.setOnTouchListener(onTouchListener);
        ImageView nine=(ImageView) frameLayout.findViewById(R.id.nine);
        nine.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_9(v);
        		        }
        		    });
		nine.setOnTouchListener(onTouchListener);
        ImageView zero=(ImageView) frameLayout.findViewById(R.id.zero);
        zero.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_0(v);
        		        }
        		    });
		zero.setOnTouchListener(onTouchListener);
        ImageView back=(ImageView) frameLayout.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        		             onClick_del(v);
        		        }
        		    });
		back.setOnTouchListener(onTouchListener);
		//继续中断的广播
        sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));

		wm.addView(frameLayout, wmParams);
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
		clearInput();
		show();
	}
	public void show()
	{
		String star="";
		for(int i=0;i<input.length();i++)
		{
			star=star+"*";
		}
		textView.setText(star);
		//getWindow().findViewById(R.id.textView);
	}
	public void tryDeblock()                 ////更改：新增了输入长度的判断
	{
		if(input.length()<6)
		{
			;
		}else if(input.length()==6){           ////只在长度是6的情况下判断密码是否正确
			if(isMatch()){                      
			    wm.removeView(frameLayout);
			    this.finish();                   
		    }else {
			    input="";
			    textView.setText("密码错误");
		    }
		}
	}
}
