package com.vcm.core.schedulers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.day.cq.replication.ReplicationException;
import com.vcm.core.constants.Constant;
import com.vcm.core.service.SiteMapService;

@Designate(ocd = SiteMapScheduler.Config.class)
@Component(service = Runnable.class, immediate = true)
public class SiteMapScheduler implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteMapScheduler.class);

    String sitemapPath;
    String siteRootPath;
    String domainName;
    String uri;


    @Reference
    SiteMapService siteMapService;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @ObjectClassDefinition(name = "VCM Sitemap Scheduler", description = "A Scheduler to generate the sitemap daily")
    public @interface Config {
        @AttributeDefinition(name = "Cron-job expression", description = "Expression stands for sec min hour monthDay month weekday year")
        String scheduler_expression() default "0 0 0 1/1 * ? *";

        @AttributeDefinition(name = "Concurrent task", description = "Whether or not to schedule this task concurrently")
        boolean scheduler_concurrent() default false;

        @AttributeDefinition(name = "Site Map Path", description = "DAM path where the sitemap wil be stored")
        String sitemapPath() default "/content/dam/vcm/sitemap/sitemap.xml";

        @AttributeDefinition(name = "Root Path", description = "Root Path from where the sitemap needs to be generated")
        String siteRootPath() default "/content/vcm/us/en";

        @AttributeDefinition(name = "Domain Name", description = "Domain where the sitemap needs to be generated")
        String domainName() default "";
    }


    @Activate
    protected void activate(final Config config) {
        sitemapPath = config.sitemapPath();
        siteRootPath = config.siteRootPath();
        domainName = config.domainName();
    }

    @Override
    public void run() {
        Map<String, Object> map = new HashMap<>();
        map.put(resourceResolverFactory.SUBSERVICE, "vcmservice");
        if (resourceResolverFactory != null) {
            try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(map)) {
                Session  session = resourceResolver.adaptTo(Session.class);
                uri = domainName + siteRootPath + Constant.SITEMAP_DOT_XML;
                LOGGER.debug("sitemap servelt url: " + uri);
                if (Objects.nonNull(sitemapPath) && Objects.nonNull(uri) && Objects.nonNull(resourceResolver) && Objects.nonNull(session)) {
                    siteMapService.updateSiteMaps(sitemapPath, uri, resourceResolver, session);
                }
            } catch (LoginException | ParserConfigurationException | SAXException | IOException | RepositoryException | TransformerException | TransformerFactoryConfigurationError | ReplicationException e) {
                LOGGER.error("Exception Occured during sitemap generation {}", e);
            }
        }
    }

    public void setResourceResolverFactory(ResourceResolverFactory resourceResolverFactory) {
        this.resourceResolverFactory = resourceResolverFactory;
    }

}
