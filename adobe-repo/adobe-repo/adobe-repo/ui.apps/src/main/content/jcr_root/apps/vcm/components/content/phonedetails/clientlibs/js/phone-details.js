$(document).ready(function(){

    //$('.backto-contactus').hide();
    $("#tfCountriesList li.allOthers").remove().insertAfter($("#tfCountriesList li:last"));//.hide();


    $(document).on('click', '.clearSearchInput', function() {
        $(this).prev('input').val('').trigger('change').focus();
        $('#tfSearchCountry').val('');
        $("#tfCountriesList li").show();
        //$("#tfCountriesList li.allOthers").hide();
        $('.clearSearchInput').hide();
    });

    $('#tfSearchCountry').on('keyup search', function(event) {
        $('.clearSearchInput').show();
        $('#tfSearchCountry').after($('<span/>', { 'class': 'clearSearchInput', 'text': 'X' }));
        filterFunction();

        if (event.keyCode === 13) {
            filterFunction();
        };

        if (!$('#tfSearchCountry').val()) {
            $("#tfCountriesList li").show();
        }
        if (!$('#tfSearchCountry').val()) {
            $("#tfCountriesList li").show();
        }
        if (event.keyCode === 8) {
            ipVal = $(this).val(), "" != ipVal ? $(".clearSearchInput").show() : $(".clearSearchInput").hide();
        };
    });

    function filterFunction() {
            var filterValue = $('#tfSearchCountry').val().toLowerCase().trim();
            if (filterValue !== "") {
                $("#tfCountriesList li").each(function() {
                    $(this).show().filter(function() {
                        return $(this).text().toLowerCase().trim().indexOf(filterValue) == -1;
                    }).hide();
                })
                $('.allOthers').show();
            }
        }
});