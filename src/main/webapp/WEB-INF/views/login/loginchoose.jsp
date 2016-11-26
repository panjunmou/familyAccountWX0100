<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/config.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="../wechat.jsp"></jsp:include>
<title>选择登录</title>
</head>
<body class="bodyBg">
	<div class="weui_msg">
		<div class="weui_icon_area">
			<img style="width: 50%" src="${ctx}/style/images/icon/logo.png" />
		</div>
		<div class="weui_text_area">
			<h2 class="weui_msg_title">
				<font color="#BBBAB9" size="3sp">登录</font>
			</h2>
		</div>
		<div class="weui_opr_area">
			<p class="weui_btn_area">
				<a href="${ctx}/login/loginPage?type=pcglxl" class="weui_btn weui_btn_primary">潘春光/刘晓丽</a>
				<a href="${ctx}/login/loginPage?type=pjmxby" class="weui_btn weui_btn_primary">潘骏谋/徐碧莹</a>
			</p>
		</div>
	</div>
</body>
</html>