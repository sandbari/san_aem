var duration_schedule_body = function(data){
	var tBody = $("<tbody/>");
	 if (data) {
			$(".Duration-Schedule-asof").append(data.as_of_date);
			let durationData = data.duration_schedule;
         durationData.forEach(durationData =>{
					if ((durationData === null) || (durationData === undefined) || (durationData === "")) {
                     durationData = "N/A"
						}else{
							var bodyRow = $("<tr/>");
							bodyRow.append($(`<td class='text-left'>${durationData.duration.replace(/[\[\]']+/g, '')}</td>`));
							bodyRow.append($(`<td>${parseFloat(durationData.percentage).toFixed(2)}</td>`));
						}
				tBody.append(bodyRow);
			});
         $(".Duration-Schedule-table").append(tBody);
     }
}

var duration_schedule_head = function(data){
	var tHead = $("<thead/>");
	var headRow = $("<tr/>");
	if (data) {
        console.log(data.assetallocation);
        $(".duration-head-label").prepend(data.durationschedule);
		headRow.append(`<th scope='col'></th>`);
		headRow.append(`<th scope='col'>${data.DurationSchedule.fund}</th>`);
		}
        tHead.append(headRow);
		$(".Duration-Schedule-table").append(tHead);
}
var beforeDurationScheduleLoadFuc = function () {
    $('.dynamic-loader-durationschedule').css("display", "block");
    $('.error-message-durationschedule').css("display", "none");
}

var durationScheduleErrorHandlingFuc = function () {
	$('.duration-schedule-component').css("visibility", "visible");
	$(".duration-head-label").prepend(ajaxData_productLabels.durationschedule);
	$(".error-message-durationschedule p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-durationschedule').css("display", "none");
    $('.error-message-durationschedule').css("display", "block");
}
$(document).ready(function() {
	$(".Duration-Schedule-table-data").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".Duration-Schedule-table-data").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));

});