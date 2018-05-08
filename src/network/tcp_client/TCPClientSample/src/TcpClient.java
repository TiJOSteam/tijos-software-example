
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.Socket;

import tijos.framework.networkcenter.TiDNS;
import tijos.framework.networkcenter.TiWLAN;

/**
 * TCP client 例程， 在运行时请设置正确的TCP Server IP地址
 * 
 * @author TiJOS
 */
public class TcpClient {
	

	public static void main(String[] args) {
	
		//TCP服务器IP及PORT
		String host = "192.168.1.86";
		int port = 8080;
		Socket client = null;
		try 
		{
			//启动WLAN及DNS
			TiWLAN.getInstance().startup(10);
			TiDNS.getInstance().startup();

			//Connect to the server with TCP 
			client = new Socket(host, port);
			System.out.print("Connect : " + client.getRemoteSocketAddress() + " local " + client.getLocalSocketAddress());

			
			OutputStream  output = client.getOutputStream();
			
			//Send data to the TCP server
			output.write("Hello, this is client".getBytes());
			output.flush();
			
			//Get remote data from the server
			InputStream input = client.getInputStream();
			
			byte[] buffer = new byte[1024];
			while (true) {
				int len = -1;
				len = input.read(buffer);
				
				if(len > 0)
				{
					System.out.println("message form server:" + new String(buffer, 0, len));
					
					//echo to the server
					output.write(buffer, 0, len);
					output.flush();
				}
			}
		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		finally
		{
			try {
				client.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}

		
	}
	
}
