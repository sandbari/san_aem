var getOptionValue = [];
var selectedAnnualExpense = loadClass;
var annual_expense_body = function(data){
    var apiClasses = [];
    data.forEach(element => {
     apiClasses.push(element.shareClass);
    });

    if (!(apiClasses.indexOf(selectedAnnualExpense) > -1)) {
        selectedAnnualExpense = apiClasses[0];
    }
    var expense_userValue = sharedJS.getCookie("subdomain_user_entity_type");
    if(expense_userValue == "usaa_member"){
        getOptionValue.push(selectedAnnualExpense);
    }
	data = sort_by_key(data, "shareClass");
	var headRow = $("<tr/>");
	var tHead = $("<thead/>");
	var tBody = $("<tbody/>");
	var tableLabels = [];
	let grossExpRatio = [];
	let netExpRatio = [];
	if (data.length) {
		//$(".annual-expense-asof").html(data[0].as_of_date);
		let expenseData = data;
		expenseData.forEach(expData => {
			if (!(!!getOptionValue && getOptionValue.length >0) || getOptionValue.indexOf(expData.shareClass) > -1) {
				tableLabels.push(expData.shareClass);
				tHead.append(headRow);
				$(".annual-expense.header-label").append(data.annualexpenses);
				$(".annualexpense-table").append(tHead);
				if ((expData === null) || (expData === undefined) || (expData === "")) {
					expData = "N/A"
				} else {
					var bodyRow = $("<tr/>");
					grossExpRatio.push('<td>' + parseFloat(expData.gross_exp_ratio).toFixed(2) + '</td>');
					netExpRatio.push('<td>' + parseFloat(expData.net_expense_ratio).toFixed(2) + '</td>');
				}
			}
		})
		headRow.append(`<th></th>`);
		tableLabels.forEach(heading => {
			headRow.append(`<th>Class ${heading}</th>`);
		})
	}
	var bodyRow = "<tr><td>Gross Expense Ratio (%)</td>";
	grossExpRatio.forEach(ratio => {
		bodyRow = bodyRow + ratio;
		
	})
	bodyRow = bodyRow + "</tr><tr><td>Net Expense Ratio (%)</td>";
	netExpRatio.forEach(ratio => {
		bodyRow = bodyRow + ratio;
	})
	bodyRow = bodyRow + "</tr>";
	tBody.append(bodyRow);

	$(".annualexpense-table").append(tBody);
	
}

function sort_by_key(array, key) {
	return array.sort(function (a, b) {
		var x = a[key]; var y = b[key];
		return ((x < y) ? -1 : ((x > y) ? 1 : 0));
	});
}

var annual_expense_head = function(data){
    if (data) {
		$(".annual-expense.header-label").append(data.annualexpenses);
		$(".annualexpense-asof-label").append(data.annualexpensesAsOf);
	}
}

var beforeAnnualExpenseLoadFuc = function () {
    $('.dynamic-loader-annualexpense').css("display", "block");
    $('.error-message-annualexpense').css("display", "none");
}

var annualExpenseErrorHandlingFuc = function () {
	$('.annual-expense').css("visibility", "visible");
	$(".annual-expense.header-label").append(ajaxData_productLabels.annualexpenses);
	$(".error-message-annualexpense p").html(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-annualexpense').css("display", "none");
    $('.error-message-annualexpense').css("display", "block");
}

$(document).ready(function() {
    $(".annual-table-data").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".annual-table-data").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));
    
});