package it.polito.netgroup.selforchestratingservices.compiler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;

public class Compiler
{

	public static void main(String[] args)
	{
		if (args.length < 1 )
		{
			System.err.println("USAGE: modello.json");
			System.exit(1);
		}
		
		String json="";
		try
		{
			json = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}

		ObjectMapper mapper = new ObjectMapper();
		
		SelfOrchestratorModel model;
		try
		{
			model = mapper.readValue(json, SelfOrchestratorModel.class);
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
	}

}
