package com.vcm.core.pojo;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import com.google.gson.annotations.Expose;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DataBean {	

	@Inject
	@Expose
    String name;

	@Inject
    String value;

	@Expose
    Number y;

	@PostConstruct
	public void init() {
		if(value.contains(".")) {
			y =  Float.parseFloat(value);
		}
		else {
			y =  Integer.parseInt(value);
		}		
	}	
    
    public void setValue(String value) {
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
    public void setName(String name) {
		this.name = name;
	}

	public Number getY() {
		return y;
	}
}
