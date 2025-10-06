var region_allocation = function(labels,data){
	var headRow = $("<tr/>"),
        tHead = $("<thead/>"),
        tBody = $("<tbody/>");

    // bind labels
    $(".region-allocation-component h3.heading .Region-Allocation.header-label").append(ajaxData_productLabels.regionallocation);

    const keys = Object.values(labels.RegionAllocation)
    keys.forEach(ele => {
        headRow.append(`<th>${ele}</th>`);
    });
    tHead.append(headRow);
    // bind data
    if (data) {
        $(".region-allocation-asof").append(data.as_of_date);
        const element = Object.values(data.regionAllocation);
        element.map((e) =>{
           regAllocValue =  Object.values(e);
           var bodyRow = $("<tr/>");
           regAllocValue.forEach(regAllTablData => {
               isNaN(regAllTablData) ? bodyRow.append($(`<td>${(regAllTablData)}</td>`)) : "" == regAllTablData || null == regAllTablData ? bodyRow.append($(`<td>N/A</td>`)) : bodyRow.append($(`<td>${(parseFloat(regAllTablData).toFixed(2))}</td>`));
               //var toInt = parseInt(regAllTablData).toFixed(2);
               //isNaN(toInt) ? bodyRow.append($(`<td>${(regAllTablData)}</td>`)) : bodyRow.append($(`<td>${(parseInt(regAllTablData).toFixed(2))}</td>`))  ;
           });
           tBody.append(bodyRow);
       })
    }
    $(".region-allocation-table").append(tHead).append(tBody);
    hideGradient();
}

var beforeRegionAllocationFuc = function () {
    $('.dynamic-loader-regionallocation').css("display", "block");
    $('.error-message-regionallocation').css("display", "none");
}

var regionAllocationErrorHandlingFuc = function () {
	$('.region-allocation-component').css("visibility", "visible");
	$(".region-allocation-component h3.heading .Region-Allocation.header-label").append(ajaxData_productLabels.regionallocation);
	$(".error-message-regionallocation p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-regionallocation').css("display", "none");
    $('.error-message-regionallocation').css("display", "block");
}


$(document).ready(function() {
	$(".region-allocation-table-wrapper ").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".region-allocation-table-wrapper").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));

});
