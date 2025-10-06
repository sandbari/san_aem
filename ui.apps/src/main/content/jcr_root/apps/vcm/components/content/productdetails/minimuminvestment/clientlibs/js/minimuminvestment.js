var selectedMinimumInvestment= loadClass;
var minimum_investment = function(labelsdata,data){
	var headerinvestment = "";
    var headerinvestamount = "";
    var apiClasses = [];
    data.forEach(element => {
     apiClasses.push(element.shareClass);
    });

    if (!(apiClasses.indexOf(selectedMinimumInvestment) > -1)) {
        selectedMinimumInvestment = apiClasses[0];
    }
    minimuminvestment_head(labelsdata);
	minimuminvestment_body(labelsdata,data);
	
}


function minimuminvestment_head(data) {

    if (data) {
        $(".minimuminvestment.header-label").append(data.minimuminvestment);
        headerinvestment = data.MinimumInvestment.InvestmentType;
        headerinvestamount = data.MinimumInvestment.amount;

    }

}

function onChangeMinimumInvestmentClass(selectClass){
    selectedMinimumInvestment = selectClass;
    if($('.minimuminvestment').length > 0 && $.trim($("div.minimuminvestment").html()).length > 0) {
    minimuminvestment_body(ajaxData_productLabels,ajaxData_minimuminvestment);
    }
}
selectboxChange(onChangeMinimumInvestmentClass);

var beforeMinimumInvestLoadFuc = function () {
    $('.dynamic-loader-minimuminvest').css("display", "block");
    $('.error-message-minimuminvest').css("display", "none");
}

var minimumInvestErrorHandlingFuc = function () {
	$('.min-invest').css("visibility", "visible");
	$(".minimuminvestment.header-label").append(ajaxData_productLabels.minimuminvestment);
	$(".error-message-minimuminvest p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-minimuminvest').css("display", "none");
    $('.error-message-minimuminvest').css("display", "block");
}

function minimuminvestment_body(labelsdata,data) {
    
    var headRow = $("<tr/>");
    var tHead = $("<thead/>");
    var tBody = $("<tbody/>");
    var tFooter = $("<tfoot/>");
    headerinvestment = labelsdata.MinimumInvestment.InvestmentType;
    headerinvestamount = labelsdata.MinimumInvestment.amount;
    if (data && data.length) {
        data.filter((item) => {
            if (selectedMinimumInvestment == item.shareClass) {
            $(".minimum-investment_table").html("");
                 headRow.append(`<th scope='col'>${headerinvestment}</th>`);
                headRow.append(`<th scope='col'>${headerinvestamount}</th>`);
                tHead.append(headRow);
                $(".minimum-investment_table").append(tHead);
                var tBody = $("<tbody/>");
                let keys = Object.keys(item);

                keys.forEach((key, index) => {
	
                    if (key == 'init_invest') {
                    	tableRenderforInvest(item, key);
						 tBody.append(row);
                    } else if (key == 'subsequent_invest_min') {
                    	tableRenderforInvest(item, key);
						 tBody.append(row);
                    } 
                   
                })

                $('.minimum-investment_table').append(tBody);

                function toTitleCaseforInvest(str) {
					if(str == 'init invest')
					{
						str = 'Minimum Investment'
					} 
					else if(str == 'subsequent invest min'){
						str = 'Subsequent Min. Investment'
					}
					return str.replace(/(?:^|\s)\w/g, function (match) {
						
                        return match.toUpperCase();
                    });
                }
                function tableRenderforInvest(data, index) {
                    let x = index;
                    row = $("<tr/>");
                    let td = '<td class="text-left">' + toTitleCaseforInvest(index.replace(/_/g, " ")) + '</td>';
                    if (data[index] == NaN || data[index] == 'null' || data[index] == null) {
                        data[index] = 'N/A';
                        td = td + '<td>' + data[index] + '</td>';
                    }
                    else {
                        td = td + '<td>' + "$" + Math.floor(data[index]) + '</td>';
                    }
                    row.append(td);
                }
            }
        })

    }
}