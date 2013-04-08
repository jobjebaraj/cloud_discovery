package org.convene.clouds.discovery.servers;

import java.util.Set;

import org.jclouds.compute.domain.Hardware;
import org.jclouds.json.Json;
import org.json.simple.JSONArray;

import com.google.gson.JsonObject;

public class ServerManagerImpl implements ServerManager {

	private static final ServerManager serverManager = new ServerManagerImpl();
	private static String provider = "rackspace-cloudservers-us";

	GetImagesHandler imagesHandler = new GetImagesHandler();
	GetServersHandler serversHandler = new GetServersHandler();
	GetLoadBalancersHandler loadBalancersHandler = new GetLoadBalancersHandler();
	String username = "mchandran";
	String apiKey = "6c84997c73856a67be0783e7e9ce96ce";
	
	private ServerManagerImpl() {

	}

	public static ServerManager getServerManager() {
		return serverManager;
	}

	@Override
	public Json createServer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Json deleteServer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray listServers() {
		serversHandler.init(username, apiKey);
		Set<? extends Hardware> servers = serversHandler.getServers();
		JSONArray list = new JSONArray();
			 
		for (Hardware profile : servers) {
			JsonObject returnObj = new JsonObject();

			returnObj.addProperty("name", profile.getName());
			returnObj.addProperty("id", profile.getId());
			returnObj.addProperty("providerId", profile.getProviderId());
			returnObj.addProperty("location", profile.getLocation().toString());
			returnObj.addProperty("RAM", profile.getRam());
			returnObj.addProperty("URI", profile.getUri().toString());
			
			// / returnObj.addProperty(property, profile.get);
           list.add(returnObj);
		}

		return list;
	}

	@Override
	public Json publishServer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Json ListImages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Json ListLoadBalancers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonObject listServer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonObject getServersByRam(int ram) {

		serversHandler.init(username, apiKey);
		Hardware profile = serversHandler.getHardwareByRam(ram);
		JsonObject returnObj = new JsonObject();

		returnObj.addProperty("name", profile.getName());
		returnObj.addProperty("id", profile.getId());
		returnObj.addProperty("providerId", profile.getProviderId());
		returnObj.addProperty("location", profile.getLocation().toString());
		returnObj.addProperty("RAM", profile.getRam());
		returnObj.addProperty("URI", profile.getUri().toString());
		
		// / returnObj.addProperty(property, profile.get);

		return returnObj;
	}

}
