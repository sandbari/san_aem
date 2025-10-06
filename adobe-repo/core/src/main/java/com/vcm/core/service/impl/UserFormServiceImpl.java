package com.vcm.core.service.impl;

import com.day.cq.dam.api.Asset;
import com.google.gson.Gson;
import com.vcm.core.pojo.*;
import com.vcm.core.service.UserFormService;
import com.vcm.core.utils.XSSRequestWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.day.cq.dam.commons.util.DamUtil.getAssets;

@Component(service = UserFormService.class, immediate = true)
public class UserFormServiceImpl implements UserFormService {

    private static final Logger LOG = LoggerFactory.getLogger(UserFormServiceImpl.class);

    private String webApi=null;
    @Override
    public String userFormsJson(SlingHttpServletRequest request) throws RepositoryException {
        XSSRequestWrapper filteredRequest = new XSSRequestWrapper((HttpServletRequest) request);

        String jsonString = StringUtils.EMPTY;
        PdfFormDetailsBean pdfFormDetailsBean = new PdfFormDetailsBean();
        ResourceResolver resolver = request.getResourceResolver();
        String[] tabTypesArr = {};
        List<String> categories = new ArrayList<>();
        List<String> funds = new ArrayList<>();
        VictoryFundBean victoryFundBean = new VictoryFundBean();
        EducationSavingsBean educationSavingsBean = new EducationSavingsBean();
        RoboBean roboBean = new RoboBean();
        BrokerageBean brokerageBean = new BrokerageBean();
        MSRFundBean msrFundBean= new MSRFundBean();
        List<AccountApplicationBean> accountApplicationBeansVCTFund = new ArrayList<>();
        List<ServiceBean> serviceBeansVCTFUND = new ArrayList<>();
        List<AccountApplicationBean> accountApplicationBeansEDUFund = new ArrayList<>();
        List<ServiceBean> serviceBeansEDUFund = new ArrayList<>();
        List<AccountApplicationBean> accountApplicationBeansROBOFund = new ArrayList<>();
        List<ServiceBean> serviceBeansROBOFund = new ArrayList<>();
        List<AccountApplicationBean> accountApplicationBeansBRKFund = new ArrayList<>();
        List<ServiceBean> serviceBeansBRKFund = new ArrayList<>();
        List<AccountApplicationBean> accountApplicationBeansMSRFund = new ArrayList<>();
        List<ServiceBean> serviceBeansMSRFund = new ArrayList<>();
        //String tagResource = filteredRequest.getParameter("formsTag");
        pdfFormDetailsBean.setBrokerage(brokerageBean);
        pdfFormDetailsBean.setRobo(roboBean);
        pdfFormDetailsBean.setEducationSavings(educationSavingsBean);
        pdfFormDetailsBean.setVictoryFund(victoryFundBean);
        pdfFormDetailsBean.setMsrFundBean(msrFundBean);
        String tagResource = "vcm:forms/victory-fund/account-application,vcm:forms/victory-fund/service," +
                "vcm:forms/education-saving/service," +
                "vcm:forms/education/service," +
                "vcm:forms/education/account-application," +
                "vcm:forms/education-saving/account-application," +
                "vcm:forms/robo/account-application,vcm:forms/robo/service," +
                "vcm:forms/dynamic-Advisor/account-application,vcm:forms/dynamic-Advisor/service," +
                "vcm:forms/dynamic-advisor/account-application,vcm:forms/dynamic-advisor/service," +
                "vcm:forms/dynamicAdvisor/account-application,vcm:forms/dynamicAdvisor/service," +
                "vcm:forms/dynamicadvisor/account-application,vcm:forms/dynamicadvisor/service," +
                "vcm:forms/brokerage/account-application,vcm:forms/brokerage/service,"+
                "vcm:forms/marketplace/account-application,vcm:forms/marketplace/service,"+
                "vcm:forms/msr-forms/service,vcm:forms/msr-forms/account-application";
        LOG.info("tagResource : " + tagResource + " :: "+ filteredRequest.getParameter("formsTag"));
        if (tagResource != null && !tagResource.equals(StringUtils.EMPTY)) {
            tabTypesArr = tagResource.split(",");
            for (String tabTypeItem : tabTypesArr) {
                String[] words = tabTypeItem.split("/");
                String category = words[words.length - 1];
                String fund = words[words.length - 2];
                categories.add(category);
                funds.add(fund);
                //categorizedResults.put(category, new ArrayList<>());
            }
        }
//		Resource resource = resolver.getResource(filteredRequest.getParameter("formsRootPath"));
        Resource resource = resolver.getResource("/content/dam/vcm/forms");
        LOG.info("resource "+ resource + resolver.getResource("/content/dam/vcm/forms"));
        if (Objects.nonNull(resource)) {
            Iterator<Asset> assetIterator = getAssets(resource);
            while (assetIterator.hasNext()) {
                Asset pdfAsset = assetIterator.next();
                String subCategory = "";
                if (Objects.nonNull(pdfAsset.getMetadataValue("cq:tags"))) {
                    String[] pdfTags;
                    pdfTags = pdfAsset.getMetadataValue("cq:tags").split(",");
                    for (String tag : pdfTags) {
                        String[] words = tag.split("/");
                        subCategory = words[words.length - 1];
                        String category = words[words.length - 2];
                        String fund = null;
                        if(words.length > 2) {
                            fund = words[words.length - 3];
                        }
                        if (fund != null && funds.contains(fund)) {
                            if (fund.equalsIgnoreCase("victory-fund")) {
                                victoryFundBean = getVictoryFundDetails(categories, category,
                                        pdfAsset, fund, subCategory,
                                        victoryFundBean, serviceBeansVCTFUND,
                                        accountApplicationBeansVCTFund);
                                pdfFormDetailsBean.setVictoryFund(victoryFundBean);
                            } else if (fund.equalsIgnoreCase("529-education-saving") ||
                                    fund.equalsIgnoreCase("education-savings") ||
                                    fund.equalsIgnoreCase("education") ||
                                    fund.equalsIgnoreCase("education-saving")) {
                                pdfFormDetailsBean.setEducationSavings(getEducationSavingFundDetails(categories
                                        , category, pdfAsset,
                                        fund, subCategory,
                                        educationSavingsBean, serviceBeansEDUFund,
                                        accountApplicationBeansEDUFund));
                            } else if (fund.equalsIgnoreCase("robo")  ||
                                    fund.equals("dynamic-advisor") ||
                                    fund.equals("dynamic-Advisor") ||
                                    fund.equals("dynamicAdvisor")) {
                                LOG.info("robo inside inside::");
                                pdfFormDetailsBean.setRobo(getRoboFundDetails(categories, category,
                                        pdfAsset, fund,
                                        subCategory, roboBean,
                                        serviceBeansROBOFund, accountApplicationBeansROBOFund));
                            } else if (fund.equalsIgnoreCase("brokerage") ||
                                    fund.equals("marketplace")) {
                                pdfFormDetailsBean.setBrokerage(getBrokerageDetails(categories, category,
                                        pdfAsset, fund,
                                        subCategory, brokerageBean,
                                        serviceBeansBRKFund, accountApplicationBeansBRKFund));
                                LOG.info("marketplace end");
                            }

                            if(webApi == "WEB"){
                                if (fund.equalsIgnoreCase("msr-forms")) {
                                    pdfFormDetailsBean.setMsrFundBean(getMSRDetails(categories, category,
                                            pdfAsset, fund,
                                            subCategory, msrFundBean,
                                            serviceBeansMSRFund, accountApplicationBeansMSRFund));
                                    LOG.info("MSR end");
                                }
                            }
/*
                            if (categories.contains(category)) {
                                PdfFormBean pdfFormBean = new PdfFormBean();
                                if (Objects.nonNull(pdfAsset.getMetadataValue("pdf:FormID"))) {
                                    formID = pdfAsset.getMetadataValue("pdf:FormID");
                                }
                                if (Objects.nonNull(pdfAsset.getMetadataValue("dc:title"))) {
                                    pdfTitle = pdfAsset.getMetadataValue("dc:title");
                                }
                                if (Objects.nonNull(pdfAsset.getMetadataValue("dc:description"))) {
                                    pdfDescription = pdfAsset.getMetadataValue("dc:description");
                                }
                                pdfFormBean.setFund(fund);
                                pdfFormBean.setCategory(category);
                                pdfFormBean.setSubCategory(subCategory);
                                pdfFormBean.setFormID(formID);
                                pdfFormBean.setPdfPath(pdfAsset.getPath());
                                pdfFormBean.setPdfTitle(pdfTitle);
                                pdfFormBean.setPdfDescription(pdfDescription);
                                categorizedResults.get(category).add(pdfFormBean);
                            }*/
                        }
                    }
                }
            }
        }
        Gson gson = new Gson();
        jsonString = gson.toJson(pdfFormDetailsBean);
        LOG.info("jsonString " + jsonString);
        return jsonString;
    }

