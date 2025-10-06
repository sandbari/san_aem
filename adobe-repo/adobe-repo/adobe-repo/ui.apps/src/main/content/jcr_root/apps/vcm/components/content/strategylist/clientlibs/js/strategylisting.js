var jsonData = [],
    URLData = [],
    aemJsonData = [];
var strategyFilterList1 = [],
    strategyFilterList0 = [],
    cSfl = [];
var checkarr1 = [],
    checkarr2 = [];
var checkboxUl, sltable, sfl, tbodyData;
var windowsize = $(window).width();

$(document).ready(function() {

    $('body').on('mousedown', function() {
        $('body').addClass('using-mouse');
    });
    // Re-enable focus styling when Tab is pressed
    $('body').on('keydown', function(event) {
        if (event.keyCode === 9) {
            $('body').removeClass('using-mouse');
        }
    });

	var apiCallFailureMsg = $('#apiFailureMessage').val();
    var strategyEndPoint = $('#strategyListEndpoint').val();
    var apikey = $('#strategyApiKey').val();
    var stLabelsJsonStr = $("#stLabels").val();
    if (stLabelsJsonStr != null || stLabelsJsonStr != undefined)
    {
	var strategyListingLabels = JSON.parse(stLabelsJsonStr);}
    $('#strategiesTable').css("display", "none");
    $('.sidebar-collapse').hide();
    $('.search-strategies-container').hide();
    //local JSON call
	let fundData = $("#strategyListData").html();

    if ($('.Strategy-container').length && (typeof fundData != 'undefined')) {

        aemJsonData = JSON.parse(fundData);
        URLData = aemJsonData.strategyMapList;
  }
  if (strategyEndPoint != null && (typeof strategyEndPoint != 'undefined'))  {
  //API Json Call
  $.ajax({
    type: "GET",
     url: strategyEndPoint,
     contentType: "application/x-www-form-urlencoded; charset=UTF-8",
    dataType: "json",
	beforeSend: function(xhr) {
                $('.dynamic-loader').css("display", "block");
                xhr.setRequestHeader("x-api-key", apikey);
                
            },
        success: function(data) {
            $('.dynamic-loader').css("display", "none");
            jsonData = data;
            $('.strategy-list').show();
            $('#strategiesTable').css("display", "table");
            $('.sidebar-collapse').show();
            $('.search-strategies-container').show();

            URLData.forEach(function(aJsnData) {
                jsonData.forEach(function(inst) {
                    if (aJsnData.strategyCode === inst.uniqueCode) {
                        aJsnData.strategyName = inst.strategyName;
                        aJsnData.category = inst.category;
                        inst.strategyUrl = aJsnData.strategyUrl;
                        inst.assetClass = aJsnData.assetClass;
                        inst.stratName = aJsnData.strategyName;
                        inst.categoryName = aJsnData.category;
                        inst.strategyPageTitle = aJsnData.strategyPageTitle;
                        strategyFilterList0.push(inst.franchise);
                        strategyFilterList1.push(inst.assetClass);
                    }
                });
            });
            aemJsonData.filters.forEach(function(aemFilterData, i) {
                var filterTitle = aemFilterData.name;
                targetID = filterTitle.replace(/\s/g, ''); //name to id convertion

                //create accordion UL Structure
                var appendtodiv = $('<div />', { class: 'filter-header', 'tabindex': '0', 'data-toggle': 'collapse', 'data-parent': '#accordion', 'aria-expanded': 'true', 'role': 'button', 'data-target': '#' + targetID }).appendTo('.sidebar-collapse .accordion');
                $('<p />', { class: 'filter-title', text: filterTitle }).appendTo(appendtodiv);
                $('<span />', { 'aria-hidden': 'true' }).appendTo(appendtodiv); //filter title
                var checkboxUldiv = $('<div />', { id: targetID, class: 'filter-body collapse show', 'data-filtertype': targetID }).appendTo('.sidebar-collapse .accordion');
                checkboxUl = $('<ul/>', { class: 'filter-list', id: 'filterList_' + i }).appendTo(checkboxUldiv);
            });

            //creating filter list items with check box (sfl :strategic filter list)
            var k,
                strategyFilterList = [strategyFilterList0, strategyFilterList1];

            for (k = 0; k < strategyFilterList.length; k++) {
                let sfl = strategyFilterList[k].filter((c, index) => {
                    return strategyFilterList[k].indexOf(c) === index; // return the filters the duplicates
                });

                let sflConcat = sfl.concat(aemJsonData.filters[k].values); //merging the new array with Json array
                let comparedSfl = sflConcat.filter((c, indx) => {
                    return sflConcat.indexOf(c) !== indx; //returns the matched list items
                })

                cSfl.push(comparedSfl);
            }
            //performing custom sort
            cSfl.forEach(function(sflitm, idx) {
                var finalSfl = [];
                var getNewarray = cSfl[idx];
                if (idx == 0) {
                    var sortOrder = [];
                } else if (idx == 1) {
                    var sortOrder = [];
                    if(strategyListingLabels.asset_class_order){
                        sortOrder = strategyListingLabels.asset_class_order.split(',');
                    }
                }
                $.each(sortOrder, function(key, value) {
                    var index = $.inArray(value, getNewarray);
                    if (index != -1) {
                        finalSfl.push(value);
                    }
                });
                $.each(getNewarray, function(key, value) {
                    var index = $.inArray(value, sortOrder);
                    if (index == -1) {
                        finalSfl.push(value);
                    }
                });
                cSfl[idx] = finalSfl;
                //creating filter list
                cSfl[idx].forEach(function(SfilterListItem, y) {
                    x = idx + 1;
                    var checkboxLi = $('<li/>', { class: 'filter-text listCheck' }).appendTo('#filterList_' + idx);
                    $('<input />', { onClick: 'check("' + SfilterListItem + '",' + x + ')', type: 'checkbox', id: 'check' + idx + y, class: 'styled-checkbox', value: SfilterListItem }).appendTo(checkboxLi);
                    $('<label />', { for: 'check' + idx + y, text: SfilterListItem }).appendTo(checkboxLi);
                });
            });
            sidebarToggle();
            tbodyData = jsonData.filter(function(data) {
                return data.strategyPageTitle;
            });
            createStrategyTable(tbodyData,strategyListingLabels);
            tableInfo();
        },
    error: function (data) {
     $('.dynamic-loader').css("display", "none");
     $('.Strategy-container').text(apiCallFailureMsg);
    }
  });
  }
  //data table search 
  $('#searchStrategies').keyup(function () {
    //sltable.search($(this).val()).draw();
    filterTableRowsByStringVal($(this).val());
  });

  function filterTableRowsByStringVal(searchKey){
      $.fn.dataTable.ext.search.pop();
      var table = document.getElementById("strategiesTable");
      var tBody = table.getElementsByTagName('tbody')[0];
      var tr = tBody.getElementsByTagName('tr');
      var total = tr.length;
      for (var i = 0; i < tr.length; i++) {
           td = tr[i].getElementsByTagName("td")[0]; // for column one
           //* ADD columns here that you want you to filter to be used on */
          if (td) {
              var tdStr=td.innerHTML.match(/<a [^>]+>([^<]+)<\/a>/)[1];
          if ((tdStr.toUpperCase().indexOf(searchKey.toUpperCase()) > -1))  {
                  tr[i].style.display = "";
              } else {
                  tr[i].style.display = "none";
              }
          }
      }
      sltable.draw();
  }

  //scroll to Top
  $("#scrollTop").click(function() {
    $("html, body").animate({ scrollTop: 0 }, "slow");
    return false;
  });
  $('#strategiesTable th').click(function() {
      if ($('#strategiesTable th').attr("aria-label")) {
          //$(this).removeAttr('aria-label');
          setTimeout(function() {
              $('#strategiesTable th').removeAttr('aria-label');
          }, 100);
      }
      setTimeout(function() {
          $('#strategiesTable th').removeAttr('aria-label');
      }, 100);
  });
});

