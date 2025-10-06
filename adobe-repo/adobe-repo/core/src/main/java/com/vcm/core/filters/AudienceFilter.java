/*package com.vcm.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.engine.EngineConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vcm.core.constants.Constant;
import com.vcm.core.service.AudienceSelectorService;

@Component(service = Filter.class, property = {
		Constants.SERVICE_DESCRIPTION + "= Audience filter to set header value in incoming requests",
		EngineConstants.SLING_FILTER_SCOPE + "=" + EngineConstants.FILTER_SCOPE_REQUEST,
		Constants.SERVICE_RANKING + ":Integer=0", EngineConstants.SLING_FILTER_EXTENSIONS + "=html",
		EngineConstants.SLING_FILTER_PATTERN + "=" + "/content/vcm/.*"

})
public class AudienceFilter implements Filter {

	private static final String VCM_AUDIENCE_TYPE = "VCM_AUDIENCE_TYPE";

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Reference
	private AudienceSelectorService audienceSelectorService;

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
			throws IOException, ServletException {
		logger.debug("Filter initiated");
		final SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) request;
		String resourcePath = slingRequest.getResource().getPath();
		if (resourcePath.startsWith(Constant.VCM_CONTENT_PATH)) {
			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Sling Request path: {}", resourcePath);
			if (resourcePath.contains(audienceSelectorService.getMemberAudienceTypePathPattern())) {
				logger.debug("member filter activated");
				slingRequest.setAttribute(VCM_AUDIENCE_TYPE, audienceSelectorService.getMemberAudienceTypeKey());
			} else if (resourcePath.contains(audienceSelectorService.getFinancialAudienceTypePathPattern())) {
				logger.debug("financial-professional filter activated");
				slingRequest.setAttribute(VCM_AUDIENCE_TYPE, audienceSelectorService.getFinancialAudienceTypeKey());
			} else if (resourcePath.contains(audienceSelectorService.getInvestorAudienceTypePathPattern())) {
				logger.debug("institutionalinvestor filter activated");
				slingRequest.setAttribute(VCM_AUDIENCE_TYPE, audienceSelectorService.getInvestorAudienceTypeKey());
			}

		}
		filterChain.doFilter(slingRequest, response);
	}

	@Override
	public void init(FilterConfig filterConfig) {
		// do nothing for now
	}

	@Override
	public void destroy() {
		// do nothing for now
	}

}
*/