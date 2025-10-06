var country_diversification = function(data,labelsdata){
	var row;
	var headername = "";
	countryTablelabelData(labelsdata);
	countryTablehead(data);
	countryTableData(data);
}

function countryTablelabelData(data) {
	if (data) {
		$(".Country-Diversification.header-label").append(data.countrydiversification);
		headername = data.CountryDiversification.fund;
	}

}

function countryTablehead(data) {
	$(".Country-Diversification-asof").append(data.as_of);
	var tHead = $("<thead/>");
	var headRow = $("<tr/>");
	if (data) {
		headRow.append(`<th scope='col'></th>`);
		let results = data.data
		results.forEach((listTitle, index) => {
			if (index === 0) {
				headRow.append(`<th scope='col'>${headername}</th>`);
			} else {
				headRow.append(`<th scope='col'>${listTitle['benchmark_name']} (%)</th>`);
			}
		});
		tHead.append(headRow);
		$(".Country-Diversification-table").append(tHead);
	}
}

function countryTableData(data) {
	var tBody = $("<tbody/>");
	let keys = Object.keys(data.data[0]);
	let lastKey = '';
	keys.forEach((key, index) => {
		if (index != keys.length - 1) {
            if (key.indexOf("CASH") >= 0 || key.indexOf("CASH & CASH EQUIVALENTS") >= 0) {
                lastKey = key;
            } else {
                tableRender(data.data, key);
            }
        }
        tBody.append(row);

	})
	$('.Country-Diversification-table').append(tBody);
	sortTable(".country-table ");
	if (!!lastKey) {
            tableRender(data.data, lastKey);
            tBody.append(row);
        }
	$('.Country-Diversification-table').append(tBody);
	$(".Country-Diversification-table tr th:nth-child(1)").append( `<span class='sr-only'>Country Diversification</span>` );
	hideNullColumns('.Country-Diversification-table');
	hideGradient();
}

var beforeContryDiverseLoadFuc = function () {
    $('.dynamic-loader-countrydiverse').css("display", "block");
    $('.error-message-countrydiverse').css("display", "none");
}

var countrydiverseErrorHandlingFuc = function () {
	$('.country-table').css("visibility", "visible");
	$(".Country-Diversification.header-label").append(ajaxData_productLabels.countrydiversification);
	$(".error-message-countrydiverse p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-countrydiverse').css("display", "none");
    $('.error-message-countrydiverse').css("display", "block");
}


$(document).ready(function() {
	$(".Country-Diversification-table-data").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".Country-Diversification-table-data").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));
	
});