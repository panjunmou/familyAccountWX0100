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
    <title>新增账目</title>
    <script type="text/javascript">
        var purposeInData = $.parseJSON('${purposeInList}');

        var purposeOutData = $.parseJSON('${purposeOutList}');

        var accountData = $.parseJSON('${accountList}');

        var payUserData = $.parseJSON('${payUserList}');

        $(function () {

            $("#datetabIn").calendar();
            $("#datetabOut").calendar();

            $("#accounttabIn").select({
                title: "选择账户",
                items: accountData,
                onOpen: function () {
                    $("#footerBar").hide();
                },
                onClose: function () {
                    $("#footerBar").show();
                }
            });

            $("#accounttabOut").select({
                title: "选择账户",
                items: accountData,
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
                items: payUserData,
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

            $("#usefulltabOut").purposeOut({
                title: "请选择用途",
                showDistrict: false,
                onClose: function (e) {
                    var cols = e.cols;
                    var oneIndex = cols[0].activeIndex;
                    var towIndex = cols[1].activeIndex;
                    var oneObj = purposeOutData[oneIndex];
                    var sub = oneObj.sub;
                    var towObj = sub[towIndex];
                    var id = towObj.id;
                    $("#purposeTypetabOut").val(id);
                }
            });

            $(".weui_btn").click(function (e) {
                var tabId = $(".weui_tab_bd_item_active").attr("id");
                var money = $("#money" + tabId).val();
                var date = $("#date" + tabId).val();
                var account = $("#account" + tabId).attr("data-values");
                var purpose = $("#purposeType" + tabId).val();
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
                if (tabId == "tabIn") {
                    if (isNaN(parseFloat(payUser)) || parseFloat(payUser) <= 0) {
                        $.alert("请选择消费者！", "提示");
                        return;
                    }
                }
                ajaxJS('${ctx}/tally/saveTally', {
                    tabId: tabId,
                    money: money,
                    payDate: date,
                    accountId: account,
                    purposeId: purpose,
                    payUserIds: payUser,
                    remark: remark
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

            //初始化支出
            //账户
            $("#accounttabIn").attr("data-values", accountData[0].value);
            $("#accounttabIn").attr("value", accountData[0].title);

            //消费者
            $("#usePersontabIn").attr("data-values", payUserData[0].value);
            $("#usePersontabIn").attr("value", payUserData[0].title);

            //用途
            $("#usefulltabIn").val(purposeInData[0].name + " " + purposeInData[0].sub[0].name);
            $("#purposeTypetabIn").val(purposeInData[0].sub[0].id);
            //初始化支出

            //初始化收入
            //账户
            $("#accounttabOut").attr("data-values", accountData[0].value);
            $("#accounttabOut").attr("value", accountData[0].title);

            //用途
            $("#usefulltabOut").val(purposeOutData[0].name + " " + purposeOutData[0].sub[0].name);
            $("#purposeTypetabOut").val(purposeOutData[0].sub[0].id);
            //初始化收入
        });
    </script>
    <script type="text/javascript" src="${ctx}/js/jquery/purposeIn.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/purposeOut.js"></script>
</head>
<body>
<div class="weui_tab">
    <div class="weui_navbar">
        <a href="#tabIn" class="weui_navbar_item weui_bar_item_on">
            支出
        </a>
        <a href="#tabOut" class="weui_navbar_item">
            收入
        </a>
    </div>
    <div class="weui_tab_bd">
        <div id="tabIn" class="weui_tab_bd_item weui_tab_bd_item_active">
            <div class="weui_cells weui_cells_form" style="margin-top: 0px">
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">金额</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" type="number" placeholder="请输入金额" id="moneytabIn">
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">日期</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" id="datetabIn" type="text" data-toggle='date' value="${today}"/>
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">账户</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" id="accounttabIn" type="text" value=""/>
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">用途</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" id="usefulltabIn" type="text" value=""/>
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">消费者</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" id="usePersontabIn" type="text" value=""/>
                    </div>
                </div>
            </div>
        </div>
        <div id="tabOut" class="weui_tab_bd_item">
            <div class="weui_cells weui_cells_form" style="margin-top: 0px">
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">金额</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" type="number" placeholder="请输入金额" id="moneytabOut">
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">日期</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" id="datetabOut" type="text" data-toggle='date' value="${today}"/>
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">账户</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" id="accounttabOut" type="text" value=""/>
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">用途</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" id="usefulltabOut" type="text" value=""/>
                    </div>
                </div>
            </div>
        </div>

        <div class="weui_cells_title">备注</div>
        <div class="weui_cells weui_cells_form">
            <div class="weui_cell">
                <div class="weui_cell_bd weui_cell_primary">
                    <textarea id="remark" class="weui_textarea" placeholder="请输入评论" rows="3"></textarea>
                    <div class="weui_textarea_counter"><span>0</span>/200</div>
                </div>
            </div>
        </div>
        <a href="javascript:;" class="weui_btn weui_btn_primary">保 存</a>
        <input type="hidden" value="" id="purposeTypetabIn">
        <input type="hidden" value="" id="purposeTypetabOut">
    </div>
</div>
<!-- 底部导航 -->
<div id="footerBar">
    <jsp:include page="../common/footer.jsp"></jsp:include>
</div>
<!-- 底部导航 -->
</body>
</html>
