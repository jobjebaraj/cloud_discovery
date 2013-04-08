package org.convene.clouds.discovery.servers;

import static com.google.common.io.Closeables.closeQuietly;

import java.io.Closeable;

import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.NovaAsyncApi;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.domain.VolumeAttachment;
import org.jclouds.openstack.nova.v2_0.extensions.VolumeAttachmentApi;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;
import org.jclouds.rest.RestContext;

import com.google.common.collect.FluentIterable;

public class GetVolumeDataHandler implements Closeable {
	String provider = "rackspace-cloudservers-us";

	String username;
	String apiKey;

	private ComputeService compute;
	private RestContext<NovaApi, NovaAsyncApi> nova;
	private ServerApi serverApi;
	private VolumeAttachmentApi volumeAttachmentApi;

	private void init(String userName, String apiKey) {
		// The provider configures jclouds to use the Rackspace open cloud (US)
		// to use the Rackspace open cloud (UK) set the provider to
		// "rackspace-cloudservers-uk"
		this.username = userName;
		this.apiKey = apiKey;

		ComputeServiceContext context = ContextBuilder.newBuilder(provider)
				.credentials(username, apiKey)
				.buildView(ComputeServiceContext.class);
		compute = context.getComputeService();
		nova = context.unwrap();
		volumeAttachmentApi = nova.getApi()
				.getVolumeAttachmentExtensionForZone(Constants.ZONE).get();
		serverApi = nova.getApi().getServerApiForZone(Constants.ZONE);
	}

	private Server getServer(String serverName) {
		FluentIterable<? extends Server> servers = serverApi.listInDetail()
				.concat();

		for (Server server : servers) {
			if (server.getName().startsWith(serverName)) {
				return server;
			}
		}

		throw new RuntimeException(Constants.NAME
				+ " not found. Run the CreateVolumeAndAttach example first.");
	}

	private void listVolumeAttachments(Server server) {
		System.out.println("List Volume Attachments");

		for (VolumeAttachment volumeAttachment : volumeAttachmentApi
				.listAttachmentsOnServer(server.getId())) {
			System.out.println("  " + volumeAttachment);
		}
	}

	public void close() {
		closeQuietly(compute.getContext());
	}

}
