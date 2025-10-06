package com.vcm.core.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.vcm.core.constants.Constant;

@Component(service = FileHandlerService.class, immediate = true)
public class FileHandlerService {

    private static Logger LOG = LoggerFactory.getLogger(FileHandlerService.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private SlingSettingsService slingSettingsService;
    
    @Reference
    private Replicator replicator;

    public void setResourceResolverFactory(ResourceResolverFactory resourceResolverFactory) {
		this.resourceResolverFactory = resourceResolverFactory;
	}

	public void setReplicator(Replicator replicator) {
		this.replicator = replicator;
	}
  

    public Asset createFile(String filePath, String content, String mimetype) {

	Map<String, Object> map = new HashMap<>();

	map.put(ResourceResolverFactory.SUBSERVICE, Constant.VCMSERVICEUSER);

	Asset asset = null;

	if (resourceResolverFactory != null && content != null) {

	    try (final ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(map)) {

		//session = resourceResolver.adaptTo(Session.class);

		InputStream is = new ByteArrayInputStream(content.getBytes());

		AssetManager assetMgr = resourceResolver.adaptTo(AssetManager.class);

		asset = assetMgr.createAsset(filePath, is, mimetype, true);

	    } catch (LoginException e) {

		LOG.error("into LoginException" + e.getMessage());

	    }

	}
	return asset;
    }

    public StringBuilder readFile(String filePath) {

	StringBuilder sb = new StringBuilder();

	Resource original = null;

	HashMap<String, Object> map = new HashMap<>();

	map.put(ResourceResolverFactory.SUBSERVICE, "vcmservice");

	InputStream content = null;

	if (resourceResolverFactory != null && filePath != null) {

	    try (final ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(map)) {

		Resource resource = resourceResolver.getResource(filePath);

		if (Objects.nonNull(resource)) {

		    Asset asset = resource.adaptTo(Asset.class);

		    if (Objects.nonNull(asset)) {

			original = asset.getOriginal();

			content = original.adaptTo(InputStream.class);

		    }
		}

		if (Objects.nonNull(content)) {

		    String line;

		    BufferedReader br = new BufferedReader(new InputStreamReader(content, StandardCharsets.UTF_8));

		    while ((line = br.readLine()) != null) {
			sb.append(line);
		    }
		}
	    } catch (LoginException e) {
		LOG.error("into LoginException" + e.getMessage());
	    } catch (IOException e) {
		LOG.error("into IOException" + e.getMessage());
	    }
	}
	return sb;
    }
    
    public void replicateAsset(String assetPath) {
	
	 Map<String, Object> map = new HashMap<>();
	
	 map.put(ResourceResolverFactory.SUBSERVICE, "vcmservice");
	 
	 Session  session = null;
	 
	 if (resourceResolverFactory != null) {
	 
	     try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(map)) {
		
		 session = resourceResolver.adaptTo(Session.class);
	         
		 if(Objects.nonNull(session) && session.isLive()) {
	         
		     replicator.replicate(session, ReplicationActionType.ACTIVATE, assetPath);
	          }
	    }
	    
	     catch (LoginException | ReplicationException e) {
		
		 LOG.error("Exception Occured during replication {}", e);
	    }
	     finally {
		 
		 if(Objects.nonNull(session) && session.isLive()) {
		 
		     session.logout();
		 }
	     }
	 }
   }
}
