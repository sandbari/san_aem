package com.vcm.core.rewriter;

import org.apache.sling.rewriter.ProcessingComponentConfiguration;
import org.apache.sling.rewriter.ProcessingContext;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.helpers.AttributesImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
class VCMLinkTransformerTest {

	
	@Mock
	private ContentHandler contentHandler;
	
	@Mock
	Locator locator;
	
	@Mock
	ProcessingContext processingContext;
	
	@Mock
	ProcessingComponentConfiguration processingComponentConfig;
	
	@Mock
	Attributes atts;
	
	@Mock
	AttributesImpl attributes;
	
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	
	private VCMLinkTranformer vcmLinkTransformer;

	@Test
	public void testMethods() throws Exception {
		vcmLinkTransformer = new VCMLinkTranformer();
		
		vcmLinkTransformer.setContentHandler(contentHandler);
		char[] ch = {'a', 'b', 'c'};
		vcmLinkTransformer.characters(ch, 0, 2);
		vcmLinkTransformer.dispose();
		vcmLinkTransformer.endDocument();
		vcmLinkTransformer.endElement("/content/vcm/en", "local", "qName");
		vcmLinkTransformer.endPrefixMapping("prefix");
		vcmLinkTransformer.ignorableWhitespace(ch, 0, 2);
		vcmLinkTransformer.processingInstruction("Target", "Data");
		vcmLinkTransformer.setDocumentLocator(locator);
		vcmLinkTransformer.skippedEntity("Name");
		vcmLinkTransformer.startDocument();
		vcmLinkTransformer.startPrefixMapping("prefix", "/content/vcm/en");
		//vcmLinkTransformer.init(processingContext, processingComponentConfig);
		vcmLinkTransformer.startElement("content/vcm/en", "local", "qName", atts);
		
	}

}