package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.wcm.testing.mock.aem.junit5.AemContext;

public class LabelsConfigModelTest {

	private LabelsConfigModel model;

	private ProductDataValues keyValueMap;

	private ProductLabelValues labelKeyValueMap;
	
	List<ProductDataValues> data = new ArrayList<ProductDataValues>();
	
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    
	@BeforeEach
	public void setUp() throws Exception {
		List<ProductLabelValues> labelInfo = new ArrayList<ProductLabelValues>();
		model = new LabelsConfigModel();
		keyValueMap = new ProductDataValues();
		labelKeyValueMap = new ProductLabelValues();
		
		keyValueMap.setKey("");
		keyValueMap.setValue("");		
		data.add(keyValueMap);
		
		labelKeyValueMap.setLabelValues(data);
		labelKeyValueMap.setKey("Age 0-2");
		labelInfo.add(labelKeyValueMap);
		
		model.setTitle("Product Labels");
		model.setKeyValueMap(data);
		model.setLabelKeyValueMap(labelInfo);
	}	
	
	@Test
	public void testGetterSetter() {
		assertNotNull(model.getTitle());
		assertNotNull(model.getKeyValueMap());
		assertNotNull(model.getLabelKeyValueMap());
	}
	
	
	@Test
	public void testProductDataValues() {
		assertNotNull(keyValueMap.getKey());
		assertNotNull(keyValueMap.getValue());
	}
	
	@Test
	public void testProductLabelValues() {
		assertNotNull(labelKeyValueMap.getKey());
		assertNotNull(labelKeyValueMap.getLabelValues());
	}
	
}