    private MSRFundBean getMSRDetails(List<String> categories,
                                      String category,
                                      Asset pdfAsset,
                                      String fund,
                                      String subCategory,
                                      MSRFundBean msrFundBean,
                                      List<ServiceBean> serviceBeansMSRFund,
                                      List<AccountApplicationBean> accountApplicationBeansMSRFund) {
        LOG.info("MSR :"+ category);
        if (categories.contains(category)) {
            if (category.equalsIgnoreCase("service")) {
                List<ServiceBean> serviceBeanList = getCategoryServiceDetails(pdfAsset, fund, category, subCategory,
                        serviceBeansMSRFund);
                LOG.info("serviceBeanList fund "+ serviceBeanList.get(0).getFund());
                if (serviceBeanList.size() > 0) {
                    serviceBeanList = serviceBeanList
                            .stream()
                            .filter(obj -> "msr-forms".equals(obj.getFund()))
                            .collect(Collectors.toList());

                    LOG.info("serviceBeanList size : "+ serviceBeanList.size());
                    Set<ServiceBean> set = new LinkedHashSet<>();
                    set.addAll(serviceBeanList);
                    serviceBeanList.clear();
                    serviceBeanList.addAll(set);
                    LOG.info("msrFundBean fund "+ serviceBeanList.get(0).getFund());
                    msrFundBean.getService().addAll(serviceBeanList);
                    LOG.info("serviceBeanList.size() "+ msrFundBean.getService().size());
                }
            } else if (category.equalsIgnoreCase("account-application")) {
                List<AccountApplicationBean> accountApplicationBeanList = getCategoryAccountApplicationDetails(pdfAsset, fund,
                        category, subCategory, accountApplicationBeansMSRFund);
                if (accountApplicationBeanList.size() > 0) {
                    accountApplicationBeanList = (List<AccountApplicationBean>) accountApplicationBeanList
                            .stream()
                            .filter(accountApplicationBean -> "msr-forms".equals(accountApplicationBean.getFund()))
                            .distinct()
                            .collect(Collectors.toList());
                    Set<AccountApplicationBean> set = new LinkedHashSet<>();
                    set.addAll(accountApplicationBeanList);
                    accountApplicationBeanList.clear();
                    accountApplicationBeanList.addAll(set);
                    msrFundBean.getAccountApplication().clear();
                    msrFundBean.getAccountApplication().addAll(accountApplicationBeanList);
                }
            }
        }
        return msrFundBean;
    }


