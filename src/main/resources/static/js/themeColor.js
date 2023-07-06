function changeStyleSheet(ssPath) {
	sessionStorage.setItem('ssPath', ssPath);
	var link = document.getElementById('PageStyleSheet');
	link.href = ssPath;
}

window.addEventListener('load', function(){
	var ssPath = sessionStorage.getItem('ssPath');
	if (ssPath) {
		var link = document.getElementById('PageStyleSheet');
		link.href = ssPath;
	}
});



//function changeStyleSheetDark() {
//    var elem = document.getElementById("PageStyleSheet");
//    elem.href = "./css/layoutThemeDark.css";
//}
//
//function changeStyleSheetGirly() {
//    var elem = document.getElementById("PageStyleSheet");
//    elem.href = "./css/layout.css";
//}