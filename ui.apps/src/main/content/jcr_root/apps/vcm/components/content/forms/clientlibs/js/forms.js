var jsonData = [],
    tabledata = [];
var winWidth = $(window).width();
var cachedWidth = $(window).width();
var valueList;
var fundName;
var fundVal = [];
var victoryFund = [];
var educationSaving = [];
var robo = [];
var brokerage = [];
$(document).ready(function() {
    if ($('.forms').length > 0) {
        //API Json Call
        $.ajax({
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            context: this,
            type: 'GET',
            url: '/content/vcm/forms.userforms.json',
            data: {
                formsTag: $('#formsTag').val(),
                formsRootPath: $('#formsRootPath').val()
            },
            dataType: "json",
            success: function(responseData) {

                jsonData = responseData;
                $.each(jsonData, function(key, value) {

                    if (key == 'victoryFund') {
                        victoryFund.push(value);
                    } else if (key == 'educationSavings') {
                        educationSaving.push(value);
                    } else if (key == 'robo') {
                        robo.push(value)
                    } else if (key == 'brokerage') {
                        brokerage.push(value)
                    }
                    valueList = value;
                });

                $.each(valueList, function(key, value) {
                    $('<input type="radio" class="category-radio-btn" name="category" value="' + key + '">' + key.replace(/([A-Z])/g, ' $1').trim() + '</input>').appendTo('#divformRadioCategory');
                });

                if (victoryFund.length > 0) {
                    $.each(victoryFund, function(key, value) {
                        $.each(value, function(key, value) {
                            $.each(value, function(key, value) {
                                fundName = value.fund;
                                tabledata.push(value);
                            });
                        });
                    });
                    if (tabledata.length > 0) {
                        $('#vct-formsTable').show();
                        createTableByCategory(tabledata, fundName);
                    } else {
                        $('.dt-more-container-vct-fund-error').show();
                    }
                    tabledata = [];
                } else {
                    $('.dt-more-container-vct-fund-error').show();
                }
                if (educationSaving.length > 0) {
                    $.each(educationSaving, function(key, value) {
                        $.each(value, function(key, value) {
                            $.each(value, function(key, value) {
                                fundName = value.fund;
                                tabledata.push(value);
                            });
                        });
                    });
                    if (tabledata.length > 0) {
                        $('#edu-formsTable').show();
                        createTableByCategory(tabledata, fundName);
                    } else {
                        $('.dt-more-container-edu-save-error').show();
                    }
                    tabledata = [];
                } else {
                    $('.dt-more-container-edu-save-error').show();
                }

                if (robo.length > 0) {
                    $.each(robo, function(key, value) {
                        $.each(value, function(key, value) {
                            $.each(value, function(key, value) {
                                fundName = value.fund;
                                tabledata.push(value);
                            });
                        });
                    });
                    if (tabledata.length > 0) {
                        $('#rob-formsTable').show();
                        createTableByCategory(tabledata, fundName);
                    } else {
                        $('.dt-more-container-robo-error').show();
                    }
                    tabledata = [];
                } else {
                    $('.dt-more-container-robo-error').show();
                }

                if (brokerage.length > 0) {
                    $.each(brokerage, function(key, value) {
                        $.each(value, function(key, value) {
                            $.each(value, function(key, value) {
                                fundName = value.fund;
                                tabledata.push(value);
                            });

                        });
                    });
                    if (tabledata.length > 0) {
                        $('#bro-formsTable').show();
                        createTableByCategory(tabledata, fundName);
                    } else {
                        $('.dt-more-container-brokerage-error').show();
                    }
                    tabledata = [];
                } else {
                    $('.dt-more-container-brokerage-error').show();
                }
            },
            error: function(data) {
                console.log('API Call Failed');
            }

        });

        function createTableByCategory(edata, category) {
            var theadFormId = $('#formID').val();
            var theadFormsCategory = $('#formsCategory').val();
            var theadFormDetails = $('#form').val();
            var theadDownload = $('#download').val();
            var tableHead = "<thead id='tableHead'><tr>  <th>" + theadFormId + "</th> <th>" + theadFormDetails + "</th><th></th> <th>Category</th> <th>Fund</th> </tr> </thead>";
            var tableId = "." + category;
            $(tableHead).appendTo(tableId);

            createTable(edata, category);

        }

        function createTable(edata, category) {
            $.fn.dataTable.ext.errMode = 'none'; //added to disable alert on loading as empty value
            var tableClass = "table." + category;
            var table = $(tableClass).DataTable({
                // oLanguage: {
                //     "sInfo": "Showing: _END_  of _MAX_ Forms",
                //   },
                infoCallback: function(settings, start, end, max, total, pre) {
                    return 'Showing: <span class="bold-text">' + end + '</span> of <span  class="bold-text">' + total + '</span> Forms <div class="filterNsort"><a href="#/"><span class="filterIcon"></span></a><a href="#/"><span  class="sortIcon ascSort"></span></a><a href="#/"><span  class="sortIcon descSort"></span></a></div>';

                },

                data: edata,
                responsive: true,
                columns: [{
                        "width": "20%",
                        "data": "formID",
                        "className": "fundId mblHide"
                    },
                    {
                        "width": "50%",
                        "data": 'pdfTitle',
                        "className": "titleLinkDesc",
                        "render": function(data, type, row) {
                            if (typeof row.pdfPath !== 'undefined' && row.pdfPath !== null) {
                                pdflink = row.pdfPath

                            } else {
                                pdflink = "#"
                            }
                            return '<h5>' + data + '</h5><a class="mobPdf" href="' + pdflink + '"download><img src="/content/dam/vcm/basic/pdf-icon-red.png" alt="' + data + '"> </a><p>' + row.pdfDescription + '</p><div class="mblShow row pt-2"><div class="col-8 text-left text-capitalize">' + row.subCategory + '</div><div class="col-4 text-right">' + row.formID + '</div></div>'

                        }
                    },
                    {
                        "width": "10%",
                        "data": "pdfTitle",
                        "className": "download-pdf-col mblHide",
                        "render": function(data, type, row) {
                            if (typeof row.pdfPath !== 'undefined' && row.pdfPath !== null) {
                                pdflink = row.pdfPath
                            } else {
                                pdflink = "#"
                            }
                            return '<a href="' + pdflink + '" download><img src="/content/dam/vcm/basic/pdf-icon-red.png" alt="' + data + '" class="downloadIcon" > Download</a>';
                        }
                    },
                    {
                        "width": "1%",
                        "data": "category"
                    },
                    {
                        "width": "1%",
                        "data": "fund"
                    }
                ],

                iDisplayLength: 3,

                drawCallback: function() {
                    var colStr, buttonText;
                    var colDataId = '.' + category + "  tr td:nth-child(2)"
                    $(colDataId).each(function() {
                        if (winWidth > 500) {
                            var colStr = $(this).html().replace('-', ' ');
                        } else if (winWidth < 500) {
                            var colStr = $(this).html();
                        }
                        $(this).html(colStr);
                    })

                    /* $('.category-btn').each(function(){
                           if (winWidth > 500) {
                               buttonText = $(this).html().replace('-', ' ');
                           } else if (winWidth < 500) {
                               buttonText = $(this).html();
                           }
                           $(this).html(buttonText);
                       }); */


                    /*  $('.filterIcon').click(function(){
                          $('#formCategory').toggle();
                      });*/

                    // $('#formCategory').insertAfter('#formsTable_info');
                    // Show or hide "Load more" button based on whether there is more data available
                    if (category == 'victory-fund') {
                        if (this.api().page.hasMore()) {
                            $('.victory-fund-btn').show();
                        }
                    } else if (category == '529-education-saving' || category == 'education-saving' ||
                                category == 'education' || category == 'educations' ) {
                        if (this.api().page.hasMore()) {
                            $('#educationSavingBtn').show();
                        }
                    } else if (category == 'robo' || category == 'dynamic-advisor'
                                || category == 'dynamicadvisor') {
                        if (this.api().page.hasMore()) {
                            $('#roboBtn').show();
                        }
                    } else if (category == 'brokerage' || category == 'marketplace') {
                        if (this.api().page.hasMore()) {
                            $('#brokerageBtn').show();
                        }
                    }

                    /*  $('#formsTable_info .sortIcon').on('click', function(){
                         if($(this).hasClass('ascSort')){
                             table.order([1, "asc"]).draw();
                             $('#formsTable_wrapper').removeClass('descending').addClass('ascending');
                         }
                         if($(this).hasClass('descSort')){
                             table.order([1, "desc"]).draw();
                             $('#formsTable_wrapper').removeClass('ascending').addClass('descending');
                         }
                     })*/
                },
                initComplete: function() {
                    $('.category-btn').first().addClass('selectedBtn');
                    $('.dataTables_wrapper').addClass('descending');
                    winWidth < 350 ? $(".dataTables_wrapper").hide() : $(".dataTables_wrapper").show();
                }
            });

            $('#vct-formsTable_wrapper').find('div#vct-formsTable_filter').first().remove();
            $('#edu-formsTable_wrapper').find('div#edu-formsTable_filter').first().remove();
            $('#bro-formsTable_wrapper').find('div#bro-formsTable_filter').first().remove();
            $('#rob-formsTable_wrapper').find('div#rob-formsTable_filter').first().remove();
            $('#edu-formsTable_wrapper').find('div#edu-formsTable_paginate').first().remove();

            $('button#educationSavingBtn').find('thead#tableHead').first().remove();
            $('button#victoryFundBtn').find('thead#tableHead').first().remove();
            $('button#roboBtn').find('thead#tableHead').first().remove();
            $('button#brokerageBtn').find('thead#tableHead').first().remove();

            //added to disable alert on loading as empty value
            $(tableClass).on('error.dt', function(message) {
                console.log("message " + message);
            });

            /* var oTable = $(tableClass).dataTable();
             $('.category-btn').click(function(e) {

                 if (e.target.id === 'AllCategories') {
                     oTable.fnFilterClear();
                 }
                 else
               oTable.fnFilter(e.target.id, 4);

                return false;
             }); */

            //	$('.dt-more-container').appendTo('#formsTable_wrapper');

            $(window).on('resize', function() {
                setTimeout(function() {
                    var newWidth = $(window).width();
                   newWidth !== cachedWidth && (350 < newWidth ? $(".dataTables_wrapper").show() : newWidth <= 350 && $(".dataTables_wrapper").hide(), cachedWidth = newWidth);
                }, 100)

            });

            function getOrientation() {
                var orientation = window.innerWidth > window.innerHeight ? "Landscape" : "Portrait";
                console.log(orientation);
            }
            //Click on fund category button
            $('.category-btn').click(function(e) {
                $('.category-btn').removeClass('selectedBtn');
                $(this).addClass('selectedBtn');

                if ($(this).attr('id') == 'victoryFund') {

                    $("div#educationSaving-fund").hide();
                    $("div#robo-fund").hide();
                    $("div#brokerage-fund").hide();
                    $('#radioBtn').prop('checked', true);
                    $('#radioBtn').click();
                    $("div#victoryFund-fund").show();

                    var oTable = $('#vct-formsTable').dataTable();
                    var oSettings = oTable.fnSettings();
                    if (oSettings != null) {
                        $('select[name^="events_table_length"]').change();
                        oSettings._iDisplayLength = 3;
                        oTable.fnDraw();
                    }

                } else if ($(this).attr('id') == 'educationSavings') {
                    $("div#victoryFund-fund").hide();
                    $("div#robo-fund").hide();
                    $("div#brokerage-fund").hide();
                    $('#radioBtn').prop('checked', true);
                    $('#radioBtn').click();
                    $("div#educationSaving-fund").show();

                    var oTable = $('#edu-formsTable').dataTable();
                    var oSettings = oTable.fnSettings();
                    if (oSettings != null) {
                        $('select[name^="events_table_length"]').change();
                        oSettings._iDisplayLength = 3;
                        oTable.fnDraw();
                    }
                } else if ($(this).attr('id') == 'robo') {
                    $("div#victoryFund-fund").hide();
                    $("div#robo-fund").show();
                    $("div#brokerage-fund").hide();
                    $('#radioBtn').prop('checked', true);
                    $('#radioBtn').click();
                    $("div#educationSaving-fund").hide();

                    var oTable = $('#rob-formsTable').dataTable();
                    var oSettings = oTable.fnSettings();
                    if (oSettings != null) {
                        $('select[name^="events_table_length"]').change();
                        oSettings._iDisplayLength = 3;
                        oTable.fnDraw();
                    }

                } else if ($(this).attr('id') == 'brokerage') {
                    $("div#victoryFund-fund").hide();
                    $("div#robo-fund").hide();
                    $("div#brokerage-fund").show();
                    $('#radioBtn').prop('checked', true);
                    $('#radioBtn').click();
                    $("div#educationSaving-fund").hide();

                    var oTable = $('#bro-formsTable').dataTable();
                    var oSettings = oTable.fnSettings();
                    if (oSettings != null) {
                        $('select[name^="events_table_length"]').change();
                        oSettings._iDisplayLength = 3;
                        oTable.fnDraw();
                    }

                } else {
                    $("div#victoryFund-fund").show();
                    $("div#robo-fund").show();
                    $("div#brokerage-fund").show();
                    $('#radioBtn').prop('checked', true);
                    $('#radioBtn').click();
                    $("div#educationSaving-fund").show();

                    var vctoTable = $('#vct-formsTable').dataTable();
                    var oSettings = vctoTable.fnSettings();
                    if (oSettings != null) {
                        $('select[name^="events_table_length"]').change();
                        oSettings._iDisplayLength = 3;
                        vctoTable.fnDraw();
                    }

                     var eduoTable = $('#edu-formsTable').dataTable();
                    var oSettings = eduoTable.fnSettings();
                    if (oSettings != null) {
                        $('select[name^="events_table_length"]').change();
                        oSettings._iDisplayLength = 3;
                        eduoTable.fnDraw();
                    }

                    var roboTable = $('#rob-formsTable').dataTable();
                    var oSettings = roboTable.fnSettings();
                    if (oSettings != null) {
                        $('select[name^="events_table_length"]').change();
                        oSettings._iDisplayLength = 3;
                        roboTable.fnDraw();
                    }

                    var brooTable = $('#bro-formsTable').dataTable();
                    var oSettings = brooTable.fnSettings();
                    if (oSettings != null) {
                        $('select[name^="events_table_length"]').change();
                        oSettings._iDisplayLength = 3;
                        brooTable.fnDraw();
                    }

                }
                $('#victoryFundBtn').text('Show More Victory Funds Forms');
                $('#educationSavingBtn').text('Show More Education Forms');
                $('#roboBtn').text('Show More Dynamic Advisor Forms');
                $('#brokerageBtn').text('Show More Marketplace forms');

                var oTable = $('table.dataTable').dataTable();
                var oSettings = oTable.fnSettings();
                if (oSettings != null) {
                    $('select[name^="events_table_length"]').change();
                    oSettings._iDisplayLength = 3;
                    oTable.fnDraw();
                }
            });


            $(".category-radio-btn").on('click', function() {
                table.draw();

                if (document.getElementById('edu-formsTable_info') == null || document.getElementById('edu-formsTable_info').getElementsByTagName('span')[0].innerHTML == 0) {
                    $('#eduError').show();
                    $('#edu-formsTable').hide();
                    $('#edu-formsTable_info').hide();
                    $('.dt-more-container-edu-save').hide()
                } else {
                    $('#eduError').hide();
                    $('#edu-formsTable').show();
                    $('#edu-formsTable_info').show();
                    let count = $('#edu-formsTable tr').length - 1;
                    if ($('#edu-formsTable tr').length - 1 > 0) {
                        $('.dt-more-container-edu-save').show()
                    }
                }

                if (document.getElementById('vct-formsTable_info') == null || document.getElementById('vct-formsTable_info').getElementsByTagName('span')[0].innerHTML == 0) {
                    $('#vctError').show();
                    $('#vct-formsTable').hide();
                    $('#vct-formsTable_info').hide();
                    $('.dt-more-container-vct-fund').hide()
                } else {
                    $('#vctError').hide();
                    $('#vct-formsTable').show();
                    $('#vct-formsTable_info').show();
                    let count = $('#vct-formsTable tr').length - 1;
                    if ($('#vct-formsTable tr').length - 1 > 0) {
                        $('.dt-more-container-vct-fund').show()
                    }
                }

                if (document.getElementById('rob-formsTable_info') == null || document.getElementById('rob-formsTable_info').getElementsByTagName('span')[0].innerHTML == 0) {
                    $('#roboError').show();
                    $('#rob-formsTable').hide();
                    $('#rob-formsTable_info').hide();
                    $('.dt-more-container-robo').hide()
                } else {
                    $('#eduError').hide();
                    $('#rob-formsTable').show();
                    $('#rob-formsTable_info').show();
                    let count = $('#rob-formsTable tr').length - 1;
                    if ($('#rob-formsTable tr').length - 1 > 0) {
                        $('.dt-more-container-robo').show()
                    }
                }

                if (document.getElementById('bro-formsTable_info') == null || document.getElementById('bro-formsTable_info').getElementsByTagName('span')[0].innerHTML == 0) {
                    $('#brokerageError').show();
                    $('#bro-formsTable').hide();
                    $('#bro-formsTable_info').hide();
                    $('.dt-more-container-brokgerage').hide()
                } else {
                    $('#brokerageError').hide();
                    $('#bro-formsTable').show();
                    $('#bro-formsTable_info').show();
                    let count = $('#bro-formsTable tr').length - 1;
                    if ($('#bro-formsTable tr').length - 1 > 0) {
                        $('.dt-more-container-brokgerage').show()
                    }
                }
            });
            //input tag click on cross/close image
            $('#search').on('search', function(e) {
                if('' == this.value) {
                    $('#search').val("");
                    $('#search').keyup();
                }
            });

            // Search input key values from search box
            $('#search').keyup(function(event) {
                if (event.keyCode === 13) { //on enter key
                    table.search($(this).val()).draw();
                    $('#radioBtn').prop('checked', true);
                    $('#radioBtn').click();
                }
                //On key up Empty
                if (!$(this).val()) {
                    table.search($(this).val()).draw();
                    $('#radioBtn').prop('checked', true);
                    $('#radioBtn').click();
                    var oTable = $('table.dataTable').dataTable();
                    var oSettings = oTable.fnSettings();
                    if (oSettings != null) {
                        $('select[name^="events_table_length"]').change();
                        oSettings._iDisplayLength = 3;
                        oTable.fnDraw();
                    }
                }
            });
        }
        // Handle click on "Load more" button
        $('#victoryFundBtn').click(function(event) {
            this.innerHTML = 'Load More';
            var count = $('#vct-formsTable tr').length - 1
            var oTable = $('#vct-formsTable').dataTable();
            var oSettings = oTable.fnSettings();
            if (oSettings != null) {
                $('select[name^="events_table_length"]').change();
                if (count == 3) {
                    oSettings._iDisplayLength = count + 7;
                } else {
                    oSettings._iDisplayLength = count + 10;
                }
                oTable.fnDraw();
                event.stopPropagation();
            }
            $("div#educationSaving-fund").hide();
            $("div#robo-fund").hide();
            $("div#brokerage-fund").hide();
            $("div#victoryFund-fund").show();
            $('button#AllCategories').removeClass('selectedBtn');
            $('button#educationSavings').removeClass('selectedBtn');
            $('button#robo').removeClass('selectedBtn');
            $('button#brokerage').removeClass('selectedBtn');
            $('button#victoryFund').addClass('selectedBtn');
        });

        //Education Saving Button load more
        $('#educationSavingBtn').on('click', function() {
            this.innerHTML = 'Load More';
            var count = $('#edu-formsTable tr').length - 1;
            var oTable = $('#edu-formsTable').dataTable();
            var oSettings = oTable.fnSettings();
            if (oSettings != null) {
                $('select[name^="events_table_length"]').change();
                if (count == 3) {
                    oSettings._iDisplayLength = count + 7;
                } else {
                    oSettings._iDisplayLength = count + 10;
                }
                oTable.fnDraw();
                event.stopPropagation();
            }
            $("div#victoryFund-fund").hide();
            $("div#robo-fund").hide();
            $("div#brokerage-fund").hide();
            $("div#educationSaving-fund").show();
            $('button#AllCategories').removeClass('selectedBtn');
            $('button#victoryFund').removeClass('selectedBtn');
            $('button#robo').removeClass('selectedBtn');
            $('button#brokerage').removeClass('selectedBtn');
            $('button#educationSavings').addClass('selectedBtn');
        });

        //Robo Button load more
        $('#roboBtn').on('click', function() {
            this.innerHTML = 'Load More';
            var count = $('#rob-formsTable tr').length - 1;
            var oTable = $('#rob-formsTable').dataTable();
            var oSettings = oTable.fnSettings();
            if (oSettings != null) {
                $('select[name^="events_table_length"]').change();
                if (count == 3) {
                    oSettings._iDisplayLength = count + 7;
                } else {
                    oSettings._iDisplayLength = count + 10;
                }
                oTable.fnDraw();
                event.stopPropagation();
            }
            $("div#victoryFund-fund").hide();
            $("div#educationSaving-fund").hide();
            $("div#robo-fund").show();
            $("div#brokerage-fund").hide();
            $('button#AllCategories').removeClass('selectedBtn');
            $('button#victoryFund').removeClass('selectedBtn');
            $('button#educationSavings').removeClass('selectedBtn');
            $('button#robo').addClass('selectedBtn');
            $('button#brokerage').removeClass('selectedBtn');
        });

        //Brokerage Button load more
        $('#brokerageBtn').on('click', function() {
            this.innerHTML = 'Load More';
            var count = $('#bro-formsTable tr').length - 1;
            var oTable = $('#bro-formsTable').dataTable();
            var oSettings = oTable.fnSettings();
            if (oSettings != null) {
                $('select[name^="events_table_length"]').change();
                if (count == 3) {
                    oSettings._iDisplayLength = count + 7;
                } else {
                    oSettings._iDisplayLength = count + 10;
                }
                oTable.fnDraw();
                event.stopPropagation();
            }
            $("div#victoryFund-fund").hide();
            $("div#educationSaving-fund").hide();
            $("div#robo-fund").hide();
            $("div#brokerage-fund").show();
            $('button#AllCategories').removeClass('selectedBtn');
            $('button#victoryFund').removeClass('selectedBtn');
            $('button#educationSavings').removeClass('selectedBtn');
            $('button#robo').removeClass('selectedBtn');
            $('button#brokerage').addClass('selectedBtn');
        });
    }
});

