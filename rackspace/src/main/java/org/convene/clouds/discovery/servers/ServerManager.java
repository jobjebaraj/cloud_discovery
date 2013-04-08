package org.convene.clouds.discovery.servers;

import org.jclouds.json.Json;
import org.json.simple.JSONArray;

import com.google.gson.JsonObject;

public interface ServerManager {

	public Json createServer();
	
	public Json deleteServer();
	
	public JsonObject listServer();
	
	public Json publishServer();
	
	public Json ListImages();
	
	public JSONArray listServers();
	
	public JsonObject getServersByRam(int ram);
	
	public Json ListLoadBalancers();
}
