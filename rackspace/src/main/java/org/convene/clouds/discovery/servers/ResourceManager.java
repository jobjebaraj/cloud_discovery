package org.convene.clouds.discovery.servers;

public class ResourceManager {
	   
	 /*  This file will load the Resources from the Resoures folder and read the properties and
	  *  will be used in ServerManger and ServerManager Implementation.
	 */

 private static final ResourceManager resourceHandler_ = new ResourceManager();
 
	private ResourceManager()
	{
		//
	}
	
	public static ResourceManager getResourceManager()
	{
		return resourceHandler_;
	}
	
	
}
