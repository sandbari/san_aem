package com.vcm.core.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.framework.Assert;

@ExtendWith(AemContextExtension.class)
public class FeaturedFundsModelTest {

    private FeaturedFundsModel featuredFundsModel;
    private ResourceResolver resourceResolver;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @BeforeEach
    public void setup() throws Exception {
        featuredFundsModel = new FeaturedFundsModel();
        resourceResolver=context.resourceResolver();
        featuredFundsModel.setResourceResolver(resourceResolver);
        featuredFundsModel.setDescription("USAA Mutual Funds are managed by Victory Capital. Get started today with a low initial investment.");
        featuredFundsModel.setHeading("Initial Investment Options");
        featuredFundsModel.setLinkTarget("same tab");
        featuredFundsModel.setLinkText("View all mutual funds");
        featuredFundsModel.setLinkUrl("/content/vcm/en");
        List<MultiFundContent> multiContent = new ArrayList<>();
        MultiFundContent multiFundContent = new MultiFundContent();
        multiFundContent.setFundName("Stock Market Fund");
        multiFundContent.setFundUrl("/content/fund/multi.html");
        multiFundContent.setTicker("JHGJKG");
        multiFundContent.setResourceResolver(resourceResolver);
        multiContent.add(multiFundContent);
        featuredFundsModel.setMultiContent(multiContent);
    }

    @Test
    void testFunds() throws Exception {
        Assert.assertEquals("USAA Mutual Funds are managed by Victory Capital. Get started today with a low initial investment.",featuredFundsModel.getDescription());
        Assert.assertEquals("Initial Investment Options",featuredFundsModel.getHeading());
        Assert.assertEquals("same tab",featuredFundsModel.getLinkTarget());
        Assert.assertEquals("View all mutual funds",featuredFundsModel.getLinkText());
        Assert.assertEquals("/content/vcm/en",featuredFundsModel.getLinkUrl());
        Assert.assertNotNull(featuredFundsModel.getResourceResolver());
        Assert.assertNotNull(featuredFundsModel.getMultiContent());
    }
}
