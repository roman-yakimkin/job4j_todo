(function ($) {

    $(document).ready(function() {
        $("#messages").css("display", "none");
        $("button[name='btn-submit']").on("click", function(){
            let $descr = $("#descr");
            console.log($descr);
            if ($descr.val() === "") {
                $("#messages")
                    .css("display", "block")
                    .html("Field \"Description\" shouldn't be empty");
                $descr.focus();
                return false;
            }
        });
    });
})(jQuery);