
import java.net.InetAddress;
import java.net.UnknownHostException;

import tijos.framework.networkcenter.TiDNS;
import tijos.framework.networkcenter.TiWLAN;

/**
 * 
 * 本例程演示如何获取系统信息以及通过java标准InetAddress访问TiJOS的DNS服务，获取指定域名IP地址
 * 
 * @author TiJOS
 */

public class DnsDemo {

	public static void main(String[] args) {

		//启动WLAN及DNS
		TiWLAN.getInstance().startup(10);
		TiDNS.getInstance().startup();

		try {
			//System properties
			System.out.println("java.vm.name = " + System.getProperty("java.vm.name"));
			System.out.println("java.vm.version = " + System.getProperty("java.vm.version"));

			System.out.println("os.name = " + System.getProperty("os.name"));
			System.out.println("os.version = " + System.getProperty("os.version"));
			System.out.println("os.arch = " + System.getProperty("os.arch"));

			System.out.println("host.name = " + System.getProperty("host.name"));
			System.out.println("host.id = " + System.getProperty("host.id"));

			//local ip
			System.out.println(" localhost ip by name = " + InetAddress.getLocalHost());
			
			//DNS
			System.out.println(" localhost ip = " + InetAddress.getByName("localhost"));
			System.out.println(" baidu ip: " + InetAddress.getByName("www.baidu.com"));
			System.out.println(" 163 ip: " + InetAddress.getByName("www.163.com"));
			System.out.println(" google ip: " + InetAddress.getByName("www.google.com"));
			System.out.println(" qq ip: " + InetAddress.getByName("www.qq.com"));
			
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException");
		}

	}

}
