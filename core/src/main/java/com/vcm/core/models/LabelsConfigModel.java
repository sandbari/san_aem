package com.vcm.core.models;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

/**
 * @author CTS. A Model class for ThemeConfig
 */

@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LabelsConfigModel {


	@Inject
	private String title;

	@ChildResource
	private List<ProductDataValues> keyValueMap;
	
	@ChildResource
	private List<ProductLabelValues> labelKeyValueMap;
	
	LinkedHashMap<String, Object> productLabels = new LinkedHashMap<>();

	public String getTitle() {
		return title;
	}

	public List<ProductDataValues> getKeyValueMap() {
		List<ProductDataValues> copyOfKeyValueMap = keyValueMap;
		Collections.copy(copyOfKeyValueMap, keyValueMap);
		return copyOfKeyValueMap;
	}

	public List<ProductLabelValues> getLabelKeyValueMap() {
		List<ProductLabelValues> copyOflabelKeyValueMap = labelKeyValueMap;
		Collections.copy(copyOflabelKeyValueMap, labelKeyValueMap);
		return copyOflabelKeyValueMap;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setKeyValueMap(List<ProductDataValues> keyValueMap) {
		List<ProductDataValues> copyOfKeyValueMap = new ArrayList<ProductDataValues>();
		copyOfKeyValueMap.addAll(keyValueMap);
		this.keyValueMap = copyOfKeyValueMap;
	}

	public void setLabelKeyValueMap(List<ProductLabelValues> labelKeyValueMap) {
		List<ProductLabelValues> copyOflabelKeyValueMap = new ArrayList<ProductLabelValues>();
		copyOflabelKeyValueMap.addAll(labelKeyValueMap);
		this.labelKeyValueMap = copyOflabelKeyValueMap;
	}

	
	/**
	 * @return the productLabels
	 */
	public LinkedHashMap<String, Object> getProductLabels() {
		return productLabels;
	}

	/**
	 * @param productLabels the productLabels to set
	 */
	public void setProductLabels(LinkedHashMap<String, Object> productLabels) {
		this.productLabels = productLabels;
	}

	@PostConstruct
	protected void init() {
		if (Objects.nonNull(keyValueMap) && !keyValueMap.isEmpty()) {
			for (ProductDataValues keyValueMapList : keyValueMap) {
				productLabels.put(keyValueMapList.getKey(), keyValueMapList.getValue());
			}
		}
		if (Objects.nonNull(labelKeyValueMap) && !labelKeyValueMap.isEmpty()) {
			for (ProductLabelValues labelKeyValueList : labelKeyValueMap) {
				Map<String, String> labelKeyValueMap = new LinkedHashMap<String, String>();
				List<ProductDataValues> labelValues = labelKeyValueList.getLabelValues();
				if (!labelValues.isEmpty()) {
					for (ProductDataValues labelValuesList : labelValues) {
						labelKeyValueMap.put(labelValuesList.getKey(), labelValuesList.getValue());
					}
				}
				productLabels.put(labelKeyValueList.getKey(), labelKeyValueMap);
			}
		}
	}
}
