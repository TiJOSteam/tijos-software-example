import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;

import tijos.runtime.net.ntp.NTPUDPClient;
import tijos.runtime.net.ntp.TimeInfo;
import tijos.runtime.networkcenter.TiNetworkCenter;

/**
 * Network Time Protocol Client 例程
 * 从指NTP服务器获得网络时间
 *
 */
public class NTPClient {

	public static void main(String[] args) {
		 
		//启动WLAN及DNS
		TiNetworkCenter.getNetworkCenter().getWLAN().startup(10000);
		TiNetworkCenter.getNetworkCenter().getDNS().startup();
		
		
		NTPUDPClient ntpcli = new NTPUDPClient();
		long interval = 0;
		try {
			//NTP 服务器
			InetAddress host = InetAddress.getByName("58.220.207.226"); //NTP Server IP, get it from http://ntp.org.cn/
			
			//从NTP服务器通过UDP获得时间 
			TimeInfo tm = ntpcli.getTime(host);
			
			//获得与本地时间的差值
			interval = tm.getOffset();
			
		} catch (UnknownHostException e1) {
			 
			e1.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		//显示当前准确时间 
		Calendar cal = Calendar.getInstance();
		
		cal.setTimeInMillis(interval + System.currentTimeMillis());
			
		System.out.println(cal.getTime().toString());
	}

}
