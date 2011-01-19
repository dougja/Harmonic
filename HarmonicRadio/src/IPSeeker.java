import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

import net.sf.javainetlocator.InetAddressLocator;
import net.sf.javainetlocator.InetAddressLocatorException;

public class IPSeeker
{
	private InetAddress thisIP = null;
	
	public IPSeeker()
	{
		this.findMyCountry();
	}
	
	private void findMyCountry() 
	{
	    try
	    {
			thisIP =InetAddress.getLocalHost();
			Locale Ip=InetAddressLocator.getLocale(thisIP);
			System.out.println(Ip.getDisplayName());
		} 
	    catch (UnknownHostException e) {e.printStackTrace();}
	    catch (InetAddressLocatorException e) {e.printStackTrace();}
	}
}