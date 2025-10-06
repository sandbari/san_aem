package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.PatternSyntaxException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.vcm.core.utils.UtilityService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FaqListModel {

	private static final Logger log = LoggerFactory.getLogger(FaqListModel.class);

	private static final String strQues = "question";
	private static final String strAns = "answer";

	@Inject
	private String headText;

	@Inject
	private String[] pages;

	@Inject
	private String seeAllText;

	@Inject
	private String seeAllUrl;
	
	@Inject
    private String linkDescription;

	@Inject
	private String tabSelect;

	@SlingObject
	private ResourceResolver resourceResolver;
	
	String linkAriaLabel;
	
	private List<FaqListContentModel> faqList = new ArrayList<FaqListContentModel>();
	
	@PostConstruct
    protected void init() {
           if(null != pages) {     	   
                        for(String page : pages) {
                        FaqListContentModel faqElement = new FaqListContentModel();
                        Resource resource = resourceResolver.getResource(page);
                        if(null != resource) {
                               ContentFragment contentFragmnent = resource.adaptTo(ContentFragment.class);
                               if(null != contentFragmnent.getElement(strQues)) {
                               faqElement.setQuestion(contentFragmnent.getElement(strQues).getContent());
                               }
                               if(null != contentFragmnent.getElement(strAns)) {
                               faqElement.setAnswer(contentFragmnent.getElement(strAns).getContent().replaceAll("\\<.*?\\>", ""));
                               }
                               faqList.add(faqElement);
                        }                      
                  }
           } 
           if(Objects.nonNull(linkDescription)){
               linkAriaLabel = linkDescription;
           } else if (Objects.nonNull(seeAllUrl) && Objects.nonNull(seeAllText)) {
               linkAriaLabel = UtilityService.getLinkDescription(seeAllUrl, seeAllText, resourceResolver);
           }
    }

	public String getHeadText() {
		return headText;
	}

	public void setHeadText(String headText) {
		this.headText = headText;
	}

	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

	public String getSeeAllText() {
		return seeAllText;
	}

	public void setSeeAllText(String seeAllText) {
		this.seeAllText = seeAllText;
	}
	
	public List<FaqListContentModel> getFAQs() {
		return Collections.unmodifiableList(faqList);
	}
	
	public String[] getPages() {
		return pages.clone();
	}

	public void setPages(String[] pages) {
		this.pages = pages.clone();
	}
	
	public String getSeeAllUrl() {
		return UtilityService.identifyLinkUrl(seeAllUrl,resourceResolver);
	}

	public String getTabSelect() {
		return tabSelect;
	}

	public void setSeeAllUrl(String seeAllUrl) {
		this.seeAllUrl = seeAllUrl;
	}

	public void setTabSelect(String tabSelect) {
		this.tabSelect = tabSelect;
	}
	
	public String getLinkDescription() {
		return linkDescription;
	}

	public void setLinkDescription(String linkDescription) {
		this.linkDescription = linkDescription;
	}
	
	public String getLinkAriaLabel() {
		return linkAriaLabel;
	}

	public void setLinkAriaLabel(String linkAriaLabel) {
		this.linkAriaLabel = linkAriaLabel;
	}
	
}
