$(function () {

    function process($parent) {
        $parent.find(".inmedia-card").slice(0, 6).css('display', 'flex');
        var totalpost = $parent.find(".inmedia-card").length;
        if ($parent.find(".inmedia-card").length > 6 ){
            $parent.find(".btn-clk-loadmr").css('display', 'inline-block');
            $parent.find(".btn-clk-loadmr").on('click', function (e) {
                e.preventDefault();
                $parent.find(".inmedia-card:hidden").slice(0, 6).css('display', 'flex');
                visiblepost=$parent.find(".inmedia-card:visible").length;
                if ( visiblepost == totalpost ){   
                    $parent.find(".btn-clk-loadmr").css('display', 'none');
                }
            });
        }
      }

      process($("#performance-tabs-0"));
      process($("#performance-tabs-1"));
      process($("#performance-tabs-2"));
      $(".tab-acrdion-body").each(function(){
        process($(this));
      });
});