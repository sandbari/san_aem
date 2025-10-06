/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.vcm.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.jackrabbit.vault.util.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.commerce.common.ValueMapDecorator;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.EmptyDataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.vcm.core.service.VCMSiteConfiguationService;

/**
 * Servlet that writes tagged page list into the response. It is mounted for all
 * resources of a specific Sling resource type. The
 * {@link SlingSafeMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the {@link SlingAllMethodsServlet}.
 */
@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Get Dynamic Color Code Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.resourceTypes=" + "vcm/components/datasource/dynamicDropDown" })
public class DynamicDropDownServlet extends SlingAllMethodsServlet {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3075571280877644256L;

	/** The Constant LOGGER. */
	private static final Logger LOG = LoggerFactory.getLogger(DynamicDropDownServlet.class);

	@Reference
	private transient VCMSiteConfiguationService configService;

	/**
	 * @param VCMSiteConfiguationService the VCMSiteConfiguationService to set
	 */
	public void setVCMSiteConfiguationService(VCMSiteConfiguationService configService) {
		this.configService = configService;
	}

	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute(DataSource.class.getName(), EmptyDataSource.instance());
		ResourceResolver resourceResolver = request.getResourceResolver();
		List<Resource> resourceList = new ArrayList<Resource>();
		Map<String, Object> keyMap = configService.getLabelConfigAsMap();
		LOG.debug("size of keyMap{}", keyMap.size());
		for (Entry<String, Object> entry : keyMap.entrySet()) {
			ValueMap valueMap = new ValueMapDecorator(new HashMap<String, Object>());
			valueMap.put("text", entry.getKey());
			valueMap.put("value", entry.getValue());
			resourceList.add(new ValueMapResource(resourceResolver, new ResourceMetadata(),
					JcrConstants.NT_UNSTRUCTURED, valueMap));
		}
		LOG.debug("size of resource List {}", resourceList.size());

		DataSource ds = new SimpleDataSource(resourceList.iterator());
		request.setAttribute(DataSource.class.getName(), ds);
	}

}
