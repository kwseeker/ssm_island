<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Lee
  Date: 2018/5/31
  Time: 19:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Object obj = request.getAttribute("checkResult");
    List<String> checkResultList = null;
    String isSuccess;
    if(obj instanceof ArrayList<?>) {
        checkResultList = (ArrayList<String>) obj;
    }
    String resultStatus = checkResultList.get(0);    // 状态
    String resultMsg = checkResultList.get(1);      // 信息
    System.out.println("Status=" + resultStatus + " Message=" + resultMsg);
    if(!"Failed".equals(resultStatus)) {
        isSuccess = "true";
    } else {
        isSuccess = "false";
    }
%>
<html>
<head>
    <title>重置密码</title>
    <script src="../js/emailSetPassword.js" type="text/javascript"></script>
</head>
<body>
    <input type = "hidden" id = "hiddenInput" name = "hiddenInput" value = "<%=isSuccess%>" >
    <input type = "hidden" id = "hiddenEmail" name = "hiddenEmail" value = "<%=resultStatus%>" >

    <form role="form" method="post" style="border: 1px dotted silver;" id="resetPasswordForm">
        <br>
        <div class="form-group">
            <label>用户新密码</label>
        </div>
        <div class="form-group">
            <label>密     码</label>
            <input type = "password" name = "password1" id = "password1" value = "">
        </div>
        <div class="form-group">
            <label>确认密码</label>
            <input type = "password" name = "password2" id = "password2" value = "">
        </div>
        <div class="form-group">
            <input type="button" value="确定" onclick="sendRequest()">
        </div>
    </form>

    <div id = "failureDivId">
        <p><%=resultMsg%></p>
        <p>
            没有收到重置密码邮件，您可以到邮件垃圾箱里找找。<br>
            或者点击：<a href = "findPassword.jsp">【重新发送重置密码邮件】</a>。
        </p>
    </div>
</body>
</html>