    @Override
    public String userFormsPrivateJson(SlingHttpServletRequest request) throws RepositoryException {

        webApi="WEB";
        String json = userFormsJson(request);
        webApi=null;
        return json;
    }

    private BrokerageBean getBrokerageDetails(List<String> categories, String category, Asset pdfAsset,
                                              String fund, String subCategory,
                                              BrokerageBean brokerageBean,
                                              List<ServiceBean> serviceBeans,
                                              List<AccountApplicationBean> accountApplicationBeans) {
        LOG.info("brokerage ");
        if (categories.contains(category)) {
            if (category.equalsIgnoreCase("service")) {
                List<ServiceBean> serviceBeanList = getCategoryServiceDetails(pdfAsset, fund, category, subCategory,
                        serviceBeans);
                if (serviceBeanList.size() > 0) {
                    serviceBeanList = serviceBeanList
                            .stream()
                            .filter(obj -> "brokerage".equals(obj.getFund()) ||
                                    "marketplace".equals(obj.getFund()))
                            .collect(Collectors.toList());
                    Set<ServiceBean> set = new LinkedHashSet<>();
                    set.addAll(serviceBeanList);
                    serviceBeanList.clear();
                    serviceBeanList.addAll(set);
                    brokerageBean.getService().clear();
                    brokerageBean.getService().addAll(serviceBeanList);
                }
            } else if (category.equalsIgnoreCase("account-application")) {
                List<AccountApplicationBean> accountApplicationBeanList = getCategoryAccountApplicationDetails(pdfAsset, fund,
                        category, subCategory, accountApplicationBeans);
                if (accountApplicationBeanList.size() > 0) {
                    accountApplicationBeanList = (List<AccountApplicationBean>) accountApplicationBeanList
                            .stream()
                            .filter(accountApplicationBean -> "brokerage".equals(accountApplicationBean.getFund()) ||
                                    "marketplace".equals(accountApplicationBean.getFund()))
                            .distinct()
                            .collect(Collectors.toList());
                    Set<AccountApplicationBean> set = new LinkedHashSet<>();
                    set.addAll(accountApplicationBeanList);
                    accountApplicationBeanList.clear();
                    accountApplicationBeanList.addAll(set);
                    brokerageBean.getAccountApplication().clear();
                    brokerageBean.getAccountApplication().addAll(accountApplicationBeanList);
                }
            }
        }
        return brokerageBean;
    }


