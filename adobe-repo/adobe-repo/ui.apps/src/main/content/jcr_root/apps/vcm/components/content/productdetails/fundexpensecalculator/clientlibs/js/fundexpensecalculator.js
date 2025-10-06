var selectedExpenseCalculator = loadClass;
var fundexpensecalculator_body = function(data,amount,labelsdata){
	let BeginningBalance = 0;
    let EndingBalance = 0;
    let NetPerformance = 0;
    let PostExpEndBalance = 0;
    let LoadBalance = 0;
    let totalAnnualExpense = 0;
    let totalDollerImpact = 0;
    var headeryear = labelsdata.CalculateExpenses.year;
    var headerexp = labelsdata.CalculateExpenses.expenses;
    var headerdoller = labelsdata.CalculateExpenses.dollarimpactonreturn;
        totalAnnualExpense = 0;
        totalDollerImpact = 0;
        let netExpense, maxExpense, ExpenseRatio;
        var headRow = $("<tr/>");
        var tHead = $("<thead/>");
        var tBody = $("<tbody/>");
        var tFooter = $("<tfoot/>");
        var footRow = $("<tr/>");
        var apiClasses = [];
        data.forEach(element => {
         apiClasses.push(element.share_class);
        });

        if (!(apiClasses.indexOf(selectedExpenseCalculator) > -1)) {
                selectedExpenseCalculator = apiClasses[0];
        }

    if (data && data.length) {
        data.filter((item) => {
            if (selectedExpenseCalculator == item.share_class) {
                netExpense = item.fees.average_net_expenses;
                maxExpense = item.fees.maximum_expense_rate;
                ExpenseRatio = item.fees.total_expense_ratio;
                //$(".AnnualcalculateFund-Expense-asof").html(item.as_of_date);
                $(".AnnualcalculateFund-Expense-table").html("");
                headRow.append(`<th scope='col'>${headeryear}</th>`);
                headRow.append(`<th scope='col'>${headerexp}</th>`);
                headRow.append(`<th scope='col'>${headerdoller}</th>`);
                tHead.append(headRow);
                $(".AnnualcalculateFund-Expense-table").append(tHead);


                function calculator(year, investAmount) {
                    const estReturnRate = 0.05;
                    let AnnualExpense = 0;
                    if (year == 1) {
                        LoadBalance = (investAmount * (1 - maxExpense));
                        PostExpEndBalance = ((LoadBalance * (estReturnRate - netExpense)) + LoadBalance);
                        AnnualExpense = ((((LoadBalance + PostExpEndBalance) / 2) * netExpense) + (maxExpense * investAmount));
                    } else if (year == 2) {
                        LoadBalance = PostExpEndBalance;
                        PostExpEndBalance = ((LoadBalance * (estReturnRate - netExpense)) + LoadBalance);
                        AnnualExpense = (((LoadBalance + PostExpEndBalance) / 2) * netExpense);
                    } else if (year >= 3) {
                        LoadBalance = PostExpEndBalance;
                        PostExpEndBalance = ((LoadBalance * (estReturnRate - ExpenseRatio)) + LoadBalance);
                        AnnualExpense = (((LoadBalance + PostExpEndBalance) / 2) * ExpenseRatio);
                    }

                    if (year === 1) {
                        BeginningBalance = amount;
                        EndingBalance = (BeginningBalance * (1 + estReturnRate));
                    } else {
                        BeginningBalance = EndingBalance;
                    }

                    EndingBalance = (BeginningBalance * (1 + estReturnRate));
                    const GrossPerformance = EndingBalance - BeginningBalance;
                    const p1 = maxExpense * investAmount;

                    if (year === 1) {
                        NetPerformance = PostExpEndBalance - LoadBalance - p1;
                    } else {
                        NetPerformance = PostExpEndBalance - LoadBalance;
                    }

                    dollarImpact = GrossPerformance - NetPerformance;
                    dollerImpact = parseFloat(dollarImpact).toFixed(2);

                    return { val: parseFloat(AnnualExpense).toFixed(2), dollerImpact };

                }

                for (var i = 1; i <= 10; i++) {
                    let { val, dollerImpact } = calculator(i, amount);
                    display(i, val, dollerImpact);
                }

                function display(year, annualExpense, dollerImpact) {
                    var bodyRow = $("<tr/>");
                    bodyRow.append($(`<td>${year}</td>`));
                    bodyRow.append($(`<td>${formatCurrency(annualExpense)}</td>`));
                    bodyRow.append($(`<td>${formatCurrency(dollerImpact)}</td>`));
                    tBody.append(bodyRow);
                    totalAnnualExpense = parseFloat(totalAnnualExpense) + parseFloat(annualExpense);
                    totalDollerImpact = parseFloat(totalDollerImpact) + parseFloat(dollerImpact);
                }
                $(".AnnualcalculateFund-Expense-table").append(tBody);
            }
        })

        footRow.append(`<td>Total</td>`);
        footRow.append(`<td>$${parseFloat(totalAnnualExpense).toFixed(2)}</td>`);
        footRow.append(`<td>$${parseFloat(totalDollerImpact).toFixed(2)}</td>`);
        tFooter.append(footRow);
        $(".AnnualcalculateFund-Expense-table").append(tFooter);
    }
}

function formatCurrency(amount) {
    var formatter = new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
      });
return formatter.format(amount);
}

var fundexpensecalculator_head = function(data){
    if (data) {
    	$(".FundExpense-Calculator.header-label").append(data.calculateexpenses);
    }
}

function onChangeExpenseCalculatorClass(selectClass){
	selectedExpenseCalculator = selectClass;
	let amountInvested = parseFloat($('.foundCont').val());
	if($('.fundexpensecalculator').length > 0 && $.trim($("div.fundexpensecalculator").html()).length > 0) {
	fundexpensecalculator_body(ajaxData_fundexpensecalculator, amountInvested,ajaxData_productLabels);
	}
}
selectboxChange(onChangeExpenseCalculatorClass);

var beforeCalculateExpenseLoadFuc = function () {
    $('.dynamic-loader-calculateexpense').css("display", "block");
    $('.error-message-calculateexpense').css("display", "none");
}

var calculateExpenseErrorHandlingFuc = function () {
	$('.expense-calculator').css("visibility", "visible");
	$(".FundExpense-Calculator.header-label").append(ajaxData_productLabels.calculateexpenses);
	$(".error-message-calculateexpense p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-calculateexpense').css("display", "none");
    $('.error-message-calculateexpense').css("display", "block");
}

$(document).ready(function() {
var amountInvested;
$('.calculateBtn').click(function () {
    let inputVal = $('.foundCont').val();
    $('.invalid_error').text('');
    inputVal = inputVal && inputVal != '' ? inputVal : 0
    if (inputVal < 0) {
      $('.invalid_error').text('Please enter a valid numeric value');
      inputVal = 0;
    }
    amountInvested = parseFloat(inputVal);
    fundexpensecalculator_body(ajaxData_fundexpensecalculator, amountInvested,ajaxData_productLabels);
});

$('.foundCont').keypress(function (e) {
    if($(this).val().length > 9) {
        e.preventDefault();
    }
});

});