var characteristics_five = function(data,labels){
	characteristics_five_tablelabelData(labels);
    characteristics_five_tableData(data);
	characteristics_five_tablehead(data);
}

// Fetching data from AEM JSON and appending to table heading
function characteristics_five_tablelabelData(data) {
    if (data) {
        $(".charFiveHead").prepend(data.characteristics);
        headername = data.Characteristics.fund;
    }
}

// Fetching data from API and appending to table head
function characteristics_five_tablehead(data) {
    $(".characteristicsvariant-five-asof").append(data.characteristics.as_of_date);
    var tHead = $("<thead/>");
    var headRow = $("<tr/>");
    if (data) {
        headRow.append(`<th scope='col'><span class="sr-only">Characteristic</span></th>`);
        let results = data.characteristics.data;
        results.forEach((listTitle, index) => {
            if (index === 0) {
                headRow.append(`<th scope='col'>${headername}</th>`);
            } else {
                headRow.append(`<th scope='col'>${listTitle['benchmark_name']}(%)</th>`);
            }
        });
        tHead.append(headRow);
        $(".characteristicsvariant-five-table").append(tHead);
    }
}

function characteristics_five_tableData(data) {
    var tBody = $("<tbody/>");
    let keys = Object.keys(data.characteristics.data[0]);
    keys.forEach((key, index) => {
        if (index != keys.length - 1) {
            tableRender(data.characteristics.data, key);
        }
        tBody.append(row);
    })
    $('.characteristicsvariant-five-table').append(tBody);
    hideGradient();
}

var beforeCharVarFiveLoadFuc = function () {
    $('.dynamic-loader-charVarFive').css("display", "block");
    $('.error-message-charVarFive').css("display", "none");
}

var charVarFiveErrorHandlingFuc = function () {
    $('.dynamic-loader-charVarFive').css("display", "none");
    $('.error-message-charVarFive').css("display", "block");
}

$(document).ready(function() {
    $(".characteristicsvariant-five-table-data").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".characteristicsvariant-five-table-data").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));
});