package com.vcm.core.models;

import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class RelatedArticlesModel{

		@Inject
		private String heading;
		
		@Inject
	    List<RelatedArticlePoints> articlePoints;
		
		@Inject
		private Boolean hideRightArrow;
				
		public Boolean getHideRightArrow() {
			return hideRightArrow;
		}

		public List<RelatedArticlePoints> getArticlePoints() {
			return articlePoints;
		}

		public void setArticlePoints(List<RelatedArticlePoints> articlePoints) {
			this.articlePoints = articlePoints;
		}

		public String getHeading() {
			return heading;
		}

		public void setHeading(String heading) {
			this.heading = heading;
		}

		

	}