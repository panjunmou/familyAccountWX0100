<%--
  Created by IntelliJ IDEA.
  User: PanJM
  Date: 2016/11/19
  Time: 20:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/config.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../wechat.jsp"></jsp:include>
    <title>查看账目</title>
</head>
<body>
<div class="weui_cells weui_cells_form" style="margin-top: 0px">
    <div class="weui_cell">
        <div class="weui_cell_hd"><label class="weui_label">金额</label></div>
        <div class="weui_cell_bd weui_cell_primary">
            <input class="weui_input" type="number" value="${tally.money}" readonly>
        </div>
    </div>
    <div class="weui_cell">
        <div class="weui_cell_hd"><label class="weui_label">日期</label></div>
        <div class="weui_cell_bd weui_cell_primary">
            <input class="weui_input" type="text" data-toggle='date' value="${tally.payDate}" readonly/>
        </div>
    </div>
    <div class="weui_cell">
        <div class="weui_cell_hd"><label class="weui_label">账户</label></div>
        <div class="weui_cell_bd weui_cell_primary">
            <input class="weui_input" readonly type="text" value="${tally.accountName}"/>
        </div>
    </div>
    <div class="weui_cell">
        <div class="weui_cell_hd"><label class="weui_label">用途</label></div>
        <div class="weui_cell_bd weui_cell_primary">
            <input class="weui_input" readonly type="text" value="${tally.purposeName}"/>
        </div>
    </div>
    <div class="weui_cell">
        <div class="weui_cell_hd"><label class="weui_label">消费者</label></div>
        <div class="weui_cell_bd weui_cell_primary">
            <input class="weui_input" readonly type="text" value="${tally.payUserNames}"/>
        </div>
    </div>
</div>
<div class="weui_cells_title">备注</div>
<div class="weui_cells weui_cells_form">
    <div class="weui_cell">
        <div class="weui_cell_bd weui_cell_primary">
            <textarea class="weui_textarea" placeholder="请输入评论" rows="3" readonly>${tally.remark}</textarea>
            <div class="weui_textarea_counter"><span>0</span>/200</div>
        </div>
    </div>
</div>
<!-- 底部导航 -->
<div id="footerBar">
    <jsp:include page="../common/footer.jsp"></jsp:include>
</div>
<!-- 底部导航 -->
</body>
</html>
