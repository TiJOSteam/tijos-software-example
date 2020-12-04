
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import tijos.framework.networkcenter.dns.TiDNS;
import tijos.framework.platform.wlan.TiWiFi;

/**
 * TCP Server 例程
 *
 * @author TiJOS
 */
public class TcpServer {

    /**
     * Runs the server.
     */
    public static void main(String[] args) {
        ServerSocket listener = null;
        Socket socket = null;
        try {
            //启动WLAN及DNS
            TiWiFi.getInstance().startup(10);
            TiDNS.getInstance().startup();

            System.out.println("local ip = " + InetAddress.getByName("localhost"));

            listener = new ServerSocket(8080);

            //listen timeout: 60 seconds
            listener.setSoTimeout(60000);
            socket = listener.accept();
            System.out.println("a client is connected: " + socket.getRemoteSocketAddress());

            InputStream input = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            out.write("Hello, This is the server.".getBytes());

            byte[] buff = new byte[1024];
            while (true) {
                int len = 0;
                while (len <= 0) {
                    len = input.read(buff);
                    if (len > 0) {
                        System.out.println("message from client:" + new String(buff, 0, len));

                        System.out.println("this is server");
                        out.write(buff, 0, len);
                        out.flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (listener != null) {
                try {
                    listener.close();
                } catch (IOException e) {
                }
            }

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }

    }
}