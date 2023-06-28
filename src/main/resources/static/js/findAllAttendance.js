function gen(){
	let month = $("#genMM").val().split("-")[1];
	location.href="./genMonth?month=" + month;
}