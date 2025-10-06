package com.vcm.core.service;

import com.day.cq.replication.ReplicationException;
import org.apache.sling.api.resource.ResourceResolver;
import org.xml.sax.SAXException;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import java.io.IOException;

public interface SiteMapService {
    public void updateSiteMaps(String rootSitemapPath,String uri,ResourceResolver resourceResolver, Session session) throws RepositoryException, ParserConfigurationException, SAXException,
            IOException, TransformerFactoryConfigurationError, TransformerException, ReplicationException, LoginException;
}
