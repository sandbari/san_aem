package com.vcm.core.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.day.cq.dam.api.Rendition;
import com.day.cq.replication.Replicator;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(AemContextExtension.class)
class FileHandlerServiceTest {
	
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    
    @Mock
    private ResourceResolverFactory resourceResolverFactory;
    
    @Mock
    private Replicator replicator;
    
    @Mock
    ResourceResolver resourceResolver;
    
    @Mock
    Session session;
    
    @Mock
    Resource resource;
    
    @Mock
    AssetManager assetMgr;
    
    @Mock
    Asset asset;
    
    @Mock
    Rendition rendition;
    
    @Mock
    InputStream is;
    
    private FileHandlerService fileHandlerService;

    @BeforeEach
    public void setup() throws Exception {
    	fileHandlerService = new FileHandlerService();
    	fileHandlerService.setResourceResolverFactory(resourceResolverFactory);
    	fileHandlerService.setReplicator(replicator);
    	Mockito.when(resourceResolverFactory.getServiceResourceResolver(Mockito.any())).thenReturn(resourceResolver);
    	
    }

    @Test
    void testCreateFile() throws Exception {
    	String content = "content";
    	InputStream is = new ByteArrayInputStream(content.getBytes());
    	Mockito.when(resourceResolver.adaptTo(AssetManager.class)).thenReturn(assetMgr);
    	//Mockito.when(assetMgr.createAsset("/content/dam/vcm", is, "application/pdf", true)).thenReturn(asset);
    	fileHandlerService.createFile("/content/dam/vcm", content, "application/pdf");
    }
    

    @Test
    void testReadFile() throws Exception {
    	Mockito.when(resourceResolver.getResource(Mockito.any())).thenReturn(resource);
    	Mockito.when(resource.adaptTo(Asset.class)).thenReturn(asset);
    	Mockito.when(asset.getOriginal()).thenReturn(rendition);
    	InputStream content = new ByteArrayInputStream("content".getBytes());
    	Mockito.when(rendition.adaptTo(InputStream.class)).thenReturn(content);
    	StringBuilder sb = fileHandlerService.readFile("/content/dam/vcm");
    	assertNotNull(sb);
    	assertEquals(7, sb.length());
    }
    

    @Test
    void testReplicateAsset() throws Exception {
    	Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
    	Mockito.when(session.isLive()).thenReturn(true);
    	fileHandlerService.replicateAsset("/content/dam/vcm");
    }
}
