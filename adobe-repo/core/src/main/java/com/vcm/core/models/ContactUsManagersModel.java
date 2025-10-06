package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vcm.core.service.AudienceSelectorService;

/**
 * @author CTS. A Model class for ContactUsManagersModel
 */

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContactUsManagersModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContactUsManagersModel.class);

	@Inject
	@Via("resource")
	private String heading;

	@Inject
	@Via("resource")
	private Resource secondaryContactDetails;
	
	@Inject
	@Via("resource")
	private Resource primaryContactDetails;

	private List<ContactManagerDetailBean> secondaryContactDetailsList;
	private List<ContactManagerPrimaryDetailBean> primaryContactDetailsList;

	@Inject
	@Source("sling-object")
	private ResourceResolver resourceResolver;

	@SlingObject
	SlingHttpServletRequest slingRequest;

	private boolean isUserAuthorized;

	private List<String> allowedAudience = new ArrayList<>();

	@Inject
	AudienceSelectorService audienceSelectorService;

	/**
	 * Post Construct method
	 */
	@PostConstruct
	protected void init() {
		LOGGER.debug("Into Contact us manager Model");
		secondaryContactDetailsList = new ArrayList<>();
		if (Objects.nonNull(secondaryContactDetails)) {
			Iterator<Resource> secondaryContactDetailsItr = secondaryContactDetails.listChildren();
			if (Objects.nonNull(secondaryContactDetailsItr)) {
				while (secondaryContactDetailsItr.hasNext()) {
					Resource detailField = secondaryContactDetailsItr.next();
					if (Objects.nonNull(detailField)) {
						ContactManagerDetailBean contactManagerDetailBean = detailField
								.adaptTo(ContactManagerDetailBean.class);
						secondaryContactDetailsList.add(contactManagerDetailBean);
					}
				}
				isUserAuthorized = audienceSelectorService.isUserAuthorized(slingRequest);
				allowedAudience = audienceSelectorService.getAllowedAudienceForComponent(slingRequest);
			}
		}
		
		primaryContactDetailsList = new ArrayList<>();
		if (Objects.nonNull(primaryContactDetails)) {
			Iterator<Resource> primaryContactDetailsItr = primaryContactDetails.listChildren();
			if (Objects.nonNull(primaryContactDetailsItr)) {
				while (primaryContactDetailsItr.hasNext()) {
					Resource primaryDetailField = primaryContactDetailsItr.next();
					if (Objects.nonNull(primaryDetailField)) {
						ContactManagerPrimaryDetailBean contactManagerPrimaryDetailBean = primaryDetailField
								.adaptTo(ContactManagerPrimaryDetailBean.class);
						primaryContactDetailsList.add(contactManagerPrimaryDetailBean);
					}
				}
			}
		}
		LOGGER.debug("End Contact us manager Model");
	}

	/**
	 * @return the productPagesList
	 */
	public List<ContactManagerDetailBean> getSecondaryContactDetailsList() {
		List<ContactManagerDetailBean> copyListBean = secondaryContactDetailsList;
		Collections.copy(copyListBean, secondaryContactDetailsList);
		return copyListBean;
	}
	
	/**
	 * @return the primaryContactDetailsList
	 */
	public List<ContactManagerPrimaryDetailBean> getPrimaryContactDetailsList() {
		List<ContactManagerPrimaryDetailBean> copyPrimayListBean = primaryContactDetailsList;
		Collections.copy(copyPrimayListBean, primaryContactDetailsList);
		return copyPrimayListBean;
	}

	public List<String> getAllowedAudience() {
		List<String> copyOfAllowedAudience = allowedAudience;
		Collections.copy(copyOfAllowedAudience, allowedAudience);
		return copyOfAllowedAudience;
	}

	public boolean getIsUserAuthorized() {
		return isUserAuthorized;
	}

	/**
	 * @return the heading
	 */
	public String getHeading() {
		return heading;
	}

}
