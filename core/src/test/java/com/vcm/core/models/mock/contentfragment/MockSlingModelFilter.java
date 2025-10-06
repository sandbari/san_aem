package com.vcm.core.models.mock.contentfragment;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.sling.api.resource.Resource;

import com.adobe.cq.export.json.SlingModelFilter;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.msm.api.MSMNameConstants;

public class MockSlingModelFilter implements SlingModelFilter {
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
				.filter(r -> !IGNORED_NODE_NAMES.contains(r.getName())).collect(Collectors.toList());
	}
}
