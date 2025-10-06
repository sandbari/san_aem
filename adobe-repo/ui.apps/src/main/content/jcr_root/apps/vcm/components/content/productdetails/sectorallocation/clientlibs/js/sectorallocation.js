var sector_allocation = function(labels,data){
	var headRow = $("<tr/>"),
        tHead = $("<thead/>"),
        tBody = $("<tbody/>");
    // bind labels

    $(".sec-allocation-component h3.heading .Sector-Allocation.header-label").append(labels.sectorallocation);

    const keys = Object.values(labels.SectorAllocation)
    keys.forEach(ele => {
        headRow.append(`<th>${ele}</th>`);
    });
    tHead.append(headRow);
    // bind data
    if (data) {
        $(".sector-allocation-asof").append(data.as_of_date);
        const element = Object.values(data.sectorAllocation);
        element.map((e) =>{
           secAllocValue =  Object.values(e);
           var bodyRow = $("<tr/>");
           secAllocValue.forEach(secAllTablData => {
               isNaN(secAllTablData) ? bodyRow.append($(`<td>${(secAllTablData)}</td>`)) : "" == secAllTablData || null == secAllTablData ? bodyRow.append($(`<td>N/A</td>`)) : bodyRow.append($(`<td>${(parseFloat(secAllTablData).toFixed(2))}</td>`));
               //var toInt = parseInt(secAllTablData).toFixed(2);
               //isNaN(toInt) ? bodyRow.append($(`<td>${(secAllTablData)}</td>`)) : bodyRow.append($(`<td>${(parseInt(secAllTablData).toFixed(2))}</td>`))  ;
           });
           tBody.append(bodyRow);
       })
    }
    $(".sector-allocation-table").append(tHead).append(tBody);

    hideGradient();
}

var beforeSectorAllocationFuc = function () {
    $('.dynamic-loader-sectorallocation').css("display", "block");
    $('.error-message-sectorallocation').css("display", "none");
}

var sectorAllocationErrorHandlingFuc = function () {
	$('.sec-allocation-component').css("visibility", "visible");
	$(".sec-allocation-component h3.heading .Sector-Allocation.header-label").append(labels.sectorallocation);
	$(".error-message-sectorallocation p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-sectorallocation').css("display", "none");
    $('.error-message-sectorallocation').css("display", "block");
}


$(document).ready(function() {
	$(".sector-allocation-table-wrapper ").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".sector-allocation-table-wrapper").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));

});
