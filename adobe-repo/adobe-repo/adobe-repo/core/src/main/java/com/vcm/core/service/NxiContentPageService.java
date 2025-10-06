package com.vcm.core.service;

import org.apache.sling.api.SlingHttpServletRequest;

import javax.jcr.RepositoryException;
import javax.servlet.ServletException;
import java.io.IOException;

public interface NxiContentPageService {
    String getNxiPageContent(SlingHttpServletRequest request) throws RepositoryException, ServletException, IOException;
}
