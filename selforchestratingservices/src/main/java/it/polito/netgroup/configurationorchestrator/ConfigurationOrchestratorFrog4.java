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
		LOGGER.info("Trying authentication...");

		
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
			LOGGER.warning("HTTP IO Error : " + e.getMessage());
			throw new ConfigurationOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
		}

		int http_status = response.getStatusLine().getStatusCode();
		LOGGER.fine("HTTP STATUS:"+http_status);
		
		if (http_status == 200)
		{
			try
			{
				auth_token = HttpResponseReader.getContent(response);
				LOGGER.fine("AUTHENTICATION TOKEN:"+auth_token);

			} catch (UnsupportedOperationException | IOException e)
			{
				LOGGER.warning("HTTP IO Error : " + e.getMessage());
				throw new ConfigurationOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
			}
		}
		LOGGER.warning("Invalid HTTP Status : " + http_status);
		throw new ConfigurationOrchestratorAuthenticationException("Invalid HTTP Status : " + http_status);
	}

	public ConfigurationSDN getConfiguration(VnfForConfigurationInterface vnf)
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
		LOGGER.info("Trying to get started VNF");

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
			LOGGER.severe("HTTP IO Error : " + e.getMessage());
			throw new ConfigurationOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
		}

		int http_status = response.getStatusLine().getStatusCode();
		if (http_status == 401)
		{
			LOGGER.severe("Authentication needed");
			throw new ConfigurationOrchestratorNotAuthenticatedException();
		}
		else if (http_status == 404)
		{
			LOGGER.severe("Unable to get started VNF (404)");
			throw new ConfigurationOrchestratorConfigurationNotFoundException("Unable to get started VNF (404)");
		}
		else if (http_status == 200)
		{
			try
			{
				String json = HttpResponseReader.getContent(response);

				return StartedVNF.getFromJson(json);
			}
			catch (UnsupportedOperationException | IOException e)
			{
				LOGGER.severe("HTTP IO Error : " + e.getMessage());
				throw new ConfigurationOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
			}
		}
		else
		{
			LOGGER.severe("Invalid HTTP Status : " + http_status);
			throw new ConfigurationOrchestratorHTTPException("Invalid HTTP Status : " + http_status);
		}
	}

	private ConfigurationSDN _getConfiguration(VnfForConfigurationInterface vnf )
			throws ConfigurationOrchestratorHTTPException, ConfigurationOrchestratorNotAuthenticatedException,
			ConfigurationOrchestratorConfigurationNotFoundException, ConfigurationorchestratorUnsupportedFunctionalCapabilityException
	{
		String tenant_id = vnf.getTenantId();
		String nffg_id = vnf.getNffgId();
		String vnf_id = vnf.getId();
		String vnf_type = vnf.getFunctionalCapability();

		LOGGER.info("Trying to get the configuration for "+tenant_id+"/"+nffg_id+"/"+vnf_id+"/"+vnf_type);
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

			LOGGER.fine("executing the GET");

			HttpResponse response;
			try
			{
				response = client.execute(request);
			} catch (IOException e)
			{
				LOGGER.warning("HTTP IO Error : " + e.getMessage());
				throw new ConfigurationOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
			}

			int http_status = response.getStatusLine().getStatusCode();
			
			LOGGER.fine("HTTP STATUS CODE:"+http_status);
			if (http_status == 401)
			{
				LOGGER.warning("Authentication needed (401)");
				throw new ConfigurationOrchestratorNotAuthenticatedException();
			} else if (http_status == 404)
			{
				LOGGER.warning("Unable to find a configuration for "+tenant_id+"/"+nffg_id+"/"+vnf_id+"/"+vnf_type);
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
						LOGGER.warning("Unable to find a Configuration class for VNF type: '"+vnf_type+"'");
						throw new ConfigurationorchestratorUnsupportedFunctionalCapabilityException(vnf_type);
					}
				} catch (UnsupportedOperationException | IOException e)
				{
					LOGGER.warning("HTTP IO Error : " + e.getMessage());
					throw new ConfigurationOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
				}
			} else if (http_status == 500)
			{
				// Try again
				LOGGER.fine("HTTP STATUS 500 : trying again");
				try
				{
					Thread.sleep(10000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
					return null;
				}
			} else
			{
				LOGGER.fine("Invalid HTTP STATUS CODE:" + http_status);
				throw new ConfigurationOrchestratorHTTPException("Invalid HTTP Status : " + http_status);
			}
		}
	}

	private void _setConfiguration(ConfigurationSDN configSDN) throws JsonProcessingException,
			ConfigurationOrchestratorNotAuthenticatedException, ConfigurationOrchestratorHTTPException
	{
		LOGGER.info("Trying to set the configuration for "+configSDN.getTenantId()+"/"+configSDN.getNffgId()+"/"+configSDN.getVnfId()+"/"+configSDN.getFunctionalCapability());

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
			
			LOGGER.fine("Trying to PUT the configuration");
			try
			{
				response = client.execute(request);
			}
			catch (IOException e)
			{
				LOGGER.warning("HTTP IO Error : " + e.getMessage());
				throw new ConfigurationOrchestratorHTTPException("HTTP IO Error : " + e.getMessage());
			}

			int http_status = response.getStatusLine().getStatusCode();
			
			LOGGER.fine("HTTP status code:"+http_status);
			if (http_status == 401)
			{
				LOGGER.warning("Authentication needed (401)");
				throw new ConfigurationOrchestratorNotAuthenticatedException();
			}
			else if (http_status == 500)
			{
				LOGGER.fine("HTTP STATUS 500 : trying again");
				try
				{
					Thread.sleep(10000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
					return;
				}
			}
			else if (http_status == 200)
			{
				LOGGER.fine("OK");

				break;
			}
			else
			{
				LOGGER.warning("Invalid HTTP status code : " + http_status);

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
		LOGGER.info("Wait until "+vnfC.getTenantId()+"/"+vnfC.getNffgId()+"/"+vnfC.getId()+"/"+vnfC.getFunctionalCapability()+" is started");

		while (true)
		{
			List<StartedVNF> list = getStartedVNF();

			LOGGER.fine("Started VNF are "+list.size());

			for (StartedVNF vnf : list)
			{
				LOGGER.fine(vnf.getTenantId()+"/"+vnf.getNffgId()+"/"+vnf.getId()+" is started");
				if (vnf.getId().equals(vnfC.getId()) && 
						vnf.getNffgId().equals(vnfC.getNffgId()) && 
						vnf.getTenantId().equals(vnfC.getTenantId()))
				{
					LOGGER.fine("done");
					return;
				}
			}
			Thread.sleep(1000);
		}
	}
}
