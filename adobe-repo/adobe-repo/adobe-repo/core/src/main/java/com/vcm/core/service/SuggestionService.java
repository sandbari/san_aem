package com.vcm.core.service;

import java.util.List;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.ResourceResolver;


public interface SuggestionService {
    List<String> suggest(ResourceResolver resourceResolver, String nodeType, String term, int limit) throws RepositoryException;
}
