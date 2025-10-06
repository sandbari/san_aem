$(document).ready(function () {
            
			let isCorpHome = $("#isCorpHome").val() || "false";
            
            let isAuthor = $("#isAuthor").val() || "false";
            
            let popupContentUrl = $("#popupContentPage").val() || "";
            
            let popupContentList = [];
            
            let prevUrl = "";
             
            let currentUrl = window.location.href;
           
            if (window.history.length == 2) {
            
            	sharedJS.setCookie(GLOBALCOOKIEKEYCONFIG.PREVIOUSURL, "", -1);
            }
            if (isAuthor == "false") {
                
            	prevUrl = sharedJS.getCookie(GLOBALCOOKIEKEYCONFIG.PREVIOUSURL);
                
            	sharedJS.setCookie(GLOBALCOOKIEKEYCONFIG.PREVIOUSURL, currentUrl);
            }
            console.log("Prev url" + prevUrl);
            
            let usertypenavigationflag = sharedJS.getCookie(COOKIEKEYCONFIG.USERTYPENAVIGATION);
            
            
            window.popupVariations = {
                
            	showPopUpModel: function (popupContent, popupId,dynamicData) {
                
            		var wrapperDiv = $("<div>", {
                        "class": "globalPopUpWrapper"
                    });
                    
            		var popUp_container_div = $("<div>", {
                        "id": "vcm-popup",
                        "tabindex": "-1",
                        "class": "vcm-popup " + popupId,
                        "aria-describedby": popupId,
                        "style": "display:block;"
                    });
                    
            		var popup_button_container = $("<div>", {
                        "id": "vcmpopup-buttons"
                    });
                    
            		if (sharedJS.nonNullCheck(popupId) && !popupId.includes("eftPopup") && !popupId.includes("institutionalPopup")) {
                    
            			popUp_container_div.append("<div class='popup-header'><a href='#/' role='button' aria-label='Close' class='vcm-popup-btn-close'></a></div>");
                    }
                    if (sharedJS.nonNullCheck(popupContent.popuptext)) {
                        
                    	let popuptext = popupContent.popuptext;
                    	
                    	if(sharedJS.nonNullCheck(dynamicData)){
                    	
                    		popuptext=popupVariations.parseAndReplaceDynamicValues(dynamicData,popuptext);
                    	}
                    	
                    	popUp_container_div.append("<div class='vcm-popup-body' id=" + popupId + ">" + popuptext + "</div>");
                    	
                    }
                    if (sharedJS.nonNullCheck(popupContent.continueText)) {
                        
                    	popup_button_container.append("<a href='#' class='accept btn-primary' role='button'>" + popupContent.continueText + "<span class='btn-triangle-rt'></span></a>");
                    }

                    if (sharedJS.nonNullCheck(popupContent.cancelText)) {
                        
                    	popup_button_container.append("<a href='#' class='cancel btn-primary' role='button'>" + popupContent.cancelText + "<span class='btn-triangle-rt'></span></a>");
                    }
                    
                    popUp_container_div.append(popup_button_container);
                    
                    $(wrapperDiv).append(popUp_container_div);
                    
                    $(wrapperDiv).append("<div class='popup-mask' style='display: block;'></div>");
                    
                    if ($(".globalPopUpWrapper").length > 0) {
                    
                    	$(wrapperDiv).css('display', 'none');
                    } 
                    else {
                    
                    	$(wrapperDiv).css('display', 'block');
                    }
                    
                    $('body').append(wrapperDiv);

                    $('.vcm-popup').attr('tabindex', -1).focus();

                    $('body .root.responsivegrid').attr('aria-hidden', 'true');

                    var element = document.getElementById('vcm-popup');
                    var focusableEls = element.querySelectorAll('a[href]:not([disabled]), button:not([disabled]), textarea:not([disabled]), input[type="text"]:not([disabled]), input[type="radio"]:not([disabled]), input[type="checkbox"]:not([disabled]), select:not([disabled])');
                    var firstFocusableEl = focusableEls[0];
                    var lastFocusableEl = focusableEls[focusableEls.length - 1];
                    var KEYCODE_TAB = 9;

                    element.addEventListener('keydown', function(e) {
                        if (e.key === 'Tab' || e.keyCode === KEYCODE_TAB) {
                            if ( e.shiftKey ) /* shift + tab */ {
                                if (document.activeElement === firstFocusableEl) {
                                    lastFocusableEl.focus();
                                    e.preventDefault();
                                }
                            } else /* tab */ {
                                if (document.activeElement === lastFocusableEl) {
                                    firstFocusableEl.focus();
                                    e.preventDefault();
                                }
                            }
                        }
                    });
                },
                
                showAudienceSelectorPopUp: function () {
                
                	$('body').addClass('active');
                    
                	$('#audience-selector-popup').css("display", "block");
                    
                	$('.popup-mask').css("display", "block");
                    
                	$('.popup').addClass('navigationPopup');
                },
                
                setGlobalPopup: function (item, popupId) {
                
                    $(item).click(function (e) {
                    
                		e.preventDefault();
                        
                		let aTag = $(this);
                        
                		let href = $(aTag).attr('href');
                        
                		let target = $(aTag).attr('target');
                		
                		let dynamicData = $(aTag).attr('data-dynamicpopup');
                        
                		popupVariations.showGlobalPopup(dynamicData , href, target, popupId);
                    });
                	
                },
                
                showGlobalPopup: function (dynamicData = null , acceptHref = '#', acceptTarget = '_self', popupId, cancelHref = '#', cancelTarget = '_self') {

                    $('body').addClass('active');

                    $.each(popupContentList, function (index, item) {
                
                    	if (item.popupId == popupId) {
                        
                    		popupVariations.showPopUpModel(item, popupId,dynamicData);
                    		
							popupVariations.modifyMorningStarPopup(dynamicData);

							if ($("#morningstar-popup").hasClass("triggered-element")) {
                                $('#vcm-popup').addClass('morningstar-rating');
                            }
                            
                    		$('#vcm-popup').attr('data-target-link', acceptHref);
                            
                    		$('#vcm-popup').attr('data-target-tab', acceptTarget);
                            
                    		$('#vcm-popup').attr('data-cancelTarget-link', cancelHref);
                            
                    		$('#vcm-popup').attr('data-cancelTarget-tab', cancelTarget);
                            
                    		$('#vcm-popup').attr('role', 'dialog');
                            
                    		$('#vcm-popup').attr('aria-label', popupId);
                        }
                    	
                    });

                    if (jQuery(window).width() < 767) {
                        
                    	$('.vcm-popup').addClass('vcm-popup-mobile');
                    } 
                    else {
                    
                    	$('.vcm-popup').removeClass('vcm-popup-mobile');
                    }
                },
                bindPopUp: function (item) {

                	if (typeof item != "undefined" && item != null &&item != "") {
                       
                		$(item).click(function (e) {
                        
                			e.preventDefault();
                            
                			let targetUrl = "";
                            
                			// Override data-target if there is user specific
							// url
                            let linkArray = [];
                            
                            $.each(USERTYPES, function (index, value) {
                            
                            	let attrName = "data-" + value + "-url";
                                
                            	let attrValue = $(item).attr(attrName);
                                
                            	if (sharedJS.nonNullCheck(attrValue)) {
                                
                            		let linkObj = {};
                                    
                            		linkObj[value] = attrValue;
                                    
                            		linkArray.push(linkObj);
                                }
                            });

                            if (linkArray.length > 0) {
                              
                            	targetUrl = JSON.stringify(linkArray);
                            }
                            else {
                            
                            	targetUrl = $(item).attr("href");
                            }
                            
                            let targetTab = $(item).attr("target");

                            // Show popup only when internal transitions
                            
                            if (!sharedJS.isExternalLink($(item))) {
                                
                            	// Check for audience config
                                let validInvestorsTypes = $(item).attr("data-audiencePopUpTarget");

                                let individualInvestorPopup = $(item).attr("data-individualinvestorpopup");
                                
                                let validInvestorArray = null;
                                
                                if (sharedJS.nonNullCheck(validInvestorsTypes)) {
                                
                                	validInvestorArray = validInvestorsTypes.split(",");
                                }
                                // If audience config null show all user types
                                if (validInvestorArray == null) {

                                    $('#audience-selector-popup .navigationLinksButton.individualInvestorSelector').addClass('hide');
                                    if(sharedJS.nonNullCheck(individualInvestorPopup)){
                                        $('#audience-selector-popup .navigationLinksButton').addClass('hide');
                                        $('#audience-selector-popup .navigationLinksButton.individualInvestorSelector').removeClass('hide');
                                    }

                                    $('#audience-selector-popup .navigationLinksButton.victoryfinancial').addClass('victoryFundsInvestor');
                                
                                	popupVariations.showAudienceSelectorPopUp();
                                }
                                
                                // If audience config non null and having only
								// one user type
                                else if (validInvestorArray.length == 1) {

                                    $('#audience-selector-popup .navigationLinksButton.victoryfinancial').addClass('victoryFundsInvestor');
                                
                                	sharedJS.setCookie(COOKIEKEYCONFIG.USER_ENTITY_TYPE, validInvestorArray[0]);
                                    
                                	sharedJS.setCookie(COOKIEKEYCONFIG.SUBDOMAIN_USER_ENTITY_TYPE, validInvestorArray[0]);
                                    
                                    if (validInvestorArray[0] == USERTYPES.INSTITUTIONAL_INVESTOR) {
                                    
                                		sharedJS.setCookie(COOKIEKEYCONFIG.ISIIPOPUPSHOWN, 'false');
                                    }

                                    targetUrl = USERDOMAINMAP[validInvestorsTypes] + targetUrl;
                                    
                                    if (sharedJS.nonNullCheck(targetTab)) {

                                    	window.open(targetUrl, targetTab);
                                    } 
                                    else {

                                    	window.open(targetUrl, "_self");
                                    }
                                    
                                    popupVariations.closePopUp();
                                }
                                
                                // If config is authored with more than one user
								// type, filter and show the popup links
                                else {
                                
                                	popupVariations.showAudienceSelectorPopUp();

                                	$('#audience-selector-popup .navigationLinksButton.victoryfinancial').addClass('victoryFundsInvestor');
                                    
                                	$('#audience-selector-popup .navigationLinksButton').addClass('hide');
                                    
                                	if (sharedJS.nonNullCheck(validInvestorsTypes)) {
                                    
                                		$.each(validInvestorArray, function (index, val) {
                                        
                                			$("#audience-selector-popup .navigationLinksButton a[data-usertype='" + val + "']").parent().removeClass('hide');

                                			if (val === "victoryfunds_investor") {
                                                $("#audience-selector-popup .navigationLinksButton").removeClass('victoryFundsInvestor')
                                			}
                                        });
                                		
                                    } 
                                	else {
                                    
                                		$('#audience-selector-popup .navigationLinksButton').removeClass('hide');
                                    }
                                    $('#audience-selector-popup .navigationLinksButton.individualInvestorSelector').addClass('hide');
                                }
                                
                                if (sharedJS.nonNullCheck(targetUrl)) {
                                
                                	$('#audience-selector-popup.investorLinks.popup').attr('data-target-link', targetUrl);
                                }
                                if (sharedJS.nonNullCheck(targetTab)) {
                                    
                                	$('#audience-selector-popup.investorLinks.popup').attr('data-target-tab', targetTab);
                                }
                            } 
                            else {
                            
                            	if (sharedJS.nonNullCheck(targetTab)) {
                                
                            		window.open(targetUrl, targetTab);
                                } 
                            	else {
                                
                            		window.open(targetUrl, "_self");
                                }
                            	
                                popupVariations.closePopUp();
                            }
                        });
                    }
                },
                
                closePopUp: function () {
                
                	$('.investorLinks').removeClass('navigationPopup');
                    
                	$('#audience-selector-popup .navigationLinksButton').removeClass('hide');
                    
                	$('body').removeClass('active');
                    
                	$('.popup-mask').css("display", "none");
                    
                	$('.popup').css("display", "none");

                	$("#audience-selector-popup .individualInvestorSelectortext").css("display", "none");

                    $(".investorLinks.popup p.freetext--audienceSelector--popup").css("display", "block");
                },
                
                closeGlobalPopUp: function (element) {
                
                	$('body').removeClass('active');
                    
                	$(element).parents('.globalPopUpWrapper').remove();
                },
                
                victoryShareConfirm: function () {
                
                	try {
                    
                		var etfjson = decodeURIComponent($("#eftPageList").attr("data-value"));
                        
                		if (sharedJS.nonNullCheck(etfjson)) {
                        
                			var etfJsonObject = JSON.parse(etfjson);
                            
                			if (sharedJS.nonNullCheck(etfJsonObject) && sharedJS.nonNullCheck(etfJsonObject.etfLinks)) {
                            
                				let etfArray = etfJsonObject.etfLinks;
                                
                				console.log("etfArray : " + etfArray);
                        
                				let referrerUrl = prevUrl; // .toLowerCase();
                                
                				if (sharedJS.nonNullCheck(referrerUrl)) {
                                
                					let currentInd = etfArray.findIndex((ele) => currentUrl.indexOf(ele) >= 0)
                                    
                					let referrerInd = etfArray.findIndex(ele => referrerUrl.indexOf(ele) >= 0)
                                    
                					if (currentInd >= 0 && referrerInd < 0) {
                                    
                						popupVariations.setVictoryShareETFPopup("vcmleavingpopup")
                                    } 
                					else if (referrerInd >= 0 && currentInd < 0) {
                                    
                						popupVariations.setVictoryShareETFPopup("etfleavingpopup");
                                    }
                                }
                            }
                        }
                    }
                	catch (err) {
                    
                		console.error(err);
                    }
                },
                
                setVictoryShareETFPopup: function (popupId) {
                
                	if (sharedJS.nonNullCheck(popupId)) {
                    
                		$('body').addClass('active');
                        
                		$.each(popupContentList, function (index, item) {
                        
                			if (item.popupId == popupId) {
                            
                				let etfPopupId = "eftPopup " + popupId;
                                
                				popupVariations.showPopUpModel(item, etfPopupId,null);
                            }
                			
                        });
                    }
                },
                
                callIfNextPopup: function () {
                
                	let globalPopUpWrapper = $('body .globalPopUpWrapper:first');
                    
                	$(globalPopUpWrapper).css('display', 'block');
                },
                
                parseAndReplaceDynamicValues : function(dynamicData,popupContent){
                
                	let dynamicDataObj = JSON.parse(dynamicData);
                	
                	$.each(dynamicDataObj, function (index,value) {
                        
                		
                		let dataEle = '{'+index+'}';
                		
                		popupContent = popupContent.replace(dataEle, value);
                	
                	});
                	
                	return popupContent;
                },
				modifyMorningStarPopup: function (dynamicData) {
					let dynamicDataObj = JSON.parse(dynamicData);
                    var count = 0;
					$.each(dynamicDataObj, function (index,value) {

                        if(index == 'morning_starrating_3Year' && value=="0"){
							$('.morning-star-3years').hide();
						}
						if(index == 'morning_starrating_5Year' && value=="0"){
							$('.morning-star-5years').hide();
                            count++;
						}
						if(index == 'morning_starrating_10Year' && value=="0"){
							$('.morning-star-10years').hide();
                            count++;
						}

                	});	
                    if(count>1){
							$('.respectively').hide();
                        }
					
				}
            };
            
            window.popupServiceCalls = {
            
            		getPopUpContent: function () {
                    
            			let popupcontentSessionStore  = sessionStorage.getItem(GLOBALCOOKIEKEYCONFIG.POPUP_CONTENT);
            		
            			let deferred = $.Deferred();
                    
            			if (sharedJS.nonNullCheck(popupcontentSessionStore)) {
            				
            				deferred.resolve(JSON.parse(popupcontentSessionStore));
            			}
            			else{
            				let sameDomain = false;
            				$.each(USERDOMAINMAP, function (index, item) {
            					if(window.location.origin.includes(item)){
            						sameDomain = true;
            					}
            				});
            				if(sameDomain == false){
            					popupContentUrl = USERDOMAINMAP.usaa_member + popupContentUrl
            				}
	            			$.ajax({
	                        
	            				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	                        
	            				context: this,
	                        
	            				type: 'GET',
	                        
	            				url: popupContentUrl,
	                        
	            				dataType: "html",
	                        
	            				success: function (response) {
	                            
	            					let popupContent = popupServiceCalls.getPopUpContentList(response);
	            					
	            					sessionStorage.setItem(GLOBALCOOKIEKEYCONFIG.POPUP_CONTENT,JSON.stringify(popupContent));
	            					
	            					deferred.resolve(popupContent);
	            				}
	            			
	            			});
            			}
                    
            		return deferred.promise();
                },
                
                getPopUpContentList: function (data) {
                
                	let popupList = [];
                    
                	let rootNode = $(data).find('section.cmp-contentfragmentlist article');
                    
                	$.each(rootNode, function (index, fragmentNode) {
                    
                		let cfFragmentElements = $(fragmentNode).find('dl.cmp-contentfragment__elements .cmp-contentfragment__element');
                        
                		let popupModel = {};
                        
                		$.each(cfFragmentElements, function (index, element) {
                        
                			let elementClass = $(element).attr("class");
                            
                			if (sharedJS.nonNullCheck(elementClass)) {
                            
                				let elementId = elementClass.replace('cmp-contentfragment__element cmp-contentfragment__element--', '');
                                
                				let elementValue = $(element).children('.cmp-contentfragment__element-value').html();
                                
                				if (sharedJS.nonNullCheck(elementValue)) {
                                
                					elementValue = elementValue.trim();
                                }
                				
                                popupModel[elementId] = elementValue;
                            }
                        });
                		
                        popupList.push(popupModel);
                    });
                	
                    return popupList;
                }
            }
            if (sharedJS.nonNullCheck(popupContentUrl)) {
                
            	// Get Popup content
                
            	$.when(popupServiceCalls.getPopUpContent()).done(function (popupcontent) {
                
            		sharedJS.globalPopupContent = popupcontent;
                    
            		popupContentList = popupcontent;
                    
            		var evt = $.Event('popupAjaxCompleted');
                    
            		popupVariations.victoryShareConfirm();
                    
            		if (typeof popupcontent != "undefined") {

            			
                        // Call popup if component having data-popup attr
            			 $("body").on('click',"a[data-popup]" , function(e) {
                            
                        		e.preventDefault();

                        		$(this).addClass('triggered-element');
                        		
                        		let aTag = $(this);
                                
                        		if (sharedJS.nonNullCheck($(aTag).attr('data-popup'))) {
                        			
                        			var popupId = $(aTag).attr('data-popup');
	                                
	                        		let href = $(aTag).attr('href');
	                                
	                        		let target = $(aTag).attr('target');
	                        		
	                        		let dynamicData = $(aTag).attr('data-dynamicpopup');
	                                
	                        		popupVariations.showGlobalPopup(dynamicData , href, target, popupId);
                        		}

                         });

                        // Bind audience popup if the page is crop home
                        
                        if (isCorpHome == "true" && isAuthor == "false") {
                        
                        	$("a[data-audiencepopup]").each(function (index, item) {
                            
                        		let attrValue = $(item).attr('data-audiencepopup');
                                
                        		if (sharedJS.nonNullCheck(attrValue) && attrValue == "true") {
                                
                        			popupVariations.bindPopUp($(item));
                                }
                            });
                        }
                    }
            		
                    $(window).trigger(evt);
                });
            }
            
            // Logic for setting user selector type cookie and redirect to
			// actual href
            $('.investorLinks .navigationLinksButton.selectorType a').click(function (e) {

                let attrValue = $(this).attr('data-individualinvestorpopup');

                $("#audience-selector-popup .individualInvestorSelectortext").css("display", "none");
                $(".investorLinks.popup p.freetext--audienceSelector--popup").css("display", "block");

                if (sharedJS.nonNullCheck(attrValue) && attrValue == "true") {

                    $("#audience-selector-popup .individualInvestorSelectortext").css("display", "block");
                    $(".investorLinks.popup p.freetext--audienceSelector--popup").css("display", "none");

                    popupVariations.bindPopUp($(this));

                } else {
            
                    e.preventDefault();

                    let parentElement = $(this).closest('.investorLinks.popup')

                    let userType = $(this).attr("data-userType");

                    sharedJS.setCookie("user_entity_type", userType);

                    sharedJS.setCookie(COOKIEKEYCONFIG.SUBDOMAIN_USER_ENTITY_TYPE, userType);

                    if (userType == USERTYPES.INSTITUTIONAL_INVESTOR) {

                        sharedJS.setCookie(COOKIEKEYCONFIG.ISIIPOPUPSHOWN, 'false');
                    }

                    if ($(parentElement).length > 0) {

                        let targetUrl = $(this).parents('.investorLinks.popup').attr('data-target-link');

                        let targetTab = $(this).parents('.investorLinks.popup').attr('data-target-tab');

                        if (targetUrl != null && typeof targetUrl != 'undefined') {

                            if (targetUrl.startsWith("[")) {

                                let urlObj = JSON.parse(targetUrl);

                                $.each(urlObj, function (index, item) {

                                    if (item.hasOwnProperty(userType)) {

                                        targetUrl = item[userType];

                                        return false;
                                    }
                                });
                            }

                            targetUrl = USERDOMAINMAP[userType] + targetUrl;

                            window.open(targetUrl, targetTab);

                            popupVariations.closePopUp();

                        }
                    }
                    else {

                        window.location.href = this.href;
                    }
                }
            });


            // Logic for accept button global popup
            $(document).on("click", "#vcm-popup .accept", function (e) {
             
            	e.preventDefault();
                
            	let parentElement = $("#vcm-popup");
                
            	let popupClassArray = parentElement.attr('class').split(" ");
                
            	let targetUrl = $("#vcm-popup").attr('data-target-link');
                
            	let targetTab = $("#vcm-popup").attr('data-target-tab');
            	
            	if (popupClassArray.includes("institutionalPopup")) {
                
            		sharedJS.setCookie(COOKIEKEYCONFIG.ISIIPOPUPSHOWN, 'true');
                }
                
                if (sharedJS.nonNullCheck(targetUrl) && targetUrl != "#") {
                    
                	if (typeof targetTab == 'undefined') {
                
                		targetTab = '_self';
                    }
                	
                    window.open(targetUrl, targetTab);
                }
                
                popupVariations.closeGlobalPopUp($(this));
                
                popupVariations.callIfNextPopup();

                if ($('.triggered-element').length > 0) {
                    $('.triggered-element').focus();
                    $('.triggered-element').removeClass('triggered-element');
                } else {
                    $('#skipContentLink').focus();
                }


                $('body .root.responsivegrid').removeAttr('aria-hidden');
            });
            
            // Logic for cancel button global popup
            $(document).on("click", "#vcm-popup a.cancel", function (e) {
            
            	e.preventDefault();
                
            	let cancelTargetUrl = $("#vcm-popup").attr('data-cancelTarget-link');
                
            	let cancelTargetTab = $("#vcm-popup").attr('data-cancelTarget-tab');
                
            	let parentElement = $("#vcm-popup");
                
            	let popupClassArray = parentElement.attr('class').split(" ");

                
            	if (popupClassArray.includes("eftPopup")) {
                
            		sharedJS.setCookie(GLOBALCOOKIEKEYCONFIG.PREVIOUSURL, "", -1);
                    
            		if (usertypenavigationflag == "true") {
                    
            			sharedJS.setpreviouseProfile();
                        
            		}
                    
            		window.history.back();
                    
            	}
            	else if (popupClassArray.includes("institutionalPopup")) {
                
            		let previouseProfile = sharedJS.getpreviouseProfile();
            		
            		sharedJS.setCookie(COOKIEKEYCONFIG.ISIIPOPUPSHOWN, 'false');
                    
            		if (!$.isEmptyObject(previouseProfile)) {
                    
            			sharedJS.setCookie(COOKIEKEYCONFIG.SUBDOMAIN_USER_ENTITY_TYPE, previouseProfile.previousSubDomainCookie);
                        
            			sharedJS.setCookie(COOKIEKEYCONFIG.USER_ENTITY_TYPE, previouseProfile.previousCookie);
                        
            			if (sharedJS.nonNullCheck(HOMEPAGECONFIG[previouseProfile.previousCookie])) {
                        
            				if(sharedJS.getCookie(COOKIEKEYCONFIG.USER_ENTITY_TYPE) != USERTYPES.INSTITUTIONAL_INVESTOR)
            				{
            					window.location.href = HOMEPAGECONFIG[previouseProfile.previousCookie];
            				}
            				else{
            					popupVariations.closeGlobalPopUp($(this));
            				}
                            
            			}
            			else {
                        
            				window.location.href = HOMEPAGECONFIG["ALL"];
                         }
            			
                        }
            		else {

            			sharedJS.setCookie(COOKIEKEYCONFIG.SUBDOMAIN_USER_ENTITY_TYPE, "", -1);
            			
            			sharedJS.setCookie(COOKIEKEYCONFIG.USER_ENTITY_TYPE, "", -1);
                        
            			window.location.href = HOMEPAGECONFIG["ALL"];
                        
            		}
                   
            	}
                
            	else {
                
            		if (sharedJS.nonNullCheck(cancelTargetUrl)) {
                    
            			if (typeof cancelTargetTab == 'undefined') {
                        
            				cancelTargetTab = '_self';
                            
            			}
                        
            			window.open(cancelTargetUrl, cancelTargetTab);
                        
            			popupVariations.closeGlobalPopUp($(this));
                        
            		}
                    
            	}

            	$('#skipContentLink').focus();
            	$('body .root.responsivegrid').removeAttr('aria-hidden');
                
            });
            
            // Logic For Close Icon in pop selector
            
            $('.investorLinks.popup span').click(function () {
            
            	popupVariations.closePopUp();
                
            });
            
            // Logic For Close Icon in pop selector
            
            $(document).on("click", "#vcm-popup .vcm-popup-btn-close", function () {
            
            	popupVariations.closeGlobalPopUp($(this));

            	$('.triggered-element').focus();
                $('.triggered-element').removeClass('triggered-element');

                $('body .root.responsivegrid').removeAttr('aria-hidden');
                
            });

            
});