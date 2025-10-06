var today = new Date(),
    todayDay = today.getDay(),
    todayDate = ("0" + today.getDate()).slice(-2),
    todayMonth =  ("0" + (today.getMonth() + 1)).slice(-2),
    todayYear = today.getFullYear(),
    completeDate = todayMonth + "/" + todayDate + "/" + todayYear; 


/* Function to check whether today is a holiday or not
   based on the JSON response */
function isLeaveToday() {
    var isTodayAHoliday = false;
    $.ajax({
        url: '/content/dam/vcm/configs/contact-us.json',
        success: function(response) {
            var holidayList = response['holidays'];
			isTodayAHoliday = holidayList.hasOwnProperty(completeDate);
        },
        error: function(error) {
			console.log(error);
        }
    })
    return isTodayAHoliday;
}

function updateWaittime() {
    if($('.wait-timing').length > 0) {
    $.ajax({
		url: $('.waittimeapi').text(),
		dataType: 'json',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify( {"id": "1"} ),
		processData: false,
		beforeSend: function(xhr) {
             xhr.setRequestHeader("x-api-key", $('.waittimekey').text());
        },
		success: function( data, textStatus, jQxhr ){
			if (data['results'][0]['estimatedWaitTimeSeconds'] != null || data['results'][0]['estimatedWaitTimeSeconds'] != undefined){
				var waittime = data['results'][0]['estimatedWaitTimeSeconds'];
				$('.wait-timing').contents().get(1).nodeValue = ' ' + waittime + " seconds";
			} else {
				$('.wait-timing').contents().get(1).nodeValue = ' ' + "0 minute";
			}
		},
		error: function( jqXhr, textStatus, errorThrown ){
			console.log( errorThrown );
		}
	});
	}
}

/* Function to check whether DST is observed or NOT */
Date.prototype.stdTimezoneOffset = function () {
    var jan = new Date(this.getFullYear(), 0, 1);
    var jul = new Date(this.getFullYear(), 6, 1);
    return Math.max(jan.getTimezoneOffset(), jul.getTimezoneOffset());
}

Date.prototype.isDstObserved = function () {
    return this.getTimezoneOffset() < this.stdTimezoneOffset();
}

function convertDateToEST(dt) {
    dt.setTime(dt.getTime() + dt.getTimezoneOffset()*60*1000);

    //Timezone offset for EST in minutes.
    var offset = dt.isDstObserved() ? -240 : -300; 
    var estDate = new Date(dt.getTime() + offset*60*1000);
    return estDate;
}

function updateOfficeOpenStatus() {
	var hoursToCheckFrom = '';

    if((todayDay >= 1) && (todayDay <= 5)) {
		hoursToCheckFrom = $('.mon-fri-hrs').text();
    }

    if(hoursToCheckFrom !== '') {
        var arr = hoursToCheckFrom.trim().split("-"),
            startTimeArr = arr[0].split(":"),
            endTimeArr = arr[1].split(":"),
            startTimeHour = startTimeArr[0],
            startTimeMin = startTimeArr[1].substring(0, 2),
            endTimeHour = endTimeArr[0],
            endTimeMin = endTimeArr[1].substring(0, 2),

            startDateTime = new Date(todayYear, todayMonth - 1, todayDate, startTimeHour, startTimeMin, 0, 0),
            endDateTime = new Date(todayYear, todayMonth - 1, todayDate, parseInt(endTimeHour) + 12, endTimeMin, 0, 0);

        var	startTime = startDateTime.getTime(),
            todayTime = convertDateToEST(today).getTime(),
            endTime = endDateTime.getTime(),
            todayAHoliday = isLeaveToday();

        if(!todayAHoliday && (startTime < todayTime) && (todayTime < endTime)) {
            $('.st-open').text($('.open-info-text').text());
            $('.st-tf-open').text('(' + $('.open-info-text').text() + ')');
        } else {
            $('.st-open').text($('.closed-info-text').text());
            $('.st-tf-open').text('(' + $('.closed-info-text').text() + ')');
            $('.wait-timing').hide();
        }

        if(todayAHoliday || todayDay == 0 || todayDay == 6) {
			$('.open-timing').hide();
        } else {
			$('.open-timing').show();
        }
    } else {
        $('.st-open').text($('.closed-info-text').text());
        $('.st-tf-open').text('(' + $('.closed-info-text').text() + ')');
		$('.open-timing').hide();
    }
}

/* Function to show working hours depending upon 
   current day of the week */
function updateWorkingHoursForToday() {
	if((todayDay >= 1) && (todayDay <= 5)) {
		$('.open-timing').contents().get(1).nodeValue = ' ' + $('.mon-fri-hrs').text().trim();
    } else {
		$('.wait-timing').hide();
    }
}

/* Function to format the phone number with hypens */
function formatPhoneNumber() {
    var phNumber = $('.phone-num').text();
    $('.st-phone-number, .st-tf-phone-number').text(phNumber.replace(/(\d{1})(\d{3})(\d{3})(\d{4})/, "$1-$2-$3-$4"));
}

$(document).ready(function() {
    if($('.contactdetails').length > 0 && $('.contactdetails .staticView').length <= 0) {
        updateOfficeOpenStatus();
        updateWorkingHoursForToday();
        updateWaittime();
    }
});