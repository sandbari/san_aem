package com.vcm.core.service;

import org.apache.sling.api.SlingHttpServletRequest;

import javax.jcr.RepositoryException;
import java.text.ParseException;

public interface PostloginBannerGetDetailsService {
    public String getPostloginBannerDetails(SlingHttpServletRequest request) throws RepositoryException, ParseException;
    public String getActivePostloginBanner(SlingHttpServletRequest request) throws RepositoryException;
}
