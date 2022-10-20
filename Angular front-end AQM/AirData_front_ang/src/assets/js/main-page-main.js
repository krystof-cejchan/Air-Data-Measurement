(function ($) {
    "use strict";

    var onLoaded = function () {

    };

    var burgerMenu = function () {

        $('.js-colorlib-nav-toggle').on('click', function (event) {

            event.preventDefault();
            var $this = $(this);

            if ($('body').hasClass('offcanvas')) {
                $this.removeClass('active');
                $('body').removeClass('offcanvas');
            } else {
                $this.addClass('active');
                $('body').addClass('offcanvas');
            }
        });
    };
    burgerMenu();

    var mobileMenuOutsideClick = function () {

        $(document).click(function (e) {
           // console.log(e)
            const container = $("a#III");
            const container2 = $("i#III");
            const this_component_body = $("body#mainbody");
            if ((container.is(e.target) || container2.is(e.target)) && (container.has(e.target).length === 0 || container2.has(e.target).length === 0)) {

                if ($(this_component_body).hasClass('offcanvas')) {

                    //hides or shows the menu
                    $(this_component_body).removeClass('offcanvas');

                    // .js-colorlib-nav-toggle represents the icon which is used to show three lines or an X symbol
                    $('.js-colorlib-nav-toggle').removeClass('active');

                }
                else {
                    $(this_component_body).addClass('offcanvas');
                    $('.js-colorlib-nav-toggle').addClass('active');
                }

            }
        });

        /*  $(window).scroll(function () {
              if ($('body').hasClass('offcanvas')) {
  
                  $('body').removeClass('offcanvas');
                  $('.js-colorlib-nav-toggle').removeClass('active');
  
              }
          });*/

    };
    mobileMenuOutsideClick();

})(jQuery);