    private RoboBean getRoboFundDetails(List<String> categories, String category, Asset pdfAsset, String fund,
                                        String subCategory, RoboBean roboBean,
                                        List<ServiceBean> serviceBeans, List<AccountApplicationBean> accountApplicationBeans) {
        if (categories.contains(category)) {
            if (category.equalsIgnoreCase("service")) {
                List<ServiceBean> serviceBeanList = getCategoryServiceDetails(pdfAsset, fund, category, subCategory,
                        serviceBeans);
                if (serviceBeanList.size() > 0) {
                    serviceBeanList = serviceBeanList
                            .stream()
                            .filter(obj -> "robo".equals(obj.getFund()) ||
                                    "dynamic-advisor".equals(obj.getFund()) ||
                                    "dynamic-Advisor".equals(obj.getFund()) ||
                                    "dynamicAdvisor".equals(obj.getFund()))
                            .collect(Collectors.toList());
                    Set<ServiceBean> set = new LinkedHashSet<>();
                    set.addAll(serviceBeanList);
                    serviceBeanList.clear();
                    serviceBeanList.addAll(set);
                    roboBean.getService().clear();
                    roboBean.getService().addAll(serviceBeanList);
                }
            } else if (category.equalsIgnoreCase("account-application")) {
                List<AccountApplicationBean> accountApplicationBeanList = getCategoryAccountApplicationDetails(pdfAsset, fund,
                        category, subCategory, accountApplicationBeans);
                if (accountApplicationBeanList.size() > 0) {
                    accountApplicationBeanList = (List<AccountApplicationBean>) accountApplicationBeanList
                            .stream()
                            .filter(accountApplicationBean -> "robo".equals(accountApplicationBean.getFund()) ||
                                    "dynamic-advisor".equals(accountApplicationBean.getFund()) ||
                                    "dynamic-Advisor".equals(accountApplicationBean.getFund()) ||
                                    "dynamicAdvisor".equals(accountApplicationBean.getFund()))
                            .distinct()
                            .collect(Collectors.toList());
                    Set<AccountApplicationBean> set = new LinkedHashSet<>();
                    set.addAll(accountApplicationBeanList);
                    accountApplicationBeanList.clear();
                    accountApplicationBeanList.addAll(set);
                    roboBean.getAccountApplication().clear();
                    roboBean.getAccountApplication().addAll(accountApplicationBeanList);
                }
            }
        }
        return roboBean;
    }

