package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class RelatedArticlePointsTest {
	
	private RelatedArticlePoints relatedArticlePoints;
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

	@BeforeEach
    public void setup() throws Exception {
		relatedArticlePoints = new RelatedArticlePoints();
		
		relatedArticlePoints.setResourceResolver(context.resourceResolver()); 
		
		relatedArticlePoints.setThumbnailImage("Thumbnail Image");
		relatedArticlePoints.setAltText("Alt Text");
		relatedArticlePoints.setArticleText("Article Text");
		relatedArticlePoints.setLinkUrl("Link Url");
		relatedArticlePoints.setTabSelect("Tab Select");
	}
	
	@Test
	 public void testRelatedArticlePoints() {
		
		assertNotNull(relatedArticlePoints.getThumbnailImage());
		assertNotNull(relatedArticlePoints.getAltText());
		assertNotNull(relatedArticlePoints.getArticleText());
		assertNotNull(relatedArticlePoints.getLinkUrl());
		assertNotNull(relatedArticlePoints.getTabSelect());
	}
	
}
