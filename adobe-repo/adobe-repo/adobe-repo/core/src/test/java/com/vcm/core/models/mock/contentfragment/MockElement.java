package com.vcm.core.models.mock.contentfragment;

import java.util.LinkedHashMap;
import java.util.Map;

public class MockElement {

	public String name;
	public String title;
	boolean isMultiValued;
	public String contentType;
	public String[] values;
	public boolean isMultiLine;
	public String htmlValue;
	public String typeString;
	public Map<String, MockVariation> variations = new LinkedHashMap<>();

	public MockElement() {
	}

	public MockElement(String name, String title, String contentType, String value, boolean isMultiLine,
			String htmlValue) {
		this(name, title, contentType, new String[] { value }, isMultiLine, htmlValue);
		this.isMultiValued = false;
	}

	public MockElement(String name, String title, String contentType, String[] values, boolean isMultiLine,
			String htmlValue) {
		this.name = name;
		this.title = title;
		this.contentType = contentType;
		this.isMultiValued = true;
		this.values = values;
		this.isMultiLine = isMultiLine;
		this.htmlValue = htmlValue;
	}

	public void addVariation(String name, String title, String contentType, String[] values, boolean isMultiline,
			String htmlValue) {
		variations.put(name, new MockVariation(name, title, contentType, values, isMultiline, htmlValue));
	}

	public void addVariation(String name, String title, String contentType, String value, boolean isMultiline,
			String htmlValue) {
		variations.put(name, new MockVariation(name, title, contentType, value, isMultiline, htmlValue));
	}
}
