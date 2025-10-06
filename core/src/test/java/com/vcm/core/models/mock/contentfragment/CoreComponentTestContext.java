package com.vcm.core.models.mock.contentfragment;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.impl.ResourceTypeBasedResourcePicker;
import org.apache.sling.models.spi.ImplementationPicker;
import org.apache.sling.testing.mock.sling.ResourceResolverType;

import com.adobe.cq.export.json.SlingModelFilter;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.msm.api.MSMNameConstants;

import io.wcm.testing.mock.aem.junit5.AemContextBuilder;

/**
 * Provides a context for unit tests.
 */
public final class CoreComponentTestContext {

	private CoreComponentTestContext() {
		// only static methods
	}

	public static io.wcm.testing.mock.aem.junit5.AemContext aemContext() {
		return new AemContextBuilder().resourceResolverType(
				ResourceResolverType.JCR_MOCK).<io.wcm.testing.mock.aem.junit5.AemContext>afterSetUp(context -> {
					context.addModelsForClasses(MockResponsiveGrid.class);
					context.addModelsForPackage("com.adobe.cq.wcm.core.components.models");
					context.registerService(SlingModelFilter.class, new MockSlingModelFilter() {
						private final Set<String> IGNORED_NODE_NAMES = new HashSet<String>() {
							{
								add(NameConstants.NN_RESPONSIVE_CONFIG);
								add(MSMNameConstants.NT_LIVE_SYNC_CONFIG);
								add("cq:annotations");
							}
						};

						@Override
						public Map<String, Object> filterProperties(Map<String, Object> map) {
							return map;
						}

						@Override
						public Iterable<Resource> filterChildResources(Iterable<Resource> childResources) {
							return StreamSupport.stream(childResources.spliterator(), false)
									.filter(r -> !IGNORED_NODE_NAMES.contains(r.getName()))
									.collect(Collectors.toList());
						}
					});
					context.registerService(ImplementationPicker.class, new ResourceTypeBasedResourcePicker());
				}).build();
	}
}
