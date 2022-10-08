console.log('s');
const navLinks = document.querySelectorAll('.nav-item')
const menuToggle = document.getElementsByClassName('navbar-toggler')
const bsCollapse = new bootstrap.Collapse(menuToggle)
navLinks.forEach((l) => {
  l.addEventListener('click', () => { bsCollapse.toggle() })
})