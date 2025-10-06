package com.vcm.core.service.impl;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.vcm.core.pojo.BlogBean;
import com.vcm.core.pojo.BlogListBean;
import com.vcm.core.service.InsightListingService;
import com.vcm.core.utils.PropertyUtil;
import com.vcm.core.utils.UtilityService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Component(service = InsightListingService.class, immediate = true)
public class InsightListingServiceImpl implements InsightListingService {

    private static final Logger LOG = LoggerFactory.getLogger(InsightListingServiceImpl.class);
    private Iterator<Page> pageIterator;
    private BlogListBean blogListBean;
    private NodeIterator nodeIterator;
    private BlogBean blogBean;
    private Page rootPage;
    private List<BlogBean> blogList;

    @Override
    public String blogListJsonString(SlingHttpServletRequest request) throws RepositoryException {
        Node contentNode = null;
        ResourceResolver resolver = request.getResourceResolver();
        contentNode = request.getResource().adaptTo(Node.class);
        String blogListJsonString = "";
        blogListBean = new BlogListBean();
        try {
            if (null == request.getParameter("blogRootPath")) {
                setBlogContent(resolver, contentNode);
            } else {
                Resource resource = resolver.getResource(request.getParameter("blogRootPath"));
                if (null != resource) {
                    rootPage = resource.adaptTo(Page.class);
                    NodeIterator blogNodeIterator = PropertyUtil.getNodesbySearchQuery(resolver,
                            JcrConstants.NT_UNSTRUCTURED, rootPage.getPath(),
                            JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, "vcm/components/content/blogs/bloglisting");
                    while (blogNodeIterator.hasNext()) {
                        setBlogContent(resolver, blogNodeIterator.nextNode());
                    }
                }

            }
            if (null != rootPage) {
                pageIterator = rootPage.listChildren();
                setBlogBeanList(resolver);
                LOG.debug("size of blogList {}", blogList.size());
                if (null != blogList && !blogList.isEmpty()) {
                    Collections.sort(blogList, new Comparator<BlogBean>() {
                        @Override
                        public int compare(BlogBean arg0, BlogBean arg1) {
                            if (arg0.getAuthorDateDt() == null) {
                                return (arg1.getAuthorDateDt() == null) ? 0 : -1;
                            }
                            if (arg1.getAuthorDateDt() == null) {
                                return 1;
                            }
                            return arg1.getAuthorDateDt().compareTo(arg0.getAuthorDateDt());
                        }
                    });
                }
                int index = 1000001;
                BlogBean featuredBlogBean = null;
                for (BlogBean blogBean : blogList) {
                    String blogId = "BLOG" + index;
                    blogBean.setBlogId(blogId);
                    if (blogBean.isFeatureBlog()) {
                        featuredBlogBean = blogBean;
                    }
                    index++;
                }
                blogList.remove(featuredBlogBean);
                blogListBean.setBlogList(blogList);
                Gson gson = new Gson();
                blogListJsonString = gson.toJson(blogListBean);
            }
        } catch (Exception e) {
            LOG.error("Error occured {} {} {}", e, e.getMessage(), e.getStackTrace());
        }
        return blogListJsonString;
    }

    public void setBlogBeanList(ResourceResolver resolver) throws RepositoryException {
        blogList = new ArrayList<>();
        TagManager tagManager = resolver.adaptTo(TagManager.class);
        while (pageIterator.hasNext()) {
            blogBean = new BlogBean();
            blogBean.setFeatureBlog(false);
            Page childPage = pageIterator.next();
            Node childPageNode = childPage.getContentResource().adaptTo(Node.class);
            blogBean.setBlogPagePath(UtilityService.identifyLinkUrl(childPage.getPath(), resolver));
            String topic = "";
            LOG.debug("setBlogBeanList childPage.getPath {}", childPage.getPath());
            LOG.debug("setBlogBeanList childPageNode.getPath {}", childPageNode.getPath());
            if (childPageNode.hasProperty("cq:tags")) {
                Value values[] = childPageNode.getProperty("cq:tags").getValues();
                for (Value val : values) {
                    Tag topicTag = tagManager.resolve(val.getString());
                    topic = topic + topicTag.getTitle() + ", ";
                }
            }
            if (topic.endsWith(", ")) {
                topic = topic.substring(0, topic.length() - 2);
            }
            LOG.debug("setBlogBeanList topic {}", topic);
            blogBean.setTopic(topic);
            nodeIterator = PropertyUtil.getNodesbySearchQuery(resolver, JcrConstants.NT_UNSTRUCTURED,
                    childPage.getPath(), JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY,
                    "vcm/components/content/insight/insightarticle");

            LOG.debug("setBlogBeanList nodeIterator.getSize {}", nodeIterator.getSize());
            if (nodeIterator.getSize() > 0) {
                setBlogArticleDetails();
                blogList.add(blogBean);
            }
        }

    }

