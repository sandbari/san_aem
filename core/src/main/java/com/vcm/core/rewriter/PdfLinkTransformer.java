package com.vcm.core.rewriter;

import java.io.IOException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.rewriter.ProcessingComponentConfiguration;
import org.apache.sling.rewriter.ProcessingContext;
import org.apache.sling.rewriter.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.vcm.core.constants.Constant;
import com.vcm.core.utils.UtilityService;

public class PdfLinkTransformer implements Transformer {

	private static final Logger log = LoggerFactory.getLogger(PdfLinkTransformer.class);

	private ContentHandler contentHandler;
	private SlingHttpServletRequest request;

	public void characters(char[] ch, int start, int length) throws SAXException {
		contentHandler.characters(ch, start, length);
	}

	public void dispose() {
	}

	public void endDocument() throws SAXException {
		contentHandler.endDocument();
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		contentHandler.endElement(uri, localName, qName);
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		contentHandler.endPrefixMapping(prefix);
	}

	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		contentHandler.ignorableWhitespace(ch, start, length);
	}

	public void init(ProcessingContext context, ProcessingComponentConfiguration config) throws IOException {
		request = context.getRequest();
	}

	public void processingInstruction(String target, String data) throws SAXException {
		contentHandler.processingInstruction(target, data);
	}

	public void setContentHandler(ContentHandler handler) {
		this.contentHandler = handler;
	}

	public void setDocumentLocator(Locator locator) {
		contentHandler.setDocumentLocator(locator);
	}

	public void skippedEntity(String name) throws SAXException {
		contentHandler.skippedEntity(name);
	}

	public void startDocument() throws SAXException {
		contentHandler.startDocument();
	}

	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		final AttributesImpl attributes = new AttributesImpl(atts);
		final String href = attributes.getValue("href");

		if (href != null && href.startsWith(Constant.VCM_DAM_ROOT_PATH) && href.endsWith(Constant.PDF_EXTENSION)) {
			log.debug("DAM Link Path before change :: " + href);
			for (int i = 0; i < attributes.getLength(); i++) {
				if ("href".equalsIgnoreCase(attributes.getQName(i))) {

					attributes.setValue(i,
							UtilityService.getPDFLinkUrl(href));
					break;

				}
			}
		}
		contentHandler.startElement(uri, localName, qName, attributes);
	}

	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		contentHandler.startPrefixMapping(prefix, uri);
	}

}