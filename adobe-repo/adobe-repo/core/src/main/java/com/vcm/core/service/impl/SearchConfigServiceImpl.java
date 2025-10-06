package com.vcm.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vcm.core.service.SearchConfigService;

@Component(service = SearchConfigService.class, immediate = true)
@Designate(ocd = SearchConfigServiceImpl.Config.class)
public class SearchConfigServiceImpl implements SearchConfigService {

	private static Logger LOGGER = LoggerFactory.getLogger(ConstantsConfigServiceImpl.class);

	private String searchRootPath;
	
	private String suggestFileIndexPath;

	/**
	 * @return the searchRootPath
	 */
	public String getSearchRootPath() {
		return searchRootPath;
	}

	
	/**
	 * @param searchRootPath the searchRootPath to set
	 */
	public void setSearchRootPath(String searchRootPath) {
		this.searchRootPath = searchRootPath;
	}

	
	/**
	 * @return the suggestFileIndexPath
	 */
	public String getSuggestFileIndexPath() {
	    return suggestFileIndexPath;
	}

	/**
	 * @param suggestFileIndexPath the suggestFileIndexPath to set
	 */
	public void setSuggestFileIndexPath(String suggestFileIndexPath) {
	    this.suggestFileIndexPath = suggestFileIndexPath;
	}

	@Activate
	@Modified
	protected void activate(final Config config) {
		
	    LOGGER.debug("Into search config service");
		
	    this.searchRootPath = String.valueOf(config.getSearchRootPath());
		
	    this.suggestFileIndexPath = String.valueOf(config.getSuggestIndexFilePath());

	}

	@ObjectClassDefinition(name = "VCM Search Configuration", description = "This service contains "
			+ "all VCM Search related  OSGI Configuration")
	public @interface Config {
		@AttributeDefinition(name = "Content Search Root Path", type = AttributeType.STRING)
		String getSearchRootPath() default StringUtils.EMPTY;
		
		@AttributeDefinition(name = "Suggest Index file Path", type = AttributeType.STRING)
		String getSuggestIndexFilePath() default StringUtils.EMPTY;

	}

}
