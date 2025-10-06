package com.vcm.core.service;

import org.apache.sling.api.SlingHttpServletRequest;

import javax.jcr.RepositoryException;

public interface PostLoginDashboardArticleService {
    String getDashboardMarketingContent(SlingHttpServletRequest request) throws RepositoryException;
}