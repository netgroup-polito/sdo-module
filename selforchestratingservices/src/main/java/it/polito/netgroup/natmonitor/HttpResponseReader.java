package it.polito.netgroup.natmonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;

public class HttpResponseReader
{
	public static String getContent(HttpResponse response) throws UnsupportedOperationException, IOException
	{
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null)
		{
			result.append(line);
		}

		return result.toString();
	}
}
