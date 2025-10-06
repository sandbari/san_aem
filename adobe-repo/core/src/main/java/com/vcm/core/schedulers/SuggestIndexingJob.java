package com.vcm.core.schedulers;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.Asset;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vcm.core.constants.Constant;
import com.vcm.core.service.SearchConfigService;
import com.vcm.core.service.SuggestionServiceListGenerator;
import com.vcm.core.utils.FileHandlerService;

@Designate(ocd = SuggestIndexingJob.Config.class)
@Component(service = Runnable.class, immediate = true)
public class SuggestIndexingJob implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(SuggestIndexingJob.class);

    @Reference
    private FileHandlerService fileHandlerService;

    @Reference
    private SlingSettingsService slingSettingsService;

    @Reference
    private SearchConfigService searchConfigService;

    @Reference
    private SuggestionServiceListGenerator suggestionServiceListGenerator;

    public void setFileHandlerService(FileHandlerService fileHandlerService) {
		this.fileHandlerService = fileHandlerService;
	}

	public void setSlingSettingsService(SlingSettingsService slingSettingsService) {
		this.slingSettingsService = slingSettingsService;
	}

	public void setSearchConfigService(SearchConfigService searchConfigService) {
		this.searchConfigService = searchConfigService;
	}

	public void setSuggestionServiceListGenerator(SuggestionServiceListGenerator suggestionServiceListGenerator) {
		this.suggestionServiceListGenerator = suggestionServiceListGenerator;
	}

	private Set<String> runmodes = null;

    @ObjectClassDefinition(name = "VCM Suggest Indexing Scheduler", description = "A Scheduler to index suggest titles daily")
    public @interface Config {
	@AttributeDefinition(name = "Cron-job expression", description = "Expression stands for sec min hour monthDay month weekday year")
	String scheduler_expression() default "0 0 0 1/1 * ? *";

	@AttributeDefinition(name = "Concurrent task", description = "Whether or not to schedule this task concurrently")
	boolean scheduler_concurrent() default false;
    }

    @Override
    public void run() {

	runmodes = slingSettingsService.getRunModes();

	if (runmodes.contains(Constant.AUTHOR_INSTANCE)) {

	    long starttime = System.currentTimeMillis();

	    LOG.debug("SuggestIndexingJob Started at {}", starttime);

	    Map<String, List<JsonObject>> suggestList = suggestionServiceListGenerator.createSuggestionList();

	    String filePath = searchConfigService.getSuggestFileIndexPath();
	    
	    LOG.debug("SuggestIndexingJob filePath at {}", filePath);


	    if (Objects.nonNull(suggestList) && !suggestList.isEmpty() && Objects.nonNull(filePath)) {

		this.createFile(suggestList, filePath);

	    }

	    long endtime = System.currentTimeMillis();

	    LOG.debug("SuggestIndexingJob Ended at {}", endtime);
	}

    }

    private void createFile(Map<String, List<JsonObject>> suggestList, String filePath) {

	if (Objects.nonNull(suggestList) && Objects.nonNull(filePath) && !StringUtils.isEmpty(filePath)) {

	    for (Map.Entry mapElement : suggestList.entrySet()) {
		
		String key = (String) mapElement.getKey();

		Gson gson = new Gson();

		String jsonString = gson.toJson(mapElement.getValue());
		
		 LOG.debug("SuggestIndexingJob key at {}", key);

		 LOG.debug("SuggestIndexingJob jsonString at {}", jsonString);


		if (Objects.nonNull(jsonString) && !jsonString.isEmpty()) {
		    if (Objects.nonNull(key) && !key.isEmpty()) {
			
			String userSpecificFilePath = filePath + "suggesstions-"+key+".json";

			Asset suggestIndexFile = fileHandlerService.createFile(userSpecificFilePath, jsonString,
				Constant.APPLICATION_JSON);

			if (Objects.nonNull(suggestIndexFile) && runmodes.contains(Constant.AUTHOR_INSTANCE)) {

			    fileHandlerService.replicateAsset(suggestIndexFile.getPath());
			}
		    }
		}
	    }

	}

    }

}
