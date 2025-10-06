package com.vcm.core.service;

import org.apache.sling.api.SlingHttpServletRequest;

import javax.jcr.RepositoryException;

public interface AutomaticInvestmentPlanService {
    String getAIPContent(SlingHttpServletRequest request) throws RepositoryException;
}