    public void setBlogArticleDetails() throws RepositoryException {
        Date authorDate = new Date();
        String imagePath = "";
        String blogTileHeading = "";
        String authorDateStr = "";
        String earMarkColor = "";
        Boolean isFeatured = false;
        String blogTileDescription = "";
        Boolean hideAuthorDate = false;
        while (nodeIterator.hasNext()) {
            Node childContentNode = nodeIterator.nextNode();
            if (childContentNode.hasProperty("authorDate")) {
                authorDate = childContentNode.getProperty("authorDate").getDate().getTime();
                LOG.debug("author Date {}", authorDate.toString());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                authorDateStr = simpleDateFormat.format(authorDate);
                LOG.debug("author Date after formatting {}", authorDateStr);
            }
            if (childContentNode.hasProperty("authorName")) {
                Property prop = childContentNode.getProperty("authorName");
                Map<String, String> authorMap = new HashMap<>();
                if (prop.isMultiple()) {
                    Set<String> myset = new HashSet<String>();
                    Value[] authorValue = prop.getValues();
                    for (Value value : authorValue) {
                        myset.add(value.getString());
                    }
                    int i = 0;
                    for (String author : myset) {
                        authorMap.put("author" + i, author);
                        i++;
                    }

                } else {
                    authorMap.put("author", prop.getString());
                }
                blogBean.setAuthorName(authorMap);
            }
            if (childContentNode.hasProperty("isFeaturedInsight")) {
                isFeatured = childContentNode.getProperty("isFeaturedInsight").getBoolean();
                blogListBean.setFeatureBlog(blogBean);
                blogBean.setFeatureBlog(isFeatured);
            }
            if (childContentNode.hasProperty("landingImage")) {
                imagePath = childContentNode.getProperty("landingImage").getString();
            }
            if (childContentNode.hasProperty("tileHeading")) {
                blogTileHeading = childContentNode.getProperty("tileHeading").getString();
            }
            if (childContentNode.hasProperty("earMarkColor")) {
                earMarkColor = childContentNode.getProperty("earMarkColor").getString();
            }
            if (childContentNode.hasProperty("tileDescription")) {
                blogTileDescription = childContentNode.getProperty("tileDescription").getString();
            }
            if (childContentNode.hasProperty("hideAuthorDate")) {
                hideAuthorDate = childContentNode.getProperty("hideAuthorDate").getBoolean();
            }
            blogBean.setAuthorDateDt(authorDate);
            blogBean.setImagePath(imagePath);
            blogBean.setTitle(blogTileHeading);
            blogBean.setDescription(blogTileDescription);
            blogBean.setAuthorDate(authorDateStr);
            blogBean.setEarMarkColor(earMarkColor);
            blogBean.setHideAuthorDate(hideAuthorDate);
        }

    }

    public void setBlogContent(ResourceResolver resolver, Node currentNode) throws RepositoryException {
        if (null != currentNode) {
            String itemsOnLoadMore = "";
            String authorPagePath = "";
            String rootPath = "";
            if (currentNode.hasProperty("rootPath")) {
                rootPath = currentNode.getProperty("rootPath").getString();
            }
            rootPage = resolver.getResource(rootPath).adaptTo(Page.class);
            LOG.debug("RootPage of the blog {}", rootPage.getPath());

            if (currentNode.hasProperty("authorPagePath")) {
                authorPagePath = currentNode.getProperty("authorPagePath").getString();
            }
            blogListBean.setAuthorLink(UtilityService.identifyLinkUrl(authorPagePath, resolver));
            if (currentNode.hasProperty("itemsOnLoadMore")) {
                itemsOnLoadMore = currentNode.getProperty("itemsOnLoadMore").getString();
            }
            blogListBean.setItemsOnLoadMore(itemsOnLoadMore);
        }
    }

    public BlogListBean getBlogListBean() {
        return blogListBean;
    }

}
