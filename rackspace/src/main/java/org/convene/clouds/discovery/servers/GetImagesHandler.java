package org.convene.clouds.discovery.servers;

import static com.google.common.io.Closeables.closeQuietly;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.config.ComputeServiceProperties;
import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.predicates.NodePredicates;
import org.jclouds.util.Preconditions2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.common.base.Predicate;

public class GetImagesHandler implements Closeable {
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

	public JSONArray getImages() {
		System.out.println("Images");
		JSONArray imagesList = new JSONArray();

		Set<? extends Image> images = computeService.listImages();
		for (Image image : images) {
			JSONObject imageObj = new JSONObject();
			imageObj.put("name", image.getName());
			imageObj.put("backendstatus", image.getBackendStatus());
			imageObj.put("description", image.getDescription());
			imageObj.put("id", image.getId());
			imageObj.put("location", image.getLocation().toString());
			imageObj.put("operatingsystem", image.getOperatingSystem()
					.toString());
			imageObj.put("status", image.getStatus());
			imageObj.put("version", image.getVersion());
			//imageObj.put("uri", image.getUri().toString());
			imagesList.add(imageObj);
		}

		return imagesList;
	}

	public JSONArray listServersByParentLocationId(String zone) {
		System.out.println("List Servers By Parent Location Id");

		Set<? extends NodeMetadata> servers = computeService
				.listNodesDetailsMatching(NodePredicates.parentLocationId(zone));
		JSONArray serversList = new JSONArray();

		for (NodeMetadata nodeMetadata : servers) {
			JSONObject imageObj = new JSONObject();
			imageObj.put("name", nodeMetadata.getName());
			imageObj.put("backendstatus", nodeMetadata.getBackendStatus());
			imageObj.put("id", nodeMetadata.getId());
			imageObj.put("location", nodeMetadata.getLocation().toString());
			imageObj.put("operatingsystem", nodeMetadata.getOperatingSystem()
					.toString());
			imageObj.put("status", nodeMetadata.getStatus());
			//imageObj.put("uri", nodeMetadata.getUri().toString());
			serversList.add(imageObj);
		}

		return serversList;
	}

	public JSONArray listServersByNameStartsWith(String startString) {
		System.out.println("List Servers By Name Starts With");

		Set<? extends NodeMetadata> servers = computeService
				.listNodesDetailsMatching(nameStartsWith(startString));

		JSONArray serversList = new JSONArray();

		for (NodeMetadata nodeMetadata : servers) {
			JSONObject imageObj = new JSONObject();
			imageObj.put("name", nodeMetadata.getName());
			imageObj.put("backendstatus", nodeMetadata.getBackendStatus());
			imageObj.put("id", nodeMetadata.getId());
			imageObj.put("location", nodeMetadata.getLocation().toString());
			imageObj.put("operatingsystem", nodeMetadata.getOperatingSystem()
					.toString());
			imageObj.put("status", nodeMetadata.getStatus());
			//imageObj.put("uri", nodeMetadata.getUri().toString());
			serversList.add(imageObj);
		}

		return serversList;
	}

	public static Predicate<ComputeMetadata> nameStartsWith(final String prefix) {
		Preconditions2.checkNotEmpty(prefix, "prefix must be defined");

		return new Predicate<ComputeMetadata>() {
			@Override
			public boolean apply(ComputeMetadata computeMetadata) {
				return computeMetadata.getName().startsWith(prefix);
			}

			@Override
			public String toString() {
				return "nameStartsWith(" + prefix + ")";
			}
		};
	}

	public void close() throws IOException {
		closeQuietly(computeService.getContext());
	}
}
