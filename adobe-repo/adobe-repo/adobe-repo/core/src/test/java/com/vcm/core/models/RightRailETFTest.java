package com.vcm.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(AemContextExtension.class)
public class RightRailETFTest {

    private RightRailETF rightRailETF;
    private ResourceResolver resourceResolver;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @BeforeEach
    public void setup() throws Exception {
        rightRailETF = new RightRailETF();
        resourceResolver=context.resourceResolver();
        rightRailETF.setResourceResolver(resourceResolver);
        rightRailETF.setFunddetailsLinkText("risk measures");
        rightRailETF.setFunddetailsOverlayContentPath("/content/dam/vcm/vcm-content-fragments/tool-tip/product-detail-tool-tip");
        rightRailETF.setFunddetailsTextPrefix("What are");
        rightRailETF.setFunddetailsoverlayContent("Test Content");
        rightRailETF.setFunddetailsoverlayId("Test ID");
        rightRailETF.setFundyieldLinkText("risk measures");
        rightRailETF.setFundyieldOverlayContentPath("/content/dam/vcm/vcm-content-fragments/tool-tip/product-detail-tool-tip");
        rightRailETF.setFundyieldTextPrefix("What are");
        rightRailETF.setFundyieldoverlayContent("Test Content");
        rightRailETF.setFundyieldoverlayId("Test ID");
        rightRailETF.setSummaryLinkText("risk measures");
        rightRailETF.setSummaryOverlayContentPath("/content/dam/vcm/vcm-content-fragments/tool-tip/product-detail-tool-tip");
        rightRailETF.setSummaryTextPrefix("What are");
        rightRailETF.setSummaryoverlayContent("Test Content");
        rightRailETF.setSummaryoverlayId("Test ID");
    }

    @Test
    public void testGetterSetters() throws Exception {

        assertNotNull(rightRailETF.getFunddetailsLinkText());
        assertNotNull(rightRailETF.getFunddetailsoverlayContent());
        assertNotNull(rightRailETF.getFunddetailsoverlayId());
        assertNotNull(rightRailETF.getFunddetailsOverlayContentPath());
        assertNotNull(rightRailETF.getFunddetailsTextPrefix());
        assertNotNull(rightRailETF.getFundyieldLinkText());
        assertNotNull(rightRailETF.getFundyieldoverlayContent());
        assertNotNull(rightRailETF.getFundyieldOverlayContentPath());
        assertNotNull(rightRailETF.getFundyieldoverlayId());
        assertNotNull(rightRailETF.getFundyieldTextPrefix());
        assertNotNull(rightRailETF.getResourceResolver());
        assertNotNull(rightRailETF.getSummaryLinkText());
        assertNotNull(rightRailETF.getSummaryoverlayContent());
        assertNotNull(rightRailETF.getSummaryOverlayContentPath());
        assertNotNull(rightRailETF.getSummaryoverlayId());
        assertNotNull(rightRailETF.getSummaryTextPrefix());
        
        rightRailETF.init();

    }
}
