package com.example.lockscreen;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


public class KeyLockScreenActivity extends Activity{
	
	String password;
    String input;
	
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		setContentView(R.layout.activity_keylockscreen);
		password=getPassword();
		input="";
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public String getPassword(){
		return "123456";
	}
	public void input(String _input){
		input=input+_input;
	}
	public void clearInput(){
		input="";
	}
	public boolean isMatch(){
		
		if(input.equals(password)){
			return true;
		}else{
			return false;
		}
	}
	public void show(){
		TextView t=(TextView)findViewById(R.id.textView1);
		t.setText(input.toString());
		
	}
	
	
	public void onClick_1(View v){
		input("1");
		show();
		if(isMatch()){
			this.finish();
		}
	}
	public void onClick_2(View v){
		input("2");
		show();
		if(isMatch()){
			this.finish();
		}
	}
	public void onClick_3(View v){
		input("3");
		show();
		if(isMatch()){
			this.finish();
		}
	}
	public void onClick_4(View v){
		input("4");
		show();
		if(isMatch()){
			this.finish();
		}
	}
	public void onClick_5(View v){
		input("5");
		show();
		if(isMatch()){
			this.finish();
		}
	}
	public void onClick_6(View v){
		input("6");
		show();
		if(isMatch()){
			this.finish();
		}
	}
	public void onClick_7(View v){
		input("7");
		show();
		if(isMatch()){
			this.finish();
		}
	}
	public void onClick_8(View v){
		input("8");
		show();
		if(isMatch()){
			this.finish();
		}
	}
	public void onClick_9(View v){
		input("9");
		show();
		if(isMatch()){
			this.finish();
		}
	}
	public void onClick_0(View v){
		input("0");
		show();
		if(isMatch()){
			this.finish();
		}
	}
	public void onClick_back(View v){
		clearInput();
		show();
	}

}
