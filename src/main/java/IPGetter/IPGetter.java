package IPGetter;

import java.net.*;
import java.io.*;
import java.net.InetAddress;

public class IPGetter

{

    public IPGetter() {

        try
        {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("System IP Address : "  + localhost.getHostAddress());
            // Find public IP address
            String systemipaddress = "";
            URL url_name = new URL("http://bot.whatismyipaddress.com");

            BufferedReader sc =
                    new BufferedReader(new InputStreamReader(url_name.openStream()));

            // reads system IPAddress
            systemipaddress = sc.readLine().trim();

            setSystemIP(localhost.getHostAddress().trim() );
            setPublicIP(systemipaddress );
//            System.out.println(systemipaddress );
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }




    }

    public String getSystemIP() {
        return systemIP;
    }

    public String getPublicIP() {
        return publicIP;
    }


    public void setSystemIP(String systemIP) {
        this.systemIP = systemIP;
    }

    public void setPublicIP(String publicIP) {
        this.publicIP = publicIP;
    }

    private  String publicIP;
    private String systemIP;
    public static void main(String args[]) throws Exception
    {

        IPGetter x = new IPGetter();

    }
}