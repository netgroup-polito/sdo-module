package it.polito.netgroup.configurationorchestrator;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.polito.netgroup.natmonitor.HttpResponseReader;

public class ConfigurationOrchestratorFrog4 implements ConfigurationOrchestrator
{

	private String username;
	private String password;
	private String base_url;
	private String auth_token;
	private int timeout_ms;

	private static final Logger LOGGER = Logger.getGlobal();

	public ConfigurationOrchestratorFrog4(String _username, String _password, String _base_url, int _timeout_ms)
	{
		username = _username;
		password = _password;
		base_url = _base_url;
		timeout_ms = _timeout_ms;
		auth_token = "";
	}

	public void authenticate()
			throws ConfigurationOrchestratorHTTPException, ConfigurationOrchestratorAuthenticationException
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
			throw new ConfigurationOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
		}

		int http_status = response.getStatusLine().getStatusCode();
		if (http_status == 200)
		{
			try
			{
				auth_token = HttpResponseReader.getContent(response);
			} catch (UnsupportedOperationException | IOException e)
			{
				throw new ConfigurationOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
			}
		}
		throw new ConfigurationOrchestratorAuthenticationException("Invalid HTTP Status : " + http_status);
	}

	public ConfigurationSDN getConfiguration(VnfForConfiguration vnf)
			throws ConfigurationOrchestratorHTTPException, ConfigurationOrchestratorConfigurationNotFoundException,
			ConfigurationOrchestratorAuthenticationException, ConfigurationOrchestratorNotAuthenticatedException, ConfigurationorchestratorUnsupportedFunctionalCapabilityException
	{
		try
		{
			return _getConfiguration(vnf);
		} catch (ConfigurationOrchestratorNotAuthenticatedException e)
		{
			authenticate();
			return _getConfiguration(vnf);
		}
	}

	public void setConfiguration(ConfigurationSDN configuration)
			throws JsonProcessingException, ConfigurationOrchestratorHTTPException,
			ConfigurationOrchestratorNotAuthenticatedException, ConfigurationOrchestratorAuthenticationException
	{
		try
		{
			_setConfiguration(configuration);
		} catch (ConfigurationOrchestratorNotAuthenticatedException e)
		{
			authenticate();
			_setConfiguration(configuration);
		}
	}

	public void removeConfiguration(String tenant_id, String nffg_id, String vnf_id)
			throws ConfigurationOrchestratorHTTPException, ConfigurationOrchestratorAuthenticationException,
			ConfigurationOrchestratorNotAuthenticatedException
	{
		try
		{
			_removeConfiguration(tenant_id, nffg_id, vnf_id);
		} catch (ConfigurationOrchestratorNotAuthenticatedException e)
		{
			authenticate();
			_removeConfiguration(tenant_id, nffg_id, vnf_id);
		}
	}

	public List<StartedVNF> getStartedVNF()
			throws ConfigurationOrchestratorHTTPException, ConfigurationOrchestratorAuthenticationException,
			ConfigurationOrchestratorConfigurationNotFoundException, ConfigurationOrchestratorNotAuthenticatedException
	{
		try
		{
			return _getStartedVNF();
		} catch (ConfigurationOrchestratorNotAuthenticatedException e)
		{
			authenticate();
			return _getStartedVNF();
		}
	}

	private List<StartedVNF> _getStartedVNF() throws ConfigurationOrchestratorConfigurationNotFoundException,
			ConfigurationOrchestratorHTTPException, ConfigurationOrchestratorNotAuthenticatedException
	{
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout_ms)
				.setConnectTimeout(timeout_ms).setSocketTimeout(timeout_ms).build();

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(base_url + "/config/started_vnf");
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
			throw new ConfigurationOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
		}

		int http_status = response.getStatusLine().getStatusCode();
		if (http_status == 401)
		{
			throw new ConfigurationOrchestratorNotAuthenticatedException();
		} else if (http_status == 404)
		{
			throw new ConfigurationOrchestratorConfigurationNotFoundException("Unable to get started VNF (404");
		} else if (http_status == 200)
		{
			try
			{
				String json = HttpResponseReader.getContent(response);

				return StartedVNF.getFromJson(json);
			} catch (UnsupportedOperationException | IOException e)
			{
				throw new ConfigurationOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
			}
		} else
		{
			throw new ConfigurationOrchestratorHTTPException("Invalid HTTP Status : " + http_status);
		}
	}

	private ConfigurationSDN _getConfiguration(VnfForConfiguration vnf )
			throws ConfigurationOrchestratorHTTPException, ConfigurationOrchestratorNotAuthenticatedException,
			ConfigurationOrchestratorConfigurationNotFoundException, ConfigurationorchestratorUnsupportedFunctionalCapabilityException
	{
		String tenant_id = vnf.getTenantId();
		String nffg_id = vnf.getNffgId();
		String vnf_id = vnf.getId();
		String vnf_type = vnf.getFunctionalCapability();
			
		while (true)
		{
			RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout_ms)
					.setConnectTimeout(timeout_ms).setSocketTimeout(timeout_ms).build();

			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(base_url + "/config/" + tenant_id + "/" + nffg_id + "/" + vnf_id);
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
				throw new ConfigurationOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
			}

			int http_status = response.getStatusLine().getStatusCode();
			if (http_status == 401)
			{
				throw new ConfigurationOrchestratorNotAuthenticatedException();
			} else if (http_status == 404)
			{
				throw new ConfigurationOrchestratorConfigurationNotFoundException(
						"Unable to find Configuration for" + tenant_id + "/" + nffg_id + "/" + vnf_id);
			} else if (http_status == 200)
			{
				try
				{
					String json = HttpResponseReader.getContent(response);
					if ( vnf_type.equals("nat"))
					{
						return NatConfiguration.getFromJson(tenant_id,nffg_id,vnf_id,vnf_type,json);
					}
					else
					{
						throw new ConfigurationorchestratorUnsupportedFunctionalCapabilityException(vnf_type);
					}
				} catch (UnsupportedOperationException | IOException e)
				{
					throw new ConfigurationOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
				}
			} else if (http_status == 500)
			{
				// Try again
			} else
			{
				throw new ConfigurationOrchestratorHTTPException("Invalid HTTP Status : " + http_status);
			}
		}
	}

	private void _setConfiguration(ConfigurationSDN configSDN) throws JsonProcessingException,
			ConfigurationOrchestratorNotAuthenticatedException, ConfigurationOrchestratorHTTPException
	{
		while (true)
		{
			RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout_ms)
					.setConnectTimeout(timeout_ms).setSocketTimeout(timeout_ms).build();

			HttpClient client = HttpClientBuilder.create().build();
			HttpPut request = new HttpPut(base_url + "/config/" + configSDN.getTenantId() + "/" + configSDN.getNffgId()+ "/" + configSDN.getVnfId()+"/");
			request.setConfig(config);
			request.addHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
			request.addHeader("cache-control", "no-cache");
			request.addHeader("X-Auth-Token", auth_token);

			String json = configSDN.getJson();
			request.setEntity(new StringEntity(json, "UTF-8"));

			HttpResponse response;
			try
			{
				response = client.execute(request);
			} catch (IOException e)
			{
				throw new ConfigurationOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
			}

			int http_status = response.getStatusLine().getStatusCode();
			if (http_status == 401)
			{
				throw new ConfigurationOrchestratorNotAuthenticatedException();
			} else if (http_status == 500)
			{
			} else if (http_status == 200)
			{
				break;
			} else
			{
				throw new ConfigurationOrchestratorHTTPException("Invalid HTTP status code : " + http_status);
			}
		}
	}

	private void _removeConfiguration(String tenant_id, String nffg_id , String vnf_id)
			throws ConfigurationOrchestratorNotAuthenticatedException
	{
		// FUTUREWORK
	}

	public void waitUntilStarted(VnfForConfigurationInterface vnfC) throws InterruptedException,
			ConfigurationOrchestratorHTTPException, ConfigurationOrchestratorAuthenticationException,
			ConfigurationOrchestratorConfigurationNotFoundException, ConfigurationOrchestratorNotAuthenticatedException
	{
		while (true)
		{
			List<StartedVNF> list = getStartedVNF();

			for (StartedVNF vnf : list)
			{
				if (vnf.getId().equals(vnfC.getId()) && 
						vnf.getNffgId().equals(vnfC.getNffgId()) && 
						vnf.getTenantId().equals(vnfC.getTenantId()))
				{
					return;
				}
			}
			Thread.sleep(1000);
		}
	}
}
