$(document).ready(function() {
    $(".loading").fadeToggle();
})

//日報作成時の確認ダイアログ
function check(){
	if(window.confirm('日報を作成しますか？')){
		return true; //送信実行
	}
	else{
		return false; // 送信中止
	}
}

function submitAndRedirect(){
    let formDoc = document.getElementById("form");
    if(check()) {
        formDoc.submit();
        sleep(3000).then(function() {
            document.location.reload();
        });
    }
}

function sleep(milliseconds) {
    return new Promise(function (resolve) {
        $(".loading").fadeToggle();
        setTimeout(resolve, milliseconds);
    });
}