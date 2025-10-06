package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.tagging.TagManager;
import com.google.gson.Gson;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FaqDetailsModel {

    private static final Logger log = LoggerFactory.getLogger(FaqDetailsModel.class);

    private final String faqTagStr = "faqTag"; // string name taken for tags.

    private final String tagGrpNameStr = "tagGrpName"; // string name taken for group.

    @SlingObject
    private ResourceResolver resourceResolver;

    @Inject
    @Default(values = "Show All FAQ")
    private String seeAllFaq;

    public String getSeeAllFaq() {
        return seeAllFaq;
    }

    public void setSeeAllFaq(String seeAllFaq) {
        this.seeAllFaq = seeAllFaq;
    }

    @Inject
    @Default(values = "/content/dam")
    private String parentPath; // root path.

    @Inject
    public Resource tagDetailsList; // multifield name.

    private String faqDetailsContentListJson;

    @PostConstruct
    protected void init() {
        List < FaqDetailsBean > faqDetailsContentList = new ArrayList < FaqDetailsBean > ();

        // TagManager allows for creating tags by paths and names.
        final TagManager tagManager = resourceResolver.adaptTo(TagManager.class);

        // Main loop to formulate the faqDetailsContentList
        if (null != tagDetailsList) {
            for (Iterator < Resource > iter = tagDetailsList.listChildren(); iter.hasNext();) {

                ValueMap tagDetailsValMap = iter.next().getValueMap();

                List < FaqDetailsCFBean > faqCfList = new ArrayList < > ();

                FaqDetailsBean faqDetailsContent = new FaqDetailsBean();

                List < String > tempResPath = new ArrayList < String > ();

                String tagGrpName = tagDetailsValMap.get(tagGrpNameStr, String.class);

                // Iterate through each tag and getting the list of faq contentfragments
                tagManager.find(parentPath, tagDetailsValMap.get(faqTagStr, new String[0]), true)
                    .forEachRemaining(resource -> {

                        // will display all the cf path
                        String cfTempPath = StringUtils.substringBeforeLast(resource.getPath(),
                            (JcrConstants.JCR_CONTENT));

                        if (!tempResPath.contains(cfTempPath)) {
                            tempResPath.add(cfTempPath);

                            // object of CFBean class
                            FaqDetailsCFBean faqCf = new FaqDetailsCFBean();
                            ContentFragment contentFragmnent = resourceResolver.getResource(cfTempPath)
                                .adaptTo(ContentFragment.class);
                            if (null != contentFragmnent.getElement("question")) {
                                faqCf.setQuestion(contentFragmnent.getElement("question").getContent());
                            }

                            if (null != contentFragmnent.getElement("answer")) {
                                faqCf.setAnswer(contentFragmnent.getElement("answer").getContent());
                            }

                            String faqNumber = contentFragmnent.getElement("faqnumber").getContent();

                            if (StringUtils.isNotBlank(faqNumber) && StringUtils.isNumeric(faqNumber)) {
                                faqCf.setFaqnumber(Integer.parseInt(faqNumber));
                            } else {
                                faqCf.setFaqnumber(0);
                            }

                            faqCfList.add(faqCf);

                        }

                    });

                faqDetailsContent.setGroupName(tagGrpName);
                faqDetailsContent.setFaqList(sortContentFragment(faqCfList));
                faqDetailsContentList.add(faqDetailsContent);

            }
        }

        // Converting to json
        faqDetailsContentListJson = null;
        if (faqDetailsContentList != null && faqDetailsContentList.size() > 0) {
            Gson gson = new Gson();
            faqDetailsContentListJson = gson.toJson(faqDetailsContentList);
            log.debug("Sorted fragments list: ", faqDetailsContentListJson);
        }
    }

    public List < FaqDetailsCFBean > sortContentFragment(List < FaqDetailsCFBean > faqcf) {
        final List < FaqDetailsCFBean > sortedFragments = new ArrayList < > (faqcf);

        Collections.sort(sortedFragments,
            Comparator.comparingInt(FaqDetailsCFBean::getFaqnumber).thenComparing(FaqDetailsCFBean::getQuestion));

        log.debug("Sorted fragments list: ", sortedFragments);

        return sortedFragments;

    }

    public void setResourceResolver(ResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public Resource getTagDetailsList() {
        return tagDetailsList;
    }

    public String getFaqDetailsContentListJson() {
        return faqDetailsContentListJson;
    }

    public void setTagDetailsList(Resource tagDetailsList) {
        this.tagDetailsList = tagDetailsList;
    }

}