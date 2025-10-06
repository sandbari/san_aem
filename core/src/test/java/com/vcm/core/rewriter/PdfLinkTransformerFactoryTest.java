package com.vcm.core.rewriter;

import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
class PdfLinkTransformerFactoryTest {

	
	@Mock
	ValueMap valueMap;

	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	
	private PdfLinkTransformerFactory pdfLinkTransformerFactory;

	@Test
	public void testGet() throws Exception {
		pdfLinkTransformerFactory = new PdfLinkTransformerFactory();
		
		Assert.assertNotNull(pdfLinkTransformerFactory.createTransformer());
	}

}