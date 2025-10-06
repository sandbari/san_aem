package com.vcm.core.service.impl;

import static org.mockito.Mockito.lenient;

import java.io.InputStream;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.replication.Replicator;
import com.vcm.core.constants.Constant;

import io.wcm.testing.mock.aem.junit5.AemContext;

@ExtendWith(MockitoExtension.class)
public class SiteMapServiceImplTest {

    private SiteMapServiceImpl siteMapService = new SiteMapServiceImpl();
    ResourceResolver resourceResolver;
    Replicator replicator;
    String path;
    String siteRootPath;
    String siteDomain;
    Resource resource;
    Node node;
    NodeIterator porductNodes;
    Property property;
    Session session;
    ValueFactory factory;
    Binary binary;
    InputStream is;
    String uri;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @BeforeEach
    public void setUp() throws RepositoryException {
        path = "/content/dam/vcm/sitemap/sitemap.xml";
        siteRootPath = "/content/vcm/us/en";
        siteDomain = "http://qa.vcm.com";
        uri = siteDomain + siteRootPath + Constant.SITEMAP_DOT_XML;
        resourceResolver = Mockito.mock(ResourceResolver.class);
        resource = Mockito.mock(Resource.class);
        node = Mockito.mock(Node.class);
        property = Mockito.mock(Property.class);
        porductNodes = Mockito.mock(NodeIterator.class);
        session = Mockito.mock(Session.class);
        factory = Mockito.mock(ValueFactory.class);
        binary = Mockito.mock(Binary.class);
        is = Mockito.mock(InputStream.class);
        replicator = Mockito.mock(Replicator.class);
        siteMapService.setReplicator(replicator);
        lenient().when(resourceResolver.getResource(path)).thenReturn(resource);
        lenient().when(resource.adaptTo(Node.class)).thenReturn(node);
        lenient().when(session.getNode("/content/dam/vcm/sitemap/sitemap.xml/jcr:content/renditions/original/jcr:content")).thenReturn(node);
        lenient().when(session.getValueFactory()).thenReturn(factory);
        lenient().when(factory.createBinary(is)).thenReturn(binary);
    }

    @Test()
    public void testSitemap() throws Exception {
        siteMapService.updateSiteMaps(path, uri,resourceResolver,session);
    }
}
