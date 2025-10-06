$(document).ready(function() {
    var fundstable;
    var responseData;
    var filterdata, selectedTab;
    var fixedleftColumns;
    var apiAemData = [];
    var filterlistingData = [];
    var filterdata = [];
    var selectedFilters = [];
	var fundApiUrl=$("#fundListEndpoint").val();
    var fundApiKey=$("#fundApiKey").val();
    var scrollJsPath=$("#dragScrollJS").val();
    var mfLabelsJsonStr = $("#mfLabels").val();
    var ajaxData_mfLabels = JSON.parse(mfLabelsJsonStr);

    if ($('.funds-list').length > 0) {

        $('body').on('mousedown', function() {
            $('body').addClass('using-mouse');
        });
        // Re-enable focus styling when Tab is pressed
        $('body').on('keydown', function(event) {
            if (event.keyCode === 9) {
                $('body').removeClass('using-mouse');
            }
        });

        /*AEM json data for pageurl,factsheeturl,franchise,iraeligible*/
        if(document.getElementById('fundListData').innerHTML !== ''){
            var aemData = JSON.parse(document.getElementById('fundListData').innerHTML);
            aemData = aemData.filter((obj, pos, arr) => {
                            return arr.map(mapObj =>
                                  mapObj.fundId).indexOf(obj.fundId) == pos;
                            });
        }

        /*AEM json data for labels*/
        loadApiData(ajaxData_mfLabels);

        var d = new Date();var n1 = d.getTime();
        //console.log("beforSIT", n1);

        function loadApiData(lblData) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: fundApiUrl,
                dataType: "json",
                beforeSend: function(xhr) {
                    $('.tab-content').css("visibility", "hidden");
                    $('.dynamic-loader').css("display", "block");
                    $('.error-message').css("display", "none");
                    xhr.setRequestHeader("x-api-key", fundApiKey);
                },
                success: function(response) {
                    var count = 0;
                    var n = new Date();var n3 = n.getTime();
                   // console.log("beforeJSON Processing", n3);

                    if (response.length) {
                        response.filter(resp => {
                            if (jQuery.isEmptyObject(resp)) {
                                count++;
                            }
                        });
                        if (count == response.length) {
                            $('.tab-content').css("display", "none");
                            $('.error-message').css("display", "block");

                        } else {
                            var myJSON = JSON.stringify(response);
                            loadDataTable(aemData, response, lblData);
                            tableInfo();
                            $('.dynamic-loader').css("display", "none");
                            $('.tab-content').css("visibility", "visible");
							$('.funds-list .fixedTabs').css("display", "block");
							if (screen.width >= 768) {
							 $('.funds-list .filter-sidebar-collapse').css("display", "block");
							}
                        }
                    } else {
                        $('.tab-content').css("display", "none");
                        $('.error-message').css("display", "block");
                    }
                    var d = new Date();var n4 = d.getTime();
                   // console.log("After JSON Processing", n4);
                    //console.log("Diff JSON Processing", (n4-n3));
                },
                error: function(jqXhr, textStatus, errMessage) {
                    $(".lblErrmsg").append(lblData.errorMessage);
                    $('.tab-content').css("display", "none");
                    $('.dynamic-loader').css("display", "none");
                    $('.error-message').css("display", "block");
                }
            });
        }

        function getCookie(cname) {
            var name = cname + "=";
            var decodedCookie = decodeURIComponent(document.cookie);
            var ca = decodedCookie.split(';');
            for(var i = 0; i <ca.length; i++) {
              var c = ca[i];
              while (c.charAt(0) == ' ') {
                c = c.substring(1);
              }
              if (c.indexOf(name) == 0) {
                return c.substring(name.length, c.length);
              }
            }
            return "";
          }

        function loadDataTable(aemData, apiData, labelData) {

            //appending AEM Labels to HTML
            if(labelData){
                $(".lblSearch").append(labelData.search);
                document.getElementById("searchFunds").placeholder = labelData.searchPlaceholder;
                $(".lblFilter").append(labelData.filter);
                $(".lblTabfirst").append(labelData.performance);
                $(".lblTabsecond").append(labelData.morningstarrating);
                $(".lblErrmsg").append(labelData.errorMessage);
                $(".lblOptMonthend").append(labelData.optMonthend);
                $(".lblOptQuarterend").append(labelData.optQuarterend);
                $(".lblOptAnnualend").append(labelData.optAnnualend);
                $(".lblMsgMonthend").append(labelData.msgMonthend);
                $(".lblMsgQuarterend").append(labelData.msgQuarterend);
                $(".lblMsgAnnualend").append(labelData.msgAnnualend);
                $(".lblAsof").append(labelData.asof);
                $(".lblRatingtext").append(labelData.ratingtext);
                $(".lblTblCaption").append(labelData.tableCaption);
                $(".lblTicker").append(labelData.ticker);
                $(".lblFundname").append(labelData.fundname);
                $(".lblAssetclass").append(labelData.th_assetclass);
                $(".lblYtd").append(labelData.ytd);
                $(".lblOneyr").append(labelData.oneyr);
                $(".lblThreeyr").append(labelData.threeyr);
                $(".lblFiveyr").append(labelData.fiveyr);
                $(".lblTenyr").append(labelData.tenyr);
                $(".lblsinceinception").append(labelData.sinceinception);
                $(".lblInceptiondate").append(labelData.inceptiondate);
                $(".lblExpratgross").append(labelData.expratgross);
                $(".lbExpratnet").append(labelData.expratnet);
                $(".lblRisk").append(labelData.risk);
                $(".lblOverall").append(labelData.overall);
                $(".lblMSthreeyr").append(labelData.msthreeyr);
                $(".lblMSfiveyr").append(labelData.msfiveyr);
                $(".lblMStenyr").append(labelData.mstenyr);
                $(".lblRatingcategory").append(labelData.ratingcategory);
                $(".lblFundfactsheet").append(labelData.fundfactsheet);
                $(".lblClose").append(labelData.close);
                $(".lblTotop").append(labelData.totop);
            }

            let responseData = "";

                selectShareClassData = [];
                selectShareClassDataUser = [];
                selectShareClassDataAll = [];
                shareClassList= [];

                for(var i=0; i < apiData.length; i++) {
                    scl='';
                    for(var j=0; j < apiData[i].classes.length; j++) {

                        scl=apiData[i].classes[j].share_class;
                        shareClassList.push(scl);

                        var classes = apiData[i].classes[j];
                        classes.fundId = apiData[i].fundId;
                        selectShareClassDataAll.push(classes); /* User: FP => API Data categoried with share class */
                        var aClassTickers = ["SRVEX","RSNRX","GUHYX","RLDAX","MUCAX","VSCVX","MGOAX","MNNAX","RSGGX","RSGRX","GUBGX","RSINX","GPAFX","RSIFX","RSDGX","RSEGX","RSVAX","MUXAX","GBEMX","SSVSX","VETAX","SSGSX","SBALX","GUTEX","THBVX","MAICX","MISAX","CUHAX"];
                        var tickerName=apiData[i].classes[j].ticker;
                        if( (scl=='Fund')||(scl=='Reward')||(scl=='Member') || (scl=='A' && aClassTickers.includes(tickerName))){  /* User: Member => API Data categoried with share class */
                            selectShareClassDataUser.push(classes);
                        }

                    }
                }

            /* Filter Panel: USER-FP / User-Member Data Initialization */
            selectShareClassData = '';
            selectUserType = '';

            /* Specify User Type {FP,Member}*/
            var userType = '';
            var userType = getCookie('subdomain_user_entity_type');
            //var userType = 'financial_professional';

            if(userType == 'financial_professional'){
                selectShareClassData = selectShareClassDataAll; /* USER-FP Data (ie all ShareClass)*/
                selectUserType='FP';
            } else {
                selectShareClassData = selectShareClassDataUser; /* USER-Member Data (ie specific shareClass)*/
                selectUserType='USER';
            }

           /* Filter Panel: USER-FP=> Make unique Share Class Type list + Sorting*/
            const distinct = (value, index, self) => {
                return self.indexOf(value) === index;
            }
            const shareClassNameListData = shareClassList.filter(distinct);
            shareClassNameList = shareClassNameListData.sort();

            /*Filter Panel: USER-FP=> Display of Share Class name */
            function shareClassLabelDisplayfn(shareclassName) {
               // shareClassLabel = shareclassName+" "+"Share Class";
               shareClassLabel = shareclassName+" "+labelData.shareclass;

                return shareClassLabel;
            }

            shareClassOption = [];
            shareClassLabel = '';

            /*Filter Panel: USER-FP=> Share Class name with Radio button*/
            for(var i=0; i < shareClassNameList.length; i++) {

                shareClassLabel = shareClassLabelDisplayfn(shareClassNameList[i]);
                shareClassOption += '<li class="filter-text"><input type="radio" name="shareclass" class="styled-radio" tabindex="0" value="' + shareClassNameList[i] + '" id="' + shareClassNameList[i] + '"><label for="' + shareClassNameList[i] + '" class="radio-label">' + shareClassLabel + '</label></li>'

            }

            /*Filter Panel: USER-FP=> Share Class Popup Content */
            if ($("#share_classLabel").length == 0) {
                shareclass_popup_content = '';
            } else {
                shareclass_popup_content = '<p class="popup-modal" id="share_classLabel">' +document.getElementById('share_classLabel').innerHTML+ '</p>';
            }

            /*Filter Panel: USER-FP=> Share Class list Display */
            filterShareClassTemplateContent = '<div class="filter-header"><p class="filter-title" id="share_class_filter" tabindex="0" role="button" data-toggle="collapse" data-parent="#filter_accordion" data-target="#share_class" aria-expanded="false">'+labelData.shareclass+'<span aria-hidden="true"></span></p></div><div role="group" id="share_class" class="filter-body collapse show" data-filtertype="share_class" aria-labelledby="share_class_filter"><ul class="filter-list">'+shareClassOption+'</ul>'+shareclass_popup_content+'</div>';


            apiDatas = selectShareClassData;

            /* AEM Variable data mapping with API data using FundID */
            for(var i=0; i < aemData.length; i++) {

                for(var j=0; j < apiDatas.length; j++) {

                    if(apiDatas[j].fundId==aemData[i].fundId){
                        apiDatas[j].fundName = apiDatas[j].entity_long_name;
                        apiDatas[j].detailpageurl = aemData[i].detailpageurl;
                        apiDatas[j].factsheeturl = aemData[i].factsheeturl;
                        apiDatas[j].advisorFactsheetUrl = aemData[i].advisorFactsheetUrl;
                        apiDatas[j].memberFactsheetUrl = aemData[i].memberFactsheetUrl;
                        apiDatas[j].isActiveFundPage = aemData[i].isActiveFundPage;
                        apiDatas[j].investMinFinancial = aemData[i].investMinFinancial;    // RS fund
                        apiDatas[j].filter.aem_franchise = aemData[i].franchise!==undefined?aemData[i].franchise:'';
                        apiDatas[j].filter.aem_ira_eligible = aemData[i].iraEligible!==undefined?aemData[i].iraEligible:'';
                        apiAemData.push(apiDatas[j]);
                    }

                }
            }

            apiAemData.map((obj) => {
                Object.keys(obj).forEach(function(key) {
                    if (obj[key] === null || obj[key] === "null") {
                        obj[key] = labelData.notApplicable;
                    }
                });
                if((obj.performance!==undefined) && (obj.performance.monthly!==undefined)){
                    var monthData = obj.performance.monthly;
                    Object.keys(monthData).forEach(function(key) {
                        if (monthData[key] === null || monthData[key] === "null") {
                            monthData[key] = labelData.notApplicable;
                        }
                    });
                }

                if((obj.performance!==undefined) && (obj.performance.quarterly!==undefined)){
                    var QuarterData = obj.performance.quarterly;
                    Object.keys(QuarterData).forEach(function(key) {
                        if (QuarterData[key] === null || QuarterData[key] === "null") {
                            QuarterData[key] = labelData.notApplicable;
                        }
                    });
                }

                if(obj.morningStarRating!==undefined){
                    var ratingData = obj.morningStarRating;
                    Object.keys(ratingData).forEach(function(key) {
                        if (ratingData[key] === null || ratingData[key] === "null") {
                            ratingData[key] = labelData.notApplicable;
                        }
                    });
                }
                if(obj.filter!==undefined){
                    var filterlistData = obj.filter;
                    Object.keys(filterlistData).forEach(function(key) {
                        if (filterlistData[key] === null || filterlistData[key] === "null") {
                            filterlistData[key] = labelData.notApplicable;
                        }
                    });
                }
            });

            responseData = apiAemData;/* AEM Data mapped with API Data: list prepared */

            /* Make unique data */
            const unique = (value, index, self) => {
                return self.indexOf(value) === index;
            }

            /* Filter listing data creation: Start */
            if(responseData.length>0)  {

                var arrKey=[];
                var arrVal=[];
                for (let i = 0; i < responseData.length; i++) {

                    data =responseData[i].filter;
                    $.each( data, function( key, value ) {

                        if((key!='ira_eligible_funds ') && (key!='morning_star_category')){ /* Restrict from API data for Filter PAnel*/
                            if((value !== undefined) && (value !== null) && (value !== 'N/A') && (value !== "") && (value !== "false")){
                                arrKey.push(key); /* Separated Key in one Array */
                                arrVal.push(value); /* Separated Value in one Array */
                            }
                        }

                    });

                }
                filterFromAPI = arrVal;

                /* Filter Panel: Grouping the Values with ',' for the corresponding Key */
                var result =  filterFromAPI.reduce(function(result, field, index) {
                    result[arrKey[index]] += field.concat(',');
                    return result;
                }, {})
                const filterListData = Object.entries(result);

                filterCategoryData=filterListData;

                function makeFilterArr(filterCategoryData) {
                /* Filter Panel: Making unique Data as Array format and removing undefined*/
                    var arrchklist = [];
                    for (let i = 0; i < filterCategoryData.length; i++) {
                        var fcdata = filterCategoryData[i];

                        for (let j = 0; j < fcdata.length; j++) {

                            if(j==1){
                                if(fcdata[j].indexOf("undefined") >= 0){
                                    fcdatavalue = fcdata[j].replace('undefined', '');
                                    var convertedarraydata = fcdatavalue.split(",");
                                    let poppedlastdata = convertedarraydata.pop();
                                } else if(fcdata[j].indexOf("undefinedall,") >= 0){
                                    fcdatavalue = fcdata[j].replace('undefinedall', '');
                                    var convertedarraydata = fcdatavalue.split(",");
                                    let poppedlastdata = convertedarraydata.pop();
                                } else {
                                convertedarraydata = fcdata[j];
                                }
                                const arrdistinct = (value, index, self) => {
                                    return self.indexOf(value) === index;
                                }
                                const fcarraydata = convertedarraydata.filter(arrdistinct);
                                fcdata[j] =fcarraydata;

                            }

                        }
                        arrchklist.push(fcdata);
                    }
                    return arrchklist;
                }

                finalDataFilter=[];
                finalDataFilter = makeFilterArr(filterCategoryData);

                /* Filter Panel: USER-FP => No Display for IRA Eigilible, Investment*/
                let finalFilter=[];
                if(selectUserType=='FP'){
                    for (let i = 0; i < finalDataFilter.length; i++) {
                        if ( (finalDataFilter[i][0]!=='aem_ira_eligible') && (finalDataFilter[i][0]!=='investment')){
                            finalFilter.push(finalDataFilter[i]);
                        }
                    }
                } else {
                    finalFilter=finalDataFilter;
                }

                /* FundID having 'N' FundType(1:N):
                1. so separating filter data which having ; and make it unique diaplay
                2. removing the filter data having ;*/
                function removeAllElements(array, elem) {
                    var index = array.indexOf(elem);
                    while (index > -1) {
                        array.splice(index, 1);
                        index = array.indexOf(elem);
                    }
                }
                for (let i = 0; i < finalFilter.length; i++) {
                    for (let j = 0; j < finalFilter[i][1].length; j++) {
                        valArr=finalFilter[i][1][j];
                        if((valArr.includes(";"))==true){
                            madeArr='';
                            madeArr=finalFilter[i][1][j].split('; ');
                            for (let z = 0;z < madeArr.length; z++){
                                finalFilter[i][1].push(madeArr[z]);
                            }
                        }
                    }
                    finalFilter[i][1]=finalFilter[i][1].filter(unique);
                }
                for (let i = 0; i < finalFilter.length; i++) {
                    for (let j = 0; j < finalFilter[i][1].length; j++) {
                        valArr=finalFilter[i][1][j];
                        if((valArr.includes(";"))==true){
                            removeAllElements(finalFilter[i][1], valArr);
                        }
                    }
                }

                /* Filter data: SORTING & final Filter Panel list for Checkbox list */
                var finalFilterList = [];
                for (let i = 0; i < finalFilter.length; i++) {
                	var ffData=finalFilter[i];
                	for (let j = 0; j < finalFilter[i][1].length; j++) {
                		if(j==0){
                			if((ffData[j].indexOf("investment") >= 0) || (ffData[j].indexOf("rating") >= 0)){
                				sortnumeric=true;
                				sortorder=false;
                				sortcustom=false;
                				if(ffData[j].indexOf("rating") >= 0){
                					sortorder=true;
                				}
                			} else if(ffData[j].indexOf("asset_class") >= 0 || ffData[j].indexOf("fund_type") >= 0 || ffData[j].indexOf("volatality") >= 0){
                				if (ffData[j].indexOf("asset_class") >= 0){
                					asset_class_sort = true;
                					fund_type_sort = false;
                					volatality_sort = false;
                				}
                				if (ffData[j].indexOf("fund_type") >= 0){
                					asset_class_sort = false;
                					fund_type_sort = true;
                					volatality_sort = false;
                				}
                				if (ffData[j].indexOf("volatality") >= 0){
                					asset_class_sort = false;
                					fund_type_sort = false;
                					volatality_sort = true;
                				}
                				sortcustom=true;
                				sortnumeric=false;
                				sortorder=false;

                			}else {
                				sortnumeric=false;
                				sortorder=false;
                				sortcustom=false;
                			}
                		}
                		if(j==1){
                			if(sortnumeric)   {
                				if(sortorder){
                					ffData[j] =ffData[j].sort(function(a, b){return b-a;});
                				}else{
                					ffData[j] =ffData[j].sort(function(a, b){return a-b;});
                				}
                			} else {
                				ffData[j] =ffData[j].sort();
                				if(sortcustom){
                					var a = ffData[j];
                					var b = [];
                					var c = [];
                					 if(asset_class_sort && ajaxData_mfLabels.asset_class_order){
                						b = ajaxData_mfLabels.asset_class_order.split(',') // ['US Equity','equal','Intl/Global Equity','Mixed Asset Class','Sector and Alternative Strategies','Fixed Income','Money Market'];
                					} else if(fund_type_sort && ajaxData_mfLabels.fund_type_order) {
                						b = ajaxData_mfLabels.fund_type_order.split(',')  //  ['Stock','Taxable Bond','Tax-Exempt Bond','Target Retirement','Target Risk','Alternative/Sector','Index','Money Market'];
                					} else if(volatality_sort && ajaxData_mfLabels.volatality_order) {
                						b = ajaxData_mfLabels.volatality_order.split(',') // ['Very Aggressive','Aggressive','Moderately Aggressive','Moderate','Moderately Conservative','Conservative','Preservation of Capital'];
                					}
                					$.each( b, function( key, value ) {
                						var index = $.inArray( value, a );
                						if( index != -1 ) {
                							c.push(value)
                						}
                					});
                					$.each( a, function( key, value ) {
                						var index = $.inArray( value, b );
                						if( index == -1 ) {
                							c.push(value)
                						}
                					});
                					ffData[j] = c;
                				}
                			}
                		}
                	}
                	finalFilterList.push(ffData);
                }



                /* Filter Panel Display Section*/
                var filterTemplate='';
                if(finalFilterList.length > 0) {

                    filterTemplateContent='';

                    for (let i = 0; i < finalFilterList.length; i++) {

                        lstArrData='';
                        popupContent='';
                        var arrdata=finalFilterList[i][1];


                        if(arrdata.length>1)
                            var lstArrDataIndex = '<li class="filter-text listcheck"><input type="checkbox" name="chkbox" id="check'+i+'" class="styled-checkbox check-all" value="all" data-category="'+finalFilterList[i][0]+'" /><label for="check'+i+'">'+labelData.all+'</label></li>';
                        else
                            var lstArrDataIndex = '';
                        for (let j = 0; j < arrdata.length; j++) {

                            if( (finalFilterList[i][0].includes('minimum') || finalFilterList[i][0].includes('investm')) && ($.isNumeric(arrdata[j])) ){
                                displyarr='$'+parseInt(arrdata[j]).toLocaleString();
                            } else if (finalFilterList[i][0].includes('ira')) {
                                //displyarr='Funds Eligible for IRAs';
                                displyarr=labelData.aem_ira_eligible_lbltrue;
                            } else if (finalFilterList[i][0].includes('rating')) {
                                if(arrdata[j]=='0'){
                                    displyarr=labelData.notApplicable;
                                }else{
                                    displyarr=arrdata[j];
                                }
                            } else {
                                displyarr=arrdata[j];
                            }
                            if(displyarr =='USAA Investments'){
                                displyarr = 'Victory Investments';
                            }
                            lstArrData += '<li class="filter-text listCheck"><input tabindex="0" type="checkbox" name="chkbox" id="check' + i + j + '" class="styled-checkbox" value="' + arrdata[j] + '" data-category="' + finalFilterList[i][0] + '" /><label for="check' + i + j + '">' + displyarr + '</label></li>';

                        }

                        if ($("#" + finalFilterList[i][0] + "Label").length == 0) {
                            popupContent = "";
                        } else {
                            popupContent = '<p class="popup-modal" id="'+finalFilterList[i][0]+'Label"+">' + document.getElementById(finalFilterList[i][0] + "Label").innerHTML + '</p>';
                        }
                        var tempData = finalFilterList[i][0];
                        filterTemplateContent += '<div class="filter-header"><p class="filter-title"  id="' + finalFilterList[i][0] + '_filter" tabindex="0" role="button" data-toggle="collapse" data-parent="#filter_accordion" data-target="#' + finalFilterList[i][0] + '">' + labelData[`${tempData}`] + '<span aria-hidden="true"></span></p></div><div role="group" id="' + finalFilterList[i][0] + '" class="filter-body collapse show" data-filtertype="' + finalFilterList[i][0] + '" aria-labelledby="' + finalFilterList[i][0] + '_filter"><ul class="filter-list">' + lstArrDataIndex + lstArrData + '</ul>' + popupContent + '</div>';

                    }
                    if(selectUserType=='FP'){
                        var filterTemplate= `<div id="filter_accordion" class="accordion">${filterShareClassTemplateContent}${filterTemplateContent}</div>`;
                    } else {
                        var filterTemplate= `<div id="filter_accordion" class="accordion">${filterTemplateContent}</div>`;
                    }

                }

            }

            /* Filter Panel: Make default checkbox from URL paramaters */
            var url_string = window.location.href;
            //var url_string = "http://www.example.com/test.html?fundType=Alternative/Sector";
            var url = new URL(url_string);
            var paramVal = url.searchParams.get("fundType");
            var assetclassParam = url.searchParams.get("assetClass");

            // mobile performance and rating grid view
            createList(responseData);
            calculateWidth();

            // Filter Panel: Default check for Checkbox & option button
            if(paramVal!==null){
                $("input[type=checkbox][name='chkbox'][data-category='fund_type'][value='"+paramVal+"']").prop("checked",true);
            }
            if(assetclassParam!==null){
                $("input[type=checkbox][name='chkbox'][data-category='asset_class'][value='"+assetclassParam+"']").prop("checked",true);
            }
            if(selectUserType=='FP'){
                $("input[type=radio][name='shareclass'][value='A']").prop('checked', true);
            }
            //Morning Star Panel display of as-of data
            var displayLabelRatingasofDate = document.getElementsByClassName('ratingdate');
            displayLabelRatingasofDate[0].innerHTML= responseData[0].morning_star_as_of_date;
            displayLabelRatingasofDate[1].innerHTML= responseData[0].morning_star_as_of_date;

            selectCheckboxes();
            //filterChange();

            // setting fixed column for pc or mobile
            if ($(window).width() < 768) {
                fixedleftColumns = 1;
                $('.mobile-radiobuttons-section').removeClass('hidden');
                $('.pc-radiobuttons-section').addClass('hidden');
                $('#monthend-mobile').prop('checked', 'true');
                $('.performance-rating-list-content').addClass('hidden');
                $('.performance-content-grid-view').removeClass('hidden');
            } else {
                fixedleftColumns = 3;
                $('.mobile-radiobuttons-section').addClass('hidden');
                $('.pc-radiobuttons-section').removeClass('hidden');
                $('.performance-content-grid-view').addClass('hidden');
                $('.rating-content-grid-view').addClass('hidden');
                $('.performance-rating-list-content').removeClass('hidden');
            }

            var currentSelectedOptionforSort = '';
            fundstable = $('#fundslistTable').DataTable({
                data: responseData,
                deferRender: true,
                scrollX: true,
                scrollCollapse: true,
                order: [],
                /*ordering: false,*/
                paging: false,
                autoWidth: false,
                responsive: true,
                searching: false,
                fixedColumns: {
                    leftColumns: fixedleftColumns,
                    rightColumns: 0,
                    heightMatch: "auto"
                },
                language: {
                    "aria": {
                        "sortAscending": "",
                        "sortDescending": ""
                    },
                    "info": labelData.showing+': _TOTAL_ '+labelData.of+' _MAX_ '+labelData.funds,
                    "infoFiltered": "",
                    "infoEmpty": labelData.showing+': _TOTAL_ '+labelData.of+' _MAX_ '+labelData.funds
                },
                columns: [{
                        className: "ticker",
                        "data": {
                            "ticker": "ticker",
                            "detailpageurl": "detailpageurl",
                            "isActiveFundPage": "isActiveFundPage"
                        },
                        "render": function(data) {
                            var ActiveFundPage = data.isActiveFundPage;
                            var detailUrl;
                            if (ActiveFundPage == "true") {
                                detailUrl = data.detailpageurl;
                            } else {
                                detailUrl = "#"
                            }
                            return `<a class="tickertext fundnameLS" href="${detailUrl}" target="_self" data-scf="${data.share_class}-${data.fundId}-${data.investMinFinancial}" data-url="${detailUrl}">${data.ticker}</a>`                        }
                    },
                    {
                        className: "fundname",
                        "data": {
                            "fundName": "fundName",
                            "detailpageurl": "detailpageurl",
                            "isActiveFundPage": "isActiveFundPage",
                            "fundId": "fundId",
                            "share_class": "share_class",
                            "investMinFinancial": "investMinFinancial"
                        },
                        "render": function(data) {
                            if( (data.fundName!==undefined) && (data.fundName!=='N/A')){
                                var ActiveFundPage = data.isActiveFundPage;
                                var detailUrl;
                                if (ActiveFundPage === "true") {
                                    detailUrl = data.detailpageurl;
                                } else {
                                    detailUrl = "#"
                                }
                                if(selectUserType=='FP') {
                                    shareclasslbl = shareClassLabelDisplayfn(data.share_class);
                                }else if(selectUserType=='USER') {
                                    shareclasslbl = shareClassLabelDisplayfn(data.share_class);
                                }else {
                                    shareclasslbl = ""
                                }

                                return `<a href="${detailUrl}" target="_self" class="fundnameLS" data-scf="${data.share_class}-${data.fundId}-${data.investMinFinancial}" data-url="${detailUrl}">${data.fundName}</a><br/><span>${shareclasslbl}</span>`
                            } else {
                                return ``

                            }
                        }
                    },
                    { className: "assetclass", "data": "asset_class",
                        "render": function(data) {
                            if(data!==undefined)    return data;    else return labelData.notApplicable;
                        }
                    },
                    {
                        className: "ytd performance-monthly-data performance-quarterly-data",
                        "data": {
                            "performance":"performance",
                            "share_class":"share_class"
                        },
                        "render": function(data, type) {
                            if( (data.performance!==undefined) && (data.performance.monthly!==undefined) && (data.performance.quarterly!==undefined) ){

                                if((data.performance.monthly.ytd_nav!==undefined) && (data.performance.monthly.ytd_nav!=='N/A') && (data.performance.monthly.ytd_nav!==null)){
                                    monthly_ytd_nav=parseFloat(data.performance.monthly.ytd_nav).toFixed(2);
                                } else {
                                    monthly_ytd_nav=labelData.notApplicable;
                                }
                                if((data.performance.quarterly.ytd_nav!==undefined) && (data.performance.quarterly.ytd_nav!=='N/A') && (data.performance.quarterly.ytd_nav!==null)){
                                    quarterly_ytd_nav=parseFloat(data.performance.quarterly.ytd_nav).toFixed(2);
                                } else {
                                    quarterly_ytd_nav=labelData.notApplicable;
                                }

                                if((data.performance.monthly.ytd_mop!==undefined) && (data.performance.monthly.ytd_mop!=='N/A') && (data.performance.monthly.ytd_mop!==null)){
                                    monthly_ytd_mop=parseFloat(data.performance.monthly.ytd_mop).toFixed(2);
                                }else {
                                    monthly_ytd_mop=labelData.notApplicable;
                                }
                                if((data.performance.quarterly.ytd_mop!==undefined) && (data.performance.quarterly.ytd_mop!=='N/A') && (data.performance.quarterly.ytd_mop!==null)){
                                    quarterly_ytd_mop=parseFloat(data.performance.quarterly.ytd_mop).toFixed(2);
                                }else {
                                    quarterly_ytd_mop=labelData.notApplicable;
                                }
                            } else {
                                monthly_ytd_nav=labelData.notApplicable;
                                quarterly_ytd_nav=labelData.notApplicable;

                                monthly_ytd_mop=labelData.notApplicable;
                                quarterly_ytd_mop=labelData.notApplicable;
                            }
                            if(type==='sort'){
                                if(currentSelectedOptionforSort == 'quarterend'){
                                    return quarterly_ytd_nav;
                                }else{
                                     return monthly_ytd_nav;
                                }
                            }
                            if(selectUserType=='FP') {
                                if( (data.share_class=='A') || (data.share_class=='C') ){
                                    return `<p class="ytdNav performance-monthly-data performance-quarterly-data"><span class="ytdlabel">${labelData.nav}</span>
                                    <span class="ytdvalue performance-monthly-data">${monthly_ytd_nav}</span>
                                    <span class="ytdvalue performance-quarterly-data hidden">${quarterly_ytd_nav}</span></p>
                                    <p class="ytdSpace"></p>
                                    <p class="ytdMop performance-monthly-data performance-quarterly-data"><span class="ytdlabel">${labelData.load}</span>
                                    <span class="ytdvalue performance-monthly-data">${monthly_ytd_mop}</span>
                                    <span class="ytdvalue performance-quarterly-data hidden">${quarterly_ytd_mop}</span></p>`;

                                } else {
                                    return `<p class="ytdNav performance-monthly-data performance-quarterly-data">
                                    <span class="ytdvalue performance-monthly-data">${monthly_ytd_nav}</span>
                                    <span class="ytdvalue performance-quarterly-data hidden">${quarterly_ytd_nav}</span></p>`;
                                }
                            } else {
                               if( (data.share_class=='A') || (data.share_class=='C') ){
                                   return `<p class="ytdNav performance-monthly-data performance-quarterly-data"><span class="ytdlabel">${labelData.nav}</span>
                                   <span class="ytdvalue performance-monthly-data">${monthly_ytd_nav}</span>
                                   <span class="ytdvalue performance-quarterly-data hidden">${quarterly_ytd_nav}</span></p>
                                   <p class="ytdSpace"></p>
                                   <p class="ytdMop performance-monthly-data performance-quarterly-data"><span class="ytdlabel">${labelData.load}</span>
                                   <span class="ytdvalue performance-monthly-data">${monthly_ytd_mop}</span>
                                   <span class="ytdvalue performance-quarterly-data hidden">${quarterly_ytd_mop}</span></p>`;

                               } else {
                                   return `<p class="ytdNav performance-monthly-data performance-quarterly-data">
                                   <span class="ytdvalue performance-monthly-data">${monthly_ytd_nav}</span>
                                   <span class="ytdvalue performance-quarterly-data hidden">${quarterly_ytd_nav}</span></p>`;
                               }

                            }
                        }
                    },
                    {
                        className: "yearone performance-monthly-data performance-quarterly-data",
                        "data": {
                            "performance":"performance",
                            "share_class":"share_class"
                        },
                        "render": function(data, type) {
                            if( (data.performance!==undefined) && (data.performance.monthly!==undefined) && (data.performance.quarterly!==undefined) ){

                                if((data.performance.monthly.oneyear_nav!==undefined) && (data.performance.monthly.oneyear_nav!=='N/A') && (data.performance.monthly.oneyear_nav!==null)){
                                    monthly_oneyear_nav=parseFloat(data.performance.monthly.oneyear_nav).toFixed(2);
                                } else {
                                    monthly_oneyear_nav=labelData.notApplicable;
                                }
                                if((data.performance.quarterly.oneyear_nav!==undefined) && (data.performance.quarterly.oneyear_nav!=='N/A') && (data.performance.quarterly.oneyear_nav!==null)){
                                    quarterly_oneyear_nav=parseFloat(data.performance.quarterly.oneyear_nav).toFixed(2);
                                } else {
                                    quarterly_oneyear_nav=labelData.notApplicable;
                                }

                                if((data.performance.monthly.oneyear_mop!==undefined) && (data.performance.monthly.oneyear_mop!=='N/A') && (data.performance.monthly.oneyear_mop!==null)){
                                    monthly_oneyear_mop=parseFloat(data.performance.monthly.oneyear_mop).toFixed(2);
                                }else {
                                    monthly_oneyear_mop=labelData.notApplicable;
                                }
                                if((data.performance.quarterly.oneyear_mop!==undefined) && (data.performance.quarterly.oneyear_mop!=='N/A') && (data.performance.quarterly.oneyear_mop!==null)){
                                    quarterly_oneyear_mop=parseFloat(data.performance.quarterly.oneyear_mop).toFixed(2);
                                }else {
                                    quarterly_oneyear_mop=labelData.notApplicable;
                                }

                            } else {
                                monthly_oneyear_nav=labelData.notApplicable;
                                quarterly_oneyear_nav=labelData.notApplicable;

                                monthly_oneyear_mop=labelData.notApplicable;
                                quarterly_oneyear_mop=labelData.notApplicable;
                            }

                            if(type==='sort'){

                                if(currentSelectedOptionforSort == 'quarterend'){
                                    return quarterly_oneyear_nav;
                                }else{
                                     return monthly_oneyear_nav;
                                }
                            }

                            if(selectUserType=='FP') {
                                if( (data.share_class=='A') || (data.share_class=='C') ){
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_oneyear_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_oneyear_nav}</span></p>
                                    <p class="ytdSpace"></p>
                                    <p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_oneyear_mop}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_oneyear_mop}</span></p>`;

                                } else {
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_oneyear_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_oneyear_nav}</span></p>`;
                                }
                            } else {
                                if( (data.share_class=='A') || (data.share_class=='C') ){
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_oneyear_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_oneyear_nav}</span></p>
                                    <p class="ytdSpace"></p>
                                    <p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_oneyear_mop}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_oneyear_mop}</span></p>`;

                                } else {
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_oneyear_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_oneyear_nav}</span></p>`;
                                }
                            }

                        }
                    },
                    {
                        className: "yearthree performance-monthly-data performance-quarterly-data",
                        "data": {
                            "performance":"performance",
                            "share_class":"share_class"
                        },
                        "render": function(data, type) {
                            if( (data.performance!==undefined) && (data.performance.monthly!==undefined) && (data.performance.quarterly!==undefined) ){

                                if((data.performance.monthly.threeyear_nav!==undefined) && (data.performance.monthly.threeyear_nav!=='N/A') && (data.performance.monthly.threeyear_nav!==null)){
                                    monthly_threeyear_nav=parseFloat(data.performance.monthly.threeyear_nav).toFixed(2);
                                } else {
                                    monthly_threeyear_nav=labelData.notApplicable;
                                }
                                if((data.performance.quarterly.threeyear_nav!==undefined) && (data.performance.quarterly.threeyear_nav!=='N/A') && (data.performance.quarterly.threeyear_nav!==null)){
                                    quarterly_threeyear_nav=parseFloat(data.performance.quarterly.threeyear_nav).toFixed(2);
                                } else {
                                    quarterly_threeyear_nav=labelData.notApplicable;
                                }

                                if((data.performance.monthly.threeyear_mop!==undefined) && (data.performance.monthly.threeyear_mop!=='N/A') && (data.performance.monthly.threeyear_mop!==null)){
                                    monthly_threeyear_mop=parseFloat(data.performance.monthly.threeyear_mop).toFixed(2);
                                }else {
                                    monthly_threeyear_mop=labelData.notApplicable;
                                }
                                if((data.performance.quarterly.threeyear_mop!==undefined) && (data.performance.quarterly.threeyear_mop!=='N/A') && (data.performance.quarterly.threeyear_mop!==null)){
                                    quarterly_threeyear_mop=parseFloat(data.performance.quarterly.threeyear_mop).toFixed(2);
                                }else {
                                    quarterly_threeyear_mop=labelData.notApplicable;
                                }
                            } else {
                                monthly_threeyear_nav=labelData.notApplicable;
                                quarterly_threeyear_nav=labelData.notApplicable;

                                monthly_threeyear_mop=labelData.notApplicable;
                                quarterly_threeyear_mop=labelData.notApplicable;
                            }

                            if(type==='sort'){
                                if(currentSelectedOptionforSort == 'quarterend'){
                                    return quarterly_threeyear_nav;
                                }else{
                                     return monthly_threeyear_nav;
                                }
                            }

                            if(selectUserType=='FP') {
                                if( (data.share_class=='A') || (data.share_class=='C') ){
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_threeyear_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_threeyear_nav}</span></p>
                                    <p class="ytdSpace"></p>
                                    <p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_threeyear_mop}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_threeyear_mop}</span></p>`;

                                } else {
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_threeyear_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_threeyear_nav}</span></p>`;
                                }
                            } else {
                                if( (data.share_class=='A') || (data.share_class=='C') ){
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_threeyear_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_threeyear_nav}</span></p>
                                    <p class="ytdSpace"></p>
                                    <p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_threeyear_mop}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_threeyear_mop}</span></p>`;

                                } else {
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_threeyear_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_threeyear_nav}</span></p>`;
                                }

                            }
                        }
                    },
                    {
                        className: "yearfive performance-monthly-data performance-quarterly-data",
                        "data": {
                            "performance":"performance",
                            "share_class":"share_class"
                        },
                        "render": function(data, type) {
                            if( (data.performance!==undefined) && (data.performance.monthly!==undefined) && (data.performance.quarterly!==undefined) ){

                                if((data.performance.monthly.fiveyear_nav!==undefined) && (data.performance.monthly.fiveyear_nav!=='N/A') && (data.performance.monthly.fiveyear_nav!==null)){
                                    monthly_fiveyear_nav=parseFloat(data.performance.monthly.fiveyear_nav).toFixed(2);
                                } else {
                                    monthly_fiveyear_nav=labelData.notApplicable;
                                }
                                if((data.performance.quarterly.fiveyear_nav!==undefined) && (data.performance.quarterly.fiveyear_nav!=='N/A') && (data.performance.quarterly.fiveyear_nav!==null)){
                                    quarterly_fiveyear_nav=parseFloat(data.performance.quarterly.fiveyear_nav).toFixed(2);
                                } else {
                                    quarterly_fiveyear_nav=labelData.notApplicable;
                                }

                                if((data.performance.monthly.fiveyear_mop!==undefined) && (data.performance.monthly.fiveyear_mop!=='N/A') && (data.performance.monthly.fiveyear_mop!==null)){
                                    monthly_fiveyear_mop=parseFloat(data.performance.monthly.fiveyear_mop).toFixed(2);
                                }else {
                                    monthly_fiveyear_mop=labelData.notApplicable;
                                }
                                if((data.performance.quarterly.fiveyear_mop!==undefined) && (data.performance.quarterly.fiveyear_mop!=='N/A') && (data.performance.quarterly.fiveyear_mop!==null)){
                                    quarterly_fiveyear_mop=parseFloat(data.performance.quarterly.fiveyear_mop).toFixed(2);
                                }else {
                                    quarterly_fiveyear_mop=labelData.notApplicable;
                                }
                            } else {
                                monthly_fiveyear_nav=labelData.notApplicable;
                                quarterly_fiveyear_nav=labelData.notApplicable;

                                monthly_fiveyear_mop=labelData.notApplicable;
                                quarterly_fiveyear_mop=labelData.notApplicable;
                            }

                            if(type==='sort'){
                                if(currentSelectedOptionforSort == 'quarterend'){
                                    return quarterly_fiveyear_nav;
                                }else{
                                     return monthly_fiveyear_nav;
                                }
                            }

                            if(selectUserType=='FP') {
                                if( (data.share_class=='A') || (data.share_class=='C') ){
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_fiveyear_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_fiveyear_nav}</span></p>
                                    <p class="ytdSpace"></p>
                                    <p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_fiveyear_mop}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_fiveyear_mop}</span></p>`;

                                } else {
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_fiveyear_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_fiveyear_nav}</span></p>`;
                                }
                            } else {
                                 if( (data.share_class=='A') || (data.share_class=='C') ){
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_fiveyear_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_fiveyear_nav}</span></p>
                                    <p class="ytdSpace"></p>
                                    <p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_fiveyear_mop}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_fiveyear_mop}</span></p>`;

                                } else {
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_fiveyear_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_fiveyear_nav}</span></p>`;
                                }

                            }
                        }
                    },
                    {
                        className: "yearten performance-monthly-data performance-quarterly-data",
                        "data": {
                            "performance":"performance",
                            "share_class":"share_class"
                        },
                        "render": function(data, type) {
                            if( (data.performance!==undefined) && (data.performance.monthly!==undefined) && (data.performance.quarterly!==undefined) ){


                                if((data.performance.monthly.tenyear_nav!==undefined) && (data.performance.monthly.tenyear_nav!=='N/A') && (data.performance.monthly.tenyear_nav!==null)){
                                    monthly_tenyear_nav=parseFloat(data.performance.monthly.tenyear_nav).toFixed(2);
                                } else {
                                    monthly_tenyear_nav=labelData.notApplicable;
                                }
                                if((data.performance.quarterly.tenyear_nav!==undefined) && (data.performance.quarterly.tenyear_nav!=='N/A') && (data.performance.quarterly.tenyear_nav!==null)){
                                    quarterly_tenyear_nav=parseFloat(data.performance.quarterly.tenyear_nav).toFixed(2);
                                } else {
                                    quarterly_tenyear_nav=labelData.notApplicable;
                                }

                                if((data.performance.monthly.tenyear_mop!==undefined) && (data.performance.monthly.tenyear_mop!=='N/A') && (data.performance.monthly.tenyear_mop!==null)){
                                    monthly_tenyear_mop=parseFloat(data.performance.monthly.tenyear_mop).toFixed(2);
                                }else {
                                    monthly_tenyear_mop=labelData.notApplicable;
                                }
                                if((data.performance.quarterly.tenyear_mop!==undefined) && (data.performance.quarterly.tenyear_mop!=='N/A') && (data.performance.quarterly.tenyear_mop!==null)){
                                    quarterly_tenyear_mop=parseFloat(data.performance.quarterly.tenyear_mop).toFixed(2);
                                }else {
                                    quarterly_tenyear_mop=labelData.notApplicable;
                                }
                            } else {
                                monthly_tenyear_nav=labelData.notApplicable;
                                quarterly_tenyear_nav=labelData.notApplicable;

                                monthly_tenyear_mop=labelData.notApplicable;
                                quarterly_tenyear_mop=labelData.notApplicable;
                            }

                            if(type==='sort'){
                                if(currentSelectedOptionforSort == 'quarterend'){
                                    return quarterly_tenyear_nav;
                                }else{
                                     return monthly_tenyear_nav;
                                }
                            }

                            if(selectUserType=='FP') {
                                if( (data.share_class=='A') || (data.share_class=='C') ){
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_tenyear_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_tenyear_nav}</span></p>
                                    <p class="ytdSpace"></p>
                                    <p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_tenyear_mop}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_tenyear_mop}</span></p>`;

                                } else {
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_tenyear_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_tenyear_nav}</span></p>`;
                                }
                            } else {
                                if( (data.share_class=='A') || (data.share_class=='C') ){
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_tenyear_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_tenyear_nav}</span></p>
                                    <p class="ytdSpace"></p>
                                    <p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_tenyear_mop}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_tenyear_mop}</span></p>`;

                                } else {
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_tenyear_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_tenyear_nav}</span></p>`;
                                }

                            }

                        }
                    },
                    {
                        className: "inception performance-monthly-data performance-quarterly-data",
                        "data": {
                            "performance":"performance",
                            "share_class":"share_class"
                        },
                        "render": function(data, type) {
                            if( (data.performance!==undefined) && (data.performance.monthly!==undefined) && (data.performance.quarterly!==undefined) ){

                                if((data.performance.monthly.since_inception_nav!==undefined) && (data.performance.monthly.since_inception_nav!=='N/A') && (data.performance.monthly.since_inception_nav!==null)){
                                    monthly_since_inception_nav=parseFloat(data.performance.monthly.since_inception_nav).toFixed(2);
                                } else {
                                    monthly_since_inception_nav=labelData.notApplicable;
                                }

                                if((data.performance.monthly.since_inception_mop!==undefined) && (data.performance.monthly.since_inception_mop!=='N/A') && (data.performance.monthly.since_inception_mop!==null)){
                                    monthly_since_inception_mop=parseFloat(data.performance.monthly.since_inception_mop).toFixed(2);
                                } else {
                                    monthly_since_inception_mop=labelData.notApplicable;
                                }

                                if((data.performance.quarterly.since_inception_nav!==undefined) && (data.performance.quarterly.since_inception_nav!=='N/A') && (data.performance.quarterly.since_inception_nav!==null)){
                                    quarterly_since_inception_nav=parseFloat(data.performance.quarterly.since_inception_nav).toFixed(2);
                                } else {
                                    quarterly_since_inception_nav=labelData.notApplicable;
                                }

                                if((data.performance.quarterly.since_inception_mop!==undefined) && (data.performance.quarterly.since_inception_mop!=='N/A') && (data.performance.quarterly.since_inception_mop!==null)){
                                    quarterly_since_inception_mop=parseFloat(data.performance.quarterly.since_inception_mop).toFixed(2);
                                } else {
                                    quarterly_since_inception_mop=labelData.notApplicable;
                                }

                            } else {
                                monthly_since_inception_nav=labelData.notApplicable;
                                quarterly_since_inception_nav=labelData.notApplicable;

                                monthly_since_inception_mop=labelData.notApplicable;
                                quarterly_since_inception_mop=labelData.notApplicable;
                            }

                            if(type==='sort'){
                                if(currentSelectedOptionforSort == 'quarterend'){
                                    return quarterly_since_inception_nav;
                                }else{
                                     return monthly_since_inception_nav;
                                }
                            }

                            if(selectUserType=='FP') {
                                if( (data.share_class=='A') || (data.share_class=='C') ){
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_since_inception_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_since_inception_nav}</span></p>
                                    <p class="ytdSpace"></p>
                                    <p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_since_inception_mop}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_since_inception_mop}</span></p>`;

                                } else {
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_since_inception_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_since_inception_nav}</span></p>`;
                                }
                            } else {
                                if( (data.share_class=='A') || (data.share_class=='C') ){
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_since_inception_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_since_inception_nav}</span></p>
                                    <p class="ytdSpace"></p>
                                    <p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_since_inception_mop}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_since_inception_mop}</span></p>`;

                                } else {
                                    return `<p class="performance-monthly-data performance-quarterly-data">
                                    <span class="performance-monthly-data">${monthly_since_inception_nav}</span>
                                    <span class="performance-quarterly-data hidden">${quarterly_since_inception_nav}</span></p>`;
                                }

                            }
                        }
                    },
                    {
                        className: "inceptdate performance-monthly-data performance-quarterly-data",
                        "data": "inception_date",
                        "render": function(data) {
                            if( (data!==undefined) && (data!==null)){
                                inception_date=data;
                            } else {
                                inception_date=labelData.notApplicable;
                            }
                            return `<p><span class="performance-monthly-data">${inception_date}</span><span class="performance-quarterly-data hidden">${inception_date}</span></p>`
                        }
                    },
                    {
                        className: "gross performance-monthly-data performance-quarterly-data",
                        "data": "gross_exp_ratio",
                        "render": function(data) {
                            if( (data!==undefined) && (data!==null) && (data!=='N/A')){
                                gross_exp_ratio=parseFloat(data).toFixed(2)+'%';
                            } else {
                                gross_exp_ratio=labelData.notApplicable;
                            }
                            return `<p><span class="performance-monthly-data">${gross_exp_ratio}</span><span class="performance-quarterly-data hidden">${gross_exp_ratio}</span></p>`
                        }

                    },
                    {
                        className: "net performance-monthly-data performance-quarterly-data",
                        "data": "net_expense_ratio",
                        "render": function(data) {
                            if( (data!==undefined) && (data!==null) && (data!=='N/A')){
                                net_expense_ratio=parseFloat(data).toFixed(2)+'%';
                            } else {
                                net_expense_ratio=labelData.notApplicable;
                            }
                            return `<p><span class="performance-monthly-data">${net_expense_ratio}</span><span class="performance-quarterly-data hidden">${net_expense_ratio}</span></p>`
                        }

                    },
                    {
                        className: "risk performance-monthly-data performance-quarterly-data",
                        "data": "risk",
                        "render": function(data) {
                            if (data!==undefined) {

                                var riskvalue = 0;
                                var riskfieldTemplate = '';
                                if (data == "Preservation of Capital") {
                                    riskvalue = 1;
                                } else if (data == "Conservative") {
                                    riskvalue = 2;
                                } else if (data == "Moderately Conservative") {
                                    riskvalue = 3;
                                } else if (data == "Moderate") {
                                    riskvalue = 4;
                                } else if (data == "Moderately Aggressive") {
                                    riskvalue = 5;
                                } else if (data == "Aggressive") {
                                    riskvalue = 6;
                                } else if (data == "Very Aggressive") {
                                    riskvalue = 7;
                                }else {
                                    riskvalue = 0;
                                }

                                for (let k = 1; k <= 7; k++) {
                                    if (k <= riskvalue) {
                                        riskfieldTemplate = riskfieldTemplate + ' <span class="risk-field active"></span>'
                                    } else {
                                        riskfieldTemplate = riskfieldTemplate + '<span class="risk-field"></span>'
                                    }
                                }

                                return `<p class="fund-content-header">${riskfieldTemplate}</p><p class="risk-field-text">${data}</p>`

                            } else {
                                return ` `
                            }
                        }

                    },
                    {
                        className: "ratingoverall",
                        "data": "morningStarRating",
                        "render": function(data, type) {

                            if(type==='display' || type==='filter'){
                                if (data!==undefined) {
                                    var outOfRatings = data.morning_stargroup_overall;
                                    var ratingvalue = data.rating;
                                    if( ((ratingvalue!==undefined) && (ratingvalue!==null) && (ratingvalue!=='N/A') && (ratingvalue!=='0')) ){
                                        var stariconTemplate = '';
                                        for (var i = 1; i <= 5; i++) {
                                            if (i <= ratingvalue) {
                                                stariconTemplate = stariconTemplate + '<i class="fa fa-star fa-lg active"></i>'
                                            } else {
                                                stariconTemplate = stariconTemplate + '<i class="fa fa-star fa-lg"></i>'
                                            }
                                        }
                                        if((outOfRatings!==undefined) && (outOfRatings!==null) && (outOfRatings!=='N/A') && (outOfRatings!=='0')) {
                                            var ofR = `<div class="sr-only">${ratingvalue} out of 5 stars</div><div class="rating-outof-overall">${labelData.out} ${labelData.of} ${outOfRatings} ${labelData.funds}</div>`;
                                        } else {
                                            var ofR = `<div></div>`;
                                        }
                                        return `<div class="overall-rating"><span>${stariconTemplate}</span>${ofR}</div>`
                                    } else {
                                        return `<div class="overall-rating"><span>N/A<span class="rating-star-icon-null"></span></span></div>`
                                    }
                                } else {
                                    return `<div class="overall-rating"><span>N/A<span class="rating-star-icon-null"></span></span></div>`
                                }
                            }

                            //for type==sort
                            var ratingvalue = data.rating;
                            if( ((ratingvalue!==undefined) && (ratingvalue!==null) && (ratingvalue!=='N/A') && (ratingvalue!=='0')) ){
                                return ratingvalue;
                            }else{
                                return "ZZZ";
                            }

                        }
                    },
                    {
                        className: "ratingyearthree",
                        "data": "morningStarRating",
                        "render": function(data, type) {
                            if(type==='display' || type==='filter'){
                                if (data!==undefined) {
                                    var outOfRatings = data.morning_starratingGroup_3Year;
                                    if( (outOfRatings!==undefined) && (outOfRatings!==null) && (outOfRatings!=='N/A') && (outOfRatings!=='0')){

                                        return `<div>${data.morning_starrating_3Year}<span class="rating-star-icon"><span class="sr-only">Star</span></span><div class="rating-outof">${labelData.out} ${labelData.of} ${outOfRatings} ${labelData.funds}</div></div>`

                                    } else {
                                        return `<div>N/A<span class="rating-star-icon-null"></span><div class="rating-outof"></div></div>`
                                    }
                                } else {
                                    return `<div>N/A<span class="rating-star-icon-null"></span></div>`
                                }
                            }

                            //for type==sort
                            var ratingYrthree = data.morning_starrating_3Year;
                            if( ((ratingYrthree!==undefined) && (ratingYrthree!==null) && (ratingYrthree!=='N/A') && (ratingYrthree!=='0')) ){
                                return ratingYrthree;
                            }else{
                                return "ZZZ";
                            }

                        }
                    },
                    {
                        className: "ratingyearfive",
                        "data": "morningStarRating",
                        "render": function(data, type) {
                            if(type==='display' || type==='filter'){
                                if (data!==undefined) {
                                    var outOfRatings = data.morning_starratingGroup5_Year;
                                    if( (outOfRatings!==undefined) && (outOfRatings!==null) && (outOfRatings!=='N/A') && (outOfRatings!=='0')){

                                        return `<div>${data.morning_starrating_5Year}<span class="rating-star-icon"><span class="sr-only">Star</span></span><div class="rating-outof">${labelData.out} ${labelData.of} ${outOfRatings} ${labelData.funds}</div></div>`

                                    } else {
                                        return `<div>N/A<span class="rating-star-icon-null"></span><div class="rating-outof"></div></div>`
                                    }
                                } else {
                                    return `<div>N/A<span class="rating-star-icon-null"></span></div>`
                                }
                            }
                            //for type==sort
                            var ratingYrfive = data.morning_starrating_5Year;
                            if( ((ratingYrfive!==undefined) && (ratingYrfive!==null) && (ratingYrfive!=='N/A') && (ratingYrfive!=='0')) ){
                                return ratingYrfive;
                            }else{
                                return "ZZZ";
                            }
                        }
                    },
                    {
                        className: "ratingyearten",
                        "data": "morningStarRating",
                        "render": function(data, type) {
                            if(type==='display' || type==='filter'){
                                if (data!==undefined) {
                                    var outOfRatings = data.morning_starratingGroup_10Year;
                                    if( (outOfRatings!==undefined) && (outOfRatings!==null) && (outOfRatings!=='N/A') && (outOfRatings!=='0')){

                                        return `<div>${data.morning_starrating_10Year}<span class="rating-star-icon"><span class="sr-only">Star</span></span><div class="rating-outof">${labelData.out} ${labelData.of} ${outOfRatings} ${labelData.funds}</div></div>`

                                    } else {
                                        return `<div>N/A<span class="rating-star-icon-null"></span><div class="rating-outof"></div></div>`
                                    }
                                } else {
                                    return `<div>N/A<span class="rating-star-icon-null"></span></div>`
                                }
                            }
                            //for type==sort
                            var ratingYrten = data.morning_starrating_10Year;
                            if( ((ratingYrten!==undefined) && (ratingYrten!==null) && (ratingYrten!=='N/A') && (ratingYrten!=='0')) ){
                                return ratingYrten;
                            }else{
                                return "ZZZ";
                            }
                        }
                    },
                    {
                        className: "ratingcategory",
                        "data": "category",
                        "render": function(data) {
                            if( (data!==undefined) && (data!==null) )    return data;    else return labelData.notApplicable;
                        }
                    },
                    {
                        className: "ratingfundsheet",
                        "data":{
                            "advisorFactsheetUrl": "advisorFactsheetUrl",
                            "memberFactsheetUrl": "memberFactsheetUrl"
                        },
                        "render": function(data) {

                            if(selectUserType=='FP'){
                                if( (data.advisorFactsheetUrl!=undefined) && (data.advisorFactsheetUrl!=null) && (data.advisorFactsheetUrl!='N/A') && (data.advisorFactsheetUrl!='') ){
                                    var replaceUrl = data.advisorFactsheetUrl;
                                } else {
                                    var replaceUrl = '';
                                }
                            }else{
                                if( (data.memberFactsheetUrl!=undefined) && (data.memberFactsheetUrl!=null) && (data.memberFactsheetUrl!='N/A') && (data.memberFactsheetUrl!='') ){
                                    var replaceUrl = data.memberFactsheetUrl;
                                } else {
                                    var replaceUrl = '';
                                }
                            }

                            if(replaceUrl!=''){
                                return `<a class="fundFactSheet" href=${replaceUrl} aria-label= "${data.fundName + ' Opens PDF in new window'}" target="_blank"><span>${labelData.download}</span></a>`

                            } else {
                                return `<p class="fundFactSheet-null">${labelData.notApplicable}</p>`
                            }
                        }
                    },
                    {
                        className: "prevyr performance-annual-data",
                        "data": "performance",
                        "render": function(data, type) {
                            if(type==='display' || type==='filter'){
                                if( (data!==undefined) && (data.annual!==undefined) && (data.annual.prev_year_performance!==undefined) ){

                                    if( ((data.annual.prev_year_performance!=='N/A') && (data.annual.prev_year_performance!==null)) ){
                                        prev_year_performance=parseFloat(data.annual.prev_year_performance).toFixed(2);
                                    }else {
                                        prev_year_performance=labelData.notApplicable;
                                    }

                                } else {
                                    prev_year_performance=labelData.notApplicable;
                                }
                                return `<p><span class="prev-val performance-annual-data">${prev_year_performance}</span></p>`
                            }
                            //for type==sort
                            prev_year_navData=parseFloat(data.annual.prev_year_performance).toFixed(2);
                            if(((prev_year_navData <= 0) || (prev_year_navData >= 0))){
                                return prev_year_navData;
                            }else{
                                return "ZZZ";
                            }
                        }
                    },
                    {
                        className: "prevyrminus1 performance-annual-data",
                        "data": "performance",
                        "render": function(data, type) {
                            if(type==='display' || type==='filter'){
                            if( (data!==undefined) && (data.annual!==undefined) && (data.annual.prev_minus1_performance!==undefined) ){

                                if( ((data.annual.prev_minus1_performance!=='N/A') && (data.annual.prev_minus1_performance!==null)) ){
                                    prev_minus1_performance=parseFloat(data.annual.prev_minus1_performance).toFixed(2);
                                }else {
                                    prev_minus1_performance=labelData.notApplicable;
                                }

                            } else {
                                prev_minus1_performance=labelData.notApplicable;
                            }
                            return `<p><span class="prev-val performance-annual-data">${prev_minus1_performance}</span></p>`
                        }
                        //for type==sort
                        prev_minus1_navData=parseFloat(data.annual.prev_minus1_performance).toFixed(2);
                        if(((prev_minus1_navData <= 0) || (prev_minus1_navData >= 0))){
                            return prev_minus1_navData;
                        }else{
                            return "ZZZ";
                        }
                        }
                    },
                    {
                        className: "prevyrminus2 performance-annual-data",
                        "data": "performance",
                        "render": function(data, type) {
                            if(type==='display' || type==='filter'){
                            if( (data!==undefined) && (data.annual!==undefined) && (data.annual.prev_minus2_performance!==undefined) ){

                                if( ((data.annual.prev_minus2_performance!=='N/A') && (data.annual.prev_minus2_performance!==null)) ){
                                    prev_minus2_performance=parseFloat(data.annual.prev_minus2_performance).toFixed(2);
                                }else {
                                    prev_minus2_performance=labelData.notApplicable;
                                }

                            } else {
                                prev_minus2_performance=labelData.notApplicable;
                            }
                            return `<p><span class="prev-val performance-annual-data">${prev_minus2_performance}</span></p>`
                        }
                        //for type==sort
                        prev_minus2_navData=parseFloat(data.annual.prev_minus2_performance).toFixed(2);
                        if(((prev_minus2_navData <= 0) || (prev_minus2_navData >= 0))){
                            return prev_minus2_navData;
                        }else{
                            return "ZZZ";
                        }
                        }
                    },
                    {
                        className: "prevyrminus3 performance-annual-data",
                        "data": "performance",
                        "render": function(data, type) {
                            if(type==='display' || type==='filter'){
                            if( (data!==undefined) && (data.annual!==undefined) && (data.annual.prev_minus3_performance!==undefined) ){

                                if( ((data.annual.prev_minus3_performance!=='N/A') && (data.annual.prev_minus3_performance!==null)) ){
                                    prev_minus3_performance=parseFloat(data.annual.prev_minus3_performance).toFixed(2);
                                }else {
                                    prev_minus3_performance=labelData.notApplicable;
                                }

                            } else {
                                prev_minus3_performance=labelData.notApplicable;
                            }
                            return `<p><span class="prev-val performance-annual-data">${prev_minus3_performance}</span></p>`
                        }
                        //for type==sort
                        prev_minus3_navData=parseFloat(data.annual.prev_minus3_performance).toFixed(2);
                        if(((prev_minus3_navData <= 0) || (prev_minus3_navData >= 0))){
                            return prev_minus3_navData;
                        }else{
                            return "ZZZ";
                        }
                        }
                    },
                    {
                        className: "prevyrminus4 performance-annual-data",
                        "data": "performance",
                        "render": function(data, type) {
                            if(type==='display' || type==='filter'){
                            if( (data!==undefined) && (data.annual!==undefined) && (data.annual.prev_minus4_performance!==undefined) ){

                                if( ((data.annual.prev_minus4_performance!=='N/A') && (data.annual.prev_minus4_performance!==null)) ){
                                    prev_minus4_performance=parseFloat(data.annual.prev_minus4_performance).toFixed(2);
                                }else {
                                    prev_minus4_performance=labelData.notApplicable;
                                }

                            } else {
                                prev_minus4_performance=labelData.notApplicable;
                            }
                            return `<p><span class="prev-val performance-annual-data">${prev_minus4_performance}</span></p>`
                        }
                        //for type==sort
                        prev_minus4_navData=parseFloat(data.annual.prev_minus4_performance).toFixed(2);
                        if(((prev_minus4_navData <= 0) || (prev_minus4_navData >= 0))){
                            return prev_minus4_navData;
                        }else{
                            return "ZZZ";
                        }
                        }
                    },
                    {
                        className: "prevyrminus5 performance-annual-data",
                        "data": "performance",
                        "render": function(data, type) {
                            if(type==='display' || type==='filter'){
                            if( (data!==undefined) && (data.annual!==undefined) && (data.annual.prev_minus5_performance!==undefined) ){

                                if( ((data.annual.prev_minus5_performance!=='N/A') && (data.annual.prev_minus5_performance!==null)) ){
                                    prev_minus5_performance=parseFloat(data.annual.prev_minus5_performance).toFixed(2);
                                }else {
                                    prev_minus5_performance=labelData.notApplicable;
                                }

                            } else {
                                prev_minus5_performance=labelData.notApplicable;
                            }
                            return `<p><span class="prev-val performance-annual-data">${prev_minus5_performance}</span></p>`
                        }
                        //for type==sort
                        prev_minus5_navData=parseFloat(data.annual.prev_minus5_performance).toFixed(2);
                        if(((prev_minus5_navData <= 0) || (prev_minus5_navData >= 0))){
                            return prev_minus5_navData;
                        }else{
                            return "ZZZ";
                        }
                        }
                    },
                    {
                        className: "prevyrminus6 performance-annual-data",
                        "data": "performance",
                        "render": function(data, type) {
                            if(type==='display' || type==='filter'){
                            if( (data!==undefined) && (data.annual!==undefined) && (data.annual.prev_minus6_performance!==undefined) ){

                                if( ((data.annual.prev_minus6_performance!=='N/A') && (data.annual.prev_minus6_performance!==null)) ){
                                    prev_minus6_performance=parseFloat(data.annual.prev_minus6_performance).toFixed(2);
                                }else {
                                    prev_minus6_performance=labelData.notApplicable;
                                }

                            } else {
                                prev_minus6_performance=labelData.notApplicable;
                            }
                            return `<p><span class="prev-val performance-annual-data">${prev_minus6_performance}</span></p>`
                        }
                        //for type==sort
                        prev_minus6_navData=parseFloat(data.annual.prev_minus6_performance).toFixed(2);
                        if(((prev_minus6_navData <= 0) || (prev_minus6_navData >= 0))){
                            return prev_minus6_navData;
                        }else{
                            return "ZZZ";
                        }
                        }
                    },
                    {
                        className: "prevyrminus7 performance-annual-data",
                        "data": "performance",
                        "render": function(data, type) {
                            if(type==='display' || type==='filter'){
                            if( (data!==undefined) && (data.annual!==undefined) && (data.annual.prev_minus7_performance!==undefined) ){

                                if( ((data.annual.prev_minus7_performance!=='N/A') && (data.annual.prev_minus7_performance!==null)) ){
                                    prev_minus7_performance=parseFloat(data.annual.prev_minus7_performance).toFixed(2);
                                }else {
                                    prev_minus7_performance=labelData.notApplicable;
                                }

                            } else {
                                prev_minus7_performance=labelData.notApplicable;
                            }
                            return `<p><span class="prev-val performance-annual-data">${prev_minus7_performance}</span></p>`
                        }
                        //for type==sort
                        prev_minus7_navData=parseFloat(data.annual.prev_minus7_performance).toFixed(2);
                        if(((prev_minus7_navData <= 0) || (prev_minus7_navData >= 0))){
                            return prev_minus7_navData;
                        }else{
                            return "ZZZ";
                        }
                        }
                    }
                ],
                columnDefs: [
                    { "orderable": false, "targets": [2, 9, 10, 11, 17, 18] },
                    { "searchable": false, "targets": [2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26] },
                    { "visible": false, "targets": [13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26] },
                    { type: 'numeric-empty-bottom', targets : [3, 4, 5, 6, 7, 8, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 26] }
                ],
                initComplete: function(settings, json) {
                   $(".dataTables_scrollBody").addClass("dragscroll");
                    var win = $(this);
                    /*if ($(window).width() < 1024) {
                        $(".dragscroll").css("overflow", "scroll");
                    } else {
                        $(".dragscroll").css("overflow", "hidden");
                    }*/
                    $(".fundscount").html($(".dataTables_info").text());
                    tableInfo();
                    loadScript(scrollJsPath);
                },
                "drawCallback": function( settings ) {

                    $('.fundnameLS').click(function () {
                        var pageUrl = $(this).data("url");
                        var pageData = $(this).data("scf").split('-');
                        var listingPageLS = [];
                        listingPageLS[0] = pageData[0];
                        listingPageLS[1] = pageData[1];
                        listingPageLS[2] = pageData[2];
                        localStorage.setItem("listingPageLS", JSON.stringify(listingPageLS));
                        $(this).attr('href',pageUrl);
                        window.location.href = pageUrl;
                    });

                    $(".fundnameLS").mousedown(function(ev) {
                        if ((ev.which ==3) || (ev.which == 2)) {
                            var pageData = $(this).data("scf").split('-');
                            var listingPageLS = [];
                            listingPageLS[0] = pageData[0];
                            listingPageLS[1] = pageData[1];
                            listingPageLS[2] = pageData[2];
                            localStorage.removeItem("listingPageLS");
                            localStorage.setItem("listingPageLS", JSON.stringify(listingPageLS));
                        }
                    });
                }

            });

            function numeric_sort(a, b, high) {
                var reg = /[+-]?((\d+(\.\d*)?)|\.\d+)([eE][+-]?[0-9]+)?/;
                a = a.match(reg);
                a = a !== null ? parseFloat(a[0]) : high;
                b = b.match(reg);
                b = b !== null ? parseFloat(b[0]) : high;
                return ((a < b) ? -1 : ((a > b) ? 1 : 0));
            }
            jQuery.extend( jQuery.fn.dataTableExt.oSort, {
                "numeric-empty-bottom-asc": function (a, b) {
                    return numeric_sort(a, b, Number.POSITIVE_INFINITY);
                },
                "numeric-empty-bottom-desc": function (a, b) {
                    return numeric_sort(a, b, Number.NEGATIVE_INFINITY) * -1;
                }
            } );

            /* Height adjustment for the data tables on page load */
            fundstable.columns.adjust().draw();

            // for radio buttons selected on table sort
            $("table.dataTable.fundslistTable thead th").click(function() {
                if ($(window).width() < 768) {
                    checkRadiobuttonselectedValueforMobile();
                } else {
                    checkRadiobuttonselectedValue();
                }
            });

            // set margin for fixed tabs dynmically
            if ($(window).width() < 768) {
                $('.fixedTabs').css("margin-left", "0");
            } else {
                calculateFundscountWidth();
            }

            function calculateFundscountWidth() {
                var fundscountWidth = $(".fundscount-width").outerWidth();
                $('.fixedTabs').css("margin-left", fundscountWidth);
            }

            // Tabs switing between performance and morning star rating
            $(".nav-tabs .fund-nav-link").on("click keypress", function (e) {
                e.preventDefault();
                $('.fund-nav-link.active').attr('tabindex', '-1');
                $(this).addClass('active').attr('tabindex', '0');
                $('.fund-nav-link').removeClass('active');
                $('.fixedTabs .nav-item').removeClass('current');
                $(this).parent().addClass('current');
                if (!$(this).data('isClicked')) {
                    var link = $(this);
                    // Your code on successful click

                    // Set the isClicked value and set a timer to reset in 3s
                    link.data('isClicked', true);
                    setTimeout(function() {
                    link.removeData('isClicked')
                    }, 3000);
                    if ($(this).text().toLowerCase() == "performance") {
                        fundstable.columns([13, 14, 15, 16, 17, 18]).visible(false);
                        fundstable.columns([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,19,20,21,22,23,24,25,26]).visible(true).draw();
                        $('.performance-scrollablesection').removeClass('hidden');
                        $('.rating-scrollablesection').addClass('hidden');
                        selectedTab = $(this).text().toLowerCase();
                        checkselectedView();
                        if($(window).width() > 768){
                             $('.rating-content-grid-view').addClass('hidden');
                             $('.performance-content-grid-view').addClass('hidden');
                        }
                        if($(window).width() >= 768){
                             calculateFundscountWidth();
                        }
                    } else {
                        fundstable.columns([3, 4, 5, 6, 7, 8, 9, 10, 11, 12,19,20,21,22,23,24,25,26]).visible(false);
                        fundstable.columns([0, 1, 2, 13, 14, 15, 16, 17, 18]).visible(true).draw();
                        $('.performance-scrollablesection').addClass('hidden');
                        $('.rating-scrollablesection').removeClass('hidden');
                        selectedTab = $(this).text().toLowerCase();
                        checkselectedView();
                        if($(window).width() > 768){
                            $('.rating-content-grid-view').addClass('hidden');
                            $('.performance-rating-list-content').removeClass('hidden');
                        }
                        if($(window).width() >= 768){
                            calculateFundscountWidth();
                        }
                    }

                    /* Height adjustment for the data tables on page load */
                    if ($(window).width() < 768) {
                        checkRadiobuttonselectedValueforMobile();
                    } else {
                        checkRadiobuttonselectedValue();
                    }
                    fundstable.columns.adjust().draw();
                    checkRadiobuttonselectedValue();

                } else {

                }
            });


            function checkRadiobuttonselectedValue() {
                 $("#monthend").on('keydown', function(e) {
                     if ((e.which == 37)) {
                         e.preventDefault();
                         $('#annualend').prop("checked", true).attr('aria-checked', 'true').focus();
                         $('.performance-quarterly-data').addClass('hidden');
                         $('.performance-monthly-data').addClass('hidden');
                         $('.performance-annual-data').removeClass('hidden');
                         $('#checkedAnual').addClass('checkedanuEND');
                         $("#monthend").attr('aria-checked', 'false');
                         $("#quarterend").attr('aria-checked', 'false');
                     }
                 });

                 if ($('.performance-quarterly-data').hasClass('hidden') && $('.performance-annual-data').hasClass('hidden')) {
                    $("#monthend").prop("checked",true);

                 } else if ($('.performance-monthly-data').hasClass('hidden') && $('.performance-annual-data').hasClass('hidden')) {
                    $("#quarterend").prop("checked",true);

                 } else if ($('.performance-monthly-data').hasClass('hidden') && $('.performance-quarterly-data').hasClass('hidden')) {
                     $('#annualend').prop('checked', true);
                 }

                 selectedChkboxid =$("input[type='radio'][name='annualreport']:checked").val();
                 if(selectedChkboxid!==undefined){
                    if ( selectedChkboxid == 'quarterend') {
                        $('.performance-monthly-data').addClass('hidden');
                        $('.performance-annual-data').addClass('hidden');
                        $('.performance-quarterly-data').removeClass('hidden');
                    } else if (selectedChkboxid == 'monthend') {
                        $('.performance-quarterly-data').addClass('hidden');
                        $('.performance-annual-data').addClass('hidden');
                        $('.performance-monthly-data').removeClass('hidden');
                    } else if (selectedChkboxid == 'annualend') {
                        $('.performance-quarterly-data').addClass('hidden');
                        $('.performance-monthly-data').addClass('hidden');
                        $('.performance-annual-data').removeClass('hidden');
                    }
                } else {
                    $("#monthend").prop("checked",true);
                }
            }

            function checkRadiobuttonselectedValueforMobile() {
                if ($('.performance-quarterly-data').hasClass('hidden') && $('.performance-annual-data').hasClass('hidden')) {
                    $("#monthend-mobile").prop("checked",true);

                 } else if ($('.performance-monthly-data').hasClass('hidden') && $('.performance-annual-data').hasClass('hidden')) {
                    $("#quarterend-mobile").prop("checked",true);

                 } else if ($('.performance-monthly-data').hasClass('hidden') && $('.performance-quarterly-data').hasClass('hidden')) {
                     $('#annualend-mobile').prop('checked', true);
                 }
                 selectedChkboxid =$("input[type='radio'][name='annualreport-mobile']:checked").val();
                 if(selectedChkboxid!==undefined){
                    if ( selectedChkboxid == 'quarterend') {
                        $('.performance-monthly-data').addClass('hidden');
                        $('.performance-annual-data').addClass('hidden');
                        $('.performance-quarterly-data').removeClass('hidden');
                    } else if (selectedChkboxid == 'monthend') {
                        $('.performance-quarterly-data').addClass('hidden');
                        $('.performance-annual-data').addClass('hidden');
                        $('.performance-monthly-data').removeClass('hidden');
                    } else if (selectedChkboxid == 'annualend') {
                        $('.performance-quarterly-data').addClass('hidden');
                        $('.performance-monthly-data').addClass('hidden');
                        $('.performance-annual-data').removeClass('hidden');
                    }
                } else {
                    $("#monthend-mobile").prop("checked",true);
                }
            }

            function checkselectedView() {
                if ($('.list-icon').hasClass('active')) {
                    $('.performance-rating-list-content').removeClass('hidden');
                    $('.performance-content-grid-view').addClass('hidden');
                    $('.rating-content-grid-view').addClass('hidden');
                } else if ($(window).width() < 768) {
                    //var selectedTab = $('.fund-nav-link.active').text().toLowerCase();
                    if (selectedTab == "morningstar rating") {
                        $('.performance-rating-list-content').addClass('hidden');
                        $('.performance-content-grid-view').addClass('hidden');
                        $('.rating-content-grid-view').removeClass('hidden');
                         $(".fund-nav-link").attr('aria-selected', 'false');
                        $(".fund-nav-link.active").attr('aria-selected', 'true');

                    } else if (selectedTab == "performance") {
                        $('.performance-content-grid-view').removeClass('hidden');
                        $('.rating-content-grid-view').addClass('hidden');
                        $(".fund-nav-link").attr('aria-selected', 'false');
                        $(".fund-nav-link.active").attr('aria-selected', 'true');
                    }
                }
            }
            $('#searchFunds').unbind();
           // Search mutual funds
            $('#searchFunds').bind('keyup', function(e) {
                const searchKey = this.value;
                var searchResult = [];

                if ($(window).width() < 768) {
                    searchResult = responseData.filter(item => {
                        return (item.ticker).toLowerCase().indexOf(searchKey.toLowerCase()) > -1 || (item.fundName).toLowerCase().indexOf(searchKey.toLowerCase()) > -1
                    })
                    createList(searchResult);
                }
                //fundstable.search(searchKey).draw();
                filterTableRowsByStringVal(searchKey);
                /* Height adjustment for the data tables on page load */
                fundstable.columns.adjust().draw();
                //$(".fundscount").html($(".dataTables_info").text());
                tableInfo();

                if ($(window).width() < 768) {
                    $('.fixedTabs').css("margin-left", "0");
                    checkRadiobuttonselectedValueforMobile();
                } else {
                    checkRadiobuttonselectedValue();
                    calculateFundscountWidth();
                }
            });

            /* Filter Panel: Functionality on Choosing Radio/Checkboxes with User: FP | Member*/
            if(selectUserType=='FP'){

                $.each($(".accordion input[name='shareclass']:checked"), function() {
                    resultResponse = filterExec('', '', responseData, this.value);
                    filterChange(this.value);
                });
                var filterRadioOptions = $('.accordion input[name="shareclass"]');
                filterRadioOptions.change(function() {
                    var selectedSC_value='';
                    filterRadioOptions.filter(':checked').each(function(i_item, selected){
                        selectedSC_value=this.value;
                        resultResponse = filterExec('', '', responseData, selectedSC_value);
                        filterChange(selectedSC_value);

                    });
                    selectedCBValues=[];
                    selectedCBFilters=[];
                    var filterCheckboxes = $('.accordion input[name="chkbox"]');
                    filterCheckboxes.filter(':checked').each(function(i_item, selected){
                        selectedCBValues.push(this.value);
                        selectedCBFilters.push(this.id);
                    });
                    resultResponse=filterExec(selectedCBValues, selectedCBFilters, responseData, selectedSC_value);

                });
                selectedCB1Values=[];
                selectedCB1Filters=[];
                $.each($(".accordion input[name='chkbox']:checked"), function() {
                    selectedSC=$('.accordion input[name="shareclass"]:checked').val();
                    selectedCB1Values.push(this.value);
                    selectedCB1Filters.push(this.id);
                    resultResponse=filterExec(selectedCB1Values, selectedCB1Filters, responseData, selectedSC);
                });

            } else {
                selectedCB1Values=[];
                selectedCB1Filters=[];
                $.each($(".accordion input[name='chkbox']:checked"), function() {
                    selectedCB1Values.push(this.value);
                    selectedCB1Filters.push(this.id);
                    resultResponse=filterExec(selectedCB1Values, selectedCB1Filters, responseData, '');
                });
                filterChange();
            }
            /*Manually filtering the table*/
            function filterTableRowsByStringVal(searchKey){
                $.fn.dataTable.ext.search.pop();
                var table = document.getElementById("fundslistTable");
                var tBody = table.getElementsByTagName('tbody')[0];
                var tr = tBody.getElementsByTagName('tr');
                var total = tr.length;
                var minRow=0;
                for (var i = 0; i < tr.length; i++) {
                     td = tr[i].getElementsByTagName("td")[0]; // for column one
                     td1 = tr[i].getElementsByTagName("td")[1]; // for column two
                    //* ADD columns here that you want you to filter to be used on */
                    if (td) {
                        var tdStr=td.innerHTML.match(/<a [^>]+>([^<]+)<\/a>/)[1];
                        var td1Str=td1.innerHTML.match(/<a [^>]+>([^<]+)<\/a>/)[1];
                        if ( (tdStr.toUpperCase().indexOf(searchKey.toUpperCase()) > -1) ||
                            (td1Str.toUpperCase().indexOf(searchKey.toUpperCase()) > -1) )  {
                            tr[i].style.display = "";
                            minRow = minRow+1;
                        } else {
                            tr[i].style.display = "none";
                        }
                    }
                }
                var finalVal=  'Showing: '+minRow+' of '+total +' funds';
                $('.dataTables_info').text(finalVal);
                $(".fundscount").html($(".dataTables_info").text());
                fundstable.draw();
            }
            /* Filter Panel: Functionality on Choosing Checkboxes*/
            function filterChange(shareclass){
                if(selectUserType=='FP'){
                    //shareclass='';
                    if(shareclass!==''){

                        shareclassSelected=shareclass;
                    }
                } else {
                    shareclassSelected='';
                }
               var filterCheckboxes = $('.accordion input[name="chkbox"]');
                filterCheckboxes.change(function() {
                    if(this.value === "all"){
                        if ($(this).is(':checked')) {
                        $(this).closest('.filter-list').find('.styled-checkbox').prop("checked", true);
                        }else{
                        $(this).closest('.filter-list').find('.styled-checkbox').prop("checked", false);
                        }
                    }else{
                        $(this).closest('div').find(".listCheck input[name=chkbox]").each(function () {
                            if (!$(this).is(':checked')) {
                                $(this).closest('div').find(".check-all").prop("checked", false);
                                return false;
                            } else {
                                $(this).closest('div').find(".check-all").prop("checked", true);
                            }
                        });
                    }

                    var search_values = [];
                    selectedFilters = [];

                    filterCheckboxes.filter(':checked').each(function(i_item, selected){
                        search_values.push(this.value);
                        selectedFilters.push(this.id);
                    });

                    //Check for mobile if any key search(grid view)
                   var resultResponse = filterExec(search_values, selectedFilters, responseData,shareclassSelected);


                    if ($('.content').hasClass('mobile-content-width')) {
                        var searchText = $("#searchFunds").val();
                        var searchResponse = [];
                        if(searchText){
                            searchResponse = resultResponse.filter(item => {
                            return (item.ticker).toLowerCase().indexOf(searchText.toLowerCase()) > -1 || (item.fund.fundname).toLowerCase().indexOf(searchText.toLowerCase()) > -1
                            })
                            createList(searchResponse);
                        }
                    }
                });

            };

            /* Filter Panel: Functionality Execution ans display filtered result*/
            function filterExec(search_values, selectedFilters, responseData, selectedSC_value) {
                searchShareClasslist=[];

                if(selectUserType=='FP'){
                    selectClassopt ='';
                    if(selectedSC_value!==''){
                        selectClassopt = selectedSC_value;
                    } else {
                        if ($('input[type=radio][name="shareclass"]').length) {
                            $.each($(".accordion input[name='shareclass']:checked"), function() {
                                selectClassopt = this.value;
                            });
                        }
                    }
                } else {
                    selectClassopt ='';
                }

                    if((selectClassopt!==undefined)&& (selectClassopt!=='')){
                        if(responseData.length > 0) {
                            responseData.forEach((ele) => {
                                const obj = ele.share_class;

                                if (obj == selectClassopt) {
                                    if(searchShareClasslist.indexOf(ele) < 0){
                                        searchShareClasslist.push(ele);
                                        }
                                }

                            });

                        }
                    } else {
                        searchShareClasslist=responseData;
                    }



                /* Filter category */
                categoryID=[];
                if (selectedFilters && selectedFilters.length > 0) {

                    selectedFilters.forEach((index) => {
                        cat=$("input[type=checkbox][name=chkbox][id='"+index+"']").data('category');
                        categoryID.push(cat);
                    });

                    selectedValues = search_values;
                    var result =  selectedValues.reduce(function(result, field, index) {
                        result[categoryID[index]] += field.concat(',');
                        return result;
                    }, {})
                    const filterInputData = Object.entries(result);

                    var filterInputArr=[];
                    filterInputArr=makeFilterArr(filterInputData);

                    var filterInputObj = {};
                    filterInputArr.forEach(function(data){
                        filterInputObj[data[0]] = data[1]
                    });

                    const getValue = value => (typeof value === 'string' ? value.toUpperCase() : value);

                    function filterData(array, filters) {

                        const filterKeys = Object.keys(filters);
                        return array.filter(item => { var fromFilterParent=item.filter;

                        // validates all filter criteria
                            return filterKeys.every(key => {
                                // ignores an empty filter
                                if (!filters[key].length) return true;

                                //return filters[key].find(filter => getValue(filter) === getValue(item.filter[key]));
                                return filters[key].find(filter => {

                                        if(fromFilterParent[key] != undefined && fromFilterParent[key].indexOf(";") > 0){
                                            if(fromFilterParent[key].includes(filter)) return true;
                                        } else {
                                            return filters[key].find(filter => getValue(filter) === getValue(fromFilterParent[key]));
                                        }

                                });
                            });
                        });
                    }
                    const filteredData = filterData(searchShareClasslist, filterInputObj);

                    redrawTable(filteredData);
                    createList(filteredData);


                } else {
                    redrawTable(searchShareClasslist);
                    createList(searchShareClasslist);

                }

                if ($(window).width() < 768) {
                    checkRadiobuttonselectedValueforMobile();
                } else {
                    checkRadiobuttonselectedValue();
                }
                fundstable.columns.adjust().draw();
                checkRadiobuttonselectedValue();

            }

            function redrawTable(data) {
                fundstable.clear();
                fundstable.rows.add(data);
                //fundstable.draw();

                fundstable.columns.adjust().draw();
                $(".fundscount").html($(".dataTables_info").text());
                tableInfo();

            }

            $('.footer-button').click(function () {
                var panelHeight = window.innerHeight-72;
                $(".panel").css("height", panelHeight).slideToggle("slow");
                $(".close-filter, .open-filter").toggleClass("hidden");
            });

            $('.to-top').click(function () {
                document.body.scrollTop = 0;
                document.documentElement.scrollTop = 0;
            });


            $('.arrow-nav-left').click(function() {
                $(".funds-list").toggleClass("active");
                $(".accordion").toggleClass("hidden");
                $(".arrow-nav-left").toggleClass("arrow-nav-right");
                $(".content").toggleClass("pc-content-width");
                if ($(this).hasClass('arrow-nav-right')) {
                    $(this).parent().children(".lblFilter").attr("aria-expanded", "false");
                    $(".filter-sidebar-collapse").animate({ width: '110px' }, "slow");
                    $('.filter-title').attr("aria-expanded", "false");
                } else {
                    $(this).parent().children(".lblFilter").attr("aria-expanded", "true")
                    $(".filter-sidebar-collapse").animate({ width: '200px' }, "slow");
                    $('.filter-title').attr("aria-expanded", "true");
                }
                filterCollapse();
            });
            $(".fund-title.lblFilter").on('keydown', function(ke) {
                var ke = ke.which;
                if (13 == ke || 32 == ke) {
                    $('.arrow-nav-left').click();
                }
                if (39 == ke) {
                    $(".funds-list").addClass("active");
                    $(".arrow-nav-left").removeClass("arrow-nav-right");
                    setTimeout(function() {
                        $(".accordion").removeClass("hidden");
                        $(".content").addClass("pc-content-width");
                    }, 500);
                    $(this).attr("aria-expanded", "true")
                    $(".filter-sidebar-collapse").animate({ width: '200px' }, "slow");
                    filterCollapse();
                }
                if (37 == ke) {
                    $(".funds-list").removeClass("active");
                    $(".arrow-nav-left").addClass("arrow-nav-right");
                    $(".accordion").addClass("hidden");
                    $(".content").removeClass("pc-content-width");
                    $(this).attr("aria-expanded", "false");
                    $(".filter-sidebar-collapse").animate({ width: '110px' }, "slow");
                    filterCollapse();
                }
            });

            function filterCollapse() {
                $($.fn.dataTable.tables( true ) ).css('width', '100%');
                $($.fn.dataTable.tables( true ) ).DataTable().columns.adjust().draw();
                if ($(window).width() < 768) {
                    checkRadiobuttonselectedValueforMobile();
                } else {
                    checkRadiobuttonselectedValue();
                    calculateFundscountWidth();
                    keyPressCollapsing();
                }
                popupAccessbility();
            }

           if ($(window).width() > 768) { //Desktop
            setTimeout(function(){
                $(".funds-list").toggleClass("active");
                $(".accordion").toggleClass("hidden");
                $(".arrow-nav-left").toggleClass("arrow-nav-right");
                $(".filter-sidebar-collapse").toggleClass("filter-width").fadeIn(500);
                //$(".filter-sidebar-collapse").animate({width: 'toggle'});
                $(".content").toggleClass("pc-content-width");
                $($.fn.dataTable.tables( true ) ).css('width', '100%');
                $($.fn.dataTable.tables( true ) ).DataTable().columns.adjust().draw();
                checkRadiobuttonselectedValue();
                calculateFundscountWidth();
                if ($('.fundslistTable th').hasClass("sorting_asc")) {
                    $('.sorting_asc').children('span').attr('aria-sort', 'ascending');
                } else if ($('.fundslistTable th').hasClass("sorting_desc")) {
                    $('.sorting_desc').children('span').attr('aria-sort', 'descending');
                }

                $('.fundslistTable th').removeAttr('tabindex rowspan colspan aria-controls aria-label aria-sort');
                $('td.ticker').attr('scope', 'row');
              },3000);
            }

            // filter radio buttons
           if ($(window).width() < 768) {
                $('#monthend-mobile').prop('checked', 'true');
            } else {
                $('#monthend').prop('checked', 'true');
            }

            function performanceOptionsLabel(radVal) {

            var annual_performance_as_of_date = responseData[0].annual_performance_as_of_date;
            var quarterly_performance_as_of_date = responseData[0].quarterly_performance_as_of_date;
            var monthly_performance_as_of_date = responseData[0].monthly_performance_as_of_date;

                if(radVal=='annualend'){
                    //radValText='Annual Average Annual total returns (%) as of '+annual_performance_as_of_date+' (last year end date)';
                    radValText=`${labelData.msgAnnualend} `+annual_performance_as_of_date;
                }  else if(radVal=='quarterend') {
                    //radValText='Quarter-End Average Annual total returns (%) as of '+quarterly_performance_as_of_date+' (last quarter end date)';
                    radValText=`${labelData.msgQuarterend} `+quarterly_performance_as_of_date;
                } else if(radVal=='monthend') {
                    //radValText='Month-End Average Annual total returns (%) as of '+monthly_performance_as_of_date+' (last month end date)';
                    radValText=`${labelData.msgMonthend} `+monthly_performance_as_of_date;
                } else {
                    radValText='';
                }
                return radValText;
            }

           $.each($("input[type='radio'][name='annualreport']:checked"), function() {

                var radVal = this.value;

                var displayLabel = document.getElementById('ptd');
                displayLabel.innerHTML= performanceOptionsLabel(radVal);

                if (this.value == 'quarterend') {
                    $('.performance-monthly-data').addClass('hidden');
                    $('.performance-annual-data').addClass('hidden');
                    $('.performance-quarterly-data').removeClass('hidden');
                } else if (this.value == 'monthend') {
                    $('.performance-quarterly-data').addClass('hidden');
                    $('.performance-annual-data').addClass('hidden');
                    $('.performance-monthly-data').removeClass('hidden');
                } else if (this.value == 'annualend') {
                    $('.performance-quarterly-data').addClass('hidden');
                    $('.performance-monthly-data').addClass('hidden');
                    $('.performance-annual-data').removeClass('hidden');
                }else {
                }
                $('#'+this.id+'').prop('checked', 'true');
            });

            $('.performance-scrollablesection input[type=radio][name=annualreport]').change(function () {
                $('#fundslistTable').DataTable().rows().invalidate();/*For sorting: read data from row source when chaning cell values*/
                var radVal = this.value;

                var displayLabel = document.getElementById('ptd');
                displayLabel.innerHTML= performanceOptionsLabel(radVal);
                calculateFundscountWidth();
                if (this.value == 'quarterend') {
                    currentSelectedOptionforSort='quarterend';
                    $('.performance-monthly-data').addClass('hidden');
                    $('.performance-annual-data').addClass('hidden');
                    $('.performance-quarterly-data').removeClass('hidden');
                    $('#checkedAnual').removeClass('checkedanuEND');
                    /*if(!$(".filter-sidebar-collapse").hasClass("filter-width")){
                        fundstable.columns.adjust().draw();
                    }else {
                        fundstable.columns.adjust().draw();
                    }*/
                    fundstable.columns.adjust().draw();
                    calculateFundscountWidth();
                    $("#monthend").attr('aria-checked', 'false');
                    $("#quarterend").prop("checked", true).attr('aria-checked', 'true');
                    $('#annualend').attr('aria-checked', 'false');
                } else if (this.value == 'monthend') {
                    currentSelectedOptionforSort='monthend';
                    $('.performance-quarterly-data').addClass('hidden');
                    $('.performance-annual-data').addClass('hidden');
                    $('.performance-monthly-data').removeClass('hidden');
                    $('#checkedAnual').removeClass('checkedanuEND');
                    fundstable.columns.adjust().draw();
                    calculateFundscountWidth();
                   $("#monthend").prop("checked", true).attr('aria-checked', 'true');
                   $("#quarterend").attr('aria-checked', 'false');
                   $('#annualend').attr('aria-checked', 'false');
                } else if (this.value == 'annualend') {
                    $('.performance-quarterly-data').addClass('hidden');
                    $('.performance-monthly-data').addClass('hidden');
                    $('.performance-annual-data').removeClass('hidden');
                    $('#checkedAnual').addClass('checkedanuEND');
                    //fundstable.columns([3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18]).visible(false);
                    fundstable.columns([0, 1, 2, 19, 20, 21, 22, 23, 24, 25, 26]).visible(true).draw();
                    if(!$(".filter-sidebar-collapse").hasClass("filter-width")){
                        fundstable.columns.adjust().draw();
                    }
                    calculateFundscountWidth();
                    $("#monthend").attr('aria-checked', 'false');
                    $("#quarterend").attr('aria-checked', 'false');
                    $('#annualend').prop("checked", true).attr('aria-checked', 'true');
                    $('#annualend').on('keydown', function(e) {
                        if ((e.which == 39)) {
                            e.preventDefault();
                            $('#monthend').prop("checked", true).focus().attr('aria-checked', 'true');;
                            $('.performance-quarterly-data').addClass('hidden');
                            $('.performance-annual-data').addClass('hidden');
                            $('.performance-monthly-data').removeClass('hidden');
                            $('#annualend').prop('checked', false).attr('aria-checked', 'false');
                            $('#checkedAnual').removeClass('checkedanuEND');
                        }
                    });
                }else {
                }
            });

            $.each($("input[type='radio'][name='annualreport-mobile']:checked"), function() {

                var radVal = this.value;

                var displayLabelMobile = document.getElementById('ptdM');
                displayLabelMobile.innerHTML= performanceOptionsLabel(radVal);

                if (this.value == 'quarterend') {
                    $('.performance-monthly-data').addClass('hidden');
                    $('.performance-annual-data').addClass('hidden');
                    $('.performance-quarterly-data').removeClass('hidden');
                } else if (this.value == 'monthend') {
                    $('.performance-quarterly-data').addClass('hidden');
                    $('.performance-annual-data').addClass('hidden');
                    $('.performance-monthly-data').removeClass('hidden');
                } else if (this.value == 'annualend') {
                    $('.performance-quarterly-data').addClass('hidden');
                    $('.performance-monthly-data').addClass('hidden');
                    $('.performance-annual-data').removeClass('hidden');
                }else {
                }
                $('#'+this.id+'').prop('checked', 'true');
            });

            $('.performance-scrollablesection input[type=radio][name=annualreport-mobile]').change(function () {

                var radVal = this.value;

                var displayLabelMobile = document.getElementById('ptdM');
                displayLabelMobile.innerHTML= performanceOptionsLabel(radVal);

                if (this.value == 'quarterend') {
                    $('.performance-monthly-data').addClass('hidden');
                    $('.performance-annual-data').addClass('hidden');
                    $('.performance-quarterly-data').removeClass('hidden');
                    $("#quarterend-mobile").prop("checked",true);
                } else if (this.value == 'monthend') {
                    $('.performance-quarterly-data').addClass('hidden');
                    $('.performance-annual-data').addClass('hidden');
                    $('.performance-monthly-data').removeClass('hidden');
                    $("#monthend-mobile").prop("checked",true);
                } else if (this.value == 'annualend') {
                    $('.performance-quarterly-data').addClass('hidden');
                    $('.performance-monthly-data').addClass('hidden');
                    $('.performance-annual-data').removeClass('hidden');

                    fundstable.columns([0, 1, 2, 19, 20, 21, 22, 23, 24, 25, 26]).visible(true).draw();
                    $('#annualend-mobile').prop('checked', 'true');
                }else {
                }
            });

            $(".toggle-icon").click(function() {
                if ($(this).hasClass('list-icon')) {
                    $('.grid-icon').removeClass("active");
                    $('.list-icon').addClass("active");
                    displayListView();
                } else {
                    $('.grid-icon').addClass("active");
                    $('.list-icon').removeClass("active");
                    displayGridView();
                }
            });

            function displayListView() {
                $('.performance-rating-list-content').removeClass('hidden');
                $('.performance-content-grid-view').addClass('hidden');
                $('.rating-content-grid-view').addClass('hidden');
            }

            function displayGridView() {

                var selectedTab = $('.fund-nav-link.active').text().toLowerCase();
                if (selectedTab == "performance") {
                    $('.performance-rating-list-content').addClass('hidden');
                    $('.performance-content-grid-view').removeClass('hidden');
                    $('.rating-content-grid-view').addClass('hidden');
                    $(".fund-nav-link").attr('aria-selected', 'false');
                    $(".fund-nav-link.active").attr('aria-selected', 'true');
                } else {
                    $('.performance-rating-list-content').addClass('hidden');
                    $('.performance-content-grid-view').addClass('hidden');
                    $('.rating-content-grid-view').removeClass('hidden');
                    $(".fund-nav-link").attr('aria-selected', 'false');
                    $(".fund-nav-link.active").attr('aria-selected', 'true');
                }
            }
            /* Width Adustment of table on window resize */

            function calculateWidth() {
                var win = $(this); //this = window
                if (win.width() < 768) {
                  fixedleftColumns = 1;
                  $('#monthend-mobile').prop('checked', 'true');
                  $('.sticky-footer').removeClass('hidden');
                  $('.content').addClass('mobile-content-width').removeClass('pc-content-width');
                  $('.filter-sidebar-collapse').addClass('hidden').removeClass('filter-width');
                  $('.mobile-radiobuttons-section').removeClass('hidden');
                  $('.pc-radiobuttons-section').addClass('hidden');
                  $('.filter-sidebar-collapse .accordion, .sticky-footer .accordion').remove();
                  $('.sticky-footer .panel').append(filterTemplate);
                }
                else {
                  fixedleftColumns = 3;
                  $('#monthend').prop('checked', 'true');
                  $(".funds-list").addClass("active");
                  $('.sticky-footer').addClass('hidden');
                  $('.content').removeClass('mobile-content-width').addClass('pc-content-width');
                  $('.filter-sidebar-collapse').removeClass('hidden').addClass('filter-width');
                  $('.mobile-radiobuttons-section').addClass('hidden');
                  $('.pc-radiobuttons-section').removeClass('hidden');
                  $('.sticky-footer .accordion, .filter-sidebar-collapse .accordion').remove();
                  $('.filter-sidebar-collapse').append(filterTemplate);
                }

                selectCheckboxes();
                //filterChange();

                return fixedleftColumns;
            }

            function selectCheckboxes(){
                if(selectedFilters.length > 0){
                    selectedFilters.forEach((filter) => {
                        $('.accordion input[name="chkbox"]').each(function(){
                          if(filter == this.id){
                            $(this).prop("checked", true);
                          }
                        });
                    })
                 }
            }

           /* $(window).on('resize', function() {
                fundstable.columns.adjust().draw();
                calculateWidth();
            });*/

            // create grid view for mobile
            function createList(result) {

                $(".performance-content-grid-view, .rating-content-grid-view").html('');

                for (let i = 0; i < result.length; i++) {

                    const data = result[i];
                    var riskvalue = 0;
                    var riskfieldTemplate = '';
                    if (data.risk == "Preservation of Capital") {
                        riskvalue = 1;
                    } else if (data.risk == "Conservative") {
                        riskvalue = 2;
                    } else if (data.risk == "Moderately Conservative") {
                        riskvalue = 3;
                    } else if (data.risk == "Moderate") {
                        riskvalue = 4;
                    } else if (data.risk == "Moderately Aggressive") {
                        riskvalue = 5;
                    } else if (data.risk == "Aggressive") {
                        riskvalue = 6;
                    } else if (data.risk == "Very Aggressive") {
                        riskvalue = 7;
                    }else {
                        riskvalue = 0;
                    }

                    for (let k = 1; k <= 7; k++) {
                        if (k <= riskvalue) {
                            riskfieldTemplate = riskfieldTemplate + ' <span class="risk-field active"></span>'
                        } else {
                            riskfieldTemplate = riskfieldTemplate + '<span class="risk-field"></span>'
                        }
                    }

                    // fund details page url
                    var ActiveFundPage = data.isActiveFundPage;
                    var detailUrl;

                    if (ActiveFundPage === "true") {
                        detailUrl = data.detailpageurl;

                    } else {
                        detailUrl = "#"
                    }
                    var fundName;
                    if(data.fundName!==undefined){
                        fundName=data.fundName;
                    } else {
                        fundName='';
                    }

                    var assetClass;
                    if(data.asset_class!==undefined){
                        assetClass=data.asset_class;
                    } else {
                        assetClass='';
                    }

                   var inceptiondate='';

                        if( (data.performance!==undefined) && (data.performance.monthly!==undefined) && (data.performance.quarterly!==undefined) ){

                                if((data.performance.monthly.ytd_nav!==undefined) && (data.performance.monthly.ytd_nav!=='N/A') && (data.performance.monthly.ytd_nav!==null)){ monthly_ytd_nav=parseFloat(data.performance.monthly.ytd_nav).toFixed(2); } else { monthly_ytd_nav=labelData.notApplicable; }
                                if((data.performance.quarterly.ytd_nav!==undefined) && (data.performance.quarterly.ytd_nav!=='N/A') && (data.performance.quarterly.ytd_nav!==null)){ quarterly_ytd_nav=parseFloat(data.performance.quarterly.ytd_nav).toFixed(2); } else { quarterly_ytd_nav=labelData.notApplicable; }
                                if((data.performance.monthly.ytd_mop!==undefined) && (data.performance.monthly.ytd_mop!=='N/A') && (data.performance.monthly.ytd_mop!==null)){ monthly_ytd_mop=parseFloat(data.performance.monthly.ytd_mop).toFixed(2); } else { monthly_ytd_mop=labelData.notApplicable; }
                                if((data.performance.quarterly.ytd_mop!==undefined) && (data.performance.quarterly.ytd_mop!=='N/A') && (data.performance.quarterly.ytd_mop!==null)){ quarterly_ytd_mop=parseFloat(data.performance.quarterly.ytd_mop).toFixed(2); }else { quarterly_ytd_mop=labelData.notApplicable; }

                                if((data.performance.monthly.oneyear_nav!==undefined) && (data.performance.monthly.oneyear_nav!=='N/A') && (data.performance.monthly.oneyear_nav!==null)){ monthly_oneyear_nav=parseFloat(data.performance.monthly.oneyear_nav).toFixed(2); } else { monthly_oneyear_nav=labelData.notApplicable; }
                                if((data.performance.quarterly.oneyear_nav!==undefined) && (data.performance.quarterly.oneyear_nav!=='N/A') && (data.performance.quarterly.oneyear_nav!==null)){ quarterly_oneyear_nav=parseFloat(data.performance.quarterly.oneyear_nav).toFixed(2); } else { quarterly_oneyear_nav=labelData.notApplicable; }
                                if((data.performance.quarterly.oneyear_nav!==undefined) && (data.performance.monthly.oneyear_mop!=='N/A') && (data.performance.monthly.oneyear_mop!==null)){ monthly_oneyear_mop=parseFloat(data.performance.monthly.oneyear_mop).toFixed(2); } else { monthly_oneyear_mop=labelData.notApplicable; }
                                if((data.performance.quarterly.oneyear_mop!==undefined) && (data.performance.quarterly.oneyear_mop!=='N/A') && (data.performance.quarterly.oneyear_mop!==null)){ quarterly_oneyear_mop=parseFloat(data.performance.quarterly.oneyear_mop).toFixed(2); }else { quarterly_oneyear_mop=labelData.notApplicable; }

                                if((data.performance.monthly.threeyear_nav!==undefined) && (data.performance.monthly.threeyear_nav!=='N/A') && (data.performance.monthly.threeyear_nav!==null)){ monthly_threeyear_nav=parseFloat(data.performance.monthly.threeyear_nav).toFixed(2); } else { monthly_threeyear_nav=labelData.notApplicable; }
                                if((data.performance.quarterly.threeyear_nav!==undefined) && (data.performance.quarterly.threeyear_nav!=='N/A') && (data.performance.quarterly.threeyear_nav!==null)){ quarterly_threeyear_nav=parseFloat(data.performance.quarterly.threeyear_nav).toFixed(2); } else { quarterly_threeyear_nav=labelData.notApplicable; }
                                if((data.performance.monthly.threeyear_mop!==undefined) && (data.performance.monthly.threeyear_mop!=='N/A') && (data.performance.monthly.threeyear_mop!==null)){ monthly_threeyear_mop=parseFloat(data.performance.monthly.threeyear_mop).toFixed(2); } else { monthly_threeyear_mop=labelData.notApplicable; }
                                if((data.performance.quarterly.threeyear_mop!==undefined) && (data.performance.quarterly.threeyear_mop!=='N/A') && (data.performance.quarterly.threeyear_mop!==null)){ quarterly_threeyear_mop=parseFloat(data.performance.quarterly.threeyear_mop).toFixed(2); }else { quarterly_threeyear_mop=labelData.notApplicable; }

                                if((data.performance.monthly.fiveyear_nav!==undefined) && (data.performance.monthly.fiveyear_nav!=='N/A') && (data.performance.monthly.fiveyear_nav!==null)){ monthly_fiveyear_nav=parseFloat(data.performance.monthly.fiveyear_nav).toFixed(2); } else { monthly_fiveyear_nav=labelData.notApplicable; }
                                if((data.performance.quarterly.fiveyear_nav!==undefined) && (data.performance.quarterly.fiveyear_nav!=='N/A') && (data.performance.quarterly.fiveyear_nav!==null)){ quarterly_fiveyear_nav=parseFloat(data.performance.quarterly.fiveyear_nav).toFixed(2); } else { quarterly_fiveyear_nav=labelData.notApplicable; }
                                if((data.performance.monthly.fiveyear_mop!==undefined) && (data.performance.monthly.fiveyear_mop!=='N/A') && (data.performance.monthly.fiveyear_mop!==null)){ monthly_fiveyear_mop=parseFloat(data.performance.monthly.fiveyear_mop).toFixed(2); } else { monthly_fiveyear_mop=labelData.notApplicable; }
                                if((data.performance.quarterly.fiveyear_mop!==undefined) && (data.performance.quarterly.fiveyear_mop!=='N/A') && (data.performance.quarterly.fiveyear_mop!==null)){ quarterly_fiveyear_mop=parseFloat(data.performance.quarterly.fiveyear_mop).toFixed(2); }else { quarterly_fiveyear_mop=labelData.notApplicable; }

                                if((data.performance.monthly.tenyear_nav!==undefined) && (data.performance.monthly.tenyear_nav!=='N/A') && (data.performance.monthly.tenyear_nav!==null)){ monthly_tenyear_nav=parseFloat(data.performance.monthly.tenyear_nav).toFixed(2); } else { monthly_tenyear_nav=labelData.notApplicable; }
                                if((data.performance.quarterly.tenyear_nav!==undefined) && (data.performance.quarterly.tenyear_nav!=='N/A') && (data.performance.quarterly.tenyear_nav!==null)){ quarterly_tenyear_nav=parseFloat(data.performance.quarterly.tenyear_nav).toFixed(2); } else { quarterly_tenyear_nav=labelData.notApplicable; }
                                if((data.performance.monthly.tenyear_mop!==undefined) && (data.performance.monthly.tenyear_mop!=='N/A') && (data.performance.monthly.tenyear_mop!==null)){ monthly_tenyear_mop=parseFloat(data.performance.monthly.tenyear_mop).toFixed(2); } else { monthly_tenyear_mop=labelData.notApplicable; }
                                if((data.performance.quarterly.tenyear_mop!==undefined) && (data.performance.quarterly.tenyear_mop!=='N/A') && (data.performance.quarterly.tenyear_mop!==null)){ quarterly_tenyear_mop=parseFloat(data.performance.quarterly.tenyear_mop).toFixed(2); }else { quarterly_tenyear_mop=labelData.notApplicable; }
                            } else {
                                monthly_ytd_nav=labelData.notApplicable;quarterly_ytd_nav=labelData.notApplicable;
                                monthly_ytd_mop=labelData.notApplicable;quarterly_ytd_mop=labelData.notApplicable;

                                monthly_oneyear_nav=labelData.notApplicable; quarterly_oneyear_nav=labelData.notApplicable;
                                monthly_oneyear_mop=labelData.notApplicable; quarterly_oneyear_mop=labelData.notApplicable;

                                monthly_threeyear_nav=labelData.notApplicable; quarterly_threeyear_nav=labelData.notApplicable;
                                monthly_threeyear_mop=labelData.notApplicable; quarterly_threeyear_mop=labelData.notApplicable;

                                monthly_fiveyear_nav=labelData.notApplicable; quarterly_fiveyear_nav=labelData.notApplicable;
                                monthly_fiveyear_mop=labelData.notApplicable; quarterly_fiveyear_mop=labelData.notApplicable;

                                monthly_tenyear_nav=labelData.notApplicable; quarterly_tenyear_nav=labelData.notApplicable;
                                monthly_tenyear_mop=labelData.notApplicable; quarterly_tenyear_mop=labelData.notApplicable;
                            }


                    if( (data.performance!==undefined) && (data.performance.monthly!==undefined) && (data.performance.quarterly!==undefined) ){

                        if((data.performance.monthly.since_inception_nav!==undefined) && (data.performance.monthly.since_inception_nav!=='N/A') && (data.performance.monthly.since_inception_nav!==null)){
                            monthly_since_inception_nav=parseFloat(data.performance.monthly.since_inception_nav).toFixed(2);
                        } else {
                            monthly_since_inception_nav=labelData.notApplicable;
                        }

                        if((data.performance.monthly.since_inception_mop!==undefined) && (data.performance.monthly.since_inception_mop!=='N/A') && (data.performance.monthly.since_inception_mop!==null)){
                            monthly_since_inception_mop=parseFloat(data.performance.monthly.since_inception_mop).toFixed(2);
                        } else {
                            monthly_since_inception_mop=labelData.notApplicable;
                        }

                        if((data.performance.quarterly.since_inception_nav!==undefined) && (data.performance.quarterly.since_inception_nav!=='N/A') && (data.performance.quarterly.since_inception_nav!==null)){
                            quarterly_since_inception_nav=parseFloat(data.performance.quarterly.since_inception_nav).toFixed(2);
                        } else {
                            quarterly_since_inception_nav=labelData.notApplicable;
                        }

                        if((data.performance.quarterly.since_inception_mop!==undefined) && (data.performance.quarterly.since_inception_mop!=='N/A') && (data.performance.quarterly.since_inception_mop!==null)){
                            quarterly_since_inception_mop=parseFloat(data.performance.quarterly.since_inception_mop).toFixed(2);
                        } else {
                            quarterly_since_inception_mop=labelData.notApplicable;
                        }

                    } else {
                        monthly_since_inception_nav=labelData.notApplicable;
                        quarterly_since_inception_nav=labelData.notApplicable;

                        monthly_since_inception_mop=labelData.notApplicable;
                        quarterly_since_inception_mop=labelData.notApplicable;
                    }


                    if ( (data.performance!== undefined) && (data.performance.annual!== undefined) && (data.performance.annual!== null)){

                        if((data.performance.annual.prev_year_performance!==undefined) &&(data.performance.annual.prev_year_performance!=='N/A') && (data.performance.annual.prev_year_performance!==null)){ annual_prev=parseFloat(data.performance.annual.prev_year_performance).toFixed(2); }else { annual_prev=labelData.notApplicable; }

                        if((data.performance.annual.prev_minus1_performance!==undefined) && (data.performance.annual.prev_minus1_performance!=='N/A') && (data.performance.annual.prev_minus1_performance!==null)){ annual_prev_minus1=parseFloat(data.performance.annual.prev_minus1_performance).toFixed(2); }else { annual_prev_minus1=labelData.notApplicable; }
                        if((data.performance.annual.prev_minus2_performance!==undefined) && (data.performance.annual.prev_minus2_performance!=='N/A') && (data.performance.annual.prev_minus2_performance!==null)){ annual_prev_minus2=parseFloat(data.performance.annual.prev_minus2_performance).toFixed(2); }else { annual_prev_minus2=labelData.notApplicable; }

                        if((data.performance.annual.prev_minus3_performance!==undefined) && (data.performance.annual.prev_minus3_performance!=='N/A') && (data.performance.annual.prev_minus3_performance!==null)){ annual_prev_minus3=parseFloat(data.performance.annual.prev_minus3_performance).toFixed(2); }else { annual_prev_minus3=labelData.notApplicable; }
                        if((data.performance.annual.prev_minus4_performance!==undefined) && (data.performance.annual.prev_minus4_performance!=='N/A') && (data.performance.annual.prev_minus4_performance!==null)){ annual_prev_minus4=parseFloat(data.performance.annual.prev_minus4_performance).toFixed(2); }else { annual_prev_minus4=labelData.notApplicable; }

                        if((data.performance.annual.prev_minus5_performance!==undefined) && (data.performance.annual.prev_minus5_performance!=='N/A') && (data.performance.annual.prev_minus5_performance!==null)){ annual_prev_minus5=parseFloat(data.performance.annual.prev_minus5_performance).toFixed(2); }else { annual_prev_minus5=labelData.notApplicable; }
                        if((data.performance.annual.prev_minus6_performance!==undefined) && (data.performance.annual.prev_minus6_performance!=='N/A') && (data.performance.annual.prev_minus6_performance!==null)){ annual_prev_minus6=parseFloat(data.performance.annual.prev_minus6_performance).toFixed(2); }else { annual_prev_minus6=labelData.notApplicable; }

                        if((data.performance.annual.prev_minus7_performance!==undefined) && (data.performance.annual.prev_minus7_performance!=='N/A') && (data.performance.annual.prev_minus7_performance!==null)){ annual_prev_minus7=parseFloat(data.performance.annual.prev_minus7_performance).toFixed(2); }else { annual_prev_minus7=labelData.notApplicable; }
                        if((data.performance.annual.prev_minus8_performance!==undefined) && (data.performance.annual.prev_minus8_performance!=='N/A') && (data.performance.annual.prev_minus8_performance!==null)){ annual_prev_minus8=parseFloat(data.performance.annual.prev_minus8_performance).toFixed(2); }else { annual_prev_minus8=labelData.notApplicable; }

                        if((data.performance.annual.prev_minus9_performance!==undefined) && (data.performance.annual.prev_minus9_performance!=='N/A') && (data.performance.annual.prev_minus9_performance!==null)){ annual_prev_minus9=parseFloat(data.performance.annual.prev_minus9_performance).toFixed(2); }else { annual_prev_minus9=labelData.notApplicable; }
                    } else {
                        annual_prev=labelData.notApplicable;
                        annual_prev_minus1=labelData.notApplicable; annual_prev_minus2=labelData.notApplicable;
                        annual_prev_minus3=labelData.notApplicable; annual_prev_minus4=labelData.notApplicable;
                        annual_prev_minus5=labelData.notApplicable; annual_prev_minus6=labelData.notApplicable;
                        annual_prev_minus7=labelData.notApplicable; annual_prev_minus8=labelData.notApplicable;
                        annual_prev_minus9=labelData.notApplicable;
                    }

                    var d = new Date();
                    var currYear = d.getFullYear();
                        annualLabel_prev=currYear-1;
                        annualLabel_prev_minus1=currYear-2; annualLabel_prev_minus2=currYear-3;
                        annualLabel_prev_minus3=currYear-4; annualLabel_prev_minus4=currYear-5;
                        annualLabel_prev_minus5=currYear-6; annualLabel_prev_minus6=currYear-7;
                        annualLabel_prev_minus7=currYear-8; annualLabel_prev_minus8=currYear-9;
                        annualLabel_prev_minus9=currYear-10;


                    if ( (data!== undefined) && (data.inception_date!== undefined) && (data.inception_date!== null) && (data.inception_date!== 'N/A')) { inceptiondate = data.inception_date ;} else {inceptiondate = labelData.notApplicable;}
                    if ( (data!== undefined) && (data.gross_exp_ratio!== undefined) && (data.gross_exp_ratio!== null) && (data.gross_exp_ratio!== 'N/A')) { expenceRatioGross = parseFloat(data.gross_exp_ratio).toFixed(2)+'%' ;} else {expenceRatioGross = labelData.notApplicable;}
                    if ( (data!== undefined) && (data.net_expense_ratio!== undefined) && (data.net_expense_ratio!== null) && (data.net_expense_ratio!== 'N/A')) { expenceRatioNet = parseFloat(data.net_expense_ratio).toFixed(2)+'%' ;} else {expenceRatioNet = labelData.notApplicable;}

    if(selectUserType=='FP') {
        if( (data.share_class=='A') || (data.share_class=='C') ){
            var dataNavLoadTemplate = `<li>
                        <p class="fund-content-header"></p>
                        <p class="fund-content-header">${labelData.nav}</p>
                        <p class="fund-content-header">${labelData.load}</p>
                    </li>
                   <li>
                        <p class="fund-content-header">${labelData.ytd}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_ytd_nav}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_ytd_nav}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_ytd_mop}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_ytd_mop}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${labelData.oneyr}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_oneyear_nav}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_oneyear_nav}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_oneyear_mop}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_oneyear_mop}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${labelData.threeyr}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_threeyear_nav}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_threeyear_nav}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_threeyear_mop}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${monthly_threeyear_mop}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${labelData.fiveyr}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_fiveyear_nav}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_fiveyear_nav}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_fiveyear_mop}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_fiveyear_mop}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${labelData.tenyr}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_tenyear_nav}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_tenyear_nav}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_tenyear_mop}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_tenyear_mop}</p>
                    </li>`;
        } else {
            var dataNavLoadTemplate = `<li>
                        <p class="fund-content-header"></p>
                        <p class="fund-content-header">${labelData.nav}</p>
                    </li>
                   <li>
                        <p class="fund-content-header">${labelData.ytd}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_ytd_nav}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_ytd_nav}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${labelData.oneyr}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_oneyear_nav}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_oneyear_nav}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${labelData.threeyr}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_threeyear_nav}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_threeyear_nav}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${labelData.fiveyr}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_fiveyear_nav}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_fiveyear_nav}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${labelData.tenyr}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_tenyear_nav}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_tenyear_nav}</p>
                    </li>`;
        }
    } else {
            var dataNavLoadTemplate = `<li>
                        <p class="fund-content-header"></p>
                        <p class="fund-content-header">${labelData.nav}</p>
                    </li>
                   <li>
                        <p class="fund-content-header">${labelData.ytd}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_ytd_nav}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_ytd_nav}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${labelData.oneyr}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_oneyear_nav}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_oneyear_nav}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${labelData.threeyr}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_threeyear_nav}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_threeyear_nav}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${labelData.fiveyr}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_fiveyear_nav}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_fiveyear_nav}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${labelData.tenyr}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_tenyear_nav}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_tenyear_nav}</p>
                    </li>`;
        }

                    let performancetemplate = `<div class="performance-grid">
        <div class="header">
            <p class="ticker"><a href="${detailUrl}" target="_self" class="fnameLS" data-scf="${data.share_class}-${data.fundId}-${data.investMinFinancial}" data-url="${detailUrl}">${data.ticker}</a></p>
            <div class="fund-details">
                <p class="share-class">${shareClassLabelDisplayfn(data.share_class)}</p>
                <a href="${detailUrl}" target="_self" class="fund-name fnameLS" data-scf="${data.share_class}-${data.fundId}-${data.investMinFinancial}" data-url="${detailUrl}">${fundName}</a>
                <p class="asset-class">${assetClass}</p>
            </div>
        </div>
        <div class="fund-content performance-annual-data">
            <div class="fund-content-row-one">
                <ul>
                    <li>
                        <p class="fund-content-header">${annualLabel_prev}</p>
                        <p class="fund-content-value performance-annual-data">${annual_prev}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${annualLabel_prev_minus1}</p>
                        <p class="fund-content-value performance-annual-data">${annual_prev_minus1}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${annualLabel_prev_minus2}</p>
                        <p class="fund-content-value performance-annual-data">${annual_prev_minus2}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${annualLabel_prev_minus3}</p>
                        <p class="fund-content-value performance-annual-data">${annual_prev_minus3}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${annualLabel_prev_minus4}</p>
                        <p class="fund-content-value performance-annual-data">${annual_prev_minus4}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${annualLabel_prev_minus5}</p>
                        <p class="fund-content-value performance-annual-data">${annual_prev_minus5}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${annualLabel_prev_minus6}</p>
                        <p class="fund-content-value performance-annual-data">${annual_prev_minus6}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${annualLabel_prev_minus7}</p>
                        <p class="fund-content-value performance-annual-data">${annual_prev_minus7}</p>
                    </li>
                </ul>
            </div>
        </div>
        <div class="fund-content performance-monthly-data performance-quarterly-data">
            <div class="fund-content-row-one">
                <ul>
                    ${dataNavLoadTemplate}
                </ul>
            </div>
            <div class="fund-content-row-two">
                <ul>
                    <li>
                        <p class="fund-content-header">${labelData.sinceinception}</p>
                        <p class="fund-content-value performance-monthly-data">${monthly_since_inception_nav}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${quarterly_since_inception_nav}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${labelData.inceptiondate}</p>
                        <p class="fund-content-value performance-monthly-data">${inceptiondate}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${inceptiondate}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${labelData.expratgross}</p>
                        <p class="fund-content-value performance-monthly-data">${expenceRatioGross}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${expenceRatioGross}</p>
                    </li>
                    <li>
                        <p class="fund-content-header">${labelData.expratnet}</p>
                        <p class="fund-content-value performance-monthly-data">${expenceRatioNet}</p>
                        <p class="fund-content-value performance-quarterly-data hidden">${expenceRatioNet}</p>
                    </li>
                </ul>
            </div>
            </div>
        </div>`;

                    $(".performance-content-grid-view").append(performancetemplate);

                    var rating_category='';
                    var morning_starrating_3Year='';
                    var morning_starratingGroup_3Year='';
                    var morning_starrating_5Year='';
                    var morning_starratingGroup_5Year='';
                    var morning_starrating_10Year='';
                    var morning_starratingGroup_10Year='';

                    if(selectUserType=='FP'){
                        if( (data.advisorFactsheetUrl!=undefined) && (data.advisorFactsheetUrl!=null) && (data.advisorFactsheetUrl!='N/A') && (data.advisorFactsheetUrl!='') ){
                            var replaceUrl = data.advisorFactsheetUrl;
                            factsheeturl = '<a target="_blank" href="'+replaceUrl+'">'+labelData.fundfactsheet+'</a>';
                        } else {
                            factsheeturl = labelData.notApplicable;
                        }
                    }else{
                        if( (data.memberFactsheetUrl!=undefined) && (data.memberFactsheetUrl!=null) && (data.memberFactsheetUrl!='N/A') && (data.memberFactsheetUrl!='') ){
                            var replaceUrl = data.memberFactsheetUrl;
                            factsheeturl = '<a target="_blank" href="'+replaceUrl+'">'+labelData.fundfactsheet+'</a>';
                        } else {
                            factsheeturl = labelData.notApplicable;
                        }
                    }

                    if( (data.category !== undefined) && (data.category !== null) ) {rating_category=data.category;}else{rating_category=' ';}

                    if( (data.morningStarRating !== undefined) && (data.morningStarRating.morning_starrating_3Year !== undefined) && (data.morningStarRating.morning_starrating_3Year !== null) && (data.morningStarRating.morning_starrating_3Year !== 'N/A') && (data.morningStarRating.morning_starrating_3Year !== '0') ) {morning_starrating_3Year=data.morningStarRating.morning_starrating_3Year;}else{morning_starrating_3Year=' ';}
                    if( (data.morningStarRating !== undefined) && (data.morningStarRating.morning_starratingGroup_3Year !== undefined) && (data.morningStarRating.morning_starratingGroup_3Year !== null) && (data.morningStarRating.morning_starratingGroup_3Year !== 'N/A') && (data.morningStarRating.morning_starratingGroup_3Year !== '0') ) {morning_starratingGroup_3Year=labelData.out+' '+labelData.of+' '+data.morningStarRating.morning_starratingGroup_3Year+' '+labelData.funds;}else{morning_starratingGroup_3Year=' ';}

                    if( (data.morningStarRating !== undefined) && (data.morningStarRating.morning_starrating_5Year !== undefined) && (data.morningStarRating.morning_starrating_5Year !== null) && (data.morningStarRating.morning_starrating_5Year !== 'N/A') && (data.morningStarRating.morning_starrating_5Year !== '0') ) {morning_starrating_5Year=data.morningStarRating.morning_starrating_5Year;}else{morning_starrating_5Year=' ';}
                    if( (data.morningStarRating !== undefined) && (data.morningStarRating.morning_starratingGroup5_Year !== undefined) && (data.morningStarRating.morning_starratingGroup5_Year !== null) && (data.morningStarRating.morning_starratingGroup5_Year !== 'N/A') && (data.morningStarRating.morning_starratingGroup5_Year !== '0') ) {morning_starratingGroup_5Year=labelData.out+' '+labelData.of+' '+data.morningStarRating.morning_starratingGroup5_Year+' '+labelData.funds;}else{morning_starratingGroup_5Year=' ';}

                    if( (data.morningStarRating !== undefined) && (data.morningStarRating.morning_starrating_10Year !== undefined) && (data.morningStarRating.morning_starrating_10Year !== null) && (data.morningStarRating.morning_starrating_10Year !== 'N/A') && (data.morningStarRating.morning_starrating_10Year !== '0') ) {morning_starrating_10Year=data.morningStarRating.morning_starrating_10Year;}else{morning_starrating_10Year=' ';}
                    if( (data.morningStarRating !== undefined) && (data.morningStarRating.morning_starratingGroup_10Year !== undefined) && (data.morningStarRating.morning_starratingGroup_10Year !== null) && (data.morningStarRating.morning_starratingGroup_10Year !== 'N/A') && (data.morningStarRating.morning_starratingGroup_10Year !== '0') ) {morning_starratingGroup_10Year=labelData.out+' '+labelData.of+' '+data.morningStarRating.morning_starratingGroup_10Year+' '+labelData.funds;}else{morning_starratingGroup_10Year=' ';}

                    //var morning_starrating_notApplicable = labelData.out+' '+labelData.of+' '+labelData.notApplicable+' '+labelData.funds;

                    if (morning_starratingGroup_3Year!=' '){
                        morningstarrating_3Year = `
                        <span class="fund-content-header">${morning_starrating_3Year}</span>
                        <span class="years-rating-star"></span>
                        <span class="fund-content-value">${morning_starratingGroup_3Year}</span>`;
                    } else {
                        morningstarrating_3Year = `<span class="fund-content-value">${labelData.notApplicable}</span>`;
                        /*morningstarrating_3Year = `
                        <span class="fund-content-header">${labelData.notApplicable}</span>
                        <span class="years-rating-star-null"></span>
                        <span class="fund-content-value">${morning_starrating_notApplicable}</span>`;*/
                    }

                    if (morning_starratingGroup_5Year!=' '){
                        morningstarrating_5Year = `
                        <span class="fund-content-header">${morning_starrating_5Year}</span>
                        <span class="years-rating-star"></span>
                        <span class="fund-content-value">${morning_starratingGroup_5Year}</span>`;
                    } else {
                        morningstarrating_5Year = `<span class="fund-content-value">${labelData.notApplicable}</span>`;
                        /*morningstarrating_5Year = `
                        <span class="fund-content-header">${labelData.notApplicable}</span>
                        <span class="years-rating-star-null"></span>
                        <span class="fund-content-value">${morning_starrating_notApplicable}</span>`;*/
                    }

                    if (morning_starratingGroup_10Year!=' '){
                        morningstarrating_10Year = `
                        <span class="fund-content-header">${morning_starrating_10Year}</span>
                        <span class="years-rating-star"></span>
                        <span class="fund-content-value">${morning_starratingGroup_10Year}</span>`;
                    } else {
                       morningstarrating_10Year = `<span class="fund-content-value">${labelData.notApplicable}</span>`;
                       /*morningstarrating_10Year = `
                        <span class="fund-content-header">${labelData.notApplicable}</span>
                        <span class="years-rating-star-null"></span>
                        <span class="fund-content-value">${morning_starrating_notApplicable}</span>`;*/
                    }


                if( (data.morningStarRating !== undefined) && (data.morningStarRating.rating !== undefined) && (data.morningStarRating.rating !== null) && (data.morningStarRating.rating !== 'N/A') && (data.morningStarRating.rating !== '0')) {
                    var ratingvalue = data.morningStarRating.rating;

                    var stariconTemplate = '';
                    for (var j = 1; j <= 5; j++) {
                        if (j <= ratingvalue) {
                            stariconTemplate = stariconTemplate + '<i class="fa fa-star fa-lg active"></i>'
                        } else {
                            stariconTemplate = stariconTemplate + '<i class="fa fa-star fa-lg"></i>'
                        }
                    }

                } else {
                    stariconTemplate = '';
                }


                if ( (data.morningStarRating !== undefined) && (data.morningStarRating.morning_stargroup_overall!=undefined) && (data.morningStarRating.morning_stargroup_overall!=null) && (data.morningStarRating.morning_stargroup_overall!='N/A') && (data.morningStarRating.morning_stargroup_overall!='0') ) {
                    morningstarrating_overall = labelData.out+' '+labelData.of+' '+data.morningStarRating.morning_stargroup_overall+' '+labelData.funds;
                } else {
                    morningstarrating_overall = labelData.notApplicable;
                    //morningstarrating_overall = labelData.out+' '+labelData.of+' '+labelData.notApplicable+' '+labelData.funds;
                }


                    let ratingtemplate = `<div class="rating-grid">
        <div class="header">
            <p class="ticker"><a href="${detailUrl}" target="_self" class="fnameLS" data-scf="${data.share_class}-${data.fundId}-${data.investMinFinancial}" data-url="${detailUrl}">${data.ticker}</a></p>
            <div class="fund-details">
                <p class="share-class">${shareClassLabelDisplayfn(data.share_class)}</p>
                <a href="${detailUrl}" target="_self" class="fund-name fnameLS" data-scf="${data.share_class}-${data.fundId}-${data.investMinFinancial}" data-url="${detailUrl}">${fundName}</a>
                <p class="asset-class">${assetClass}</p>
            </div>
        </div>
        <div class="fund-content">
            <div class="rating-section">
                <div class="overall-rating">
                    <p class="fund-content-header">${labelData.overall}</p>
                    <p class="star-rating">${stariconTemplate}</p>
                    <p class="fund-content-value">${morningstarrating_overall}</p>
                </div>
                <div class="years-rating">
                    <p>
                        <span class="fund-content-header rating-years-header">${labelData.msthreeyr}:</span>
                        ${morningstarrating_3Year}
                    </p>
                    <p>
                        <span class="fund-content-header rating-years-header">${labelData.msfiveyr}:</span>
                        ${morningstarrating_5Year}
                    </p>
                    <p>
                        <span class="fund-content-header rating-years-header">${labelData.mstenyr}:</span>
                        ${morningstarrating_10Year}
                    </p>
                </div>
            </div>
            <div class="pdf-section">
                <div class="rating-category">
                    <p class="fund-content-header">${labelData.ratingcategory}:</p>
                    <p class="fund-content-value">${rating_category}</p>
                </div>
                <div class="fund-sheet">
                    ${factsheeturl}
                </div>
            </div>
        </div>
    </div>`;
                    $(".rating-content-grid-view").append(ratingtemplate);

                }



            $('.fnameLS').click(function () {
                var pageUrl = $(this).data("url");
                var pageData = $(this).data("scf").split('-');
                var listingPageLS = [];
                listingPageLS[0] = pageData[0];
                listingPageLS[1] = pageData[1];
                listingPageLS[2] = pageData[2];
                localStorage.setItem("listingPageLS", JSON.stringify(listingPageLS));
                $(this).attr('href',pageUrl);
                window.location.href = pageUrl;
            });
            }

            if(selectUserType=='FP'){
                $('#filter_accordion .filter-header').filter(function(){
                    return $(this).children('.filter-title').data('target') == '#fund_type';
                }).hide();
                $('#filter_accordion #fund_type').hide();
            } else {
                $('#filter_accordion .filter-header').filter(function(){
                    return $(this).children('.filter-title').data('target') == '#fund_type';
                }).show();
                $('#filter_accordion #fund_type').show();
            }

        }

    function tableInfo() {
        var sorttxt;
        if ($('.fundslistTable thead th').hasClass('sorting_asc')) {
            sorttxt = $('.sorting_asc').first().text() + " in ascending order";
        } else if ($('.fundslistTable thead th').hasClass('sorting_desc')) {
            sorttxt = $('.sorting_desc').first().text() + " in descending order";
        }
        $(".sr-only.table-info").html($(".dataTables_info").text() + " of " + $(".lblTblCaption").text() + " table sorted by " + sorttxt);
    }

    $('.fundslistTable th').click(function() {
        $('.fundslistTable th span').removeAttr('aria-sort');
        $('.popup-modal.info-icon.popup-link').removeAttr('aria-sort');
        $(this).children('span').attr('aria-sort', 'ascending');
        if ($(this).hasClass('sorting_desc')) {
            $(this).children('span').attr('aria-sort', 'ascending');
        } else if ($(this).hasClass('sorting_asc')) {
            $(this).children('span').attr('aria-sort', 'descending');
        }

        setTimeout(function() {
            $('.fundslistTable th').removeAttr('aria-label aria-sort');
        }, 100);
    });

    $('.fundslistTable th').on('keydown', function(kbdEvent) {
        var ke = kbdEvent.which;
        if (13 == ke) {
            $('.fundslistTable th span').removeAttr('aria-sort');
            $('.popup-modal.info-icon.popup-link').removeAttr('aria-sort');
            $(this).children('span').attr('aria-sort', 'ascending');
            if ($(this).hasClass('sorting_desc')) {
                $(this).children('span').attr('aria-sort', 'ascending');
            } else if ($(this).hasClass('sorting_asc')) {
                $(this).children('span').attr('aria-sort', 'descending');
            }

            setTimeout(function() {
                $('.fundslistTable th').removeAttr('aria-label aria-sort');
            }, 100);
        }
    });

    // load script function with callback to handle sync
    function loadScript(src) {
        var script = document.createElement('script');
        script.src = src;
        document.getElementsByTagName('head')[0].appendChild(script);
    }


    //Accessibility Fixes Starts
    popupAccessbility();
    function popupAccessbility(){
        $('span.popup-link a, a.popup-link').on('click', function(ev){
            ev.preventDefault();
            ev.stopPropagation();
            $('#riskinfo').modal('show');
            $(this).addClass('triggered-element');
            $('.riskinfo-popup').attr('tabindex', -1).focus();
            var element = document.getElementById('riskinfo');
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
                $('.riskinfo-popup').click(function(e) {
                    e.stopPropagation();
                });
            });
        }
        $('.popup-modal.info-icon.popup-link').on('keydown', function(e) {
            if (e.which == 13 || e.which == 32) {
                e.preventDefault();
                e.stopPropagation();
                $('#riskinfo').modal('show');
                $(this).addClass('triggered-element');
                $('.modal.show').attr('tabindex', -1).focus();
                $('body .root.responsivegrid').attr('aria-hidden', 'true');
            }
        })
        //for riskinfo popup focus on close
        $('.riskinfo-popup a.close-link').click(function(e) {
            e.preventDefault();
            //$('.popup-modal.info-icon.popup-link').focus();
            //$('.triggered-element').focus();
            $('.riskinfo-popup').removeAttr('tabindex');
            $('.triggered-element').removeClass('triggered-element');
            $('body .root.responsivegrid').removeAttr('aria-hidden');
        });
        $('.riskinfo-popup a.close-link').on('keydown', function(e) {
            if (e.which == 13 || e.which == 32) {
                e.preventDefault();
                $('.riskinfo-popup a.close-link').click();
                $('.dataTables_scrollHead .popup-modal.info-icon.popup-link').focus();
            }
        });
        $('.riskinfo-popup').on('keydown', function(event) {
            if (event.key == "Escape") {
                $('.triggered-element').focus();
            }
        });


        $('a.close-link').click(function(e) {
            //$('.popup-modal.info-icon.popup-link').focus();
            //$('.triggered-element').focus();
            $('.triggered-element').removeClass('triggered-element');

        });
    $('#searchFunds').on('keydown', function(e){
         if( (e.which == 27)){
            $(this).val('');
         }
    })
    //Accessibility Fixes Ends

     }

     //header sticky
    var s = $(".funds-list");
    var pos = s.position();
    $(window).scroll(function() {
        var windowpos = $(window).scrollTop();
        var filterBoxWidth = $('.filter-sidebar-collapse').outerWidth();
        var fundsCountColWidth = $(".fundscount-width").outerWidth();
        var posRight = $('.funds-list ul.nav.nav-tabs').css('right');
        rightPosVal = parseInt(posRight);

        var fixedTabsWidth = $('table.fundslistTable.dataTable.no-footer tr.pc-radiobuttons-section td.performance-scrollablesection').outerWidth();
        if (fixedTabsWidth == null){
            fixedTabsWidth = $('table.fundslistTable.dataTable.no-footer tr.pc-radiobuttons-section td.rating-scrollablesection').outerWidth();
        }
        // pth - Performance Table Height
        var pth =  $('.performance-rating-list-content').height();
        if (pth == 0) {
            pth = $('.performance-rating-list-content').css('height', $('.dataTables_scroll').height());
        }
        var performanceTableHeight = pth + $('table.fundslistTable.dataTable').height() + $('.fixedTabs').height();


        if (windowpos >= pos.top & windowpos <= performanceTableHeight) {
            s.addClass("header-sticky");
            if ($(window).width() < 767){
                $('.funds-list.header-sticky .content .fixedTabs').css("padding-left", '');
                $('.funds-list.header-sticky .content .fixedTabs ul.nav.nav-tabs').css('width', '');
            } else{
                $('.funds-list.header-sticky .content .fixedTabs').css("padding-left", fundsCountColWidth);
                $('.funds-list.header-sticky .content .fixedTabs ul.nav.nav-tabs').css('width', fixedTabsWidth);
            }
            $('.dataTables_scrollHeadInner').addClass('stickyTableHeader');

        } else {
            s.removeClass("header-sticky");
            $('.funds-list .content .fixedTabs').css("padding-left", "");
            $('.funds-list .content .fixedTabs ul.nav.nav-tabs').css('width', '');
            $('.funds-list.header-sticky .content.pc-content-width .fixedTabs ul.nav.nav-tabs').css('width', '');
            $('.dataTables_scrollHeadInner').removeClass('stickyTableHeader');
        }
        if ($('.radio-label').hasClass('checkedanuEND')){
            $('.funds-list.header-sticky .content .fixedTabs ul.nav.nav-tabs').css('width', fixedTabsWidth);
            $('.radio-label.checkedanuEND').focus();
        }
        if($(".filter-sidebar-collapse").hasClass("filter-width") && $('.radio-label').hasClass('checkedanuEND')){
            $('.funds-list.header-sticky .content .fixedTabs ul.nav.nav-tabs').css('width', fixedTabsWidth);
            $('.radio-label.checkedanuEND').focus();
        }

    });

    //filter focus
    function keyPressCollapsing() {
        $('.filter-header').on('keydown', function(key) {
            var key = key.which;
            if (13 == key || 32 == key) { return $(this).children('.filter-title').toggleClass("collapsed"),$(this).next().toggleClass('show'), $(this).children('.filter-title').hasClass("collapsed") ? $(this).children('.filter-title').attr("aria-expanded", "false") : $(this).children('.filter-title').attr("aria-expanded", "true"), !1 }

        });

        $(".styled-checkbox").focusin(function() { $(this).parent(".filter-text").css("border", "2px solid #000") }), $(".styled-checkbox").focusout(function() { $(this).parent(".filter-text").css("border", "2px solid transparent") });
        $(".styled-checkbox").focusin(function() { $(this).parent("body.using-mouse .filter-text").css("border", "2px solid transparent") }), $(".styled-checkbox").focusout(function() { $(this).parent("body.using-mouse .filter-text").css("border", "2px solid transparent") });
        $(".filter-sidebar-collapse #share_class input[type='radio']").focusin(function() { $(this).parent(".filter-text").css("border", "2px solid #000") }), $(".filter-sidebar-collapse #share_class input[type='radio']").focusout(function() { $(this).parent(".filter-text").css("border", "2px solid transparent") });
        $(".filter-sidebar-collapse #share_class input[type='radio']").focusin(function() { $(this).parent("body.using-mouse .filter-text").css("border", "2px solid transparent") }), $(".filter-sidebar-collapse #share_class input[type='radio']").focusout(function() { $(this).parent("body.using-mouse .filter-text").css("border", "2px solid transparent") });
    }

    $(".performanceOption input[type='radio']").focusin(function() { $(this).next(".radio-label").css("border", "2px solid #000") }), $(".performanceOption input[type='radio']").focusout(function() { $(this).next(".radio-label").css("border", "2px solid transparent") });

    $(".performanceOption input[type='radio']").focusin(function() { $(this).next("body.using-mouse .radio-label").css("border", "2px solid transparent") }), $(".performanceOption input[type='radio']").focusout(function() { $(this).next("body.using-mouse .radio-label").css("border", "2px solid transparent") });

     $('#searchFunds').on('keydown', function(e) {
        if ((e.which == 27)) {
            $(this).val('');
        }
     });

    //tab switching on keypress
    $(".fixedTabs .nav-item").on('keyup', function(e) {
        if (e.which == 39) {
            $(this).next().children('.fund-nav-link').focus()
            return;
        }
        if (e.which == 37) {
            $(this).prev().children('.fund-nav-link').focus();
            return;
        }
    });
    $('.fixedTabs .nav-item > .fund-nav-link:last').focusin(function() {
        $(this).on('keyup', function(e) {
            if (e.which == 39) {
                $('.fixedTabs .nav-item > .fund-nav-link:first').focus();
                return;
            }
        });
    });
    $('.nav-tabs .fund-nav-link').on('keyup', function(e) {
        if (e.which == 13 || e.which == 32) {

            $('.fund-nav-link.active').attr('tabindex', '-1');
            $('.fund-nav-link').removeClass('active').attr('aria-selected', 'false');
            $('.fixedTabs .nav-item').removeClass('current');

            $(this).addClass('active').attr({ 'tabindex': '0', 'aria-selected': 'true' });
            $(this).parent().addClass('current');
        }
        return;
    });
});