//sidebar toggle
function sidebarToggle() {
  $('.angle--arrow, .strategy-list .fund-title').click(function () {
    $(".strategy-list").toggleClass("active");
    $(".sidebar-collapse").toggleClass("filter-width");
    $(".content").toggleClass("pc-content-width");
    //for filters
    if (windowsize <= 768) {
        var setHeight = window.innerHeight;
        var headerHeight = $('header').height();
        var filterHeight = setHeight - headerHeight - 60;
        $('.strategy-list .sidebar-collapse .accordion').css({
            "height": filterHeight,
            "overflow-y": "auto",
            "margin-bottom": 0
        });
    }
    if($(".strategy-list").hasClass("active")) {
    	$(".sidebar-collapse .arrow-container .strategy-filter-lbl").attr("aria-expanded","true");
    } else {
    	$(".sidebar-collapse .arrow-container .strategy-filter-lbl").attr("aria-expanded","false");
    }
  });
  keyPressCollapsing();

}

function keyPressCollapsing() {
    $(".strategy-list .fund-title").on('keydown', function(kbdEvent) {
        var ke = kbdEvent.which;
        if (13 == ke || 32 == ke) {
            kbdEvent.preventDefault();
            $('.angle--arrow, .strategy-list .fund-title').click();
        }
        if (37 == ke) { // close
            $('.strategy-list').removeClass('active');
            $('.sidebar-collapse').removeClass('filter-width');
        }
        if (39 == ke) { //open
            $('.strategy-list').addClass('active');
            $('.sidebar-collapse').addClass('filter-width');
        }
    });
    $('.filter-header').on('keydown', function(kbdEvent) {
        var e = kbdEvent.which;
        if (13 == e || 32 == e) {
            kbdEvent.preventDefault();
            $(this).toggleClass("collapsed").next().toggleClass('show');
            $(this).hasClass("collapsed") ? $(this).attr("aria-expanded", "false") : $(this).attr("aria-expanded", "true");
            return false;
        }
    });


    $(".styled-checkbox").focusin(function() {
        $(this).parent(".filter-text").css("border", "2px solid #000");
        $(this).parent("body.using-mouse .filter-text").css("border", "2px solid transparent");
    });
    $(".styled-checkbox").focusout(function() {
        $(this).parent(".filter-text").css("border", "2px solid transparent");
    });
}

