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
    <title>修改账目</title>
    <script type="text/javascript">
        var purposeInData = $.parseJSON('${purposeInList}');

        $(function () {

            $("#datetabIn").calendar();

            $("#accounttabIn").select({
                title: "选择账户",
                items: $.parseJSON('${accountList}'),
                onOpen: function () {
                    $("#footerBar").hide();
                },
                onClose: function () {
                    $("#footerBar").show();
                }
            });

            $("#usePersontabIn").select({
                title: "选择消费者",
                multi: true,
                items: $.parseJSON('${payUserList}'),
                onOpen: function () {
                    $("#footerBar").hide();
                },
                onClose: function () {
                    $("#footerBar").show();
                }
            });

            $("#usefulltabIn").purposeIn({
                title: "请选择用途",
                showDistrict: false,
                onClose: function (e) {
                    var cols = e.cols;
                    var oneIndex = cols[0].activeIndex;
                    var towIndex = cols[1].activeIndex;
                    var oneObj = purposeInData[oneIndex];
                    var sub = oneObj.sub;
                    var towObj = sub[towIndex];
                    var id = towObj.id;
                    $("#purposeTypetabIn").val(id);
                }
            });

            $(".weui_btn").click(function (e) {
                var money = $("#moneytabIn").val();
                var date = $("#datetabIn").val();
                var account = $("#accounttabIn").attr("data-values");
                var purpose = $("#purposeTypetabIn").val();
                var payUser = $("#usePersontabIn").attr("data-values");
                var remark = $("#remark").val();

                if (isNaN(parseFloat(money)) || parseFloat(money) <= 0) {
                    $.alert("请输入金额！", "提示");
                    return;
                }
                if (isNaN(parseFloat(date)) || parseFloat(date) <= 0) {
                    $.alert("请选择日期！", "提示");
                    return;
                }
                if (isNaN(parseFloat(account)) || parseFloat(account) <= 0) {
                    $.alert("请选择账号！", "提示");
                    return;
                }
                if (isNaN(parseFloat(purpose)) || parseFloat(purpose) <= 0) {
                    $.alert("请选择用途！", "提示");
                    return;
                }
                if (isNaN(parseFloat(payUser)) || parseFloat(payUser) <= 0) {
                    $.alert("请选择消费者！", "提示");
                    return;
                }
                ajaxJS('${ctx}/tally/updateTally', {
                    id:'${tally.id}',
                    money: money,
                    payDate: date,
                    accountId: account,
                    purposeId: purpose,
                    payUserIds: payUser,
                    remark: remark,
                    purposeType:'${tally.purposeType}',
                    tallyNo:'${tally.tallyNo}'
                }, false, "post", function (result) {
                    if (result.success) {
                        //成功
                        $.modal({
                            title: "提示",
                            text: result.msg,
                            buttons: [
                                {
                                    text: "查看明细",
                                    onClick: function () {
                                        window.location.href = "${ctx}/index/list?tabbar=0";
                                    }
                                },
                                {
                                    text: "再记一笔",
                                    onClick: function () {
                                        window.location.href = "${ctx}/index/tally?tabbar=1";
                                    }
                                }
                            ]
                        });
                    } else {
                        showError(result.msg);
                    }
                });
            });
        });
    </script>
    <script type="text/javascript" src="${ctx}/js/biz/purposeIn.js"></script>
</head>
<body>
<div class="weui_cells weui_cells_form" style="margin-top: 0px">
    <div class="weui_cell">
        <div class="weui_cell_hd"><label class="weui_label">金额</label></div>
        <div class="weui_cell_bd weui_cell_primary">
            <input class="weui_input" type="number" placeholder="请输入金额" id="moneytabIn" value="${tally.money}">
        </div>
    </div>
    <div class="weui_cell">
        <div class="weui_cell_hd"><label class="weui_label">日期</label></div>
        <div class="weui_cell_bd weui_cell_primary">
            <input class="weui_input" id="datetabIn" type="text" data-toggle='date' value="${tally.payDate}"/>
        </div>
    </div>
    <div class="weui_cell">
        <div class="weui_cell_hd"><label class="weui_label">账户</label></div>
        <div class="weui_cell_bd weui_cell_primary">
            <input class="weui_input" id="accounttabIn" type="text" value="${tally.accountName}" data-values="${tally.accountId}"/>
        </div>
    </div>
    <div class="weui_cell">
        <div class="weui_cell_hd"><label class="weui_label">用途</label></div>
        <div class="weui_cell_bd weui_cell_primary">
            <input class="weui_input" id="usefulltabIn" type="text" value="${tally.purposeName}"/>
        </div>
    </div>
    <div class="weui_cell">
        <div class="weui_cell_hd"><label class="weui_label">消费者</label></div>
        <div class="weui_cell_bd weui_cell_primary">
            <input class="weui_input" id="usePersontabIn" type="text" value="${tally.payUserNames}" data-values="${tally.payUserIds}"/>
        </div>
    </div>
</div>
<div class="weui_cells_title">备注</div>
<div class="weui_cells weui_cells_form">
    <div class="weui_cell">
        <div class="weui_cell_bd weui_cell_primary">
            <textarea id="remark" class="weui_textarea" placeholder="请输入评论" rows="3">${tally.remark}</textarea>
            <div class="weui_textarea_counter"><span>0</span>/200</div>
        </div>
    </div>
</div>
<a href="javascript:;" class="weui_btn weui_btn_primary">保 存</a>
<input type="hidden" value="${tally.purposeId}" id="purposeTypetabIn">
<!-- 底部导航 -->
<div id="footerBar">
    <jsp:include page="../common/footer.jsp"></jsp:include>
</div>
<!-- 底部导航 -->
</body>
</html>
