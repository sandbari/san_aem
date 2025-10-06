package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vcm.core.service.AudienceSelectorService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentFragment;

@Model(adaptables = { SlingHttpServletRequest.class,Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DisclaimerTextModel {

	private static final Logger log = LoggerFactory.getLogger(DisclaimerTextModel.class);

	@Inject
	@Via("resource")
	private String headText;

	@Inject
	@Via("resource")
	private String containerwidth;

	@Inject
	@Via("resource")
	private Resource disclaimerTextList;

	@Inject
	@Source("sling-object")
	private ResourceResolver resourceResolver;

	private List<String> disclaimerContentFragmentList = new ArrayList<>();

	private List<String> allowedAudience = new ArrayList<String>();

	@Inject
	AudienceSelectorService audienceSelectorService;

	@SlingObject
	SlingHttpServletRequest slingRequest;

	/**
	 * Init Methods
	 */
	@PostConstruct
	protected void init() {
		log.debug("Inside init method of DisclaimerTextModel");
		allowedAudience= audienceSelectorService.getAllowedAudienceForComponent(slingRequest);
		if (disclaimerTextList != null) {
			Iterator<Resource> listChildren = disclaimerTextList.listChildren();
			while (listChildren.hasNext()) {
				ValueMap contentFramentResourceValueMap = listChildren.next().getValueMap();
				String disclaimerText = contentFramentResourceValueMap.get("disclaimerText", String.class);
				if (Objects.nonNull(disclaimerText)) {
					Resource contentFragmentResource = resourceResolver.getResource(disclaimerText);
					if (Objects.nonNull(contentFragmentResource)) {
						ContentFragment contentFragment = contentFragmentResource.adaptTo(ContentFragment.class);
						if (Objects.nonNull(contentFragment)) {
							disclaimerContentFragmentList
									.add(contentFragment.getElement("disclaimertext").getContent());
						}
					}
				}

			}
		}

	}

	/**
	 * 
	 * @return headText
	 */
	public String getDisclaimerHeadText() {
		return headText;
	}

	/**
	 * 
	 * @param disclaimerHeadText
	 */
	public void setDisclaimerHeadText(String disclaimerHeadText) {
		this.headText = disclaimerHeadText;
	}

	/**
	 * 
	 * @param disclaimerTextList
	 */
	public void setDisclaimerTextList(Resource disclaimerTextList) {
		this.disclaimerTextList = disclaimerTextList;
	}

	/**
	 * 
	 * @return disclaimerContentFragmentList
	 */
	public List<String> getDisclaimerContentFragmentList() {
		List<String> copyListBean = disclaimerContentFragmentList;
		Collections.copy(copyListBean, disclaimerContentFragmentList);
		return copyListBean;
	}

	/**
	 * @param resourceResolver the resourceResolver to set
	 */
	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

	public String getContainerwidth() {
		return containerwidth;
	}

	public void setContainerwidth(String containerwidth) {
		this.containerwidth = containerwidth;
	}

	public void setAudienceSelectorService(AudienceSelectorService audienceSelectorService) {
		this.audienceSelectorService = audienceSelectorService;
	}

	public void setSlingRequest(SlingHttpServletRequest slingRequest) {
		this.slingRequest = slingRequest;
	}

	public List<String> getAllowedAudience() {
		List<String> copyOfAllowedAudience = allowedAudience;
		Collections.copy(copyOfAllowedAudience, allowedAudience);
		return copyOfAllowedAudience;
	}

}
