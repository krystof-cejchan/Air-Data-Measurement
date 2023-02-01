/**
 * a function that is triggered whenever a user clicks anywhere on the document.
 */
$(document).ready(function () {
    "use strict";
    $(document).on('click', (event) => {
        const notCloseableElements = ["nav-link dropdown-toggle", "navbar-toggler"];
        const clickover = $(event.target);
        const open = $(".navbar-collapse").hasClass("show");
        // if user does not click on any element from notCloseableElements or if open===true, then nothing happens; otherwise nav-bar gets closed.
        if (open && !notCloseableElements.some(value => clickover.hasClass(value))) {
            $("button.navbar-toggler").trigger('click');
        }
    });
});

/*
  $(document).ready(function () {
    "use strict";
    $(document).click(function(event) {
      const notCloseableElements = ["nav-link dropdown-toggle", "navbar-toggler"];
      const clickover = $(event.target);
      const closed = $(".navbar-collapse").hasClass("show");
      if (closed && !notCloseableElements.some(value => clickover.hasClass(value))) {
        $("button.navbar-toggler").trigger('click');
      }
    });
  });
*/

/*
document.addEventListener("DOMContentLoaded", function() {
    "use strict";
    document.addEventListener("click", (event) => {
      const notCloseableElements = ["nav-link dropdown-toggle", "navbar-toggler"];
      const clickover = event.target;
      const closed = document.querySelector(".navbar-collapse").classList.contains("show");
      if (closed && !notCloseableElements.some(value => clickover.classList.contains(value))) {
        document.querySelector("button.navbar-toggler").click();
      }
    });
  });
  
  */

