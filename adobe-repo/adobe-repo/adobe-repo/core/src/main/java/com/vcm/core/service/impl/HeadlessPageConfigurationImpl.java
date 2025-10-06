package com.vcm.core.service.impl;

import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.vcm.core.service.HeadlessPageConfiguration;

@Component(service = HeadlessPageConfiguration.class, immediate = true)
@Designate(ocd = HeadlessPageConfigurationImpl.Config.class)
public class HeadlessPageConfigurationImpl implements HeadlessPageConfiguration {

	//private static Logger LOGGER = LoggerFactory.getLogger(HeadlessPageConfigurationImpl.class);

	private String[] cssLinks;
	private String[] clientLibsCategories;
	private String[] productPageClientLibCategories;
	private String[] filterClientlibsCategories;
	private String[] filterComponents;
	private String[] hostPatternRequiredElements;

	@Activate
	@Modified
	protected void activate(final Config config) {
		this.cssLinks = config.cssLinks();
		this.clientLibsCategories= config.clientLibsCategories();
		this.productPageClientLibCategories= config.productPageClientLibCategories();
		this.filterClientlibsCategories= config.filterClientlibsCategories();
		this.filterComponents=config.filterComponents();
		this.hostPatternRequiredElements=config.getHostPatternRequiredElements();
	}

	
	@ObjectClassDefinition(name = "VCM Headless Page Configuration", description = "This service contains "
			+ "all Headless page related configuraions")
	public @interface Config {
	 
	   
	   @AttributeDefinition(name = "External CSS Links", description = "Please add external css links")
	   String[] cssLinks() default {""};
	   
	   @AttributeDefinition(name = "ClientLibs to be added", description = "Please add list clientlibs to be added in headless page")
	   String[] clientLibsCategories() default {""};
	   
	   @AttributeDefinition(name = "ClientLibs to be added for product pages", description = "Please add list clientlibs to be added in headless page")
	   String[] productPageClientLibCategories() default {""};

	   @AttributeDefinition(name = "ClientLibs to be filtered", description = "Please add list clientlibs to be filtered in headless page")
	   String[] filterClientlibsCategories() default {""}; 
	   
	   @AttributeDefinition(name = "Components to be filtered", description = "Please add list of components to be filtered in headless page")
	   String[] filterComponents() default {""}; 
	   
	   @AttributeDefinition(name = "Elements to be added with hostpattern", description = "Please add list of elements to be added with hostpattern in headless page")
	   String[] getHostPatternRequiredElements() default {""}; 
	}


	@Override
	public String[] getCSSLinks() {
		String[] cssLinksLocal= {""};
		if(Objects.nonNull(cssLinks)) {
			cssLinksLocal=cssLinks.clone();
		}
		return cssLinksLocal;
		
	}


	@Override
	public String[] getClientLibsCategories() {
		String[] clientLibsCategoriesLocal= {""};
		if(Objects.nonNull(clientLibsCategories)) {
			clientLibsCategoriesLocal=clientLibsCategories.clone();
		}
		return clientLibsCategoriesLocal;
	}


	@Override
	public String[] getProductPageClientLibsCategories() {
		String[] productPageClientLibCategoriesLocal= {""};
		if(Objects.nonNull(productPageClientLibCategories)) {
			productPageClientLibCategoriesLocal=productPageClientLibCategories.clone();
		}
		return productPageClientLibCategoriesLocal;
	}


	@Override
	public String[] getFilterClientlibsCategories() {
		String[] filterClientlibsCategoriesLocal= {""};
		if(Objects.nonNull(filterClientlibsCategories)) {
			filterClientlibsCategoriesLocal=filterClientlibsCategories.clone();
		}
		return filterClientlibsCategoriesLocal;
	}


	/**
	 * @return the filterComponents
	 */
	@Override
	public String[] getFilterComponents() {
		String[] filterComponentsLocal= {""};
		if(Objects.nonNull(filterComponents)) {
			filterComponentsLocal=filterComponents.clone();
		}
		return filterComponentsLocal;
	}


	/**
	 * @param filterComponents the filterComponents to set
	 */
	public void setFilterComponents(String[] filterComponents) {
		if(Objects.nonNull(filterComponents)) {
			String[] filterComponentsLocal = filterComponents.clone();
			this.filterComponents = filterComponentsLocal;
		}
	}


	@Override
	public String[] getHostPatternRequiredElements() {
	    String[] hostPatternRequiredElementsLocal= {""};
		if(Objects.nonNull(filterComponents)) {
		    hostPatternRequiredElementsLocal=hostPatternRequiredElements.clone();
		}
		return hostPatternRequiredElementsLocal;
	}

}
