package com.vcm.core.schedulers;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
public class SiteMapSchedulerTest {
    private SiteMapScheduler siteMapScheduler = new SiteMapScheduler();
    private ResourceResolver resourceResolver;
    public final AemContext context = new AemContext();

    @Mock
    private ResourceResolverFactory resourceResolverFactory;

    @BeforeEach
    void setup() throws LoginException {
        resourceResolver=context.resourceResolver();
        siteMapScheduler.setResourceResolverFactory(resourceResolverFactory);
        Mockito.when(resourceResolverFactory.getServiceResourceResolver(Mockito.any())).thenReturn(resourceResolver);
    }

    @Test
    void run() {
        SiteMapScheduler.Config config = mock(SiteMapScheduler.Config.class);
        siteMapScheduler.activate(config);
        siteMapScheduler.run();
    }
}
