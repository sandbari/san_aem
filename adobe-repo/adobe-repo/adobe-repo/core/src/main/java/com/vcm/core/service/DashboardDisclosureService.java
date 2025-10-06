package com.vcm.core.service;

import org.apache.sling.api.SlingHttpServletRequest;

import javax.jcr.RepositoryException;

public interface DashboardDisclosureService {
    String getDashboardDisclosureContent(SlingHttpServletRequest request) throws RepositoryException;
}
