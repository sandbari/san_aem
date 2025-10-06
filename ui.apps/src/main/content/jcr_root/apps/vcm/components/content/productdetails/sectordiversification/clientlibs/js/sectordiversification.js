var sector_diversification = function(data,labelsdata){
	var row;
	var headername = "";
	sectorTablelabelData(labelsdata);
	sectorTablehead(data);
	sectorTableData(data);
}

function sectorTablelabelData(data) {
	if (data) {
		$(".Sector-Diversification.header-label").append(data.sectordiversification);
		headername = data.SectorDiversification.fund;
	}

}

function sectorTablehead(data) {
	$(".Sector-Diversification-asof").append(data.as_of);
	var tHead = $("<thead/>");
	var headRow = $("<tr/>");
	if (data) {
		headRow.append(`<th scope='col'><span class="sr-only">Sector</span></th>`);
		let results = data.data
		results.forEach((listTitle, index) => {
			if (index === 0) {
				headRow.append(`<th scope='col'>${headername}</th>`);
			} else {
				headRow.append(`<th scope='col'>${listTitle['benchmark_name']} (%)</th>`);
			}
		});
		tHead.append(headRow);
		$(".Sector-Diversification-table").append(tHead);
	}

}

function sectorTableData(data) {
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
	$('.Sector-Diversification-table').append(tBody);
	sortTable(".sector-table ");
	if (!!lastKey) {
            tableRender(data.data, lastKey);
            tBody.append(row);
        }
	$('.Sector-Diversification-table').append(tBody);
	hideNullColumns('.Sector-Diversification-table');
	hideGradient();
}

var beforeSectorDiverseLoadFuc = function () {
    $('.dynamic-loader-sectordiverse').css("display", "block");
    $('.error-message-sectordiverse').css("display", "none");
}

var sectorDiverseErrorHandlingFuc = function () {
	$('.sector-table').css("visibility", "visible");
	$(".Sector-Diversification.header-label").append(ajaxData_productLabels.sectordiversification);
	$(".error-message-sectordiverse p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-sectordiverse').css("display", "none");
    $('.error-message-sectordiverse').css("display", "block");
}


$(document).ready(function() {
	$(".Sector-Diversification-table-data").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".Sector-Diversification-table-data").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));
	
});