var flag = false;
/* Custom filtering function which will search data in column four between two values */
$.fn.dataTable.ext.search.push(function(settings, data, dataIndex) {

    var colValue = data[3].split('-').reduce((a, b) => a + b.charAt(0).toUpperCase() + b.slice(1));
    if ($('input[name="category"]:checked').val() == 'on') {
        return true;
    } else if (colValue == 'service') {
        flag == true
        return (colValue == $('input[name="category"]:checked').val());
    } else if (colValue == 'accountApplication') {
        flag == true
        return (colValue == $('input[name="category"]:checked').val());
    } else {
        if (flag == false) {
            return false;
        } else {
            return true;
        }

    }
    return false;

});


jQuery.fn.dataTableExt.oApi.fnFilterClear = function(oSettings) {

    var i, iLen;

    /* Remove global filter */
    oSettings.oPreviousSearch.sSearch = "";

    /* Remove the text of the global filter in the input boxes */
    if (typeof oSettings.aanFeatures.f != 'undefined') {
        var n = oSettings.aanFeatures.f;
        for (i = 0, iLen = n.length; i < iLen; i++) {
            $('input', n[i]).val('');
        }
    }

    /* Remove the search text for the column filters - NOTE - if you have input boxes for these
     * filters, these will need to be reset
     */
    for (i = 0, iLen = oSettings.aoPreSearchCols.length; i < iLen; i++) {
        oSettings.aoPreSearchCols[i].sSearch = "";
    }

    /* Redraw */
    oSettings.oApi._fnReDraw(oSettings);
};