var qualitystructure_body = function(data){
        var tBody = $("<tbody/>");
        if (data) {
			$(".Quality-Structure-asof").append(data.as_of_date);
				let qualityData = data.quality_structure
				qualityData.forEach(quaData =>{
					if ((quaData === null) || (quaData === undefined) || (quaData === "")) {
							quaData = "N/A"
						}else{
							var bodyRow = $("<tr/>");
							bodyRow.append($(`<td class='text-left'>${quaData.name.replace(/[\[\]']+/g, '')}</td>`));
							bodyRow.append($(`<td>${parseFloat(quaData.portfolio_percentage).toFixed(2)}</td>`));
							bodyRow.append($(`<td>${parseFloat(quaData.benchmark_percentage).toFixed(2)}</td>`));
						}
					tBody.append(bodyRow);
				})
		}
		$(".Quality-Structure-table").append(tBody);
}
var qualitystructure_head = function(data){
    var headRow = $("<tr/>");
    var tHead = $("<thead/>");
    if (data) {
        var qualityTabelLabel = data.QualityStructure;
        let tableLabels = Object.values(qualityTabelLabel);
        headRow.append(`<th scope='col'><span class="sr-only">Quality rating</span></th>`);
        tableLabels.forEach(heading =>{
            headRow.append(`<th scope='col'>${heading}</th>`);
        })
        tHead.append(headRow);
        $(".quality-header-label").append(data.qualitystructure);
        $(".Quality-Structure-table").append(tHead);
    }
}

var beforeQualityStrucOneLoadFuc = function () {
    $('.dynamic-loader-qualitystructureone').css("display", "block");
    $('.error-message-qualitystructureone').css("display", "none");
}

var qualityStrucOneErrorHandlingFuc = function () {
	$('.qualitystructure-one').css("visibility", "visible");
	$(".quality-header-label").append(ajaxData_productLabels.qualitystructure); 
	$(".error-message-qualitystructureone p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-qualitystructureone').css("display", "none");
    $('.error-message-qualitystructureone').css("display", "block");
}

$(document).ready(function() {
     $(".Quality-Structure-table-data").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".Quality-Structure-table-data").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));


});