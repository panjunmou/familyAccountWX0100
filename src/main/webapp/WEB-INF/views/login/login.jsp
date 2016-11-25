<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/config.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../wechat.jsp"></jsp:include>
    <title>登录</title>
    <script type="text/javascript">
        function login() {
            var userName = $("input[name='username']").val();
            var passWord = $("input[name='password']").val();
            if (userName != '' && passWord != '') {
                ajaxJS("${ctx}/login/login", {
                    userName: userName,
                    passWord: passWord
                }, true, "post", function (result) {
                    if (result.success) {
                        window.location.href = "${ctx}/tally/list";
                    } else {
                        showError(result.msg);
                    }
                });
            } else {
                showError("请输入用户名或密码");
            }
        }
    </script>
    <style type="text/css">
        input {
            margin: 5% 10% 5% 10%;
            width: 80%;
            height: 40px;
            -webkit-border-radius: none;
            -moz-border-radius: none;
            border-radius: none;
            text-indent: 15px;
        }
    </style>
</head>
<body class="bodyBg">
<div class="weui_msg">
    <div class="weui_icon_area">
        <img style="width: 50%" src="${ctx}/style/images/icon/logo.png"/>
    </div>
    <div class="weui_text_area">
        <h2 class="weui_msg_title">
            <input type="text" name="username" placeholder="用户名" maxlength="20" value="${loginName}"/>
            <input type="password" name="password" placeholder="密码" maxlength="20"/>
        </h2>
    </div>
    <div class="weui_opr_area">
        <p class="weui_btn_area">
            <a href="javascript:void(0);" class="weui_btn weui_btn_primary" onclick="login()">登&nbsp;&nbsp;录</a>
        </p>
    </div>
</div>
</body>
</html>