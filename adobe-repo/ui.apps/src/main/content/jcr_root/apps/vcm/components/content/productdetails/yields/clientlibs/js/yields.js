var getLabelYield;
var selectedYieldFixedIncome = loadClass;
var yieldincometHead = $("<thead/>");
var yields_one_head = function(data){
    	$(".header-label-fxdinc").prepend(data.yields);
        var headRow = $("<tr/>");
        headRow.append(`<th scope='col'><span class="sr-only">Yield</span></th>`);
        headRow.append(`<th scope='col'>(%)</th>`);
        yieldincometHead.append(headRow);
        //$(".yields_fixed-income_table").append(tHead);
        getLabelYield = data.Yields;
}
var yields_one_body = function(data){
        var tBody = $("<tbody>");
        var datatr = [];
        var dataObj = data;
        var apiClasses = [];
            data.forEach(element => {
                apiClasses.push(element.share_class);
        });

        if (!(apiClasses.indexOf(selectedYieldFixedIncome) > -1)) {
            selectedYieldFixedIncome = apiClasses[0];
        }
        if (dataObj) {
        	 data.map(item => {
                    if (item.share_class == selectedYieldFixedIncome) {
                         $(".yields_fixed-income-asof").html("").append(item.yields_fixed_income.as_of_date);
                         Object.keys(item.yields_fixed_income).forEach(function (key) {
                             if (key != "as_of_date") {
                                 if (getLabelYield.hasOwnProperty(key)) {
                                     if (item.yields_fixed_income[key] !== null) {
                                         tBody.append($(`<tr><td>${(getLabelYield[key]).replace(":",'')}</td><td>${parseFloat(item.yields_fixed_income[key]).toFixed(2)}</td></tr>`));
                                     } /*else if (item.yields_fixed_income[key] == null) {
                                         tBody.append($(`<tr><td>${(getLabelYield[key]).replace(":",'')}</td><td>N/A</td></tr>`));
                                     }*/
                                 }
                             }
                         });
                    }
             });
             //tBody.append("</tbody>");
             $(".yields_fixed-income_table").html("").append(yieldincometHead).append(tBody);

             if($('.yields-fixed-income tbody').html() == ""){
                 $('.yields_fixed-income_container').hide();
             }
        }
}

function onChangeYieldClass(selectClass){
	selectedYieldFixedIncome = selectClass;
	if($('.yields_fixed-income_table').length > 0) {
    	yields_one_body(ajaxData_yields);
	}
}
selectboxChange(onChangeYieldClass);

var beforeYieldsLoadFuc = function () {
    $('.dynamic-loader-yieldsfixdinc').css("display", "block");
    $('.error-message-yieldsfixdinc').css("display", "none");
}

var yieldFixedIncErrorHandlingFuc = function () {
	$('.yields-fixed-income').css("visibility", "visible");
	$(".header-label-fxdinc").html("").prepend(ajaxData_productLabels.yields);
	$(".error-message-yieldsfixdinc p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-yieldsfixdinc').css("display", "none");
    $('.error-message-yieldsfixdinc').css("display", "block");
}