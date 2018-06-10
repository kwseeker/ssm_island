window.onload = function () {
    var flag = document.getElementById("hiddenInput").value;
    if(flag == "true") {

        document.getElementById("resetPasswordForm").style.display="";
        document.getElementById("failureDivId").style.display="none";
    } else {
        document.getElementById("resetPasswordForm").style.display="none";
        document.getElementById("failureDivId").style.display="";
    }
};

function sendRequest() {
    var url = "resetPasswdByEmail.do";
    var data = "email=" + encodeURI(document.getElementById("hiddenEmail").value)
        + "&newPassword=" + encodeURI(document.getElementById("password1").value);
    xhr = new XMLHttpRequest();
    xhr.open("post", url);
    xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    // xhr.send(form);
    xhr.send(data);
}
//        var flag = $("#hiddenInput").val();
//        if(isSuccess == "true") {
//            $("#failureDivId").hide();
//            $("#resetPasswordForm").show();
//        } else {
//            $("#resetPasswordForm").hide();
//            $("#failureDivId").show();
//        }
