let btnId;
let textId;

$('.copy').click(function(){
	btnId = this.getAttribute("id");
	textId = $('#'+ btnId).parent().parent().children().children("div").attr("id");
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

function toSendMail() {
    let tid = $(".select").children("p.bg-lb").attr("id");
    let teid = $(".select").children("div").attr("id");

    let tcont = $('#' + tid).text().split(":")[1].trim();
    let tecont = $('#' + teid).html();
    tecont = tecont.replace(/<span>|<\/span>|<\/div>/ig, "").replace(/<br>|<div>/ig, "%0A");
//    console.log(tecont);

    window.location.href = "./sendMail?title="+tcont+"&text="+tecont;

//    window.location.href = "mailto:?"+sendTo+"subject="+tcont+"&body="+tecont;

}

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