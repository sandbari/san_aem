var regional_diversification = function(data,labelsdata){
	var row;
	var headername = "";
	regionalTablelabelData(labelsdata);
	regionalTablehead(data);
	regionalTableData(data);
}

/* Fetching data from AEM JSON and appending to table heading */
function regionalTablelabelData(data) {
	if (data) {
		$(".Regional-Diversification.header-label").append(data.regionaldiversification);
		headername = data.RegionalDiversification.fund;
	}

}

function regionalTableData(data) {
	var tBody = $("<tbody/>");
	let keys = Object.keys(data.data[0]);
	let lastKey = '';
	keys.forEach((key, index) => {
		if (index != keys.length - 1) {
            if (key.indexOf("CASH & CASH EQUIVALENTS") >= 0) {
                lastKey = key;
            } else {
                tableRender(data.data, key);
            }
        }
        tBody.append(row);

	})
	$('.Regional-Diversification-table').append(tBody);
	sortTable(".regional-table ");
	 if (!!lastKey) {
            tableRender(data.data, lastKey);
            tBody.append(row);
        }
	$('.Regional-Diversification-table').append(tBody);
	$(".Regional-Diversification-table tr th:nth-child(1)").append( `<span class='sr-only'>Regional Diversification</span>` );
	hideNullColumns('.Regional-Diversification-table');
	hideGradient();
}

/* Fetching data from API and appending to table head */

function regionalTablehead(data) {
	$(".Regional-Diversification-asof").append(data.as_of);
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
		$(".Regional-Diversification-table").append(tHead);
	}
}

var beforeRegionalDiverseLoadFuc = function () {
    $('.dynamic-loader-regionaldiverse').css("display", "block");
    $('.error-message-regionaldiverse').css("display", "none");
}

var regionalDiverseErrorHandlingFuc = function () {
	 $('.regional-table').css("visibility", "visible");
	 $(".Regional-Diversification.header-label").append(ajaxData_productLabels.regionaldiversification);
	 $(".error-message-regionaldiverse p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
     $('.dynamic-loader-regionaldiverse').css("display", "none");
     $('.error-message-regionaldiverse').css("display", "block");
}

$(document).ready(function() {
	$(".Regional-Diversification-table-data").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".Regional-Diversification-table-data").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));
	
});