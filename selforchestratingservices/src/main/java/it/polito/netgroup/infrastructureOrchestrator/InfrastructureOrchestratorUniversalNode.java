package it.polito.netgroup.infrastructureOrchestrator;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.polito.netgroup.nffg.json.NF_FGExtended;

public class InfrastructureOrchestratorUniversalNode implements InfrastructureOrchestrator
{

	private String username;
	private String password;
	private String base_url;
	private String auth_token;
	private int timeout_ms;

	private static final Logger LOGGER = Logger.getGlobal();

	public InfrastructureOrchestratorUniversalNode(String _username, String _password, String _base_url, int _timeout_ms)
	{
		username = _username;
		password = _password;
		base_url = _base_url;
		timeout_ms = _timeout_ms;
		auth_token = "";
	}

	public boolean getNFFGStatus(String nffg_name)
	{
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public NF_FGExtended getNFFG(String nffg_id)
			throws InfrastructureOrchestratorHTTPException, InfrastructureOrchestratorAuthenticationException,
			InfrastructureOrchestratorNotAuthenticatedException, InfrastructureOrchestratorNF_FGNotFoundException
	{
		try
		{
			return _getNFFG(nffg_id);
		} catch (InfrastructureOrchestratorNotAuthenticatedException e)
		{
			authenticate();
			return _getNFFG(nffg_id);
		}
	}

	public void removeNFFG(String nffg_name) throws InfrastructureOrchestratorHTTPException,
			InfrastructureOrchestratorAuthenticationException, InfrastructureOrchestratorNotAuthenticatedException
	{
		try
		{
			_removeNFFG(nffg_name);
		} catch (InfrastructureOrchestratorNotAuthenticatedException e)
		{
			authenticate();
			_removeNFFG(nffg_name);
		}
	}

	public void addNFFG(NF_FGExtended nffg) throws JsonProcessingException, InfrastructureOrchestratorHTTPException,
			InfrastructureOrchestratorAuthenticationException, InfrastructureOrchestratorNotAuthenticatedException
	{
		String nffg_json = nffg.getJson();
		try
		{
			_addNFFG(nffg.getId(),nffg_json);
		} catch (InfrastructureOrchestratorNotAuthenticatedException e)
		{
			authenticate();
			_addNFFG(nffg.getId(),nffg_json);
		}
	}
	
	public void addNFFG(String id,String nffg_json) throws JsonProcessingException, InfrastructureOrchestratorHTTPException,
	InfrastructureOrchestratorAuthenticationException, InfrastructureOrchestratorNotAuthenticatedException
	{
		try
		{
			_addNFFG(id,nffg_json);
		} catch (InfrastructureOrchestratorNotAuthenticatedException e)
		{
			authenticate();
			_addNFFG(id,nffg_json);
		}
	}

	private void _addNFFG(String id, String nffg_json) throws InfrastructureOrchestratorNotAuthenticatedException,
			InfrastructureOrchestratorHTTPException, JsonProcessingException
	{
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout_ms)
				.setConnectTimeout(timeout_ms).setSocketTimeout(timeout_ms).build();

		HttpClient client = HttpClientBuilder.create().build();
		HttpPut request = new HttpPut(base_url + "/NF-FG/" + id);
		request.setConfig(config);
		request.addHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
		request.addHeader("cache-control", "no-cache");
		request.addHeader("X-Auth-Token", auth_token);
		
		request.setEntity(new StringEntity(nffg_json, "UTF-8"));

		HttpResponse response;
		LOGGER.log(Level.FINE,"Adding a new NFFG with id "+id);
		
		try
		{
			response = client.execute(request);
		} catch (IOException e)
		{
			LOGGER.log(Level.SEVERE,"IOException during the HTTP Request");
			throw new InfrastructureOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
		}

		int http_status = response.getStatusLine().getStatusCode();
		LOGGER.log(Level.FINE,"Response HTTP status code: "+http_status);
		if (http_status == 401)
		{
			LOGGER.log(Level.FINE,"Authentication required");
			throw new InfrastructureOrchestratorNotAuthenticatedException();
		}
		else if ( http_status != 200 && http_status != 201 )
		{
			LOGGER.log(Level.SEVERE,"Invalid HTTP status code: "+http_status);
			throw new InfrastructureOrchestratorHTTPException(nffg_json);
		}
		LOGGER.log(Level.FINE,"NFFG added.");
	}

