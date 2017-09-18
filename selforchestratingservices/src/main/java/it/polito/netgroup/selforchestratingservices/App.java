package it.polito.netgroup.selforchestratingservices;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.polito.netgroup.configurationorchestrator.ConfigurationOrchestratorFrog4;
import it.polito.netgroup.datastoreclient.DatastoreClient;
import it.polito.netgroup.infrastructureOrchestrator.InfrastructureOrchestratorUniversalNode;
import it.polito.netgroup.infrastructureOrchestrator.InfrastructureOrchestratorAuthenticationException;
import it.polito.netgroup.infrastructureOrchestrator.InfrastructureOrchestratorHTTPException;
import it.polito.netgroup.infrastructureOrchestrator.InfrastructureOrchestratorNotAuthenticatedException;
import it.polito.netgroup.selforchestratingservices.auto.MySelfOrchestrator;
import it.polito.netgroup.selforchestratingservices.declarative.Infrastructure;
import it.polito.netgroup.selforchestratingservices.declarative.InfrastructureImplementation;
import it.polito.netgroup.selforchestratingservices.declarative.SelfOrchestrator;
import it.polito.netgroup.selforchestratingservices.declarative_new.Framework;
import it.polito.netgroup.selforchestratingservices.declarative_new.MyFramework;
import it.polito.netgroup.selforchestratingservices.declarative_new.MyResourceManager;
import it.polito.netgroup.selforchestratingservices.declarative_new.ResourceManager;


/**
 * Hello world!
 *
 */
public class App
{


	
	public static void main(String[] args)
	{

		String datastore_url = "http://127.0.0.1:8081";
		String datastore_username = "admin";
		String datastore_password = "admin";

		InfrastructureOrchestratorUniversalNode orchestrator;
		String controller_url = "http://127.0.0.1:8080";
		String controller_username = "admin";
		String controller_password = "admin";

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

		Logger.getGlobal().setLevel(Level.FINEST);
		Handler h = new ConsoleHandler();
		h.setFormatter(new Formatter()
		{
			
			@Override
			public String format(LogRecord record)
			{
			       StringBuilder sb = new StringBuilder();

			       DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			       sb.append(df.format(new Date(record.getMillis())))
			            .append(" ")
			            .append(record.getLevel().getName())
			            .append(" ")
			            .append(	record.getSourceClassName().replace("it.polito.netgroup.",""))
			            .append(" ")
			            .append(	record.getSourceMethodName())
			            .append(": ")
			            .append(formatMessage(record))
			            .append("\n");

			        if (record.getThrown() != null) {
			            try {
			                StringWriter sw = new StringWriter();
			                PrintWriter pw = new PrintWriter(sw);
			                record.getThrown().printStackTrace(pw);
			                pw.close();
			                sb.append(sw.toString());
			            } catch (Exception ex) {
			                // ignore
			            }
			        }

			        return sb.toString();
			}
		});
		h.setLevel(Level.FINEST);
		Logger.getGlobal().addHandler(h);
		Logger.getGlobal().setUseParentHandlers(false);
		
		//SelfOrchestratorImperativo soi = new SelfOrchestratorImperativo();
		//soi.run();
		
		String tenant_id = "2";
		String nffg_name="test_nffg_nat";

		DatastoreClient datastore = new DatastoreClient(datastore_username, datastore_password, datastore_url, timeout_ms);
		orchestrator = new InfrastructureOrchestratorUniversalNode(controller_username, controller_password, controller_url,
				timeout_ms);
		configurationService = new ConfigurationOrchestratorFrog4(configuration_username, configuration_password,
				configuration_url, timeout_ms);


		Framework myFramework = new MyFramework();
		ResourceManager myResourceManager = new MyResourceManager();


		SelfOrchestrator mySelfOrchestrator = new MySelfOrchestrator(myFramework);


		//Setting default NFFG

		String nffg_json="";
		try
		{
			nffg_json = new String(Files.readAllBytes(Paths.get(mySelfOrchestrator.getInitNFFGFilename())));
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		try {
			Infrastructure myInfrastructure = new InfrastructureImplementation(myFramework,
					mySelfOrchestrator.getVariables(),
					mySelfOrchestrator.getInfrastructureEventhandler(),
					orchestrator,
					datastore,
					configurationService,
					nffg_name,
					tenant_id);

			try {
				orchestrator.addNFFG(nffg_name, nffg_json);
			} catch (JsonProcessingException | InfrastructureOrchestratorHTTPException
					| InfrastructureOrchestratorAuthenticationException
					| InfrastructureOrchestratorNotAuthenticatedException e) {
				System.exit(1);
			}

			myFramework.setResourceManager(myResourceManager);
			myFramework.setSelfOrchestrator(mySelfOrchestrator);
			myFramework.setInfrastructure(myInfrastructure);

			myResourceManager.newServiceState();
			myFramework.mainLoop();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
