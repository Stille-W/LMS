$(document).ready(function() {
    $(".loading").fadeToggle();
})

//function encrypt(password) {
//    let publicKey = $("input[name='public']").val();
//    let encrypt = new JSEncrypt();
//    encrypt.setPublicKey(publicKey);
//    password = encrypt.encrypt(password);
//    $("input[name='password']").val(password);
//}
//
//function submitAndRedirect(){
//    let formDoc = document.getElementById("form");
//    encrypt($("input[name='password']").val());
//    if(true) {
//        formDoc.submit();
//        sleep(3000).then(function() {
//        });
//    }
//}

function send() {
    let sendTo = $("input[name='sendTo']").val();
    let cc = $("input[name='sendCC']").val();
    let tcont = $("input[name='title']").val();
    let tecont = $('textarea[name="text"]').val().replace(/\n/ig,"%0D%0A");
    window.location.href = "mailto:"+sendTo+"?cc="+cc+"&subject="+tcont+"&body="+tecont;
}

function sleep(milliseconds) {
    return new Promise(function (resolve) {
        $(".loading").fadeToggle();
        setTimeout(resolve, milliseconds);
    });
}