    private EducationSavingsBean getEducationSavingFundDetails(List<String> categories, String category, Asset pdfAsset, String fund,
                                                               String subCategory, EducationSavingsBean educationSavingsBean, List<ServiceBean> serviceBeans, List<AccountApplicationBean> accountApplicationBeans) {
        LOG.info("getEducationSavingFundDetails method " + categories + " : : " + category);
        if (categories.contains(category)) {
            if (category.equalsIgnoreCase("service")) {
                List<ServiceBean> serviceBeanList = getCategoryServiceDetails(pdfAsset, fund, category,
                        subCategory, serviceBeans);
                if (serviceBeanList.size() > 0) {
                    LOG.info("getEducationSavingFundDetails size " + serviceBeanList.size());
                    serviceBeanList = serviceBeanList
                            .stream()
                            .filter(obj -> "529-education-saving".equals(obj.getFund()) ||
                                    "education-saving".equals(obj.getFund()) ||
                                    "education".equals(obj.getFund()) ||
                                    "education-savings".equals(obj.getFund()))
                            .collect(Collectors.toList());

                    Set<ServiceBean> set = new LinkedHashSet<>();
                    set.addAll(serviceBeanList);
                    serviceBeanList.clear();
                    serviceBeanList.addAll(set);
                    educationSavingsBean.getService().clear();
                    educationSavingsBean.getService().addAll(serviceBeanList);
                }
            } else if (category.equalsIgnoreCase("account-application")) {
                List<AccountApplicationBean> accountApplicationBeanList = getCategoryAccountApplicationDetails(pdfAsset, fund,
                        category, subCategory, accountApplicationBeans);
                if (accountApplicationBeanList.size() > 0) {
                    accountApplicationBeanList = accountApplicationBeanList
                            .stream()
                            .filter(accountApplicationBean -> "529-education-saving".equals(accountApplicationBean.getFund()) ||
                                    "education-saving".equals(accountApplicationBean.getFund()) ||
                                    "education".equals(accountApplicationBean.getFund()) ||
                                    "education-savings".equals(accountApplicationBean.getFund()))
                            .distinct()
                            .collect(Collectors.toList());
                    Set<AccountApplicationBean> set = new LinkedHashSet<>();
                    set.addAll(accountApplicationBeanList);
                    accountApplicationBeanList.clear();
                    accountApplicationBeanList.addAll(set);
                    educationSavingsBean.getAccountApplication().clear();
                    educationSavingsBean.getAccountApplication().addAll(accountApplicationBeanList);
                }
            }
            LOG.info("Education End::");
        }
        return educationSavingsBean;
    }

    private VictoryFundBean getVictoryFundDetails(List<String> categories, String category, Asset pdfAsset,
                                                  String fund, String subCategory, VictoryFundBean victoryFundBean,
                                                  List<ServiceBean> serviceBeans,
                                                  List<AccountApplicationBean> accountApplicationBeans) {
        LOG.info("getVictoryFundDetails method " + categories + " : : " + category);
        if (categories.contains(category)) {
            if (category.equalsIgnoreCase("service")) {
                List<ServiceBean> serviceBeanList = getCategoryServiceDetails(pdfAsset, fund, category,
                        subCategory, serviceBeans);
                LOG.info("serviceBeanList.size() " +serviceBeanList.size());
                if (serviceBeanList.size() > 0) {
                    serviceBeanList = serviceBeanList
                            .stream()
                            .filter(serviceBean -> "victory-fund".equals(serviceBean.getFund()))
                            .collect(Collectors.toList());
                    Set<ServiceBean> set = new LinkedHashSet<>();
                    set.addAll(serviceBeanList);
                    serviceBeanList.clear();
                    serviceBeanList.addAll(set);
                    victoryFundBean.getService().clear();
                    victoryFundBean.getService().addAll(serviceBeanList);
                    LOG.info("serviceBeanList.size() end");
                }
            } else if (category.equalsIgnoreCase("account-application")) {
                List<AccountApplicationBean> accountApplicationBeanList = getCategoryAccountApplicationDetails(pdfAsset, fund,
                        category, subCategory, accountApplicationBeans);
                if (accountApplicationBeanList.size() > 0) {
                    accountApplicationBeanList = accountApplicationBeanList
                            .stream()
                            .filter(accountApplicationBean -> "victory-fund".equals(accountApplicationBean.getFund()))
                            .collect(Collectors.toList());
                    Set<AccountApplicationBean> set = new LinkedHashSet<>();
                    set.addAll(accountApplicationBeanList);
                    accountApplicationBeanList.clear();
                    accountApplicationBeanList.addAll(set);
                    victoryFundBean.getAccountApplication().clear();
                    victoryFundBean.getAccountApplication().addAll(accountApplicationBeanList);
                }
            }
        }
        return victoryFundBean;
    }

