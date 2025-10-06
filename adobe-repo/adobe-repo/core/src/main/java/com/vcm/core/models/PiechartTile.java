package com.vcm.core.models;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vcm.core.pojo.DataBean;
import com.vcm.core.pojo.PieChartBean;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PiechartTile {

	@Inject
	List<DataBean> dataContent;

	@Inject
	String title;

	@Inject
	String age;

	@Inject
	String description;

	String json;

	@PostConstruct
	protected void init() {
		Map<Integer, PieChartBean> map = new HashMap<Integer, PieChartBean>();
		String beanAge = "";
		String beanDescription = "";
		String beanTitle = "";
		PieChartBean bean = new PieChartBean();
		if (null != age) {
			beanAge = age;
		}
		if (null != description) {
			beanDescription = description;
		}
		if (null != title) {
			beanTitle = title;
		}
		bean.setAge(beanAge);
		bean.setDescription(beanDescription);
		bean.setTitle(beanTitle);
		bean.setData(dataContent);

		map.put(0, bean);

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		json = gson.toJson(map);
	}	

	public List<DataBean> getDataContent() {
		List<DataBean> copyDataContent = dataContent;
		Collections.copy(copyDataContent, dataContent);
		return copyDataContent;
	}
	

	public void setDataContent(List<DataBean> dataContent) {
		this.dataContent = dataContent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJson() {
		return json;
	}
}
