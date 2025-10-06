package com.vcm.core.rewriter;

import org.apache.sling.rewriter.Transformer;
import org.apache.sling.rewriter.TransformerFactory;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 * A TransformerFactory service instance for creating a Transformer to shorten 
 * pdf links.
 * 
 */
@Component(property = { "pipeline.type=pdflinkchecker" }, service = { TransformerFactory.class })
public class PdfLinkTransformerFactory implements TransformerFactory {
 
    private static final Logger log = LoggerFactory.getLogger(PdfLinkTransformerFactory.class);
 
    @Override
    public Transformer createTransformer() {
        log.trace("createTransformer");
        return new PdfLinkTransformer();
    }
 
}
