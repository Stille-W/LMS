function del(index){
	let result = confirm('削除しますか？');
	
	if(result) {
		$.post('./doManualDeleteAjax',{aId:index},function(){
			console.log(index);
			$("#table").load('./manualDelete table');
		});
	}	
}