package com.vcm.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vcm.core.service.ConstantsConfigService;

import junit.framework.Assert;

@ExtendWith(MockitoExtension.class)
class USSA529PerformanceFundModelTest {

	private USSA529PerformanceFundModel ussa529PerformanceFundModel;
	
	@Mock
	private ConstantsConfigService constantsConfigService;

	@BeforeEach
	public void setup() throws Exception {
		ussa529PerformanceFundModel = new USSA529PerformanceFundModel();
		ussa529PerformanceFundModel.setConstantsConfigService(constantsConfigService);
		Mockito.when(constantsConfigService.getUSSA529PerformanceFundEndPoint()).thenReturn("/529EndPoint");
		Mockito.when(constantsConfigService.getUSSA529PerformanceFundKey()).thenReturn("529EndPointKey");
	}

	@Test
	void testFundModelList() throws Exception {
		ussa529PerformanceFundModel.init();
		Assert.assertEquals("/529EndPoint", ussa529PerformanceFundModel.getUssa529PerformanceFundEndPoint());
		Assert.assertEquals("529EndPointKey", ussa529PerformanceFundModel.getUssa529PerformanceFundKey());
	}

}