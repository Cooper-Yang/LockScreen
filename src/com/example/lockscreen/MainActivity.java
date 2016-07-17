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
		setContentView(R.layout.activity_main);           ////ע��1�����ò���
		
		Intent myintent=new Intent(MainActivity.this,ScreenService.class);  /////ע��2����������������������Ѿ�������Ҳ����ͻ
		startService(myintent);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);                   
		return true;
	}
	
	public void onClickOK(View v){                        ////////ע��3�������ġ���ť�����Ժ���õĺ���
		 TextView t=(TextView)findViewById(R.id.editText1);   /////
		 String s=t.getText().toString();
		 if(s.length()!=6)                                  ////ע��4��������������λ������������
		 {
			 TextView t2=(TextView)findViewById(R.id.textView1);
			 t2.setText("������6λ����");
		 }
		 else{                                                       
			 SharedPreferences preferences=getSharedPreferences("user",Context.MODE_PRIVATE);           
			 Editor editor=preferences.edit();                 ////////ע��5����������ַ������ܱ��浽SharedPreferences
			 String hashword=hash(s);
			 editor.putString("hashValue", hashword);
			 editor.commit();
			 
			 TextView t2=(TextView)findViewById(R.id.textView1);
			 t2.setText("�ѱ���");
			 t.setText("");
		 }
	}

	public String hash(String s)                         ////////ע��6�������㷨�������д�Ĵպ���
	{
		int h=Integer.parseInt(s);
		h=h*13+34;
		String r=""+h;
		return r;
	}

}
