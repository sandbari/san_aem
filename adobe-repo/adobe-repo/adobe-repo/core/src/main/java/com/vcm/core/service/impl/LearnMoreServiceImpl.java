package com.vcm.core.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.engine.SlingRequestProcessor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.contentsync.handler.util.RequestResponseFactory;
import com.day.cq.wcm.api.WCMMode;
import com.vcm.core.constants.Constant;
import com.vcm.core.service.LearnMoreService;

@Component(service = LearnMoreService.class, immediate = true)
public class LearnMoreServiceImpl implements LearnMoreService {
	private static final String DEFAULT_FILTER = "default";

	private static final Logger LOG = LoggerFactory.getLogger(LearnMoreServiceImpl.class);

	private static final String JCR_CONTENT = "/jcr:content/root/responsivegrid";

	private static final String DETAILED_CONTENT = "detailed";

	private static final String CONTAINER_CONTENT_TYPE = "filterViewType";

	private static final String VCM_BACKGROUNDCONTAINER = "vcm/components/content/backgroundcontainer";

	/** Service to create HTTP Servlet requests and responses */
	@Reference
	private RequestResponseFactory requestResponseFactory;

	/** Service to process requests through Sling */
	@Reference
	private SlingRequestProcessor requestProcessor;
	
	@Override
	public String getRenderedHTML(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		String htmlContent="";
		ResourceResolver resourceResolver = request.getResourceResolver();
		String parentResourcePath = request.getRequestURI();
		parentResourcePath = parentResourcePath.substring(0, parentResourcePath.indexOf('.'));
		String[] selectors = request.getRequestPathInfo().getSelectors();
		String rootNode = parentResourcePath + "/" + selectors[1] + JCR_CONTENT;
		LOG.debug("Sling selectors : {}", selectors.length);
		if (Objects.nonNull(selectors) && selectors.length >= 2) {
				if(selectors[1].equals(DEFAULT_FILTER)) {
					htmlContent=this.getDefaultContent(parentResourcePath,resourceResolver);
				}
				else {
					htmlContent=this.getDetailedContent(rootNode,resourceResolver);
				}
		}
		return htmlContent;
	}
	
	private String getHTMLContent(Resource resource) {
		String html = "";
		if (Objects.nonNull(resource)) {
			try {
				/* Setup request */
				String requestPath=resource.getPath()+Constant.HTML_EXTENSION;
				HttpServletRequest req = requestResponseFactory.createRequest("GET", requestPath);
				WCMMode.DISABLED.toRequest(req);

				/* Setup response */
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				HttpServletResponse resp = requestResponseFactory.createResponse(out);
				/* Process request through Sling */
				requestProcessor.processRequest(req, resp, resource.getResourceResolver());
				
				html = out.toString();
				
				LOG.debug("Target Content {}", html);
				
			} catch (ServletException e) {
				LOG.error("Error in Learnmore servlet" + e.getMessage());
			} catch (IOException e) {
				LOG.error("Error in Learnmore servlet" + e.getMessage());
			}

		}
		return html;
	}
	private String getDetailedContent(String rootNode,ResourceResolver resourceResolver) {
		String htmlContent = "";
		LOG.debug("Target Parent Page Path {}", rootNode);
		Resource rootResource = resourceResolver.getResource(rootNode);
		if (Objects.nonNull(rootResource)) {
			if (rootResource.hasChildren()) {
				Iterator<Resource> childResources = rootResource.listChildren();
				while (childResources.hasNext()) {
					Resource childResource = childResources.next();
					ValueMap childResourceProps = childResource.getValueMap();
					String resourceType = childResource.getResourceType();
					LOG.debug("Target resourceType {}", resourceType);
					if (Objects.nonNull(resourceType)) {
						if (resourceType.equals(VCM_BACKGROUNDCONTAINER)) {
							if (childResourceProps.containsKey(CONTAINER_CONTENT_TYPE)) {
								String contentType = childResourceProps.get(CONTAINER_CONTENT_TYPE, String.class);
								if (Objects.nonNull(contentType) && contentType.equals(DETAILED_CONTENT)) {
									LOG.debug("Target Node Path {}", childResource.getPath());
									htmlContent = htmlContent + this.getHTMLContent(childResource);
								}
							}
						}
					}
				}
			}
		}
		return htmlContent;
	}
	public String getDefaultContent(String parentPage,ResourceResolver resourceResolver) {
		StringBuilder htmlContent = new StringBuilder(); 
		LOG.debug("Target Parent Page Path {}", parentPage);
		Resource rootResource = resourceResolver.getResource(parentPage);
		if (Objects.nonNull(rootResource)) {
			if (rootResource.hasChildren()) {
				Iterator<Resource> childResources = rootResource.listChildren();
				while (childResources.hasNext()) {
					 Resource child=resourceResolver.getResource(childResources.next().getPath()+JCR_CONTENT);
					
					 htmlContent.append(getContainerContent(child,DEFAULT_FILTER));
				}
			}
		}
		return htmlContent.toString();
	}
	private String getContainerContent(Resource childResource,String containerType) {
		StringBuilder htmlContent = new StringBuilder(); 
		if (Objects.nonNull(childResource)) {
			if (childResource.hasChildren()) {
				Iterator<Resource> childrens = childResource.listChildren();
				while (childrens.hasNext()) {
					Resource childrenResource= childrens.next();
						ValueMap childResourceProps = childrenResource.getValueMap();
						String resourceType = childrenResource.getResourceType();
						LOG.debug("Target resourceType {}", resourceType);
						if (Objects.nonNull(resourceType)) {
							if (resourceType.equals(VCM_BACKGROUNDCONTAINER)) {
								if (childResourceProps.containsKey(CONTAINER_CONTENT_TYPE)) {
									String contentType = childResourceProps.get(CONTAINER_CONTENT_TYPE, String.class);
									if (Objects.nonNull(contentType) && contentType.equals(containerType)) {
										LOG.debug("Target Node Path {}", childrenResource.getPath());
										htmlContent.append(this.getHTMLContent(childrenResource));
									}
								}
							}
						}
				}
			}
		}
		return htmlContent.toString();
	}
	/**
	 * @param requestResponseFactory the requestResponseFactory to set
	 */
	public void setRequestResponseFactory(RequestResponseFactory requestResponseFactory) {
		this.requestResponseFactory = requestResponseFactory;
	}

	/**
	 * @param requestProcessor the requestProcessor to set
	 */
	public void setRequestProcessor(SlingRequestProcessor requestProcessor) {
		this.requestProcessor = requestProcessor;
	}


}
