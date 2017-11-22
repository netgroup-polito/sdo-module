package it.polito.netgroup.selforchestratingservices;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.polito.netgroup.configurationorchestrator.*;

public class testTranscoderConfiguration {

	public static void main(String[] args)
	{
		ConfigurationOrchestratorFrog4 configurationService;
		String configuration_url = "http://127.0.0.1:8082";
		String configuration_username = "admin";
		String configuration_password = "admin";

		int timeout_ms = 240000;

		org.apache.log4j.BasicConfigurator.configure();

		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
		System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "ERROR");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "ERROR");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "ERROR");


		configurationService = new ConfigurationOrchestratorFrog4(configuration_username, configuration_password,
				configuration_url, timeout_ms);


		VnfForConfigurationInterface vnf = new VnfForConfiguration("1","1","1","transcoder");

		ConfigurationSDN configuration = null;
		try {
			configuration = configurationService.getConfiguration(vnf);
		} catch (ConfigurationOrchestratorHTTPException e) {
			e.printStackTrace();
		} catch (ConfigurationOrchestratorConfigurationNotFoundException e) {
			e.printStackTrace();
		} catch (ConfigurationOrchestratorAuthenticationException e) {
			e.printStackTrace();
		} catch (ConfigurationOrchestratorNotAuthenticatedException e) {
			e.printStackTrace();
		} catch (ConfigurationorchestratorUnsupportedFunctionalCapabilityException e) {
			e.printStackTrace();
		}
		TranscoderConfiguration tconfiguration = (TranscoderConfiguration) configuration;

		System.out.println("Prima: "+tconfiguration.getEnabled());
		System.out.println("Prima: "+tconfiguration.getTranscoderTemplate());


		tconfiguration.setEnabled(!tconfiguration.getEnabled());
		tconfiguration.setTranscoderTemplate("TransrateLight"); //TransrateLight o transcode

		try {
			configurationService.setConfiguration(tconfiguration);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (ConfigurationOrchestratorHTTPException e) {
			e.printStackTrace();
		} catch (ConfigurationOrchestratorNotAuthenticatedException e) {
			e.printStackTrace();
		} catch (ConfigurationOrchestratorAuthenticationException e) {
			e.printStackTrace();
		}

		try {
			configuration = configurationService.getConfiguration(vnf);
		} catch (ConfigurationOrchestratorHTTPException e) {
			e.printStackTrace();
		} catch (ConfigurationOrchestratorConfigurationNotFoundException e) {
			e.printStackTrace();
		} catch (ConfigurationOrchestratorAuthenticationException e) {
			e.printStackTrace();
		} catch (ConfigurationOrchestratorNotAuthenticatedException e) {
			e.printStackTrace();
		} catch (ConfigurationorchestratorUnsupportedFunctionalCapabilityException e) {
			e.printStackTrace();
		}
		tconfiguration = (TranscoderConfiguration) configuration;

		System.out.println("Dopo: "+tconfiguration.getEnabled());
		System.out.println("Dopo: "+tconfiguration.getTranscoderTemplate());



	}
}
