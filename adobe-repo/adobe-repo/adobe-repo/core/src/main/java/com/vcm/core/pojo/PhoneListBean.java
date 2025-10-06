package com.vcm.core.pojo;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PhoneListBean {	

   
	@Inject
    String staticPhoneNumber;
	
	@Inject
    String staticOpenText;

	/**
	 * @return the staticOpenText
	 */
	public String getStaticOpenText() {
		return staticOpenText;
	}

	/**
	 * @return the staticPhoneNumber
	 */
	public String getStaticPhoneNumber() {
		return staticPhoneNumber;
	}

	public void setStaticPhoneNumber(String staticPhoneNumber) {
		this.staticPhoneNumber = staticPhoneNumber;
	}

	public void setStaticOpenText(String staticOpenText) {
		this.staticOpenText = staticOpenText;
	}	
	
	
    
}