//create Strategy Table integrate with data table plugin
//create Strategy Table integrate with data table plugin
function createStrategyTable(jsData,strategyListingLabels) {

    $.fn.dataTable.ext.errMode = 'none';
    sltable = $('#strategiesTable').DataTable({
        bJQueryUI: true,
        bFilter: true,
        bInfo: false,
        paging: false,
        aaData: jsData,
        searching: false,
        "language": {
            "aria": {
                "sortAscending": "",
                "sortDescending": ""
            }
        },
        aoColumns: [{
                data: 'strategyPageTitle',
                "render": function(data, type, row) {
                    if (type === 'display') {
                        if (typeof row.strategyUrl !== 'undefined' && row.strategyUrl !== null) {
                            pageLink = row.strategyUrl
                        } else {
                            pageLink = "#"
                        }
                        return '<a href="' + pageLink + '">' + data + '</a>';
                    } else {
                        return data;
                    }
                }
            },
            { data: 'assetClass' },
            { data: 'categoryName' }
        ]
    });

    //disabled warning due col loading as empty value
    $('#strategiesTable').on('error.dt', function(e, settings, techNote, message) {
        console.log(message);
    });
    AxControls();
    if(strategyListingLabels){
        $(".filter").append(strategyListingLabels.filter);
        $(".strategy_name").append(strategyListingLabels.strategy_name);
        $(".asset_class").append(strategyListingLabels.asset_class);
        $(".category").append(strategyListingLabels.category);
        $(".to_top").append(strategyListingLabels.to_top);
        $(".str_close").append(strategyListingLabels.close);
        if (windowsize <= 768) {
            $('<h2>', { class: 'filter', text: strategyListingLabels.filter }).prependTo($(".sidebar-collapse .accordion"));
        }
    }
}

//performs filter
function check(val, j) {

    if (j == 1) {
        var index = checkarr1.indexOf(val);
        if (index !== -1) {
            checkarr1.splice(index, 1); //will get checked val
        } else {
            checkarr1.push(val)
                //console.log(val);
        }
    }
    if (j == 2) {
        var index = checkarr2.indexOf(val);
        if (index !== -1) {
            checkarr2.splice(index, 1); //will get checked val
        } else {
            checkarr2.push(val)
                //console.log(val);
        }
    }
    //console.log(checkarr1, checkarr2);
    var res = [];
    if (checkarr1.length > 0 && checkarr2.length > 0) {

        jsonData.forEach(function(init) {
            if (checkarr1.indexOf(init.franchise) !== -1 && checkarr2.indexOf(init.assetClass) !== -1) {
                res.push(init);
            }
        });
        filteredarr = res.filter(function(data) {
            return data.stratName;
        });;
    } else if (checkarr1.length > 0 && checkarr2.length == 0) {
        jsonData.forEach(function(init) {
            if (checkarr1.indexOf(init.franchise) !== -1) {
                res.push(init);
            }
        });
        filteredarr = res.filter(function(data) {
            return data.stratName;
        });;
    } else if (checkarr1.length == 0 && checkarr2.length > 0) {
        jsonData.forEach(function(init) {
            if (checkarr2.indexOf(init.assetClass) !== -1) {
                res.push(init);
            }
        });
        filteredarr = res.filter(function(data) {
            return data.stratName;
        });;
    } else {
        filteredarr = jsonData.filter(function(data) {
            return data.stratName;
        });
    }
    $("#strategiesTable").dataTable().fnDestroy();
    createStrategyTable(filteredarr);
    tableInfo();
}
//accessbility contorls
function AxControls() {
    //interchange of aria-sort from th to span AX-Fix
    var ariaSort_asc = $('#strategiesTable th.sorting_asc').attr('aria-sort');
    $('#strategiesTable th.sorting_asc span').attr('aria-sort', ariaSort_asc);
    var ariaSort_desc = $('#strategiesTable th.sorting_asc').attr('aria-sort');
    $('#strategiesTable th.sorting_asc span').attr('aria-sort', ariaSort_desc);

    $('#strategiesTable th').click(function() {
        ariaSort = $(this).attr('aria-sort');
        $(this).children('span').attr('aria-sort', ariaSort);
        $(this).removeAttr('aria-sort');
        $('.sorting span').removeAttr('aria-sort');
    });

    //removal of attr in strategies Table
    $('#strategiesTable th').removeAttr('tabindex aria-controls aria-label aria-sort rowspan colspan');
}

//table-info
function tableInfo() {
    var sorttxt;
    if ($('.strategies-table-container table.dataTable thead th').hasClass('sorting_asc')) {
        sorttxt = $('.sorting_asc').first().text() + " in ascending order";
    } else if ($('.strategies-table-container table.dataTable thead th').hasClass('sorting_desc')) {
        sorttxt = $('.sorting_desc').first().text() + " in descending order";
    }
    $(".sr-only.table-info").html(" Table Strategies table sorted by " + sorttxt);
}