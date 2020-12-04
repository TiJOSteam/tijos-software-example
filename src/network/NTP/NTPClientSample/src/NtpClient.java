import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;

import tijos.framework.networkcenter.ntp.NTPUDPClient;
import tijos.framework.networkcenter.ntp.TimeInfo;
import tijos.framework.platform.lpwan.lte.TiLTE;

/**
 * Network Time Protocol Client 例程
 * 从指NTP服务器获得网络时间
 *
 * @author TiJOS
 */
public class NtpClient {

    public static void main(String[] args) {

        long interval = 0;

        try {
            //启动WLAN及DNS
            TiLTE.getInstance().startup(10);

            NTPUDPClient ntpcli = new NTPUDPClient();

            //NTP 服务器, NTP Server IP, get it from http://ntp.org.cn/
            InetAddress host = InetAddress.getByName("203.107.6.88");

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
