//{}
var jsonData = [];
var tData = [];
var jdate, jdformat, currentDate, exporttitle;
var indexEndPoint = $('#indexEndpoint').val();
var apikey = $('#indexApiKey').val();
$(document).ready(function () {
    var apiCallFailureMsg = $('#apiFailureMessage').val();
    var indexLabelsJsonStr = $("#indexLabels").val();
    if (indexLabelsJsonStr != null || indexLabelsJsonStr != undefined)
    {
	var indexLabels = JSON.parse(indexLabelsJsonStr);}
 if (indexEndPoint != null && (typeof indexEndPoint != 'undefined'))  {
  //API Json Call
  $.ajax({
    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
    type: "GET",
    //url: '/components/datatables/clientlibs/json/dhi.json',

    url: indexEndPoint,
    dataType: "json",
    beforeSend: function(xhr) {
       $('.dynamic-loader').css("display", "block");
       xhr.setRequestHeader("x-api-key", apikey);
    },
    success: function (data) {
      tempData = {};
      tData = [];
      jsonData = data;
      if(jsonData.length){
          $('.dynamic-loader').css("display", "none");
          var asofDate = jsonData[0].as_of;
          $('<p />', { class: 'mt-2', text: '(As of '+asofDate+')' }).appendTo('.dhi-title');
          
          for (var i = 0; i < jsonData.length; i++) {
            var tempData = jsonData[i].daily_highest_value;
            //tempData.as_of = jsonData[i].as_of;
            tempData.entity_id = jsonData[i].entity_id;
            tempData.entity_long_name = jsonData[i].entity_long_name;
            tData.push(tempData); //push the values to array of tData
          }
    
          getExportBtnTitle()
          //createDataTable(jsonData);
          createDataTable(tData,indexLabels);
          smallScreenFunctions();
          $('.vcm-datatable-container').show();
      }
    },
    error: function (data) {

      $('.dynamic-loader').css("display", "none");
       $ ('.vcm-datatable-container').show()
      $('.vcm-datatable-container').text(apiCallFailureMsg);

    }
  });
  }
  $('#dataHighIndexTable th').click(function() {
      if ($('#dataHighIndexTable th').attr("aria-label")) {
          //$(this).removeAttr('aria-label');
          setTimeout(function() {
              $('#dataHighIndexTable th').removeAttr('aria-label');
          }, 100);
      }
      setTimeout(function() {
          $('#dataHighIndexTable th').removeAttr('aria-label');
      }, 100);
  });
});

//create Strategy Table integrate with data table plugin 
function createDataTable(jsData,indexLabelsData) {

  //console.log(jsData)
  $('#dataHighIndexTable').DataTable({

    dom: 'frtipB',
        "language": {
            "aria": {
                "sortAscending": "",
                "sortDescending": ""
            }
        },

    buttons: [
      {
        extend: 'csv',
        text: indexLabelsData.export_to_CSV,
        title: exportbtntitle,
        className: 'ow-dt-buttons',
      }
    ],
    bJQueryUI: true,
    bFilter: false,
    bInfo: false,
    paging: false,
    columnDefs: [ { type: 'date', 'targets': [2] } ],
    order: [[ 2, 'desc' ]],
    aaData: jsData,
     aoColumns: [
      //{ data: 'as_of' },
      {
        data: 'entity_long_name',
      },
      {
        data: 'entity_id',
      },   
      {
        data: 'high_date',
      },
      {
        data: 'high_value',
        "render": function (data) {
            return parseFloat(data);
        }
      },
      {
        data: 'current_value',
        "render": function (data) {
          return parseFloat(data);
        }
      },
      {
        data: 'decline_percentage',
        "render": function (data) {
          return parseFloat(data);
        }
      },
      {
        data: 'long_cash_invested_percentage',
        "render": function (data) {
          return parseFloat(data);
        }
      }
    ]
  });
  if(indexLabelsData){
	  $(".index").append(indexLabelsData.index);
      $(".daily_highest_value").append(indexLabelsData.daily_highest_value);
      $(".symbol").append(indexLabelsData.symbol);
      $(".current_value").append(indexLabelsData.current_value);
      $(".decline_percentage").append(indexLabelsData.decline_percentage);
      $(".high_date").append(indexLabelsData.high_date);
      $(".high_value").append(indexLabelsData.high_value);
      $(".long_cash_invested_percentage").append(indexLabelsData.long_cash_invested_percentage);
      $('#dataHighIndexTable th').click(function() {
          ariaSort = $(this).attr('aria-sort');
          $(this).children('span').attr('aria-sort', ariaSort);
          $(this).removeAttr('aria-sort');
          $('.sorting span').removeAttr('aria-sort');
      });
      $('#dataHighIndexTable th').removeAttr('tabindex aria-controls aria-label aria-sort rowspan colspan').children('span').attr({
          "role": "button",
          "tabindex": "0"
      })
  }
}
function getExportBtnTitle() {
  jdate = new Date($.now());
  jdformat = {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'/*,
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'*/
  };
  
  exportbtntitle = 'Index Daily Highest Value Update_' + jdate.toLocaleDateString('en-US', jdformat);
}

/*from the below added for mobile view table scroll shadow; */
function smallScreenFunctions() {
    let windowsize = $(window).width();
    if (windowsize <= 768) {
        tableShadowsLROnScrollX();
    }
}

function tableShadowsLROnScrollX() {
    $('<div />', { class: "tableShadowLeft" }).append($('<div />', { class: "actualShadow" })).appendTo('.table-shadowsLR-onScrollX-sm');
    $('<div />', { class: "tableShadowRight" }).append($('<div />', { class: "actualShadow" })).appendTo('.table-shadowsLR-onScrollX-sm');

    $(".table-shadowsLR-onScrollX-sm .tableShadowLeft").hide();
    $('.table-shadowsLR-onScrollX-sm .dataTables_wrapper').scroll(function() {
        var scrollPos = $(this).scrollLeft();
        var offsetWidth = ($(this)[0].offsetWidth) + 1;
        var scrollWidth = $(this)[0].scrollWidth - Math.round($(this)[0].scrollLeft);

        if (offsetWidth >= scrollWidth) {
            $(this).siblings(".tableShadowRight").hide();
            $(this).siblings(".tableShadowLeft").show();
        } else if (scrollPos != 0) {
            $(this).siblings(".tableShadowLeft").show();
            $(this).siblings(".tableShadowRight").show();
        } else if (scrollPos == 0) {
            $(this).siblings(".tableShadowLeft").hide();
            $(this).siblings(".tableShadowRight").show();
        }

    });
}
