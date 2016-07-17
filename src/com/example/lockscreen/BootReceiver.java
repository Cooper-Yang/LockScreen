package com.example.lockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {  ////////注释1：注册了一个广播接收器用来接收开机广播
	@Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context,ScreenService.class);   /////////注释2：接收到开机广播以后用显式意图启动锁屏服务
        //myIntent=new Intent(,ScreenService.class);                 //注释3：凡是被我注释掉的代码都是之前的草稿，无视就行，后面也是一样
        //myIntent.setAction("Android.intent.action.ScreenService");
        //myIntent.addFlags(android.intent.category.DEFAULT);
        context.startService(myIntent);                             
    }

}
