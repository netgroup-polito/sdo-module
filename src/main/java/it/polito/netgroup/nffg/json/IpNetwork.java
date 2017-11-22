package it.polito.netgroup.nffg.json;

import java.util.StringTokenizer;

public class IpNetwork
{
	private String network;
	private String netmask;

	public IpNetwork(String _network, String _netmask)
	{
		network = _network;
		netmask = _netmask;
	}

	public String getValue()
	{
		return network+"/"+netmask;
	}

	public boolean contains(String host_ip)
	{
	    
	    int ipaddrInt = parseNumericAddress(host_ip);
	    if ( ipaddrInt == 0)
	      return false;
	      
	    int subnetInt = parseNumericAddress(network);
	    if ( subnetInt == 0)
	      return false;
	      
	    int maskInt = parseNumericAddress(netmask);
	    if ( maskInt == 0)
	      return false;
	      
	    //  Check if the address is part of the subnet
	    
	    if (( ipaddrInt & maskInt) == subnetInt)
	      return true;
	    return false;
	}

	private int parseNumericAddress(String ipaddr)
	{
	//  Check if the string is valid
	    
	    if ( ipaddr == null || ipaddr.length() < 7 || ipaddr.length() > 15)
	      return 0;
	      
	    //  Check the address string, should be n.n.n.n format
	    
	    StringTokenizer token = new StringTokenizer(ipaddr,".");
	    if ( token.countTokens() != 4)
	      return 0;

	    int ipInt = 0;
	    
	    while ( token.hasMoreTokens()) {
	      
	      //  Get the current token and convert to an integer value
	      
	      String ipNum = token.nextToken();
	      
	      try {
	        
	        //  Validate the current address part
	        
	        int ipVal = Integer.valueOf(ipNum).intValue();
	        if ( ipVal < 0 || ipVal > 255)
	          return 0;
	          
	        //  Add to the integer address
	        
	        ipInt = (ipInt << 8) + ipVal;
	      }
	      catch (NumberFormatException ex) {
	        return 0;
	      }
	    }
	    
	    //  Return the integer address
	    
	    return ipInt;
	}
}
