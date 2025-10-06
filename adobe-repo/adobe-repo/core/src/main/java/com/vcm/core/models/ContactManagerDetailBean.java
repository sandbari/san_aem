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
public class ContactManagerDetailBean {

	@Inject
	private String secondaryContactName;
	@Inject
	private String secondaryContactPhoneNo;
	@Inject
	private String secondaryContactUsMailId;

	/**
	 * @return the secondaryContactName
	 */
	public String getSecondaryContactName() {
		return secondaryContactName;
	}

	/**
	 * @return the secondaryContactPhoneNo
	 */
	public String getSecondaryContactPhoneNo() {
		return secondaryContactPhoneNo;
	}

	/**
	 * @return the secondaryContactUsMailId
	 */
	public String getSecondaryContactUsMailId() {
		return secondaryContactUsMailId;
	}

}
