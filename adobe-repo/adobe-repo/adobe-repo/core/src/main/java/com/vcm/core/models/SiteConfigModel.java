package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CTS. A Model class for GenericList
 */

@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SiteConfigModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(SiteConfigModel.class);

	@Inject
	private String title;
	
	@Inject
	private String key;

	@Inject
	private Resource values;

	private List<String> valuesList;

	/**
	 * Post Construct method
	 */
	@PostConstruct
	protected void init() {
		LOGGER.debug("Into SiteConfigModel Model key is {}",key);
		valuesList = new ArrayList<>();
		if(Objects.nonNull(values)) {
			Iterator<Resource> valuesItr = values.listChildren();
			while(valuesItr.hasNext()) {
				Resource value = valuesItr.next();
					if(Objects.nonNull(value)) {
						ValueMap itemValuemap=value.getValueMap();
						if(itemValuemap.containsKey("value")) {
							String itemValue=itemValuemap.get("value",String.class);
							if(Objects.nonNull(itemValue)) {
								LOGGER.debug("itemValue is {}",itemValue);
								valuesList.add(itemValue);
							}
						}
						
					}
				}
			}
		LOGGER.debug("End SiteConfigModel Model");
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the valuesList
	 */
	public List<String> getValuesList() {
		List<String> copyListBean = valuesList;
		Collections.copy(copyListBean, valuesList);
		return copyListBean;
	}

	/**
	 * @param valuesList the valuesList to set
	 */
	public void setValuesList(List<String> valuesList) {
		List<String> copyListBean = new ArrayList<>();
		copyListBean.addAll(valuesList);
		this.valuesList = copyListBean;
	}

	public void setValues(Resource values) {
		this.values = values;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}


}
