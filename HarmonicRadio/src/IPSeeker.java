import java.net.*;
import java.util.Locale;
import java.io.*;
import net.sf.javainetlocator.*;

public class IPSeeker {
	
	public void IPSeeker() {
		findMyCountry();
	}
	private static void findMyCountry() {
		InetAddress thisIp = null;
		
		
	     try {
			thisIp =InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		try {
			
			Locale Ip=InetAddressLocator.getLocale(thisIp);
			System.out.println(Ip.getDisplayName());
			
		} catch (InetAddressLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}