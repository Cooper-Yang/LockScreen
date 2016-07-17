package com.example.lockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

//注册一个广播接收器用来接收开机广播
public class BootReceiver extends BroadcastReceiver {
	@Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context,ScreenService.class);
        context.startService(myIntent);
        Intent lockintent = new Intent(context,KeyLockScreenActivity.class);
        lockintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(lockintent);
    }
}
