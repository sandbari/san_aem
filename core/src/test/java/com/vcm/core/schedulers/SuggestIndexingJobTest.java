package com.vcm.core.schedulers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.dam.api.Asset;
import com.google.gson.JsonObject;
import com.vcm.core.service.SearchConfigService;
import com.vcm.core.service.SuggestionServiceListGenerator;
import com.vcm.core.utils.FileHandlerService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
public class SuggestIndexingJobTest {
	
    private SuggestIndexingJob suggestIndexingJob = new SuggestIndexingJob();
    public final AemContext context = new AemContext();

    @Mock
    private SlingSettingsService slingSettingsService;
    
    @Mock
    private SuggestionServiceListGenerator suggestionServiceListGenerator;
    
    @Mock
    private SearchConfigService searchConfigService;
    
    @Mock
    private FileHandlerService fileHandlerService;
    
    @Mock
    Asset asset;
    
	private Set<String> runmodes = null;

    @BeforeEach
    void setup() throws LoginException {
    	suggestIndexingJob.setSearchConfigService(searchConfigService);
    	Mockito.when(searchConfigService.getSuggestFileIndexPath()).thenReturn("/content/dam/vcm");
        suggestIndexingJob.setFileHandlerService(fileHandlerService);
        Mockito.when(fileHandlerService.createFile(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(asset);
    }

    @Test
    public void testRun() {
    	runmodes = new HashSet<String>();
    	runmodes.add("author");
    	runmodes.add("publish");
    	suggestIndexingJob.setSlingSettingsService(slingSettingsService);
    	Mockito.when(slingSettingsService.getRunModes()).thenReturn(runmodes);
    	suggestIndexingJob.setSuggestionServiceListGenerator(suggestionServiceListGenerator);
    	Map<String, List<JsonObject>> suggestList = new HashMap<String, List<JsonObject>>();
    	JsonObject resultItem = new JsonObject();
    	resultItem.addProperty("value", "content");
    	List<JsonObject> membersSuggestList = new ArrayList<JsonObject>();
    	membersSuggestList.add(resultItem);
    	suggestList.put("key1", membersSuggestList);
    	Mockito.when(suggestionServiceListGenerator.createSuggestionList()).thenReturn(suggestList);
    	
    	suggestIndexingJob.run();
    	assertNotNull(asset);
    }
}
