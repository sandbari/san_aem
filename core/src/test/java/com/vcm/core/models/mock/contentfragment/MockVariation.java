package com.vcm.core.models.mock.contentfragment;

public class MockVariation {

	String name;
	String title;
	public String contentType;
	public String[] values;
	public boolean isMultiLine;
	public String htmlValue;

	public MockVariation(String name, String title, String contentType, String[] values, boolean isMultiLine,
			String htmlValue) {
		this.name = name;
		this.title = title;
		this.contentType = contentType;
		this.values = values;
		this.isMultiLine = isMultiLine;
		this.htmlValue = htmlValue;
	}

	public MockVariation(String name, String title, String contentType, String value, boolean isMultiLine,
			String htmlValue) {
		this(name, title, contentType, new String[] { value }, isMultiLine, htmlValue);
	}
}
