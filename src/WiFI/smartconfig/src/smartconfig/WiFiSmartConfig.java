package smartconfig;

import java.io.IOException;

import tijos.framework.platform.wlan.TiWiFi;
import tijos.framework.util.Delay;

/**
 * SmartConfig WIFI 通过手机APP进行WIFI快速连接配置例程
 * 手机APP及源码可从以下路径获得 https://github.com/EspressifApp/EsptouchForAndroid
 * 该配置执行一次即可，下次启动会自动连接WIFI
 * @author tijos
 *
 */
public class WiFiSmartConfig {

	public static void main(String[] args) {
		try {
				
			   //请启动用于配置WIFI的手机APP, 并且该手机已连接到WIFI路由器
				System.out.println("Start smart configuration ... ");
				
				//启动SmartConfig 30秒超时
				TiWiFi.getInstance().smartConfig(30);
				
				//配置成功
				System.out.println("OK");
				
				//当前配置为
				System.out.println(" SSID: " + TiWiFi.getInstance().getSSID());
				System.out.println(" Password: " + TiWiFi.getInstance().getPassword());
			
			
				Delay.msDelay(1000);
				
			}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}

}
