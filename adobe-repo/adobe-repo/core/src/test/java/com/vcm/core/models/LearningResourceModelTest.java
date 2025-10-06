package com.vcm.core.models;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class LearningResourceModelTest {
		
	private LearningResourceModel learningResourceModel;
	private LearningResourceTileContent learningResourceTileContent;
	private List<LearningResourceTileContent> learningResourceTile;
	
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	
	@BeforeEach
    public void setup() throws Exception {
		learningResourceTileContent = new LearningResourceTileContent();
		learningResourceModel = new LearningResourceModel();
		
 		List<LearningResourceTileContent> tileContent = new ArrayList<LearningResourceTileContent>();  
		
		learningResourceModel.setResourceResolver(context.resourceResolver()); 
		
		learningResourceModel.setHeading("Advice & Tools");
		learningResourceModel.setDescription("Description");
		learningResourceModel.setLinkText("Visit the Investor Learning Center");
		learningResourceModel.setLinkUrl("/content/vcm/us/en");
		learningResourceModel.setTabSelect("_self");
		
		learningResourceTileContent.setTopic("TOPIC");
		learningResourceTileContent.setTileHeading("Tile Heading");
		learningResourceTileContent.setTileLinkURL("Tile Link Url");
		learningResourceTileContent.setTiletabSelect("_self");
		learningResourceTileContent.setBgimage("Tile Background Image");
		learningResourceTileContent.setTopicbackgroundcolor("Topic Background Color");
		learningResourceTileContent.setAltText("Tile Alt Text for Background Image");
	    tileContent.add(learningResourceTileContent);
		learningResourceModel.setTileContent(tileContent);
 


	}
	
	 @Test
	 	void testLearningResourceModel() {
		
		assertNotNull(learningResourceModel.getHeading());
		assertNotNull(learningResourceModel.getDescription());
		assertNotNull(learningResourceModel.getLinkText());	
		assertNotNull(learningResourceModel.getTabSelect());
		assertNotNull(learningResourceModel.getLinkUrl());
		assertNotNull(learningResourceModel.getTileContent());

	    
	 }
}
