<%--
  Created by IntelliJ IDEA.
  User: Lee
  Date: 2018/5/25
  Time: 12:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link href="../css/findPassword.css" rel="stylesheet">
  <script src="../js/getEmailVerify.js" type="text/javascript"></script>
  <title>找回密码</title>
</head>
<body>
  <h2>重置密码</h2>

  <form id="tf">
    <p>请输入要重置的账号邮箱</p>
    <input type="text" id="email" spellcheck="false" placeholder="账号邮箱" size="20"><br>
    <input type="text" id="verifyCode" spellcheck="false" placeholder="验证码" size="6" maxlength="4"/>
    <!-- 点击后发送user/getVerifyCode.do?a=xxx请求,然后更新img图片（修改src属性值就是修改图片） -->
    <img id="code" src="getVerifyCode.do" title="刷新验证码" style="cursor : pointer;" onclick="return refreshCode()"/><br>
    <input type="button" value="下一步" onclick="sendRequestByFormData()">
  </form>
  <!-- 点击下一步后，等待后端Json数据，在当前页面显示，Ajax修改InnerHtml -->
  <div id="result">
  </div>
  <!--
  <form method=post action="user/forgetPasswd.do">
    <p>请输入要重置的账号邮箱</p>
    <input type="text" name="email" spellcheck="false" placeholder="账号邮箱" size="20"><br>
    <input type="text" name="verifyCode" spellcheck="false" placeholder="验证码" size="6" maxlength="4"/>
    <img id="code2" src="user/getVerifyCode.do" title="刷新验证码" style="cursor : pointer;" onclick="return refreshCode()"/><br>
    <input type=submit value="下一步">
  </form>
  -->

</body>
</html>
