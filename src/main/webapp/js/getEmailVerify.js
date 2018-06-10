var xhr = null;
// var url = "user/forgetPasswd.do";
var url = "forgetPasswd.do";

// 原生Ajax post发送请求并用回调获取后端返回的数据，然后更新显示result元素
function sendRequest() {
    // var form = new FormData(document.getElementById("tf"));
    var data = "email=" + encodeURI(document.getElementById("email").value)
                + "&verifyCode=" + encodeURI(document.getElementById("verifyCode").value);
    xhr = new XMLHttpRequest();
    xhr.open("post", url);  //异步执行
    xhr.onreadystatechange = refreshView;
    xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    // xhr.send(form);
    xhr.send(data);
}

// FormData的使用（简化往请求中添加数据的代码）
// Content-Type: multipart/form-data
function sendRequestByFormData() {
    var form = new FormData();
    form.append("email", document.getElementById("email").value);
    form.append("verifyCode", document.getElementById("verifyCode").value);
    // var form = new FormData(document.getElementById("tf"));
    xhr = new XMLHttpRequest();
    xhr.onreadystatechange = refreshView;
    xhr.open("post", url);
    xhr.send(form);
}

function refreshView() {
    console.log("refreshView called： readyState=" + xhr.readyState + " status=" + xhr.status);
    if(xhr.readyState == 4 && xhr.status == 200) {
        document.getElementById("result").innerHTML = xhr.responseText;
    }
}

function refreshCode(){
    document.getElementById("code").src="getVerifyCode.do?a="+Math.random();   // URL改变自动刷新
    // document.getElementById("code").src="user/getVerifyCode.do";
    return false;
}
