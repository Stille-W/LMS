window.onload = function() {
	document.querySelectorAll('.nav-link').forEach(link => {
	if(window.location.pathname.split("goodsunlms/")[1].includes(link.href.split("goodsunlms/")[1])){
	link.classList.add('active')
    }
  })
}