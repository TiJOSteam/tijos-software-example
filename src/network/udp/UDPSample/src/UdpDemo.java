 
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import tijos.framework.networkcenter.TiDNS;
import tijos.framework.networkcenter.TiWLAN;


/**
 * UDP 例程, 运行之前请设置正确的IP地址
 * 
 * @author TiJOS
 */
public class UdpDemo {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) 
    {
    	System.out.println("UdpDemo start...");
		DatagramSocket udpSocket  = null;
    	try
    	{
    		//启动WLAN及DNS
    		TiWLAN.getInstance().startup(10);
    		TiDNS.getInstance().startup();

    		
			udpSocket = new DatagramSocket();
	        String host = "192.168.1.86";
	        int port = 8080;
	        
	
	        byte [] msg = ("Hello Server").getBytes();
	       
	        DatagramPacket dp = new DatagramPacket(msg, msg.length, InetAddress.getByName(host), port);
	        udpSocket.send(dp);
	        
	        byte [] buffer = new byte[1024];
	        while(true)
		    {
	        	dp.setData(buffer);
	        	dp.setAddress(null);
	        	
	            udpSocket.receive(dp);
	            
	            String info = new String(dp.getData(), 0, dp.getLength());
	            System.out.println("Received: " + info);
	            System.out.println("Remote :" + dp.getAddress().getHostAddress());
	            
	            dp.setAddress(InetAddress.getByName(host));
	            dp.setPort(port);
	            udpSocket.send(dp);
		    }
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		udpSocket.close();
    	}
    }
}