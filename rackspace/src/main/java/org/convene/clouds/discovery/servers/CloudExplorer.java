package org.convene.clouds.discovery.servers;

import org.json.simple.JSONArray;

public class CloudExplorer {

	private static final ServerManager serverManager = ServerManagerImpl.getServerManager();
	
	public static void main(String... av)
	{
        /*JSONArray servers =   serverManager.listServers();
        JsonObject hardware = serverManager.getServersByRam(512);
        
        System.out.println(" Servers " + servers);
        System.out.println(" Hardware " + hardware);*/
		
		//JSONArray servers =   serverManager.listServersByParentLocationId("DFW");
		//System.out.println(" Servers " + servers);
		
		
		//JSONArray servers =   serverManager.ListImages();
		//System.out.println(" Servers " + servers);
		
		
		JSONArray servers =   serverManager.listServersByNameStartsWith("wordpress");
		System.out.println(" Servers " + servers);
		
        System.exit(0);
	}
	
	
}
