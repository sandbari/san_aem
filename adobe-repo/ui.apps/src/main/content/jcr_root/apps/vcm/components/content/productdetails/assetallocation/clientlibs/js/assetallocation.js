var asset_allocation_body = function(data){
    var tBody = $("<tbody/>");
    if (data) {
        $(".asset-allocation-asof").append(data.as_of_date);
        let assetAllocationData = data.asset_allocation;
        assetAllocationData.forEach(assetData =>{
                if ((assetData === null) || (assetData === undefined) || (assetData === "")) {
                    assetData = "N/A"
                    }else{
                        var bodyRow = $("<tr/>");
                        bodyRow.append($(`<td class='text-left'>${assetData.label.replace(/[\[\]']+/g, '')}</td>`));
                        bodyRow.append($(`<td>${parseFloat(assetData.value).toFixed(2)}</td>`));
                    }
            tBody.append(bodyRow);
        });
    }
    $(".asset-allocation-table").append(tBody);
}
var asset_allocation_head = function(data){
    var tHead = $("<thead/>");
    var headRow = $("<tr/>");
    if (data) {
        $(".asset-head-label").prepend(data.assetallocation);
        headRow.append(`<th scope='col'></th>`);
        headRow.append(`<th scope='col'>${data.AssetAllocation.fund}</th>`);
    }
    tHead.append(headRow);
    $(".asset-allocation-table").append(tHead);
}

var beforeassetallocLoadFuc = function () {
    $('.dynamic-loader-assetallocation').css("display", "block");
    $('.error-message-assetallocation').css("display", "none");
}

var assetallocErrorHandling = function () {
	$('.asset-allocation-component').css("visibility", "visible");
	$(".asset-head-label").prepend(ajaxData_productLabels.assetallocation);
	$(".error-message-assetallocation p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-assetallocation').css("display", "none");
    $('.error-message-assetallocation').css("display", "block");
}

$(document).ready(function() {
    $(".asset-allocation-table-data").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".asset-allocation-table-data").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));
});