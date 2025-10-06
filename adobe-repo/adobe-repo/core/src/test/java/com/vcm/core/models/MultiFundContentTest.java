package com.vcm.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.framework.Assert;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(AemContextExtension.class)
public class MultiFundContentTest {

    private MultiFundContent multiFundContent;
    private ResourceResolver resourceResolver;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @BeforeEach
    public void setup() throws Exception {
        multiFundContent = new MultiFundContent();
        resourceResolver=context.resourceResolver();
        multiFundContent.setResourceResolver(resourceResolver);
        multiFundContent.setFundName("Victory INCORE Fund for Income");
        multiFundContent.setFundUrl("/content/vcm/language-masters/en/member/about-victory");
        multiFundContent.setTicker("IPFIX");
    }

    @Test
    void testMultiFunds() throws Exception {
        Assert.assertNotNull(multiFundContent.getResourceResolver());
        Assert.assertNotNull(multiFundContent.getFundName());
        Assert.assertNotNull(multiFundContent.getFundUrl());
        Assert.assertNotNull(multiFundContent.getTicker());
    }
}
