package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vcm.core.pojo.PhoneListBean;
import com.vcm.core.service.ConstantsConfigService;
import com.vcm.core.utils.UtilityService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContactDetailsModel {

	private static final Logger LOG = LoggerFactory.getLogger(ContactDetailsModel.class);

	@Inject
	private String phoneSectionHeading;
	
	@Inject
	private String phoneIconImage;
	
	@Inject
	String altTextPhone;
	
	@Inject
	private String phoneNumber;

	@Inject
	private String internationalText;

	@Inject
	private String internationalNumber;
	
	@Inject
	private String emailIconImage;
	
	@Inject
	String altTextEmail;
	
	@Inject
	private String email;

	@Inject
	private String faxNumber;

	@Inject
	private String openInfoText;
	
	@Inject
	private String closedInfoText;
	
	@Inject
	private String todayOpenHoursText;
	
	@Inject
	private String hoursText;
	
	@Inject
	private String mondayToFridayText;
	
	@Inject
	private String mondayToFridayOpenHours;
	
	@Inject
	private String currentWaitTimeText;
	
	@Inject
	private String mailSectionHeading;
	
	@Inject
	private String mailiconImage;
	
	@Inject
	String altTextMail;
	
	@Inject
	private String mailingAddressHeading;

	@Inject
	private String mailingAddress;
	
	@Inject
	private String linkText;
	
	@Inject
	private String linkURL;
	
	@Inject
	private String tabSelect;
	
	@Inject
	private ConstantsConfigService constantsConfigService;
	
	@SlingObject
	private ResourceResolver resourceResolver;
	
	@ChildResource
	private Resource phoneList;
	
	@Inject
	private String popupOverlayContentPath;
	
	@Inject
    private String staticPhoneIconImage;

	@Inject
	private String staticAltTextPhone;
	
	
	String popupId;
	
	private List<PhoneListBean> contactList=new ArrayList<PhoneListBean>();
	
	@PostConstruct
	protected void init() {
	    
		popupId = UtilityService.getPopupId(popupOverlayContentPath, resourceResolver);
		if (Objects.nonNull(phoneList)) {
			Iterator<Resource> phoneListItr = phoneList.listChildren();
			if (Objects.nonNull(phoneListItr)) {
				while (phoneListItr.hasNext()) {
					Resource detailField = phoneListItr.next();
					if (Objects.nonNull(detailField)) {
						PhoneListBean phoneListBean = detailField
								.adaptTo(PhoneListBean.class);
						contactList.add(phoneListBean);
					}
				}
			}
		}
		LOG.debug("END Contact Detail init method");
	}

	public String getPhoneSectionHeading() {
		return phoneSectionHeading;
	}

	public void setPhoneSectionHeading(String phoneSectionHeading) {
		this.phoneSectionHeading = phoneSectionHeading;
	}

	public String getPhoneIconImage() {
		return phoneIconImage;
	}

	public void setPhoneIconImage(String phoneIconImage) {
		this.phoneIconImage = phoneIconImage;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getOpenInfoText() {
		return openInfoText;
	}

	public void setOpenInfoText(String openInfoText) {
		this.openInfoText = openInfoText;
	}

	public String getClosedInfoText() {
		return closedInfoText;
	}

	public void setClosedInfoText(String closedInfoText) {
		this.closedInfoText = closedInfoText;
	}

	public String getTodayOpenHoursText() {
		return todayOpenHoursText;
	}

	public void setTodayOpenHoursText(String todayOpenHoursText) {
		this.todayOpenHoursText = todayOpenHoursText;
	}

	public String getHoursText() {
		return hoursText;
	}

	public void setHoursText(String hoursText) {
		this.hoursText = hoursText;
	}

	public String getMondayToFridayText() {
		return mondayToFridayText;
	}

	public void setMondayToFridayText(String mondayToFridayText) {
		this.mondayToFridayText = mondayToFridayText;
	}

	public String getMondayToFridayOpenHours() {
		return mondayToFridayOpenHours;
	}

	public void setMondayToFridayOpenHours(String mondayToFridayOpenHours) {
		this.mondayToFridayOpenHours = mondayToFridayOpenHours;
	}

	public String getCurrentWaitTimeText() {
		return currentWaitTimeText;
	}

	public void setCurrentWaitTimeText(String currentWaitTimeText) {
		this.currentWaitTimeText = currentWaitTimeText;
	}

	public String getMailSectionHeading() {
		return mailSectionHeading;
	}

	public void setMailSectionHeading(String mailSectionHeading) {
		this.mailSectionHeading = mailSectionHeading;
	}

	public String getMailiconImage() {
		return mailiconImage;
	}

	public void setMailiconImage(String mailiconImage) {
		this.mailiconImage = mailiconImage;
	}

	public String getMailingAddressHeading() {
		return mailingAddressHeading;
	}

	public void setMailingAddressHeading(String mailingAddressHeading) {
		this.mailingAddressHeading = mailingAddressHeading;
	}

	public String getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public String getLinkText() {
		return linkText;
	}

	public void setLinkText(String linkText) {
		this.linkText = linkText;
	}

	public String getLinkURL() {
		return UtilityService.identifyLinkUrl(linkURL,resourceResolver);
	}

	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}

	public String getTabSelect() {
		return tabSelect;
	}

	public void setTabSelect(String tabSelect) {
		this.tabSelect = tabSelect;
	}

	public String getEmailIconImage() {
		return emailIconImage;
	}

	public void setEmailIconImage(String emailIconImage) {
		this.emailIconImage = emailIconImage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setConstantsConfigService(ConstantsConfigService constantsConfigService) {
		this.constantsConfigService = constantsConfigService;
	}
	
	public void setResourceResolver(ResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }	

	public String getAltTextPhone() {
		return altTextPhone;
	}

	public void setAltTextPhone(String altTextPhone) {
		this.altTextPhone = altTextPhone;
	}

	public String getAltTextEmail() {
		return altTextEmail;
	}

	public void setAltTextEmail(String altTextEmail) {
		this.altTextEmail = altTextEmail;
	}

	public String getAltTextMail() {
		return altTextMail;
	}

	public void setAltTextMail(String altTextMail) {
		this.altTextMail = altTextMail;
	}

	/**
	 * @return the contactList
	 */
	public List<PhoneListBean> getContactList() {
		List<PhoneListBean> copyListBean = contactList;
		Collections.copy(copyListBean, contactList);
		return copyListBean;
	}

	/**
	 * @return the popupId
	 */
	public String getPopupId() {
		return popupId;
	}

	/**
	 * @param popupOverlayContentPath the popupOverlayContentPath to set
	 */
	public void setPopupOverlayContentPath(String popupOverlayContentPath) {
		this.popupOverlayContentPath = popupOverlayContentPath;
	}

	/**
	 * @return the staticPhoneIconImage
	 */
	public String getStaticPhoneIconImage() {
		return staticPhoneIconImage;
	}

	/**
	 * @return the staticAltTextPhone
	 */
	public String getStaticAltTextPhone() {
		return staticAltTextPhone;
	}

	public void setPhoneList(Resource phoneList) {
		this.phoneList = phoneList;
	}

	public String getInternationalText() {
		return internationalText;
	}

	public void setInternationalText(String internationalText) {
		this.internationalText = internationalText;
	}

	public String getInternationalNumber() {
		return internationalNumber;
	}

	public void setInternationalNumber(String internationalNumber) {
		this.internationalNumber = internationalNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	
}

