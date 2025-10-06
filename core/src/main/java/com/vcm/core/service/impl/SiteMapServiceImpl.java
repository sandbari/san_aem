package com.vcm.core.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

import javax.jcr.Binary;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.vcm.core.constants.Constant;
import com.vcm.core.service.SiteMapService;

@Component(service = SiteMapService.class, immediate = true)
public class SiteMapServiceImpl implements SiteMapService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteMapServiceImpl.class);

    @Reference
    private Replicator replicator;

    String sitemapPath;

    @Override
    public void updateSiteMaps(String path, String uri, ResourceResolver resourceResolver, Session session)
	    throws RepositoryException, ParserConfigurationException, SAXException, IOException,
	    TransformerFactoryConfigurationError, TransformerException, ReplicationException, LoginException {
	sitemapPath = path;
	HttpGet getMethod = new HttpGet(uri);
	try (CloseableHttpClient client = HttpClients.createDefault();) {
	    HttpResponse httpResponse = client.execute(getMethod);
	    int status = httpResponse.getStatusLine().getStatusCode();
	    LOGGER.debug("status code {}", status);
	    boolean isError = isError(status);
	    if (!isError) {
		try (InputStream inputStream = httpResponse.getEntity().getContent()) {
		    String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
		    LOGGER.debug("result {}", result);
		    if (StringUtils.isNotBlank(result)) {
			addAndReplicateSitemap(sitemapPath, result, resourceResolver, session);
		    }
		}
	    }
	} catch (IOException | RepositoryException | ParserConfigurationException | SAXException | TransformerException
		| ReplicationException e) {
	    LOGGER.error("Exception occured while updating the sitemap :: {}", e);
	}
    }

    public void addAndReplicateSitemap(String path, String result, ResourceResolver resourceResolver, Session session)
	    throws RepositoryException, ParserConfigurationException, SAXException, IOException,
	    TransformerFactoryConfigurationError, TransformerException, ReplicationException {
	// Document resultDoc = prepareXmlDocument(result);
	// String doc = resultDoc.toString();
	Resource localeSitemapResource = resourceResolver.getResource(path);
	if (Objects.nonNull(localeSitemapResource) && Objects.nonNull(session)) {
	    Node originalNode = session.getNode(path + Constant.SITEMAP_JCR_DATA);
	    ValueFactory factory = session.getValueFactory();
	    try (InputStream is = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8))) {
		Binary binary = factory.createBinary(is);
		Value value = factory.createValue(binary);
		originalNode.setProperty(JcrConstants.JCR_DATA, value);
		session.save();
		replicator.replicate(session, ReplicationActionType.ACTIVATE, path);
		LOGGER.debug("Replication for locale {}", path);
	    } finally {
		Optional.ofNullable(session).ifPresent(ses -> {
		    if (ses.isLive()) {
			ses.logout();
		    }
		});
		Optional.ofNullable(resourceResolver).ifPresent(resolver -> {
		    if (resolver.isLive()) {
			resolver.close();
		    }
		});
	    }
	}
	LOGGER.debug("End addAndReplicateSitemap method");
    }

    /*
     * public Document prepareXmlDocument(String checkText) { Document doc = null;
     * try { DocumentBuilderFactory docBuilderFactory =
     * DocumentBuilderFactory.newInstance(); DocumentBuilder docBuilder =
     * docBuilderFactory.newDocumentBuilder(); doc = docBuilder.parse(new
     * InputSource(new StringReader(checkText)));
     * doc.getDocumentElement().normalize();
     * LOGGER.debug("End prepareXmlDocument method"); } catch (Exception e) {
     * LOGGER.error("Exception Occured {}", e); } return doc; }
     */

    public boolean isError(int status) {
	if (status == 200 || status == 201 || status == 204 || status == 400 || status == 401) {
	    return Boolean.FALSE;
	}
	return Boolean.TRUE;
    }

    /**
     * @param replicator the replicator to set
     */
    public void setReplicator(Replicator replicator) {
        this.replicator = replicator;
    }
}
