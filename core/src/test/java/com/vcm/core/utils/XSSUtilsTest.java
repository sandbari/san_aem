package com.vcm.core.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class XSSUtilsTest {
	
    private ResourceResolver resourceResolver;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @BeforeEach
    public void setup() throws Exception {
        resourceResolver=context.resourceResolver();
    }

    @Test
    void testEncodeQueryParameter() throws Exception {
    	assertNotNull(XSSUtils.encodeQueryParameter("param"));
    	//Mockito.when(XSSUtils.encodeQueryParameter(Mockito.any())).thenThrow(new IllegalStateException("foo"));
    }
}
