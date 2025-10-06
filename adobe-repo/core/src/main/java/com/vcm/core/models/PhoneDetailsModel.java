package com.vcm.core.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcm.core.pojo.PhoneListBean;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.*;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = "jackson", extensions = "json")
public class PhoneDetailsModel {

	private static final Logger LOG = LoggerFactory.getLogger(PhoneDetailsModel.class);

	@Inject
	private String phoneConfig;

	@Inject
	private String phoneLink;

	@ChildResource
	private Resource phoneList;

	@ChildResource
	private Resource segmentList;

	@Inject
	private String displayMode;

	@Inject
	private String backToContactLink;
	
	private List<PhoneListBean> phones = new ArrayList<PhoneListBean>();
	private List<String> segments = new ArrayList<String>();
	private String phonesJson;

	@PostConstruct
	protected void init() {
		if (Objects.nonNull(phoneList)) {
			Iterator<Resource> phoneListItr = phoneList.listChildren();
			if (Objects.nonNull(phoneListItr)) {
				while (phoneListItr.hasNext()) {
					Resource detailField = phoneListItr.next();
					if (Objects.nonNull(detailField)) {
						PhoneListBean phoneListBean = detailField
								.adaptTo(PhoneListBean.class);
						phones.add(phoneListBean);
					}
				}
				if(!phones.isEmpty()){
					ObjectMapper om = new ObjectMapper();
					try {
						phonesJson = om.writeValueAsString(phones);
					} catch (JsonProcessingException e) {
						LOG.error("JsonProcessException=", e);
					}
				}
			}
		}

		if (Objects.nonNull(segmentList)) {
			Iterator<Resource> segmentListItr = segmentList.listChildren();
			if (Objects.nonNull(segmentListItr)) {
				while (segmentListItr.hasNext()) {
					Resource detailField = segmentListItr.next();
					if (Objects.nonNull(detailField)) {
						ValueMap vm = detailField.getValueMap();
						segments.add(vm.get("segmentConfig", ""));
					}
				}
			}
		}

		LOG.debug("END Phone Detail init method");
	}

	public String getPhoneConfig() {
		return phoneConfig;
	}

	public void setPhoneConfig(String phoneConfig) {
		this.phoneConfig = phoneConfig;
	}

	public String getPhoneLink() {
		return phoneLink;
	}

	public void setPhoneLink(String phoneLink) {
		this.phoneLink = phoneLink;
	}

	public Resource getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(Resource phoneList) {
		this.phoneList = phoneList;
	}

	public Resource getSegmentList() {
		return segmentList;
	}

	public void setSegmentList(Resource segmentList) {
		this.segmentList = segmentList;
	}

	public String getDisplayMode() {
		return new String(displayMode);
	}

	public void setDisplayMode(String displayMode) {
		this.displayMode = new String(displayMode);
	}

	public String getBackToContactLink() {
		return new String(backToContactLink);
	}

	public void setBackToContactLink(String backToContactLink) {
		this.backToContactLink = new String(backToContactLink);
	}

	public List<PhoneListBean> getPhones() {
		return new ArrayList<>(phones);
	}

	public void setPhones(List<PhoneListBean> phones) {
		this.phones = new ArrayList<>(phones);
	}

	public List<String> getSegments() {
		return new ArrayList<>(segments);
	}

	public void setSegments(List<String> segments) {
		this.segments = new ArrayList<>(segments);
	}

	public String getPhonesJson() {
		return phonesJson;
	}

	public void setPhonesJson(String phonesJson) {
		this.phonesJson = phonesJson;
	}
}

