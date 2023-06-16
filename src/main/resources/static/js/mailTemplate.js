let btnId;
let textId;

$('button').click(function(){
	btnId = this.getAttribute("id");
	textId = $('#'+ btnId).parent().parent().children("div").attr("id");
	console.log($('#' + textId).text());
	// let btn = getElementById("btnId");
	
	let p_as = document.querySelector('#'+textId);
	
	if (!navigator.clipboard) {
	   	 alert("このブラウザは対応していません");
	   	 return;
	 	 }
	
	 navigator.clipboard.writeText(p_as.textContent).then(
	 () => {
		  alert('本文をコピーしました。');
	  },
	  () => {
	   alert('コピーに失敗しました。');
	 });
});

/*
$(document).ready(function(){

	if (document.getElementById('text_absence')){
		const p_as = document.querySelector('#text_absence');
		const button_as = document.querySelector('#copy_absence');
	
		button_as.addEventListener('click', () => {
	  	if (!navigator.clipboard) {
	   	 alert("このブラウザは対応していません");
	   	 return;
	 	 }
	
	 	 navigator.clipboard.writeText(p_as.textContent).then(
	   	 () => {
	    	  alert('文章をコピーしました。');
	  	  },
	  	  () => {
	   	   alert('コピーに失敗しました。');
	   	 });
		});
	} else if (document.getElementById('text_docSubmit')){
		const p_doc = document.querySelector('#text_docSubmit');
		const button_doc = document.querySelector('#copy_docSubmit');
		
		button_doc.addEventListener('click', () => {
	  	if (!navigator.clipboard) {
	   	 alert("このブラウザは対応していません");
	   	 return;
	 	 }
	
	 	 navigator.clipboard.writeText(p_doc.textContent).then(
	   	 () => {
	    	  alert('文章をコピーしました。');
	  	  },
	  	  () => {
	   	   alert('コピーに失敗しました。');
	   	 });
		});
	} else if (document.getElementById('text_greeting')){
		const p_greet = document.querySelector('#text_greeting');
		const button_greet = document.querySelector('#copy_greeting');
	
		button_greet.addEventListener('click', () => {
	  	if (!navigator.clipboard) {
	   	 alert("このブラウザは対応していません");
	   	 return;
	 	 }
	
	 	 navigator.clipboard.writeText(p_greet.textContent).then(
	   	 () => {
	    	  alert('文章をコピーしました。');
	  	  },
	  	  () => {
	   	   alert('コピーに失敗しました。');
	   	 });
		});
	}
	
});

*/