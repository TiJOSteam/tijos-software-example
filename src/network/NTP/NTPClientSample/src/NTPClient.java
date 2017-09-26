import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;

import tijos.runtime.net.ntp.NTPUDPClient;
import tijos.runtime.net.ntp.TimeInfo;
import tijos.runtime.networkcenter.TiNetworkCenter;

/**
 * Network Time Protocol Client ����
 * ��ָNTP�������������ʱ��
 *
 */
public class NTPClient {

	public static void main(String[] args) {
		 
		//����WLAN��DNS
		TiNetworkCenter.getNetworkCenter().getWLAN().startup(10000);
		TiNetworkCenter.getNetworkCenter().getDNS().startup();
		
		
		NTPUDPClient ntpcli = new NTPUDPClient();
		long interval = 0;
		try {
			//NTP ������
			InetAddress host = InetAddress.getByName("58.220.207.226"); //NTP Server IP, get it from http://ntp.org.cn/
			
			//��NTP������ͨ��UDP���ʱ�� 
			TimeInfo tm = ntpcli.getTime(host);
			
			//����뱾��ʱ��Ĳ�ֵ
			interval = tm.getOffset();
			
		} catch (UnknownHostException e1) {
			 
			e1.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		//��ʾ��ǰ׼ȷʱ�� 
		Calendar cal = Calendar.getInstance();
		
		cal.setTimeInMillis(interval + System.currentTimeMillis());
			
		System.out.println(cal.getTime().toString());
	}

}