	private void _removeNFFG(String nffg_id)
			throws InfrastructureOrchestratorHTTPException, InfrastructureOrchestratorNotAuthenticatedException
	{
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout_ms)
				.setConnectTimeout(timeout_ms).setSocketTimeout(timeout_ms).build();

		HttpClient client = HttpClientBuilder.create().build();
		HttpDelete request = new HttpDelete(base_url + "/NF-FG/" + nffg_id);
		request.setConfig(config);
		request.addHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
		request.addHeader("cache-control", "no-cache");
		request.addHeader("X-Auth-Token", auth_token);

		LOGGER.log(Level.FINE,"Removing NFFG with id "+nffg_id);

		HttpResponse response;
		try
		{
			response = client.execute(request);
		} catch (IOException e)
		{
			LOGGER.log(Level.SEVERE,"IOException during the HTTP Request");
			throw new InfrastructureOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
		}

		int http_status = response.getStatusLine().getStatusCode();
		LOGGER.log(Level.FINE,"Response HTTP status code: "+http_status);

		if (http_status == 401)
		{
			LOGGER.log(Level.FINE,"Authentication required");
			throw new InfrastructureOrchestratorNotAuthenticatedException();
		}
		else if (http_status == 405)
		{
			LOGGER.log(Level.WARNING,"The NFFG "+nffg_id+" does not exist on the infrastructure");
		}
		else if ( http_status != 204 )
		{
			LOGGER.log(Level.SEVERE,"Invalid HTTP status code: "+http_status);
			throw new InfrastructureOrchestratorHTTPException();
		}
		
		LOGGER.log(Level.FINE,"NFFG remove completed");
	}

	public NF_FGExtended _getNFFG(String nffg_id) throws InfrastructureOrchestratorHTTPException,
			InfrastructureOrchestratorNotAuthenticatedException, InfrastructureOrchestratorNF_FGNotFoundException
	{
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout_ms)
				.setConnectTimeout(timeout_ms).setSocketTimeout(timeout_ms).build();

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(base_url + "/NF-FG/" + nffg_id);
		request.setConfig(config);
		request.addHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
		request.addHeader("cache-control", "no-cache");
		request.addHeader("X-Auth-Token", auth_token);

		HttpResponse response;
		LOGGER.log(Level.FINE,"Getting NFFG with id "+nffg_id);

		try
		{
			response = client.execute(request);
		} catch (IOException e)
		{
			LOGGER.log(Level.SEVERE,"IOException during the HTTP Request");
			throw new InfrastructureOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
		}

		int http_status = response.getStatusLine().getStatusCode();
		LOGGER.log(Level.FINE,"Response HTTP status code: "+http_status);

		if (http_status == 401)
		{
			LOGGER.log(Level.FINE,"Authentication required");
			throw new InfrastructureOrchestratorNotAuthenticatedException();
		} else if (http_status == 404)
		{
			LOGGER.log(Level.WARNING,"Not found NFFG with id "+nffg_id);
			throw new InfrastructureOrchestratorNF_FGNotFoundException("Unable to find NFFG with id:" + nffg_id);
		}
		else if (http_status == 200)
		{
			LOGGER.log(Level.FINE,"Get completed successfully");

			try
			{
				String json = HttpResponseReader.getContent(response);
				return NF_FGExtended.getFromJson(json);
			} catch (UnsupportedOperationException | IOException e)
			{
				LOGGER.log(Level.SEVERE,"Unable to parse JSON");
				throw new InfrastructureOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
			}
		}
		LOGGER.log(Level.SEVERE,"Invalid HTTP status code: "+http_status);
		throw new InfrastructureOrchestratorHTTPException("Invalid HTTP Status : " + http_status);
	}

	private void authenticate()
			throws InfrastructureOrchestratorAuthenticationException, InfrastructureOrchestratorHTTPException
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
		LOGGER.log(Level.FINE,"Authentication required");
		try
		{
			response = client.execute(request);
		} catch (IOException e)
		{
			LOGGER.log(Level.FINE,"Authentication required");
			throw new InfrastructureOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
		}

		int http_status = response.getStatusLine().getStatusCode();
		if (http_status == 200)
		{
			LOGGER.log(Level.FINE,"Authentication required");
			try
			{
				auth_token = HttpResponseReader.getContent(response);
			} catch (UnsupportedOperationException | IOException e)
			{
				LOGGER.log(Level.FINE,"Authentication required");
				throw new InfrastructureOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
			}
		} else
		{
			LOGGER.log(Level.FINE,"Authentication required");
			throw new InfrastructureOrchestratorAuthenticationException("Invalid HTTP Status : " + http_status);
		}
	}
}
