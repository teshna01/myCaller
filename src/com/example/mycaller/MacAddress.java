package com.example.mycaller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.List;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MacAddress extends Activity {
	private static final String TAG_NETWORK = null;
	private WifiAdmin wifiAdmin;
	//列表
	   private List<WifiConfiguration> configuratedList;
	   private List<ScanResult> scanResultList;
	   TextView textv;
	   StringBuffer ips=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mac_address);
		//取扫描得到的ip地址找对应的mac地址
		//ips=new StringBuffer(wifiAdmin.getIPAddress());
		//String ip_set=ips.toString();
		String macs = getMacFromArpCache("192.168.10.1");
		textv = (TextView) findViewById(R.id.txtv1);
		textv.setText(macs);
	}

	public String getMacFromArpCache(String ip) {
		if (ip == null)
			return null;

		BufferedReader br = null;
		try {
			FileReader fr=new FileReader("/proc/net/arp");
			//LineNumberReader lnr=new LineNumberReader(fr);
			br = new BufferedReader(fr);
			String line = br.readLine();
			int count = 0;
//		while (line != null) {
//			
//			Log.i(TAG_NETWORK, "/proc/net/arp 里的数据是------: " + line);
//			line = br.readLine();		
//		    count++;
//		}
			while (line != null) {
				line=br.readLine();
				String[] splitted = line.split("+");
				if (splitted != null && splitted.length >= 4 && ip.equals(splitted[0])) {
					// 戳去mac地址
					String mac = splitted[3];
					Log.i(TAG_NETWORK, "/proc/net/arp 里的数据是------: " + mac);
					if (mac.matches("..:..:..:..:..:..")) {
						return mac;
						
						
					}

					else {
						return null;
					}

				}

			}
			

		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}


}
