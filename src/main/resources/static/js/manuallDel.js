function del(index){
	$.post('./doManualDelete1',{aId:index},function(){
		console.log(index);
		$("#table").load('./manualDelete table');
	});
}