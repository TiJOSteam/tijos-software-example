 
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import tijos.runtime.networkcenter.TiNetworkCenter;


/**
 * UDP 例程, 运行之前请设置正确的IP地址
 */
public class UDPDemo {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) 
    {
		TiNetworkCenter.getNetworkCenter().getWLAN().startup(10000);
		TiNetworkCenter.getNetworkCenter().getDNS().startup();
	   	
		DatagramSocket udpSocket  = null;
    	try
    	{
			udpSocket = new DatagramSocket();
	        String host = "192.168.1.55";
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
	            System.out.println("Remote :" + dp.getAddress());
	            
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