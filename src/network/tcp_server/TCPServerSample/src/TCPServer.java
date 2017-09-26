
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.ServerSocket;
import java.net.Socket;
import tijos.runtime.networkcenter.TiNetworkCenter;
 
/**
 * TCP Server Àý³Ì
 *
 */
public class TCPServer {

	/**
	 * Runs the server.
	 */
	public static void main(String[] args) 
	{
		//Startup the WLAN 
		TiNetworkCenter.getNetworkCenter().getWLAN().startup(10000);
		TiNetworkCenter.getNetworkCenter().getDNS().startup();
		
		ServerSocket listener = null;
		Socket socket = null;
		try {
			listener = new ServerSocket(8080);
			System.out.println("local ip = " + listener.getLocalSocketAddress());

			
			//default timeout: 60 seconds
			socket = listener.accept();
			System.out.println("a client is connected: " + socket.getRemoteSocketAddress());
			
			InputStream input = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			
			out.write("Hello, This is the server.".getBytes());
			
			byte[] buff = new byte[1024];
			while(true)
			{
				int len = 0;
				while (len <= 0) 
				{
					len = input.read(buff);
					if(len > 0)
					{
						System.out.println("message from client:" + new String(buff, 0, len));
					
						System.out.println("this is server");
						out.write(buff, 0, len);
					}
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally {
			
			if(listener != null)
			{
				try {
					listener.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(socket != null)
			{
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}