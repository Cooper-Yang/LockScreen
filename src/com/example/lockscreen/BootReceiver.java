package com.example.lockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {  ////////ע��1��ע����һ���㲥�������������տ����㲥
	@Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context,ScreenService.class);   /////////ע��2�����յ������㲥�Ժ�����ʽ��ͼ������������
        //myIntent=new Intent(,ScreenService.class);                 //ע��3�����Ǳ���ע�͵��Ĵ��붼��֮ǰ�Ĳݸ壬���Ӿ��У�����Ҳ��һ��
        //myIntent.setAction("Android.intent.action.ScreenService");
        //myIntent.addFlags(android.intent.category.DEFAULT);
        context.startService(myIntent);                             
    }

}
