<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="config.jsp"%>
<script>
	$(function() {
		$(".weui_tabbar a").removeClass("weui_bar_item_on");
		var tabbar = "${param['tabbar']}";
		switch (tabbar) {
		case '1':
			$("#tabbar_xw").addClass("weui_bar_item_on");
			break;
		case '2':
			$("#tabbar_zx").addClass("weui_bar_item_on");
			break;
		case '3':
			$("#tabbar_shopcart").addClass("weui_bar_item_on");
			break;
		case '4':
			$("#tabbar_my").addClass("weui_bar_item_on");
			break;
		default:
			$("#tabbar_home").addClass("weui_bar_item_on");
			break;
		}
	});
</script>
<!-- <div class="weui_tab" style="position: fixed; left: 0%; right: 0%; bottom: 0%; top: auto; width: 100%"> -->
<!-- 	<div class="weui_tab_bd"></div> -->
<div style="width: 100%; height: 48px;">&nbsp;</div>
<div class="weui_tabbar" style="position: fixed; left: 0%; right: 0%; bottom: 0%; top: auto; width: 100%">
	<a id="tabbar_home" href="${ctx}/index/list?tabbar=0" class="weui_tabbar_item">
		<div class="weui_tabbar_icon">
			<img src="${ctx}/style/images/wechat/index.png" alt="icon" />
		</div>
		<p class="weui_tabbar_label">账目明细</p>
	</a>
	<a id="tabbar_xw" href="${ctx}/index/tally?tabbar=1" class="weui_tabbar_item">
		<div class="weui_tabbar_icon">
			<img src="${ctx}/style/images/wechat/xw.png" alt="icon" />
		</div>
		<p class="weui_tabbar_label">记一笔</p>
	</a>
	<a id="tabbar_zx" href="${ctx}/index/baobiao?tabbar=2" class="weui_tabbar_item">
		<div class="weui_tabbar_icon">
			<img src="${ctx}/style/images/wechat/zx.png" alt="icon" />
		</div>
		<p class="weui_tabbar_label">报表</p>
	</a>
	<a id="tabbar_shopcart" href="${ctx}/index/baoxian?tabbar=3" class="weui_tabbar_item">
		<div class="weui_tabbar_icon">
			<img src="${ctx}/style/images/wechat/gwc.png" alt="icon" />
		</div>
		<p class="weui_tabbar_label">保险</p>
	</a>
	<%--<a id="tabbar_my" href="${ctx}/wechat/personalCenter?tabbar=4&loginType=${loginUserInfo.loginType}" class="weui_tabbar_item weui_bar_item_on">
		<div class="weui_tabbar_icon">
			<img src="${ctx}/style/images/wechat/my.png" alt="icon" />
		</div>
		<p class="weui_tabbar_label">个人中心</p>
	</a>--%>
	<!-- </div> -->
</div>