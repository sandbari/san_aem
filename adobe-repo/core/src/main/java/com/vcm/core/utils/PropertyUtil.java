package com.vcm.core.utils;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

public class PropertyUtil {
	
	/**
	 * Get nodes by search query
	 *
	 * @param resovler The resource resolver.
	 * @param type  The type of node to return.
	 * @param path  The path of nodes to search.
	 * @param property  The property name to search.
	 * @param propertyValue  The property value to search.
	 * @return The node iterator.
	 * @throws LoginException
	 * @throws RepositoryException 
	 */
	public static NodeIterator getNodesbySearchQuery(ResourceResolver resolver, String type, String path,
			String property, String propertyValue) throws RepositoryException {
		NodeIterator nodes = null;

		String searchQuery = "SELECT * FROM [" + type + "] AS p WHERE ISDESCENDANTNODE ([" + path + "]) AND p.["
				+ property + "]='" + propertyValue + "'";

		Session session = null;

		// Invoke the adaptTo method to create a Session used to create a QueryManager
		session = resolver.adaptTo(Session.class);
		if (session != null) {

			QueryManager queryManager = session.getWorkspace().getQueryManager();
			Query query = queryManager.createQuery(searchQuery, Query.JCR_SQL2);

			javax.jcr.query.QueryResult result = query.execute();
			nodes = result.getNodes();

		}
		return nodes;
	}

	public static List<Tag> getTagListFromProperty(Value[] values, TagManager tagManager)
			throws RepositoryException {
		List<Tag> tagList = new ArrayList<>(values.length);
		for (Value tagPath : values) {
			tagList.add(tagManager.resolve(tagPath.getString()));
		}
		return tagList;
	}

}
