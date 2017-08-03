package it.polito.netgroup.datastoreclient;

import java.io.IOException;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import it.polito.netgroup.natmonitor.HttpResponseReader;
import it.polito.netgroup.nffg_template.json.NF_FGTemplateExtended;

public class DatastoreClient
{

	private String username;
	private String password;
	private String base_url;
	private String auth_token;
	private int timeout_ms;
	
	private static final Logger LOGGER = Logger.getGlobal();

	public DatastoreClient(String _username, String _password, String _base_url, int _timeout_ms)
	{
		username = _username;
		password = _password;
		base_url = _base_url;
		timeout_ms = _timeout_ms;
		auth_token = "";
	}

	public void authenticate() throws DatastoreClientAuthenticationException, DatastoreClientHTTPException
	{
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout_ms)
				.setConnectTimeout(timeout_ms).setSocketTimeout(timeout_ms).build();

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(base_url + "/login");
		request.setConfig(config);
		request.addHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
		request.addHeader("cache-control", "no-cache");

		String json = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
		StringEntity entity = new StringEntity(json, "UTF-8");
		request.setEntity(entity);

		HttpResponse response;
		try
		{
			response = client.execute(request);
		} catch (IOException e)
		{
			throw new DatastoreClientHTTPException("HTTP IO Error : " + e.getMessage());
		}

		int http_status = response.getStatusLine().getStatusCode();
		if (http_status == 200)
		{
			try
			{
				auth_token = HttpResponseReader.getContent(response);
			} catch (UnsupportedOperationException | IOException e)
			{
				throw new DatastoreClientHTTPException("HTTP IO Error : " + e.getMessage());
			}
		}
		throw new DatastoreClientAuthenticationException("Invalid HTTP Status : " + http_status);
	}

	public NF_FGTemplateExtended getTemplate(String template_name)
			throws DatastoreClientHTTPException, DatastoreClientNotAuthenticatedException,
			DatastoreClientTemplateNotFoundException, DatastoreClientAuthenticationException
	{
		try
		{
			return _getTemplate(template_name);
		} catch (DatastoreClientNotAuthenticatedException e)
		{
			authenticate();
			return _getTemplate(template_name);
		}
	}

	public NF_FGTemplateExtended _getTemplate(String template_name) throws DatastoreClientHTTPException,
			DatastoreClientNotAuthenticatedException, DatastoreClientTemplateNotFoundException
	{
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout_ms)
				.setConnectTimeout(timeout_ms).setSocketTimeout(timeout_ms).build();

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(base_url + "/v2/nf_template/" + template_name);
		request.setConfig(config);
		request.addHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
		request.addHeader("cache-control", "no-cache");
		request.addHeader("X-Auth-Token", auth_token);

		HttpResponse response;
		try
		{
			response = client.execute(request);
		} catch (IOException e)
		{
			throw new DatastoreClientHTTPException("HTTP IO Error : " + e.getMessage());
		}

		int http_status = response.getStatusLine().getStatusCode();
		if (http_status == 401)
		{
			throw new DatastoreClientNotAuthenticatedException();
		} else if (http_status == 404)
		{
			throw new DatastoreClientTemplateNotFoundException("Unable to find template with name " + template_name);
		} else if (http_status == 200)
		{

			try
			{
				String json = HttpResponseReader.getContent(response);
				return NF_FGTemplateExtended.getFromJson(json);
			} catch (UnsupportedOperationException | IOException e)
			{
				throw new DatastoreClientHTTPException("HTTP IO Error : " + e.getMessage());
			}
		} else
		{
			throw new DatastoreClientHTTPException("Invalid HTTP Status : " + http_status);
		}
	}
}
