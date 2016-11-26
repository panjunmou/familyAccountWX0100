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
    <title>首页</title>
    <script type="text/javascript">
        var purposeInData = $.parseJSON('${purposeInList}');
        var purposeOutData = $.parseJSON('${purposeOutList}');

        $(function () {

            $("#date").calendar();
            $("#date2").calendar();

            $("#account").select({
                title: "选择账户",
                items: $.parseJSON('${accountList}'),
                onOpen: function () {
                    $("#footerBar").hide();
                },
                onClose: function () {
                    $("#footerBar").show();
                }
            });

            $("#account2").select({
                title: "选择账户",
                items: $.parseJSON('${accountList}'),
                onOpen: function () {
                    $("#footerBar").hide();
                },
                onClose: function () {
                    $("#footerBar").show();
                }
            });

            $("#usePerson").select({
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

            $("#usefull").purposeIn({
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
                    $("[name='purposeTypeId']").val(id);
                }
            });

            $("#usefull2").purposeOut({
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
                    $("[name='purposeTypeId']").val(id);
                }
            });

            $(".weui_btn").click(function (e) {
                var amount = $("#intentionAmount").val();
                if (isNaN(parseFloat(amount)) || parseFloat(amount) <= 0) {
                    $.alert("请输入正确的意向金金额！", "提示");
                    return;
                }
                ajaxJS('${ctx}/get/intentionAdd', {
                    intentionAmount: $("#intentionAmount").val()
                }, false, "post", function (result) {
                    if (result.success) {//成功
                        //showSucc(result.msg);
                        $.alert({
                            title: '提示',
                            text: result.msg,
                            onOK: function () {
                                //点击确认
                                window.location.href = "${ctx}/wechat/intentionList";
                            }
                        });

                    } else {
                        showError(result.msg);
                    }
                });
            });
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
            <div class="weui_cells weui_cells_form">
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">金额</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" type="number" placeholder="请输入金额" id="money">
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">日期</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" id="date" type="text" data-toggle='date'/>
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">账户</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" id="account" type="text" value=""/>
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">用途</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" id="usefull" type="text" value=""/>
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">消费者</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" id="usePerson" type="text" value=""/>
                    </div>
                </div>
            </div>
        </div>
        <div id="tabOut" class="weui_tab_bd_item">
            <div class="weui_cells weui_cells_form">
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">金额</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" type="number" placeholder="请输入金额" id="money2">
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">日期</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" id="date2" type="text" data-toggle='date'/>
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">账户</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" id="account2" type="text" value=""/>
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">用途</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" id="usefull2" type="text" value=""/>
                    </div>
                </div>
            </div>
        </div>

        <div class="weui_cells_title">备注</div>
        <div class="weui_cells weui_cells_form">
            <div class="weui_cell">
                <div class="weui_cell_bd weui_cell_primary">
                    <textarea class="weui_textarea" placeholder="请输入评论" rows="3"></textarea>
                    <div class="weui_textarea_counter"><span>0</span>/200</div>
                </div>
            </div>
        </div>
        <a href="javascript:;" class="weui_btn weui_btn_primary">保 存</a>
        <input type="hidden" value="" name="purposeTypeId">
    </div>
</div>
<!-- 底部导航 -->
<div id="footerBar">
    <jsp:include page="../common/footer.jsp"></jsp:include>
</div>
<!-- 底部导航 -->
</body>
</html>
