function send() {
    let sendTo = $("input[name='sendTo']").val();
    let cc = $("input[name='sendCC']").val();
    let tcont = $("input[name='title']").val();
    let tecont = $('textarea[name="text"]').val().replace(/\n/ig,"%0D%0A");
    window.location.href = "mailto:"+sendTo+"?cc="+cc+"&subject="+tcont+"&body="+tecont;
}