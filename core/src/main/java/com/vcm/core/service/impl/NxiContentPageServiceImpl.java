package com.vcm.core.service.impl;

import com.day.cq.contentsync.handler.util.RequestResponseFactory;
import com.day.cq.wcm.api.WCMMode;
import com.google.gson.Gson;
import com.vcm.core.pojo.NxiContentPageBean;
import com.vcm.core.service.NxiContentPageService;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.engine.SlingRequestProcessor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component(service = NxiContentPageService.class, immediate = true)
public class NxiContentPageServiceImpl implements NxiContentPageService {
    private static final Logger LOG = LoggerFactory.getLogger(NxiContentPageServiceImpl.class);
    @Reference
    private RequestResponseFactory requestResponseFactory;

    /**
     * Service to process requests through Sling
     */
    @Reference
    private SlingRequestProcessor requestProcessor;

    @Override
    public String getNxiPageContent(SlingHttpServletRequest request) throws RepositoryException, ServletException, IOException {
        LOG.info("getArticlePageContent method");
        String jsonString = "";
        String requestPath = request.getResource().getPath() + ".html";
        /* Setup request */
        HttpServletRequest req = requestResponseFactory.createRequest("GET", requestPath);
        WCMMode.DISABLED.toRequest(req);

        /* Setup response */
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpServletResponse resp = requestResponseFactory.createResponse(out);

        /* Process request through Sling */
        requestProcessor.processRequest(req, resp, request.getResourceResolver());
        String htmlContent = out.toString();

        /** Replacing js and css files extension in html */
        //String content = htmlContent.replace(".js", ".nojs");
        // content = content.replace(".css", ".nocss");
        String content = htmlContent;

        NxiContentPageBean nxiContentPageBean = new NxiContentPageBean();
        if (StringUtils.isNotBlank(content)) {
            content = content.replaceAll("(\r?\n+\t?\\s*)", "\n");
            content = content.replaceAll("\n", "");
            Document doc = Jsoup.parse(content);
            Element link = doc.select("div.responsivegrid").attr("role", "main").get(1);
            // Getting the Between div tag role="main" content <div role="main" class="*?"> *?(All the content)</div>
            String linkOuterH = link.outerHtml();
            LOG.info("linkOuterH :\n" + linkOuterH);
            if (!linkOuterH.trim().isEmpty()) {
                nxiContentPageBean.setNxiPageContent(linkOuterH.trim());
            } else {
                nxiContentPageBean.setNxiPageContent("");
            }
        }
        Gson gson = new Gson();
        jsonString = gson.toJson(nxiContentPageBean);
        LOG.info("jsonString :" + jsonString);
        return jsonString.replaceAll("\\\\r\\\\n", "");
    }
}
