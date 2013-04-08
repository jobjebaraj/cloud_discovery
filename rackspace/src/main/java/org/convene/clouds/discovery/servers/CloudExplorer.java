package org.convene.clouds.discovery.servers;

import org.json.simple.JSONArray;

import com.google.gson.JsonObject;

public class CloudExplorer {

	private static final ServerManager serverManager = ServerManagerImpl.getServerManager();
	
	public static void main(String... av)
	{
        JSONArray servers =   serverManager.listServers();
        JsonObject hardware = serverManager.getServersByRam(512);
        System.out.println(" Servers " + servers);
        System.out.println(" Hardware " + hardware);
        System.exit(0);
	}
	
	
}
