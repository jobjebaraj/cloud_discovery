package org.convene.clouds.discovery.servers;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import static com.google.common.io.Closeables.closeQuietly;

import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.config.ComputeServiceProperties;
import org.jclouds.compute.domain.Hardware;
import java.io.Closeable;

public class GetServersHandler implements Closeable {
	private static ComputeService computeService;
	private static String provider = "rackspace-cloudservers-us";
    String username;
    String apiKey;
    
	public void init(String username, String apiKey) {
		// The provider configures jclouds to use the Rackspace open cloud (US)
		// to use the Rackspace open cloud (UK) set the provider to
		// "rackspace-cloudservers-uk"
		// // Spring resource handler will get it from the properties file

		this.username = username;
		this.apiKey = apiKey;

		// These properties control how often jclouds polls for a status udpate
		Properties overrides = new Properties();
		overrides.setProperty(ComputeServiceProperties.POLL_INITIAL_PERIOD,
				Constants.POLL_PERIOD_TWENTY_SECONDS);
		overrides.setProperty(ComputeServiceProperties.POLL_MAX_PERIOD,
				Constants.POLL_PERIOD_TWENTY_SECONDS);

		ComputeServiceContext context = ContextBuilder.newBuilder(provider)
				.credentials(username, apiKey).overrides(overrides)
				.buildView(ComputeServiceContext.class);
		computeService = context.getComputeService();
	}

	public Set<? extends Hardware> getServers() {
		System.out.println("Hardware Profiles (Flavors)");
		init(username, apiKey);
		Set<? extends Hardware> profiles = computeService
				.listHardwareProfiles();

		return profiles;
	}

	public Hardware getHardwareByRam(int ram) {
		System.out.println("Hardware Profiles (Flavors)");

		Set<? extends Hardware> profiles = computeService
				.listHardwareProfiles();
		Hardware result = null;

		for (Hardware profile : profiles) {
			//System.out.println(" Profile ::==  " + profile);
			if (profile.getRam() == ram) {
				result = profile;
			}
		}

		if (result == null) {
			System.err.println("Flavor with" + ram
					+ " MB of RAM not found. Using first flavor found.");
			result = profiles.iterator().next();
		}

		return result;
	}

	public void close() throws IOException {
		closeQuietly(computeService.getContext());
	}
}
