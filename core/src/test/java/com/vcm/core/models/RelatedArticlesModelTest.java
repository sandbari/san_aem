package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
 public class RelatedArticlesModelTest {

	private RelatedArticlesModel relatedArticlesModel;
	private RelatedArticlePoints relatedArticlePoints;
	
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	
	@BeforeEach
    public void setup() throws Exception {
		relatedArticlePoints = new RelatedArticlePoints();
		relatedArticlesModel = new RelatedArticlesModel();
		List<RelatedArticlePoints> articlePoints = new ArrayList<RelatedArticlePoints>();
		
		relatedArticlesModel.setHeading("Heading");
		
		relatedArticlePoints.setThumbnailImage("Thumbnail Image");
		relatedArticlePoints.setAltText("Alt Text");
		relatedArticlePoints.setArticleText("Article Text");
		relatedArticlePoints.setLinkUrl("Link Url");
		relatedArticlePoints.setTabSelect("Tab Select");
		articlePoints.add(relatedArticlePoints);
		relatedArticlesModel.setArticlePoints(articlePoints);
	}
	
	@Test
 	void testRelatedArticlesModel() {
	
	assertNotNull(relatedArticlesModel.getHeading());
	assertNotNull(relatedArticlesModel.getArticlePoints());
	relatedArticlesModel.getHideRightArrow();
	}

}
