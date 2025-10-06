var qualitystructure_two = function(selectedTab, data){
		var headRow = $("<tr/>");
        var tHead = $("<thead/>");
        var tBody = $("<tbody/>");
        if (data.length) {
            data.filter((item) => {

                $(".Qualitystructurevariant-two-asof").html("");
                $(".Qualitystructurevariant-two-asof").append(item.asOf);
                $(".Qualitystructurevariant-two-table").html("");
                 headRow.append(`<th scope='col'><span class="sr-only">Quality rating</span></th>`);
                item.xAxis.categories.map(ele => {
                    headRow.append(`<th scope='col'>${ele + "(%)"}</th>`);
                });
                tHead.append(headRow);
                $(".Qualitystructurevariant-two-table").append(tHead);
                item.series.map(ele => {
                    var bodyRow = $("<tr/>");
                    bodyRow.append($(`<td class='text-left'>${ele.name.replace(/[\[\]']+/g, '')} <div class='disclaimerTxt'>
									<p>GNMA securities are backed by the same full faith and credit guarantee offered by U.S. Treasury securities which is an unconditional commitment to pay interest and principal on debt. This guarantee applies only to the underlying securities in the Fund and not to the Victory Fund for Income.</p>
								</div></td>`));
                        //$("td:nth-of-type(1)").append("disclaimertxt");
                        // $('.disclaimerTxt').clone().appendTo( ".Qualitystructurevariant-two tbody tr td:nth-of-type(1)" );
                     if ((ele.data === null) || (ele.data === "null") || (ele.data === "") ) {
                        ele.data = "N/A"
                         }else{
                        ele.data = parseFloat(ele.data);
                         }
                     bodyRow.append($(`<td>${ele.data + "%"}</td>`));
                    
                       
                 
                    tBody.append(bodyRow);
                });
                $(".Qualitystructurevariant-two-table").append(tBody);
            })
        }
}