package test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class TestNetwork {

    public void doTest() {
        System.out.println( getLocalIp("192.168.0.2") );
    }

    public boolean getLocalIp(String ip) {
        Map<String,Boolean> ipMap = new HashMap<>();
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                        String localIP = inetAddress.getHostAddress().toString();
                        System.out.println("localip:"+localIP);
                        ipMap.put(localIP,true);
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return ipMap.containsKey(ip);
    }
}
