<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/config.jsp"%>
<META charset="UTF-8">
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="X-UA-Compatible" content="IE=edge">
<META HTTP-EQUIV="Content-Type" content="text/html; charset=UTF-8">
<META name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

<!-- [JQUERY] -->
<script type="text/javascript" src="${ctx}/js/jquery/jquery.min.js"></script>

<script type="text/javascript" src="${ctx}/js/jquery/jquery.ajax.js"></script>

<script type="text/javascript" src="${ctx}/js/jquery/jquery-weui.min.js"></script>
<!-- [JQUERY] -->

<!-- 微信CSS  -->
<link rel="stylesheet" type="text/css" href="${ctx}/style/css/weui.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/style/css/jquery-weui.min.css">

<link rel="stylesheet" type="text/css" href="${ctx}/style/css/jquery.silde.css">

<script type="text/javascript">
	function backBtnListener(method) {
		pushHistory();
		var bool = false;
		setTimeout(function() {
			bool = true;
		}, 1500);
		window.addEventListener("popstate", function(e) {
			if (bool) {
				method();
			}
			pushHistory();
		}, false);
	}

	function pushHistory() {
		var state = {
			title : "title",
			url : "#"
		};
		window.history.pushState(state, "title", "#");
	}

	function getTotalPage(total,pageSize) {
        var totalPage = 0;
        total = parseInt(total);
        pageSize = parseInt(pageSize);
        if(total / pageSize <= 1) {
            totalPage = 1;
        }else{
            totalPage = parseInt(total / pageSize) + 1;
        }
        return totalPage;
    }
</script>

<style type="text/css">
body {
	width: 100%;
	height: 100%;
	background-color: #F0EFF0;
}

.bodyBg {
	width: 100%;
	height: 100%;
	background-image: url("${ctx}/style/images/bg02.jpg");
	background-repeat: no-repeat;
	background-size: cover;
}

.fontA {
	color: gray;
	font-size: 16px;
}

/* 覆盖微信按钮样式颜色 */
.weui_btn_primary {
	margin: 10%;
	width: 80%;
	background-color: #f0ad4e;
}

.weui_btn_primary:active {
	color: hsla(0, 0%, 100%, .4);
	background-color: #f0ad4e;
}
/* 覆盖微信按钮样式颜色 */
#search_bar {
	width: 100%;
	position: fixed;
	top: 0;
	z-index: 999;
}

.croslat {
	background-color: rgba(191, 191, 191, 0.2);
	width: 100%;
	height: 6px;
	filter: alpha(opacity = 50);
}
</style>