package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class LearningResourceTileContentTest {

	private LearningResourceTileContent learningResourceTileContent;
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	
	@BeforeEach
    public void setup() throws Exception {
		learningResourceTileContent = new LearningResourceTileContent();
		
		learningResourceTileContent.setResourceResolver(context.resourceResolver()); 
		
		learningResourceTileContent.setTopic("TOPIC");
		learningResourceTileContent.setTileHeading("Tile Heading");
		learningResourceTileContent.setTileLinkURL("Tile Link Url");
		learningResourceTileContent.setTiletabSelect("_self");
		learningResourceTileContent.setBgimage("Tile Background Image");
		learningResourceTileContent.setTopicbackgroundcolor("Topic Background Color");
		learningResourceTileContent.setAltText("Tile Alt Text for Background Image");

	}
	
	@Test
	 public void testLearningResourceTileContent() {
		
		assertNotNull(learningResourceTileContent.getTopic());
		assertNotNull(learningResourceTileContent.getTileHeading());
		assertNotNull(learningResourceTileContent.getTiletabSelect());
		assertNotNull(learningResourceTileContent.getBgimage());
		assertNotNull(learningResourceTileContent.getTopicbackgroundcolor());
		assertNotNull(learningResourceTileContent.getTileLinkURL());
		assertNotNull(learningResourceTileContent.getAltText());

        

	     
	 }
}
