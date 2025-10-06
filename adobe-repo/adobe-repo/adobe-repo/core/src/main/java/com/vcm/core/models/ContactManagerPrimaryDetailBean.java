package com.vcm.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

/**
 * 
 * @author CTS Bean class
 *
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContactManagerPrimaryDetailBean {

	@Inject
	private String primaryContactName;
	@Inject
	private String primaryContactProfilePhoto;
	@Inject
	private String primaryContactDesignation;
	@Inject
	private String primaryContactPhoneNo;
	@Inject
	private String primaryContactMailId;
	@Inject
	private String primaryContactProfileAltText;
	
	/**
	 * @return the primaryContactName
	 */
	public String getPrimaryContactName() {
		return primaryContactName;
	}
	
	/**
	 * @return the primaryContactProfilePhoto
	 */
	public String getPrimaryContactProfilePhoto() {
		return primaryContactProfilePhoto;
	}
	
	/**
	 * @return the primaryContactDesignation
	 */
	public String getPrimaryContactDesignation() {
		return primaryContactDesignation;
	}
	
	/**
	 * @return the primaryContactPhoneNo
	 */
	public String getPrimaryContactPhoneNo() {
		return primaryContactPhoneNo;
	}
	
	/**
	 * @return the primaryContactMailId
	 */
	public String getPrimaryContactMailId() {
		return primaryContactMailId;
	}
	
	/**
	 * @return the primaryContactProfileAltText
	 */
	public String getPrimaryContactProfileAltText() {
		return primaryContactProfileAltText;
	}
	
	

}
