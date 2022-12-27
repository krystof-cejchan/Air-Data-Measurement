$(document).ready(function ($) {
    "use strict";

    /*var menu_btn = $("menu-btn")
    var sidebar = document.querySelector("#sidebar");
    var container = document.querySelector(".my-container");
    console.log($("menu-btn"))
    $(document).click(function (e) {
        if (e.target ===  $("menu-btn")) {
            console.log('a')
            sidebar.classList.toggle("active-nav");
            container.classList.toggle("active-cont");
        }

    });*/

    /*$('body').on('click', function (e) {
        console.log( $('.navbar-toggler').attr('class'));
        $('.navbar-toggler').toggleClass('collapsed');
       // $('.nav-bar-toggler')
      //  console.log($('.nav-bar-toggler'))
       // bs_collapse.toggle()
    });
    $('.navbar-nav>li>a').on('click', function () {
        $('.navbar-collapse').collapse('hide');
    });*/


    $(document).click(function (event) {

        const notCloseableElements = ["nav-link dropdown-toggle", "navbar-toggler"];

        const clickover = $(event.target);
        const closed = $(".navbar-collapse").hasClass("show");
        // console.log(notCloseableElements.map(value => clickover.hasClass(value)));
        if (closed === true && notCloseableElements.map(value => clickover.hasClass(value)).includes(true) === false) {
            $("button.navbar-toggler").click();
        }
    });

}(jQuery));