    private List<AccountApplicationBean> getCategoryAccountApplicationDetails(Asset pdfAsset, String fund,
                                                                              String category, String subCategory,
                                                                              List<AccountApplicationBean> accountApplicationBeans) {
        LOG.info("getCategoryAccountApplicationDetails method ");
        String formID = "";
        String pdfTitle = "";
        String pdfDescription = "";
        AccountApplicationBean accountApplicationBean = new AccountApplicationBean();
        if (Objects.nonNull(pdfAsset.getMetadataValue("pdf:FormID"))) {
            formID = pdfAsset.getMetadataValue("pdf:FormID");
        }
        if (Objects.nonNull(pdfAsset.getMetadataValue("dc:title"))) {
            pdfTitle = pdfAsset.getMetadataValue("dc:title");
        }
        if (Objects.nonNull(pdfAsset.getMetadataValue("dc:description"))) {
            pdfDescription = pdfAsset.getMetadataValue("dc:description");
        }
        accountApplicationBean.setFund(fund);
        accountApplicationBean.setCategory(category);
        accountApplicationBean.setSubCategory(subCategory);
        accountApplicationBean.setFormID(formID);
        accountApplicationBean.setPdfPath(pdfAsset.getPath());
        accountApplicationBean.setPdfTitle(pdfTitle);
        accountApplicationBean.setPdfDescription(pdfDescription);
        accountApplicationBeans.add(accountApplicationBean);
        LOG.info("getCategoryAccountApplicationDetails method end");
        return accountApplicationBeans;
    }

    private List<ServiceBean> getCategoryServiceDetails(Asset pdfAsset, String fund, String category,
                                                        String subCategory,
                                                        List<ServiceBean> serviceBeans) {
        LOG.info("getCategoryServiceDetails method ");
        String formID = "";
        String pdfTitle = "";
        String pdfDescription = "";
        ServiceBean serviceBean = new ServiceBean();
        if (Objects.nonNull(pdfAsset.getMetadataValue("pdf:FormID"))) {
            formID = pdfAsset.getMetadataValue("pdf:FormID");
        }
        if (Objects.nonNull(pdfAsset.getMetadataValue("dc:title"))) {
            pdfTitle = pdfAsset.getMetadataValue("dc:title");
        }
        if (Objects.nonNull(pdfAsset.getMetadataValue("dc:description"))) {
            pdfDescription = pdfAsset.getMetadataValue("dc:description");
        }
        serviceBean.setFund(fund);
        serviceBean.setCategory(category);
        serviceBean.setSubCategory(subCategory);
        serviceBean.setFormID(formID);
        serviceBean.setPdfPath(pdfAsset.getPath());
        serviceBean.setPdfTitle(pdfTitle);
        serviceBean.setPdfDescription(pdfDescription);
        serviceBeans.add(serviceBean);
        LOG.info("getCategoryServiceDetails method end");
        return serviceBeans;
    }
}