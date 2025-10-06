$(document).ready(function() {
    $("#performance-tabs .tablist li:nth-of-type(1)").addClass('active');
    $("#performance-tabs .tabCont:nth-of-type(1)").addClass('show');
    $("#performance-tabs .tablist li:first-child a").attr('aria-selected','true');
    $("#performance-tabs .tablist li:first-child a").attr('tabindex','0');
    $("#performance-tabs .tablist li").click(function(e) {
        e.preventDefault();
        $('#performance-tabs').find('.tablist li').removeClass('active')
        $('#performance-tabs').find('.tablist li a').attr('aria-selected','false')
        $('#performance-tabs').find('.tablist li a').attr('tabindex','-1')
        $(this).addClass('active')
        $(this).find('a').attr('aria-selected','true')
        $(this).find('a').attr('tabindex','0')
        $(".tabCont").removeClass('show')
        let currentTab = $(this).find('a').attr('href')
        $(currentTab).addClass('show')

    });

    var totalTabs = $('#performance-tabs ul.tablist li').length;
    $('#performance-tabs ul.tablist li a').attr('aria-setsize', totalTabs);
    tabnum = 1;
    var activeTabName=''
    $('input[id="activeTab"]').each(function() {
        if ($.trim($(this).val()) == '') { activeTabName = activeTabName; }
        else { activeTabName = $(this).val(); }
    });
    $('#performance-tabs ul.tablist li').each(function(el, list) {
        var $thisList = $(list);
        var $posinset = el+1;
        $thisList.find('a').attr('aria-posinset', $posinset);
        if(activeTabName ===$thisList.find('a').text()){
            var tabVal = "#performance-tabs .tablist li:nth-of-type("+$posinset+")";
            $(tabVal).addClass('active');
            var tabContent = "#performance-tabs .tabCont:nth-of-type("+$posinset+")";
            $(tabContent).addClass('show');
            $('#performance-tabs').find('.tablist li').removeClass('active')
            $('#performance-tabs').find('.tablist li a').attr('aria-selected','false')
            $('#performance-tabs').find('.tablist li a').attr('tabindex','-1')
            $(this).addClass('active')
            $(this).find('a').attr('aria-selected','true')
            $(this).find('a').attr('tabindex','0')
            $(".tabCont").removeClass('show')
            var currentTab = $(this).find('a').attr('href')
            $(currentTab).addClass('show')
        }
    });

    $('.tab-acrdion-hdng').click(function(e) {
        e.preventDefault();
        var $this = $(this);
        $this.toggleClass('collapsed');
        $this.next().toggleClass('show');
        $this.next().slideToggle(350);
    });

    $('#performance-tabs .tablist li a,.etf-tab-section .nav-pills li a').on('keydown', function(e){
        if (e.which == 39) {
            $(this).parent().next().find('a').focus();
            return;
         }

         if (e.which == 37) {
            $(this).parent().prev().find('a').focus();
            return;
         }

        if( (e.which == 32) || (e.which == 13) ){
            $(this).trigger("click");
            e.preventDefault();
         }
    })

    $('#performance-tabs .tablist li a').on('keydown', function(e){

         if( e.which == 9 ){
            $(this).parent().siblings('li').find('a').attr('tabindex', '-1');
            $(this).parents('ul').find('li.active a').attr('tabindex', '0');
         }
    });
});