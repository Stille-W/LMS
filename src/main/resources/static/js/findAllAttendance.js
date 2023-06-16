$(document).ready(function(){
	$("#genBtn").attr("month", $("#genMM").val().split("-")[1])
	$("#genMM").change(function(){
		
	})
})

function gen(month){
	$.post('./genReport',{month:month}, function(){
		console.log(month);
		alert("done")
		})
		.fail(function(xhr, status, error){
			alert("error");
		});	
}