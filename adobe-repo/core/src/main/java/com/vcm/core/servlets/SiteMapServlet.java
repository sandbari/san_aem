package com.vcm.core.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.vcm.core.service.AudienceSelectorService;
import com.vcm.core.utils.UtilityService;

@Component(immediate = true, service = Servlet.class, property = { "sling.servlet.extensions=" + "xml",
	"sling.servlet.selectors=" + "sitemap", "sling.servlet.resourceTypes=" + "sling/servlet/default",
	"sling.servlet.methods="
		+ HttpConstants.METHOD_GET, }, configurationPid = "com.vcm.core.servlets.SiteMapServlet")
@Designate(ocd = SiteMapServlet.SiteMapConfig.class)

public final class SiteMapServlet extends SlingSafeMethodsServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(SiteMapServlet.class);

    private static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");

    private static final boolean DEFAULT_INCLUDE_LAST_MODIFIED = false;

    private static final boolean DEFAULT_INCLUDE_INHERITANCE_VALUE = false;

    private static final String NS = "http://www.sitemaps.org/schemas/sitemap/0.9";

    @Reference
    private transient AudienceSelectorService audienceSelectorService;

    private boolean includeInheritValue;

    private boolean includeLastModified;

    private String[] changefreqProperties;

    private String[] priorityProperties;

    private String excludeFromSiteMapProperty;

    private String excludeTag;

    private String characterEncoding;

    @Activate
    protected void activate(SiteMapConfig config) {

	LOG.debug("Inside Sitemap activate method");

	this.includeLastModified = PropertiesUtil.toBoolean(config.include_lastmod(), DEFAULT_INCLUDE_LAST_MODIFIED);

	this.includeInheritValue = PropertiesUtil.toBoolean(config.include_inherit(),
		DEFAULT_INCLUDE_INHERITANCE_VALUE);

	this.changefreqProperties = PropertiesUtil.toStringArray(config.changefreq_properties(), new String[0]);

	this.priorityProperties = PropertiesUtil.toStringArray(config.priority_properties(), new String[0]);

	this.excludeFromSiteMapProperty = PropertiesUtil.toString(config.exclude_property(),
		NameConstants.PN_HIDE_IN_NAV);

	this.excludeTag = PropertiesUtil.toString(config.exclude_tag(), null);

	this.characterEncoding = PropertiesUtil.toString(config.character_encoding(), null);

	LOG.debug("Exiting Sitemap activate method");
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
	    throws ServletException, IOException {

	LOG.debug("Inside Sitemap doGet method");

	response.setContentType(request.getResponseContentType());

	if (StringUtils.isNotEmpty(this.characterEncoding)) {

	    response.setCharacterEncoding(characterEncoding);
	}

	ResourceResolver resourceResolver = request.getResourceResolver();

	PageManager pageManager = resourceResolver.adaptTo(PageManager.class);

	Page page = pageManager.getContainingPage(request.getResource());

	XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();

	try {

	    XMLStreamWriter stream = outputFactory.createXMLStreamWriter(response.getWriter());

	    stream.writeStartDocument("1.0");

	    stream.writeStartElement("", "urlset", NS);

	    stream.writeNamespace("", NS);

	    // first do the current page
	    write(page, stream, resourceResolver);

	    for (Iterator<Page> children = page.listChildren(new PageFilter(false, true), true); children.hasNext();) {

		write(children.next(), stream, resourceResolver);
	    }

	    stream.writeEndElement();

	    stream.writeEndDocument();

	} catch (XMLStreamException e) {

	    throw new IOException(e);
	}

	LOG.debug("Exiting Sitemap doGet method");

    }

    private void write(Page page, XMLStreamWriter stream, ResourceResolver resolver) throws XMLStreamException {

	if (isHidden(page)) {

	    return;
	}

	stream.writeStartElement(NS, "url");

	String loc = "";

	loc = this.getPageURL(page, resolver);

	writeElement(stream, "loc", loc);

	if (includeLastModified) {

	    Calendar cal = page.getLastModified();

	    if (cal != null) {

		writeElement(stream, "lastmod", DATE_FORMAT.format(cal));
	    }
	}

	if (includeInheritValue) {

	    HierarchyNodeInheritanceValueMap hierarchyNodeInheritanceValueMap = new HierarchyNodeInheritanceValueMap(
		    page.getContentResource());

	    writeFirstPropertyValue(stream, "changefreq", changefreqProperties, hierarchyNodeInheritanceValueMap);

	    writeFirstPropertyValue(stream, "priority", priorityProperties, hierarchyNodeInheritanceValueMap);

	} else {

	    ValueMap properties = page.getProperties();

	    writeFirstPropertyValue(stream, "changefreq", changefreqProperties, properties);

	    writeFirstPropertyValue(stream, "priority", priorityProperties, properties);
	}

	stream.writeEndElement();
    }

    private boolean isHidden(final Page page) {

	String[] tagsArray = page.getProperties().get(this.excludeFromSiteMapProperty, new String[0]);

	String searchIgnoreTag = this.excludeTag;

	if (tagsArray != null && tagsArray.length > 0) {

	    List<String> tagsArrayList = Arrays.asList(tagsArray);

	    if (tagsArrayList.contains(searchIgnoreTag)) {

		return true;

	    } else {

		return false;
	    }
	}

	return false;
    }

    private void writeFirstPropertyValue(final XMLStreamWriter stream, final String elementName,
	    final String[] propertyNames, final ValueMap properties) throws XMLStreamException {

	for (String prop : propertyNames) {

	    String value = properties.get(prop, String.class);

	    if (value != null) {

		writeElement(stream, elementName, value);

		break;
	    }
	}
    }

    private void writeFirstPropertyValue(final XMLStreamWriter stream, final String elementName,
	    final String[] propertyNames, final InheritanceValueMap properties) throws XMLStreamException {

	for (String prop : propertyNames) {

	    String value = properties.get(prop, String.class);

	    if (value == null) {

		value = properties.getInherited(prop, String.class);
	    }

	    if (value != null) {

		writeElement(stream, elementName, value);

		break;
	    }
	}
    }

    private void writeElement(final XMLStreamWriter stream, final String elementName, final String text)
	    throws XMLStreamException {

	stream.writeStartElement(NS, elementName);

	stream.writeCharacters(text);

	stream.writeEndElement();
    }

    public String getPageURL(Page page, ResourceResolver resourceResolver) {

	String pagePath = page.getPath();

	if (!StringUtils.isEmpty(page.getVanityUrl())) {

	    pagePath = page.getVanityUrl();

	}

	if (Objects.nonNull(page)) {

	    pagePath = UtilityService.identifyLinkUrl(pagePath, resourceResolver);

	    Resource jcrContentResource = page.getContentResource();
	    if (Objects.nonNull(jcrContentResource)) {

		String domainValue = audienceSelectorService.getMappedDomainForPage(jcrContentResource);

		if (Objects.nonNull(domainValue)) {

		    pagePath = domainValue + pagePath;
		}

	    }

	}
	return pagePath;

    }

    @ObjectClassDefinition(name = "VCM - Site Map Configuration", description = "VCM - Site Map Configuration")
    public @interface SiteMapConfig {

	@AttributeDefinition(name = "Sling Resource Type", description = "Sling Resource Type for the Home Page component or components.", type = AttributeType.STRING)
	String[] sling_servlet_resourceTypes();

	@AttributeDefinition(name = "Include Last Modified", description = "If true, the last modified value will be included in the sitemap.", type = AttributeType.BOOLEAN)
	boolean include_lastmod() default false;

	@AttributeDefinition(name = "Change Frequency Properties", description = "The set of JCR property names which will contain the change frequency value.", type = AttributeType.STRING)
	String[] changefreq_properties();

	@AttributeDefinition(name = "Priority Properties", description = "The set of JCR property names which will contain the priority value.", type = AttributeType.STRING)
	String[] priority_properties();

	@AttributeDefinition(name = "Exclude from Sitemap Property", description = "The boolean [cq:Page]/jcr:content property name which indicates if the Page should be hidden from the Sitemap. Default value: hideInNav", type = AttributeType.STRING)
	String exclude_property();

	@AttributeDefinition(name = "Tag Name", description = "Pages with these tags will be excluded from the sitemap generation", type = AttributeType.STRING)
	String exclude_tag();

	@AttributeDefinition(name = "Include Inherit Value", description = "If true searches for the frequency and priority attribute in the current page if null looks in the parent.", type = AttributeType.BOOLEAN)
	boolean include_inherit() default false;

	@AttributeDefinition(name = "Character Encoding", description = "If not set, the container's default is used (ISO-8859-1 for Jetty)", type = AttributeType.STRING)
	String character_encoding();
    }

    /**
     * @param audienceSelectorService the audienceSelectorService to set
     */
    public void setAudienceSelectorService(AudienceSelectorService audienceSelectorService) {
	this.audienceSelectorService = audienceSelectorService;
    }

}
