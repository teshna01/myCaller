package com.example.mycaller;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MacAddress extends Activity {
	public static String IP;             //本机IP
    public static String MAC;            //本机MAC    
    Button btn=null;
    TextView ip_txt=null;
    TextView mac_txt=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mac_address);
		btn=(Button)findViewById(R.id.btn);
        ip_txt=(TextView)findViewById(R.id.ip_txt);
        mac_txt=(TextView)findViewById(R.id.mac_txt);
	}


	public void onclick(View v){
        switch (v.getId()) {
        case R.id.btn:
            IP = getLocalIpAddress();  //获取本机IP
            MAC = getLocalMacAddress();//获取本机MAC
            ip_txt.setText(IP);
            mac_txt.setText(MAC);
            break;
        }
    }
     
    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("WifiPreference IpAddress", ex.toString());
        }
        return null;
    }
     
    public String getLocalMacAddress() {
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }


}
