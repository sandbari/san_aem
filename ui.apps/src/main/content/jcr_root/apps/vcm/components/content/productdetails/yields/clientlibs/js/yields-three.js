var yields_three = function(data,labels){
    var divFundIdVal = $('body').find('div.taxTable').data('fundid');
    jsonData = data.filter((jdata) => {
        return (jdata.fundid === divFundIdVal);
    });

    jsonData.forEach(funds => {
        var appendTodiv = $('<div />', { class: 'taxbondstitlewrapper' }).appendTo('.taxTable');
        var taxTitle = $('<h3 />', { class: 'title', text: labels.taxyield }).appendTo(appendTodiv);
        $('<span />', { class: 'title-asof', text: ' (As of ' + funds.asofdate + ')' }).appendTo(taxTitle);
		var $tablecontainer = $('<div />', { class: 'yieldTableContainer' }).appendTo('.taxTable');
        var $tcontainer = $('<div />', { class: 'taxTableContainer table-wrap' }).appendTo('.yieldTableContainer');
        var $taxTable = $('<table />', { class: 'table table-striped' })
        var $taxTableBody = $taxTable.append('<tbody />').children('tbody');
        $taxTable.appendTo($tcontainer);


        funds.tax_yields.forEach(tax => {

            var $taxtr = $('<tr />', { class: '' }).appendTo($taxTableBody);
            $('<td />', { class: '', text: tax.name }).appendTo($taxtr);
            $('<td />', { class: '', text: parseFloat(tax.value_one || 0).toFixed(2) }).appendTo($taxtr);
            $('<td />', { class: '', text: parseFloat(tax.value_two || 0).toFixed(2) }).appendTo($taxtr);
            $('<td />', { class: '', text: parseFloat(tax.value_three || 0).toFixed(2) }).appendTo($taxtr);
            $('<td />', { class: '', text: parseFloat(tax.value_four || 0).toFixed(2) }).appendTo($taxtr);

        });

    });
	$(".yields_taxable-equivalent_table .yieldTableContainer").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".yields_taxable-equivalent_table .yieldTableContainer").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));
}

var yieldTaxEqvErrorHandlingFuc = function () {
	$('.yields_taxable-equivalent_table').css("visibility", "visible");
	var appendTodiv = $('<div />', { class: 'taxbondstitlewrapper' }).appendTo('.taxTable');
    $('<h3 />', { class: 'title', text: 'Taxable Equivalent Yields' }).appendTo(appendTodiv);
	$(".error-message-yieldstaxeqv p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-yieldstaxeqv').css("display", "none");
    $('.error-message-yieldstaxeqv').css("display", "block");
}