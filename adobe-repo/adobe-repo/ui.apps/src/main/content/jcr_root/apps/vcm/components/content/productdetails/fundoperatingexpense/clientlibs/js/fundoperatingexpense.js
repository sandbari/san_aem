var selectedFundExpense = loadClass;
var fundoperatingexpense = function(data,labelsdata){
		fundoperatingexpense_body(data,labelsdata);
		fundoperatingexpense_head(labelsdata);
}
function fundoperatingexpense_body(data,labelsdata){
     var headRow = $("<tr/>");
     var tHead = $("<thead/>");
     var tBody = $("<tbody/>");
     var tFooter = $("<tfoot/>");
     var expenseLableValue;
     var apiClasses = [];
     data.forEach(element => {
      apiClasses.push(element.share_class);
     });

     if (!(apiClasses.indexOf(selectedFundExpense) > -1)) {
             selectedFundExpense = apiClasses[0];
     }
     var headerfee = labelsdata.AnnualFundExpense.feetype;
     var headeramount = labelsdata.AnnualFundExpense.amount;
     $(".Annual-FundExpense-table").html("");

     if (data && data.length) {
         data.filter((item) => {
             if (selectedFundExpense == item.share_class) {
            	 /* $(".Annual-FundExpense-asof").html(item.as_of_date); */                 
            	 headRow.append(`<th scope='col'>${headerfee}</th>`);
                 headRow.append(`<th scope='col'>${headeramount}</th>`);
                 tHead.append(headRow);
                 $(".Annual-FundExpense-table").append(tHead);
                 var tBody = $("<tbody/>");
                 let keys = Object.keys(item.fees);
                 keys.forEach((key, index) => {
                	 let lableKeys = Object.keys(ajaxData_productLabels.AnnualFundExpense);
                	 
                	 lableKeys.forEach((lablekey, indexvalue) => {
                	 if (key == 'average_net_expenses') {
                		 expenseLableValue = ajaxData_productLabels.AnnualFundExpense.average_net_expenses;
                		 expenseTableRender(item.fees[key], expenseLableValue);
                     } else if (key == 'distribution_fees') {
                    	 expenseLableValue = ajaxData_productLabels.AnnualFundExpense.distribution_fees;
                    	 expenseTableRender(item.fees[key], expenseLableValue);
                     } else if (key == 'management_fees') {
                    	 expenseLableValue = ajaxData_productLabels.AnnualFundExpense.management_fees;
                    	 expenseTableRender(item.fees[key], expenseLableValue);
                     }
                     else if (key == 'other_expenses') {
                    	 expenseLableValue = ajaxData_productLabels.AnnualFundExpense.other_expenses;
                    	 expenseTableRender(item.fees[key], expenseLableValue);
                     }
                     else if (key == 'waiver_expense_reimbursement') {
                    	 expenseLableValue = ajaxData_productLabels.AnnualFundExpense.waiver_expense_reimbursement;
                    	 expenseTableRender(item.fees[key], expenseLableValue);
                     }
                     else if (key == 'total_expense_ratio') {
                    	 expenseLableValue = ajaxData_productLabels.AnnualFundExpense.total_expense_ratio;
                    	 expenseTableRender(item.fees[key], expenseLableValue);
                     }
                	 });
                     tBody.append(row);
                 })
                 $('.Annual-FundExpense-table').append(tBody);

                 function expenseTableRender(data, index) {
                     let x = index;
                     row = $("<tr/>");
                     let td = '<td class="text-left">' + toTitleCase(index.replace(/_/g, " ")) + '</td>';
                     if (data == NaN || data == 'null' || data == null) {
                         data = 'N/A';
                         td = td + '<td>' + data + '</td>';
                     }
                     else {
							if (data < 0){
								td = td + '<td>' + (parseFloat(data) * 100).toFixed(2) + "%"  + '</td>';
							}
							else{
                                td = td + '<td>' +  (parseFloat(data) * 100).toFixed(2) + "%" + '</td>';
							}
                     }
                     row.append(td);
                 }
             }
         })

     }
}

function onChangeFundExpenseClass(selectClass){
	selectedFundExpense = selectClass;
	if($('.fundoperatingexpense').length > 0 && $.trim($("div.fundoperatingexpense").html()).length > 0) {
    fundoperatingexpense_body(ajaxData_fundoperatingexpense, ajaxData_productLabels);
	}
}
selectboxChange(onChangeFundExpenseClass);

function fundoperatingexpense_head(data){
	 if (data) {
         $(".fundoperating-expense.header-label").append(data.annualfundexpenses);
     }
}

var beforeFundExpenseLoadFuc = function () {
    $('.dynamic-loader-fundexpense').css("display", "block");
    $('.error-message-fundexpense').css("display", "none");
}

var fundExpenseErrorHandlingFuc = function () {
	$('.fund-expense').css("visibility", "visible");
	$(".fundoperating-expense.header-label").append(ajaxData_productLabels.annualfundexpenses);
	$(".error-message-fundexpense p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-fundexpense').css("display", "none");
    $('.error-message-fundexpense').css("display", "block");
}