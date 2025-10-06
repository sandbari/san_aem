package com.vcm.core.service;

import com.day.cq.replication.ReplicationException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.xml.sax.SAXException;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import java.io.IOException;

public interface PostloginBannerService {
    public String getPostloginBannerDetails(SlingHttpServletRequest request) throws RepositoryException;
    public String getActivePostloginBanner(SlingHttpServletRequest request) throws RepositoryException;
}
