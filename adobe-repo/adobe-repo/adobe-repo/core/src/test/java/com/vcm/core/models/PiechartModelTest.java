package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import com.vcm.core.pojo.DataBean;
import com.vcm.core.pojo.PieChartBean;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import org.junit.jupiter.api.Test;


@ExtendWith(AemContextExtension.class)
public class PiechartModelTest {
	
	private PieChartModel model;

	private PieChartBean chartBean;

	private DataBean dataBean;
	
	List<DataBean> data = new ArrayList<DataBean>();
	
	private ResourceResolver resourceResolver;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    
	@BeforeEach
	public void setUp() throws Exception {
		List<PieChartBean> chartInfo = new ArrayList<PieChartBean>();
		model = new PieChartModel();
		chartBean = new PieChartBean();
		dataBean = new DataBean();
		
		dataBean.setName("stock");
		dataBean.setValue("100");		
		data.add(dataBean);
		
		chartBean.setData(data);
		chartBean.setAge("Age 0-2");
		chartBean.setDescription("Description");
		chartBean.setTitle("Conservative");
		chartBean.setViewData("View Performance Plan");
		chartInfo.add(chartBean);
		
		resourceResolver=context.resourceResolver();
		model.setResourceResolver(resourceResolver);
		model.setPortfolioOptionText("Portfolio Options");
		model.setAggressiveText("Aggressive");
		model.setConservativeText("Conservative");
		model.setLinkUrl("/content/vcm/language-masters/en/member/products");
		model.setViewAllText("View All");
		model.setDescription("Decription");
		model.setChartInfo(chartInfo);
		model.setIncludeSlider("true");
	}	
	
	@Test
	public void testGetterSetter() {
		assertNotNull(model.getResourceResolver());
		assertNotNull(model.getPortfolioOptionText());
		assertNotNull(model.getAggressiveText());
		assertNotNull(model.getConservativeText());
		assertNotNull(model.getLinkUrl());
		assertNotNull(model.getViewAllText());
		assertNotNull(model.getDescription());
		assertNotNull(model.getChartInfo());
		assertNotNull(model.getIncludeSlider());
	}
	
	@Test
	public void testInit() {
		model.init();
		assertNotNull(model.getJson());
	}
	
	@Test
	public void testPieChartBean() {
		assertNotNull(chartBean.getAge());
		assertNotNull(chartBean.getDescription());
		assertNotNull(chartBean.getTitle());
		assertNotNull(chartBean.getViewData());
		assertNotNull(chartBean.getData());
	}
	
	@Test
	public void testDataBean() {
		dataBean.init();
		assertNotNull(dataBean.getName());
		assertNotNull(dataBean.getY());
	}
	
	@Test
	public void testFloatDataBean() {
		dataBean = new DataBean();
		
		dataBean.setName("stock");
		dataBean.setValue("16.5");		
		dataBean.init();
		assertNotNull(dataBean.getName());
		assertNotNull(dataBean.getY());
	}
	
}
