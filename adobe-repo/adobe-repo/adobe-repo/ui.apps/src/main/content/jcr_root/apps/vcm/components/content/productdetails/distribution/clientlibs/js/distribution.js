var selectedDistribution = loadClass;
var distribution_component = function(data,labels,checkFundCat){
	distribution_tableheadData(labels,checkFundCat);
	distribution_tableData(data,checkFundCat);
}
var headRow = $("<tr/>");
var tHead = $("<thead/>");

function distribution_tableheadData(data,checkFundCat) {

    let distributiontableLabels;
	if (data) {
		var annualTabelLabel = data.Distributions;
		distributiontableLabels = Object.values(annualTabelLabel);
         if (checkFundCat == "MF") {
             $(".Distribution.header-label").append(data.distribution);
             distributiontableLabels.forEach(heading => {
                 if (heading != annualTabelLabel.total_distribution && heading != annualTabelLabel.ordinary_income && heading != annualTabelLabel.exdate)
                     headRow.append(`<th scope='col'>${heading}</th>`);
             })
         } else if (checkFundCat == "ETF") {
             $(".Distribution.header-label").append(data.recentdistribution);
             distributiontableLabels.forEach(heading => {
                 if (heading != annualTabelLabel.ordinary_income && heading != annualTabelLabel.exdate) {
                     if (heading == annualTabelLabel.declareddate) {
                         heading = annualTabelLabel.exdate;
                     } else if (heading == annualTabelLabel.incomeamt) {
                         heading = annualTabelLabel.ordinary_income;
                     }
                     if (heading != annualTabelLabel.returnofcapital) {
                         headRow.append(`<th scope='col'>${heading}</th>`);
                     }
                 }
             })
         }
        tHead.append(headRow);
		//$(".distributions-table").append(tHead);
		}
}
function distribution_tableData(data,checkFundCat) {
	if (!!data) {
		if (checkFundCat == "MF") {
			var apiClasses = [];
			data.forEach(element => {
			 apiClasses.push(element.share_class);
			});

			if (!(apiClasses.indexOf(selectedDistribution) > -1)) {
				selectedDistribution = apiClasses[0];
			}
			data.map(item => {
				if (item.share_class == selectedDistribution) {
					distribution_content(item,checkFundCat);
				}
			});
		} else if (checkFundCat == "ETF") {
			distribution_content(data,checkFundCat);
		}
	}
}

function distribution_content(item,checkFundCat){
    var tBody = $("<tbody/>");
	let distributeData=[];
    var income_value,longTerm_value,shortTerm_value,total_dist_sum;
    if(Array.isArray(item)){ item = item[0]}
     if (typeof item.distributions != "undefined" && !!item.distributions.length) {
         $('.distributions-component').css("display", "block");
         function comp(a, b) {
              return new Date(a.declared_date).getTime() - new Date(b.declared_date).getTime();
         }
         item.distributions.sort(comp);
         distributeData = item.distributions;
         distributeData.reverse();
         updatedArray =[];
         for(var j=0;j<distributeData.length;j++){
             if(j<12){
              updatedArray.push(distributeData[j]);
             }
         }
         distributeData = updatedArray;

        distributeData.forEach(expData => {
            Object.keys(expData).forEach(key => {
                if (expData[key] == null) {
                    expData[key] = 'N/A';

                } else {
                    expData[key] = expData[key];
                }
            })

            var bodyRow = $("<tr/>");
            bodyRow.append($(`<td>${(expData.declared_date)}</td>`));
            bodyRow.append($(`<td>${(expData.record_date)}</td>`));
            bodyRow.append($(`<td>${(expData.payable_date)}</td>`));
            bodyRow.append($(`<td>${(parseFloatValue(expData.income_amt))}</td>`));
            bodyRow.append($(`<td>${(parseFloatValue(expData.long_term_capital_gains))}</td>`));
            bodyRow.append($(`<td>${(parseFloatValue(expData.short_term_capital_gains))}</td>`));
            income_value = Number(parseFloatValue(expData.income_amt));
            longTerm_value =Number(parseFloatValue(expData.long_term_capital_gains));
            shortTerm_value = Number(parseFloatValue(expData.short_term_capital_gains));
             total_dist_sum = (income_value+longTerm_value+shortTerm_value).toFixed(2);
            if(checkFundCat == "ETF"){
                bodyRow.append($(`<td>${(total_dist_sum)}</td>`));
            }else if(checkFundCat == "MF"){
            	bodyRow.append($(`<td>${(expData.return_of_capital)}</td>`));
            }
            tBody.append(bodyRow);
			$(".distributions-table").html("").append(tHead).append(tBody);
        });

        var footRow = $("<tr/>");
        var tFoot = $("<tfoot/>");
        // Table footer data calculation
        var table=$(".distributions-table tbody tr"),
        tdCell=$(".distributions-table tbody tr td");
        var sumVal_income=0;
        sumVal_lterm=0, sumVal_sterm=0;
        for (var i = 0; i < table.length; i++) {
            var tdData_income = table[i].cells[3].innerHTML;
            if(tdData_income == "N/A" ){
                tdData_income =0;
            }
            var tdData_lterm = table[i].cells[4].innerHTML;
            if(tdData_lterm == "N/A" ){
                tdData_lterm =0;
            }

            var tdData_sterm = table[i].cells[5].innerHTML;
            if(tdData_sterm == "N/A" ){
                tdData_sterm =0;
            }
            sumVal_lterm = parseFloat(sumVal_lterm) + parseFloat(tdData_lterm);
            sumVal_sterm = parseFloat(sumVal_sterm) + parseFloat(tdData_sterm);
            sumVal_income = parseFloat(sumVal_income) + parseFloat(tdData_income);
        }

        tFoot.append(footRow);
        footRow.append(`<th colspan="3">Total</th>`);
        footRow.append(`<td>${"$"+parseFloat(sumVal_income).toFixed(5)}</td>`);
        footRow.append(`<td>${"$"+parseFloat(sumVal_lterm).toFixed(5)}</td>`);
        footRow.append(`<td>${"$"+parseFloat(sumVal_sterm).toFixed(5)}</td>`);
        footRow.append(`<td></td>`);
        $(".distributions-table").append(tFoot);
    } else {
        $('.distributions-component').css("display", "none");
    }
}
function parseFloatValue(value){
    if(!value|| value == "N/A"){
        value = "N/A";
    } else
        value = parseFloat(value).toFixed(5);
    return value;
}

function onChangeDistributionClass(selectClass){
	selectedDistribution = selectClass;
	if($('.distribution').length > 0 && $.trim($("div.distribution").html()).length > 0) {
        var checkFundCat = $("#fund-category").attr('value');
    	distribution_tableData(ajaxData_distribution,checkFundCat);
	}
}
selectboxChange(onChangeDistributionClass);

var beforeDistributionLoadFuc = function () {
    $('.dynamic-loader-distribution').css("display", "block");
    $('.error-message-distribution').css("display", "none");
}

var distributionErrorHandlingFuc = function () {
	$('.distributions-component').css("visibility", "visible");
	$(".Distribution.header-label").append(ajaxData_productLabels.distribution);
	$(".error-message-distribution p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-distribution').css("display", "none");
    $('.error-message-distribution').css("display", "block");
}

$(document).ready(function() {
    if($('.distribution').length > 0) {
        $(".distribution-table-data").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
        $(".distribution-table-data").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));
    }
});