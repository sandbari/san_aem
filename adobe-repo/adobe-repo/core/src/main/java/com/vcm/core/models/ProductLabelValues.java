package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductLabelValues {

	
	@Inject
	private String key;

	@ChildResource
	private List<ProductDataValues> labelValues;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<ProductDataValues> getLabelValues() {
		List<ProductDataValues> copyOflabelValues = labelValues;
		Collections.copy(copyOflabelValues, labelValues);
		return copyOflabelValues;
	}

	public void setLabelValues(List<ProductDataValues> labelValues) {
		List<ProductDataValues> copyOflabelValues = new ArrayList<ProductDataValues>();
		copyOflabelValues.addAll(labelValues);
		this.labelValues = copyOflabelValues;
	}
	
}

