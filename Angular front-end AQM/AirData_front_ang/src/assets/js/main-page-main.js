(function ($) {
    "use strict";


    var menu_btn = $("menu-btn")
    var sidebar = document.querySelector("#sidebar");
    var container = document.querySelector(".my-container");
    console.log($("menu-btn"))
    $(document).click(function (e) {
        if (e.target ===  $("menu-btn")) {
            console.log('a')
            sidebar.classList.toggle("active-nav");
            container.classList.toggle("active-cont");
        }

    });

}(jQuery));