package com.example.lockscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);           ////注释1：设置布局
		
		Intent myintent=new Intent(MainActivity.this,ScreenService.class);  /////注释2：启动锁屏服务，如果服务已经启动了也不冲突
		startService(myintent);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);                   
		return true;
	}
	
	public void onClickOK(View v){                        ////////注释3：“更改”按钮按下以后调用的函数
		 TextView t=(TextView)findViewById(R.id.editText1);   /////
		 String s=t.getText().toString();
		 if(s.length()!=6)                                  ////注释4：如果输入的数字位数不对则提醒
		 {
			 TextView t2=(TextView)findViewById(R.id.textView1);
			 t2.setText("请输入6位数字");
		 }
		 else{                                                       
			 SharedPreferences preferences=getSharedPreferences("user",Context.MODE_PRIVATE);           
			 Editor editor=preferences.edit();                 ////////注释5：把输入的字符串加密保存到SharedPreferences
			 String hashword=hash(s);
			 editor.putString("hashValue", hashword);
			 editor.commit();
			 
			 TextView t2=(TextView)findViewById(R.id.textView1);
			 t2.setText("已保存");
			 t.setText("");
		 }
	}

	public String hash(String s)                         ////////注释6：加密算法，我随便写的凑合用
	{
		int h=Integer.parseInt(s);
		h=h*13+34;
		String r=""+h;
		return r;
	}

}
