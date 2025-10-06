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
class PdfLinkTransformerTest {

	
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
	
	private PdfLinkTransformer pdfLinkTransformer;

	@Test
	public void testMethods() throws Exception {
		pdfLinkTransformer = new PdfLinkTransformer();
		
		pdfLinkTransformer.setContentHandler(contentHandler);
		char[] ch = {'a', 'b', 'c'};
		pdfLinkTransformer.characters(ch, 0, 2);
		pdfLinkTransformer.dispose();
		pdfLinkTransformer.endDocument();
		pdfLinkTransformer.endElement("/content/vcm/en", "local", "qName");
		pdfLinkTransformer.endPrefixMapping("prefix");
		pdfLinkTransformer.ignorableWhitespace(ch, 0, 2);
		pdfLinkTransformer.processingInstruction("Target", "Data");
		pdfLinkTransformer.setDocumentLocator(locator);
		pdfLinkTransformer.skippedEntity("Name");
		pdfLinkTransformer.startDocument();
		pdfLinkTransformer.startPrefixMapping("prefix", "/content/vcm/en");
		pdfLinkTransformer.init(processingContext, processingComponentConfig);
		pdfLinkTransformer.startElement("content/vcm/en", "local", "qName", atts);
		
	}

}